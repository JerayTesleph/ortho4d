package ortho4d.gui;

import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import ortho4d.Logger;
import ortho4d.logic.CompoundEntity;
import ortho4d.logic.DebugEntity;
import ortho4d.logic.Entity;
import ortho4d.logic.GridSlicer;
import ortho4d.logic.Renderer;

public final class DemoStarter implements Runnable {
	private static final boolean CANVAS_DEBUG = true;
	
	/**
	 * The delay the Rendering thread must sleep per cycle. Note that you
	 * actually CAN set it to zero, which means "don't wait at all.". Also note
	 * that in this case, the EDT starves, and the applications feels
	 * unresponsive.
	 */
	private static final int DELAY_PER_FRAME = 100;

	private final List<Entity> rootEntities = new LinkedList<Entity>();
	private final long init = System.currentTimeMillis();

	public void addEntity(Entity e) {
		rootEntities.add(e);
	}

	@Override
	public void run() {
		Logger.println("EDT running after "
				+ (System.currentTimeMillis() - init));

		// Build GUI
		CameraController controller = new CameraController();
		InstantCanvasWindow win = new InstantCanvasWindow(1024, 768);
		controller.listenOn(win);

		if (CANVAS_DEBUG) {
			addEntity(new DebugEntity(win.getCanvas()));
		}

		// Run!
		Renderer r = new Renderer(win.getCanvas(), controller.getCamera(),
				rootEntities, DELAY_PER_FRAME);
		GridSlicer.addSlices(4, 4, win.getCanvas(), win.getBounds());
		win.addDisposeListener(r);

		// try {
		// Logger.println("Sleeping...");
		// Thread.sleep(3000);
		// } catch (InterruptedException e) {
		// // Nothing to do here
		// }
		// Logger.println("Awake");

		final long now = System.currentTimeMillis();
		Logger.println("Starting renderer after " + (now - init));
		Logger.println("Now it's " + now);
		new Thread(r).start();
	}

	public static void main(String[] args) {
		final DemoStarter d = new DemoStarter();

		// SimpleSphere c = new SimpleSphere(new Vector(0, 0, 0, 50));
		// c.setRadius(50);
		// c.setColor(Color.RED);
		// d.addEntity(new SphereEntity(c));

		d.addEntity(CompoundEntity.createCoordEntity());

		SwingUtilities.invokeLater(d);
	}
}
