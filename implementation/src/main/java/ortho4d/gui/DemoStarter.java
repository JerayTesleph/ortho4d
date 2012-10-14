package ortho4d.gui;

import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import ortho4d.Logger;
import ortho4d.logic.DebugEntity;
import ortho4d.logic.Entity;
import ortho4d.logic.GridSlicer;
import ortho4d.logic.Renderer;

public final class DemoStarter implements Runnable {
	private static final boolean CANVAS_DEBUG = true;

	private final List<Entity> rootEntities = new LinkedList<Entity>();

	public void addEntity(Entity e) {
		rootEntities.add(e);
	}

	@Override
	public void run() {
		// Build GUI
		CameraController controller = new CameraController();
		InstantCanvasWindow win = new InstantCanvasWindow(800, 600);
		controller.listenOn(win);

		if (CANVAS_DEBUG) {
			addEntity(new DebugEntity(win.getCanvas()));
		}

		// Run!
		Renderer r = new Renderer(win.getCanvas(), controller.getCamera(),
				rootEntities, 500);
		GridSlicer.addSlices(2, 2, win.getCanvas(), win.getBounds());
		win.addDisposeListener(r);

		try {
			Logger.println("Sleeping...");
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// Nothing to do here
		}
		Logger.println("Awake");

		new Thread(r).start();
	}

	public static void main(String[] args) {
		final DemoStarter d = new DemoStarter();

		// SimpleSphere c = new SimpleSphere(new Vector(0, 0, 0, 50));
		// c.setRadius(50);
		// c.setColor(Color.RED);
		// d.addEntity(new SphereEntity(c));

		// d.addEntity(CompoundEntity.createCoordEntity());

		SwingUtilities.invokeLater(d);
	}
}
