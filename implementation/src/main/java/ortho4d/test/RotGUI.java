package ortho4d.test;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BoundedRangeModel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ortho4d.test.CoordinatePreview.Coordinate;
import ortho4d.test.CoordinatePreview.RevertingCoordinate;

public final class RotGUI implements ChangeListener {
	private final RotPanel panel;
	private final CoordinatePreview prevXY, prevWZ;
	private final Calculator calc;

	public RotGUI(Calculator calc) {
		this.calc = calc;

		// Set up internal infrastructure
		this.panel = new RotPanel();
		prevXY = new CoordinatePreview();
		panel.setPreviewXY(prevXY);
		prevWZ = new CoordinatePreview();
		panel.setPreviewWZ(prevWZ);

		for (Coordinate c : calc.getCoordinates()) {
			prevXY.add(c);
			prevWZ.add(new RevertingCoordinate(c));
		}

		panel.getAlpha().addChangeListener(this);
		panel.getBeta().addChangeListener(this);
		panel.getGamma().addChangeListener(this);

		// Build frame
		JFrame win = new JFrame("Actual 4D rotation");
		win.setContentPane(panel);
		win.pack();
		Dimension dim = win.getPreferredSize();
		dim.width += 50;
		dim.height += 50;
		win.setPreferredSize(dim);
		win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Force first update
		stateChanged(null);

		// Let the games begin!
		win.setVisible(true);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		final double alpha = fiddle(panel.getAlpha());
		final double beta = fiddle(panel.getBeta());
		final double gamma = fiddle(panel.getGamma());
		calc.plug(alpha, beta, gamma);
		prevXY.repaint();
		prevWZ.repaint();
	}

	private static final double fiddle(BoundedRangeModel model) {
		return Math.toRadians(model.getValue());
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new RotGUI(new SingleWCalculator());
			}
		});
	}

	public static interface Calculator {
		void plug(double alpha, double beta, double gamma);

		List<Coordinate> getCoordinates();
	}
}
