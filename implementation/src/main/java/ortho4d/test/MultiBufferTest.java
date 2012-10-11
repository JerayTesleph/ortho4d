package ortho4d.test;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;

public class MultiBufferTest {
	private static Color[] COLORS = new Color[] { Color.red, Color.blue,
			Color.green, Color.white, Color.black, Color.yellow, Color.gray,
			Color.cyan, Color.pink, Color.lightGray, Color.magenta,
			Color.orange, Color.darkGray };
	private static DisplayMode[] BEST_DISPLAY_MODES = new DisplayMode[] {
			new DisplayMode(640, 480, 32, 0), new DisplayMode(640, 480, 16, 0),
			new DisplayMode(640, 480, 8, 0) };

	Frame mainFrame;

	public MultiBufferTest(int numBuffers, GraphicsDevice device) {
		System.out.println("Using device " + device);
		try {
			GraphicsConfiguration gc = device.getDefaultConfiguration();
			System.out.println("Using config " + gc);
			mainFrame = new Frame(gc);
			mainFrame.setResizable(false);
			mainFrame.setUndecorated(true);
			mainFrame.setIgnoreRepaint(true);
			mainFrame.setSize(640, 480);
			mainFrame.setVisible(true);
//			device.setFullScreenWindow(mainFrame);
//			if (device.isDisplayChangeSupported()) {
//				chooseBestDisplayMode(device);
//			} else {
//				System.out.println("Change not supported");
//			}
			Rectangle bounds = mainFrame.getBounds();
			System.out.println("bounds = " + bounds);
			mainFrame.createBufferStrategy(numBuffers);
			BufferStrategy bufferStrategy = mainFrame.getBufferStrategy();
			System.out.println("Using strategy " + bufferStrategy);
			for (float lag = 2000.0f; lag > 0.0000001f; lag = lag / 1.25f) {
				for (int i = 0; i < numBuffers; i++) {
					Graphics g = bufferStrategy.getDrawGraphics();
					if (!bufferStrategy.contentsLost()) {
						g.setColor(COLORS[i]);
						g.fillRect(0, 0, bounds.width, bounds.height);
						g.setColor(Color.WHITE);
						g.drawRect(5, 5, 630, 470);
						bufferStrategy.show();
						g.dispose();
					}
					try {
						Thread.sleep((int) lag);
					} catch (InterruptedException e) {
						System.out.println("Interrupted?!");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			device.setFullScreenWindow(null);
		}
	}

	private static DisplayMode getBestDisplayMode(GraphicsDevice device) {
		DisplayMode[] modes = device.getDisplayModes();
		System.out.println("Multi-bit-mode = " + DisplayMode.BIT_DEPTH_MULTI);
		for (DisplayMode m : modes) {
			System.out.println(m);
			System.out.println("\t" + m.getWidth() + "x" + m.getHeight() + "x"
					+ m.getBitDepth() + "@" + m.getRefreshRate());
		}
		for (int x = 0; x < BEST_DISPLAY_MODES.length; x++) {
			for (int i = 0; i < modes.length; i++) {
				boolean okay = modes[i].getBitDepth() == BEST_DISPLAY_MODES[x].getBitDepth();
				okay = okay || modes[i].getBitDepth() == DisplayMode.BIT_DEPTH_MULTI;
				okay = okay && modes[i].getWidth() == BEST_DISPLAY_MODES[x].getWidth();
				okay = okay && modes[i].getHeight() == BEST_DISPLAY_MODES[x].getHeight();
				if (okay) {
					return modes[i];
				}
			}
		}
		return null;
	}

	public static void chooseBestDisplayMode(GraphicsDevice device) {
		DisplayMode best = getBestDisplayMode(device);
		if (best != null) {
			device.setDisplayMode(best);
			System.out.println("Setting display mode to " + best);
		} else {
			System.out.println("No good ones found");
		}
	}

	public static void main(String[] args) {
		try {
//			int numBuffers = 2;
//			if (args != null && args.length > 0) {
//				numBuffers = Integer.parseInt(args[0]);
//				if (numBuffers < 2 || numBuffers > COLORS.length) {
//					System.err.println("Must specify between 2 and "
//							+ COLORS.length + " buffers");
//					System.exit(1);
//				}
//			}
			GraphicsEnvironment env = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			GraphicsDevice device = env.getDefaultScreenDevice();
			new MultiBufferTest(2, device);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}
