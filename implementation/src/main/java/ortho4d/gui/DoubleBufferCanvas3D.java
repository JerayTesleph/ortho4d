package ortho4d.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import ortho4d.Logger;
import ortho4d.logic.Canvas2D;
import ortho4d.logic.SingleGraphicsCanvas2D;
import ortho4d.logic.SingleGraphicsCanvas3D;

public class DoubleBufferCanvas3D extends SingleGraphicsCanvas3D {
	private static final boolean VISUAL_DEBUG = true;
	private static final Collection<Canvas2D> EMPTY = Collections.emptyList();
	private static final boolean DEBUG = true;

	private final BufferStrategy bufferStrategy;
	private final int height;
	private final int width;

	private boolean buffersLost = false;

	public DoubleBufferCanvas3D(BufferStrategy bufferStrategy, int width,
			int height) {
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
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width + 5, height + 5);

		g.setColor(Color.RED);
		g.drawRect(0, 0, width - 1, height - 1);

		final Rectangle clip = new Rectangle();

		for (SingleGraphicsCanvas2D c2d : getAllCanvases()) {
			c2d.getClip(clip);

			if (VISUAL_DEBUG) {
				g.setColor(randomize(Color.WHITE));
				g.fillRect(clip.x, clip.y, clip.width + 5, clip.height + 5);
				g.setColor(randomize(Color.RED));
				g.drawRect(clip.x, clip.y, clip.width - 1, clip.height - 1);
			} else {
				g.setColor(Color.WHITE);
				g.fillRect(clip.x, clip.y, clip.width, clip.height);
			}
		}
	}

	private static final Color randomize(Color base) {
		int r = randomize(base.getRed());
		int g = randomize(base.getGreen());
		int b = randomize(base.getBlue());
		return new Color(r, g, b);
	}

	private static final Random RANDOM = new Random();

	private static final int randomize(int base) {
		base += RANDOM.nextInt(50) - 25;
		base = Math.min(255, base);
		base = Math.max(0, base);
		return base;
	}
}
