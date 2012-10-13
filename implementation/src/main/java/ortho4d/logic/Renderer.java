package ortho4d.logic;

import java.util.LinkedList;
import java.util.List;

import ortho4d.Logger;
import ortho4d.gui.DisposeListener;

/**
 * This is the renderer, which can do a single step of "rendering" at a time.<br>
 * <br>
 * Due to Swing, there will most definitely be the problem of two threads
 * simultaneously trying to access the data structures: The thread polling for
 * mouse-/keyboard-events, and the rendering thread. And, maybe, once upon a
 * time in the future, the networking thread.<br>
 * To make sure that all the page flipping, data copying etc. remains
 * consistent, and something like
 * "half the page was rendered using Camera A, the other half with Camera B"
 * (the user rotated and shifted the camera while the rendering is in progress)
 * doesn't happen, each involved method has a clearly defined description which
 * thread may call each method when and how the return values may change during
 * flow of time.<br>
 * This directly implies that one thread must be a dedicated "Rendering Thread",
 * that only cares about the work of rendering. This thread may call into
 * doCycle() and therefore trigger all the rendering.
 */
public class Renderer implements Runnable, DisposeListener {
	private static final boolean DEBUG = true;
	
	private final Canvas3D canvas;
	private final Camera camera;
	private final RenderableQueue queue;
	private final List<Entity> rootEntities;
	private final int delay;

	private volatile boolean haltAfterCycle = false;

	/**
	 * Creates a new Renderer, using the provided structures.<br>
	 * Note that there is no need for methods like addEntity() etc, since you
	 * could build an Entity that emulates this kind of behavior as efficient
	 * (if not even more efficient, since the "parent" entity could do a
	 * bounding sphere check first)<br>
	 * Constructors may (usually) be called from any thread, unlike the Swing
	 * libraries.<br>
	 * Also note that it makes no sense to involve more than one canvas (see
	 * getRelevantCanvases()) or camera (cross-eyed rendering?).
	 * 
	 * @param canvas
	 *            the canvas which will be used for all the painting
	 * @param camera
	 *            the camera which will be polled regularly
	 * @param rootEntities
	 *            the "root" entities used for rendering. Note that you can
	 *            easily write custom, efficient Entity containers that can
	 *            addEntity() and stuff
	 * @param delay
	 *            the amount of milliseconds to wait between cycles, or
	 *            non-positive to "not wait at all"
	 */
	public Renderer(Canvas3D canvas, Camera camera, List<Entity> rootEntities,
			int delay) {
		this.canvas = canvas;
		this.camera = camera;
		this.delay = delay;
		this.rootEntities = new LinkedList<Entity>(rootEntities);
		queue = canvas.createQueue();
	}

	/**
	 * Initiates and runs a full cycle, calling the appropriate cycleComplete()
	 * method when finished and returns.
	 */
	public void doCycle() {
		// Fill
		for (Entity e : rootEntities) {
			e.registerRenderables(queue, camera);
		}

		// Paint
		for (Renderable r : queue) {
			r.render(canvas);
		}

		canvas.cycleComplete();
		camera.cycleComplete();
		queue.clear();
		
		if (DEBUG) {
			Logger.println("Cycle complete");
		}
	}

	@Override
	public void run() {
		while (!haltAfterCycle) {
			doCycle();
			if (delay > 0) {
				yield(delay);
			}
		}
	}
	
	@Override
	public void dispose() {
		haltAfterCycle = true;
	}

	private static final void yield(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			System.out.println("Interrupted?!");
		}
	}
}
