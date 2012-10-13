package ortho4d.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Collection;
import java.util.Collections;

import ortho4d.logic.Canvas2D;
import ortho4d.logic.SingleGraphicsCanvas3D;

public class DoubleBufferCanvas3D extends SingleGraphicsCanvas3D {
	private static final Collection<Canvas2D> EMPTY = Collections.emptyList();
	private final BufferStrategy bufferStrategy;
	private boolean buffersLost = false;

	public DoubleBufferCanvas3D(BufferStrategy bufferStrategy) {
		super(getGraphics(bufferStrategy));
		this.bufferStrategy = bufferStrategy;
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
}
