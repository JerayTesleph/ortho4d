package ortho4d.gui;

import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import ortho4d.logic.CompoundEntity;
import ortho4d.logic.Entity;
import ortho4d.logic.GridSlicer;
import ortho4d.logic.Renderer;

public final class DemoStarter implements Runnable {
	private final List<Entity> rootEntities = new LinkedList<Entity>();
	
	public void addEntity(Entity e) {
		rootEntities.add(e);
	}
	
	@Override
	public void run() {
		// Build GUI
		CameraController controller = new CameraController();
		InstantCanvasWindow win = new InstantCanvasWindow(640, 480);
		controller.listenOn(win);

		// Run!
		Renderer r = new Renderer(win.getCanvas(), controller.getCamera(),
				rootEntities, 1000);
		GridSlicer.addSlices(4, 3, win.getCanvas(), win.getBounds());
		win.addDisposeListener(r);
		new Thread(r).start();
	}

	public static void main(String[] args) {
		final DemoStarter d = new DemoStarter();
		d.addEntity(CompoundEntity.createCoordEntity());
		SwingUtilities.invokeLater(d);
	}
}
