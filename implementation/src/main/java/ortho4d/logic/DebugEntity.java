package ortho4d.logic;

import java.awt.Color;
import java.awt.Graphics2D;

import ortho4d.math.Vector;

public class DebugEntity implements Entity, Renderable {
	private static final Color MARKER_COLOR = Color.DARK_GRAY;
	private static final int MARKER_SIZE = 5;
	
	private static final Vector ORIGN = new Vector();

	private final SingleGraphicsCanvas3D canvas;

	public DebugEntity(SingleGraphicsCanvas3D canvas) {
		this.canvas = canvas;
	}

	@Override
	public void registerRenderables(RenderableQueue q, Camera c) {
		q.add(this);
	}

	@Override
	public Vector getCenter() {
		return ORIGN;
	}

	@Override
	public double getRoughRadius() {
		return 1;
	}

	@Override
	public double getSquareRadius() {
		return 1;
	}

	@Override
	public void render(Canvas3D c) {
		if (c != canvas) {
			throw new RuntimeException("May not be used for multiple canvases!");
		}
		
		for (SingleGraphicsCanvas2D c2d : canvas.getAllCanvases()) {
			Graphics2D g = c2d.prepare();
			
			g.setColor(MARKER_COLOR);
			g.drawLine(-MARKER_SIZE, 0, MARKER_SIZE, 0);
			g.drawLine(0, -MARKER_SIZE, 0, MARKER_SIZE);
			
			g.setColor(Color.BLACK);
			g.drawString(c2d.getDebugInfo(), 2, -2);
		}
	}
}
