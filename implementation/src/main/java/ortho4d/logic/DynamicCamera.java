package ortho4d.logic;

import java.util.concurrent.atomic.AtomicStampedReference;

import ortho4d.math.Matrix;
import ortho4d.math.Vector;

/**
 * THis class provides a framework to easily interact with the Camera
 * configuration, although the rendering thread has very strict requirements
 * about the behavior of an instance of class Camera.<br>
 * Subclasses can freely change and modify the "controlledConfiguration", but
 * have to make sure that they do not so while swapInto() is being called. They
 * must also assume that the instance representing the controlledConfiguration
 * has changed after swapInto().<br>
 * <br>
 * Implementation note: This class takes care to create as few instances as
 * possible, and uses lock-free structures to communicate the Configuration
 * objects to achieve performance -- and yes, I know, this is only called at
 * most 70 times per second, so it doesn't really matter after all.
 */
public abstract class DynamicCamera<C extends Configuration<C>> implements
		Camera {
	/**
	 * The main communication interface between the rendering thread and the
	 * controller thread. If the controller wants to "push" his changes, he
	 * simply updates this reference, and sets the stamp to "1".<br>
	 * The renderer swaps its instance if and only if the stamp is "1", and sets
	 * the stamp to "0". This is to prevent "jumping forth and back", when the
	 * camera has moved, but the controller isn't updating things anymore.<br>
	 * <br>
	 * This implementation also achieves these two goals:
	 * <ul>
	 * <li>An object may not be modified after swapping</li>
	 * <li>I want to re-use objects as often as possible.</li>
	 * </ul>
	 */
	private final AtomicStampedReference<C> nextConf;

	private C controlledConf;
	private C renderingConf;

	protected DynamicCamera(C initialConf) {
		controlledConf = initialConf;
		renderingConf = initialConf.copy();
		nextConf = new AtomicStampedReference<C>(initialConf.copy(), 0);
	}

	@Override
	public final Vector getOrigin() {
		return renderingConf.getOrigin();
	}

	@Override
	public final Matrix getMatrix() {
		return renderingConf.getMatrix();
	}

	@Override
	public final Matrix getInverseMatrix() {
		return renderingConf.getInverseMatrix();
	}

	@Override
	public final void cycleComplete() {
		if (nextConf.getStamp() == 0) {
			// current data is newer than nextConf
			// old data is uninteresting
			return;
		}

		// Swap renderingConf <--> nextConf
		// Since we now know that there is "new" data available

		C nextConfContent;
		do {
			nextConfContent = nextConf.getReference();
		} while (!nextConf.compareAndSet(nextConfContent, renderingConf, 1, 0));

		renderingConf = nextConfContent;
	}

	/**
	 * Swaps or "pushes" the changes on the controlledConfig "into" the
	 * rendering thread. To be called by the controller thread.
	 */
	protected final void swapInto() {
		// Swap controlledConf <--> nextConf
		// (unconditionally)

		C nextConfContent;
		int nextConfStamp;
		do {
			nextConfContent = nextConf.getReference();
			// If the data gets out-of-data mid-air, we'll notice
			nextConfStamp = nextConf.getStamp();
		} while (!nextConf.compareAndSet(nextConfContent, controlledConf,
				nextConfStamp, 1));

		// But make sure that the data isn't lost
		// May access data since renderer may not modify CONTENT of this object
		// ... even if the renderer already has swapped objects and is using
		// this right now:
		nextConfContent.setMatrices(controlledConf);
		nextConfContent.getOrigin().set(controlledConf.getOrigin());

		controlledConf = nextConfContent;
	}

	protected final C getControlledConfiguration() {
		return controlledConf;
	}
}
