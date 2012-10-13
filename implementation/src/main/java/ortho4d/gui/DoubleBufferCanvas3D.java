package ortho4d.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Collection;
import java.util.Collections;

import ortho4d.Logger;
import ortho4d.logic.Canvas2D;
import ortho4d.logic.SingleGraphicsCanvas3D;

public class DoubleBufferCanvas3D extends SingleGraphicsCanvas3D {
	private static final Collection<Canvas2D> EMPTY = Collections.emptyList();
	private static final boolean DEBUG = true;
	
	private final BufferStrategy bufferStrategy;
	private final int height;
	private final int width;
	
	private boolean buffersLost = false;

	public DoubleBufferCanvas3D(BufferStrategy bufferStrategy, int width, int height) {
		super(getGraphics(bufferStrategy));
		this.bufferStrategy = bufferStrategy;
		this.width = width;
		this.height = height;
		
		buffersLost = bufferStrategy.contentsLost();
	}

	@Override
	public Collection<? extends Canvas2D> getRelevantCanvases(double fromZ,
			double toZ) {
		if (buffersLost) {
			return EMPTY;
		} else {
			return super.getRelevantCanvases(fromZ, toZ);
		}
	}

	private static final Graphics2D getGraphics(BufferStrategy bufferStrategy) {
		Graphics g = bufferStrategy.getDrawGraphics();
		if (g instanceof Graphics2D) {
			return (Graphics2D) g;
		}
		throw new RuntimeException("Bad JVM: Cannot cast to Graphics2D"
				+ " (which is pretty much THE standard");
	}

	@Override
	public void cycleComplete() {
		if (!buffersLost) {
			doAfterpainting();
			bufferStrategy.show();
		}
		if (DEBUG) {
			if (bufferStrategy.contentsLost()) {
				Logger.println("DAMN contents lost :-(");
			}
		}
		getCurrentGraphics().dispose();

		buffersLost = bufferStrategy.contentsLost();
		if (!buffersLost) {
			Graphics2D g2d = getGraphics(bufferStrategy);
			super.useGraphics(g2d);
		}
	}

	public void dispose() {
		getCurrentGraphics().dispose();
		bufferStrategy.dispose();
	}

	@Override
	protected void prepareGraphics() {
		Graphics2D g = getCurrentGraphics();
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width+5, height+5);
		
		g.setColor(Color.RED);
		g.drawRect(0, 0, width-1, height-1);
	}
}
