package ortho4d.logic;

import java.util.concurrent.atomic.AtomicReference;

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
	 * simply updates this reference.<br>
	 * This only gets complicated because we have to make sure:
	 * <ul>
	 * <li>An object isn't updated after swapping</li>
	 * <li>Aaand: I want to re-use objects as often as possible.</li>
	 * </ul>
	 * Of course, each would be easy to achieve without the other.<br>
	 * Currently, exactly three Configuration instances are created. Ever.
	 * Except maybe the matrices, but that's not avoidable -- or is it?
	 */
	private final AtomicReference<C> nextConf;

	private C controlledConf;
	private C renderingConf;

	protected DynamicCamera(C initialConf) {
		controlledConf = initialConf;
		renderingConf = initialConf.copy();
		nextConf = new AtomicReference<C>(initialConf.copy());
	}

	@Override
	public final Vector getOrigin() {
		return renderingConf.getOrigin();
	}

	@Override
	public final Matrix getMatrix() {
		return renderingConf.getMatrix();
	}

//	/**
//	 * Subclasses may override this to "finish up" and push their Configuration
//	 */
//	protected void finishConfig() {
//		// Nothing to do here
//	}

	@Override
	public final void cycleComplete() {
//		finishConfig();
		// Swap renderingConf <--> nextConf
		renderingConf = nextConf.getAndSet(renderingConf);
	}

	/**
	 * Swaps or "pushes" the changes on the controlledConfig "into" the
	 * rendering thread. To be called by the controller thread.
	 */
	protected final void swapInto() {
		// Swap controlledConf <--> nextConf
		C newControlledConf = nextConf.getAndSet(controlledConf);

		// But make sure that the data isn't lost
		// newControlledConf.setMatrix(controlledConf.getMatrix());
		newControlledConf.getOrigin().set(controlledConf.getOrigin());
		controlledConf = newControlledConf;
	}

	protected final C getControlledConfiguration() {
		return controlledConf;
	}
}
