package ortho4d.gui;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

public class InstantCanvasWindow extends JFrame {
	private static final int MULTI_BUFFER_COUNT = 2;
	private static final long serialVersionUID = 2558753416113591206L;
	private final DoubleBufferCanvas3D canvas;
	private final List<DisposeListener> disposables = new LinkedList<DisposeListener>();

	public InstantCanvasWindow(int width, int height) {
		setResizable(false);
		setUndecorated(true);
		setIgnoreRepaint(true);
		setSize(width, height);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// device.setFullScreenWindow(mainFrame);
		// if (device.isDisplayChangeSupported()) {
		// chooseBestDisplayMode(device);
		// } else {
		// System.out.println("Change not supported");
		// }

		createBufferStrategy(MULTI_BUFFER_COUNT);
		canvas = new DoubleBufferCanvas3D(getBufferStrategy());
	}

	public DoubleBufferCanvas3D getCanvas() {
		return canvas;
	}

	public void addDisposeListener(DisposeListener l) {
		disposables.add(l);
	}

	public void removeDisposeListener(DisposeListener l) {
		disposables.remove(l);
	}

	@Override
	public void dispose() {
		super.dispose();
		canvas.dispose();
		for (DisposeListener l : disposables) {
			l.dispose();
		}
	}
}
