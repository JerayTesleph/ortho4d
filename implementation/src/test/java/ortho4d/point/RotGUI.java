package ortho4d.point;

import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoundedRangeModel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ortho4d.math.Vector;
import ortho4d.point.CoordinatePreview.ColoredSphere;
import ortho4d.point.CoordinatePreview.CoordRevertingSphere;
import ortho4d.point.CoordinatePreview.SimpleSphere;

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

		for (ColoredSphere c : calc.getCoordinates()) {
			prevXY.add(c);
			prevWZ.add(new CoordRevertingSphere(c));
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
			private final Calculator buildCalculator() {
//				return new SingleWCalculator();
				
				List<SimpleSphere> coordinates = new LinkedList<SimpleSphere>();
				SimpleSphere c;
				
				c = new SimpleSphere(new Vector(1, 0, 0, 0));
				c.setColor(Color.RED);
				coordinates.add(c);
				
				c = new SimpleSphere(new Vector(0, 1, 0, 0));
				c.setColor(Color.GREEN);
				coordinates.add(c);
				
				c = new SimpleSphere(new Vector(0, 0, 1, 0));
				c.setColor(Color.BLUE);
				coordinates.add(c);
				
				c = new SimpleSphere(new Vector(0, 0, 0, 1));
				c.setColor(Color.ORANGE);
				coordinates.add(c);
				
				return new MatrixCalculator(coordinates);
			}
			
			@Override
			public void run() {
				Calculator c = buildCalculator();
				new RotGUI(c);
			}
		});
	}

	public static interface Calculator {
		void plug(double alpha, double beta, double gamma);

		List<? extends ColoredSphere> getCoordinates();
	}
}
