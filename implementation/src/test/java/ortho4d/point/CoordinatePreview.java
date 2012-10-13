package ortho4d.point;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

import ortho4d.math.Vector;

public class CoordinatePreview extends JComponent {
	private static final int PADDING = 2, CENTER_SIZE = 2;
	private static final long serialVersionUID = 8106381354063819837L;

	private final double scaling;
	private final int roundedUpRadius;
	private final List<ColoredSphere> coloredSpheres = new LinkedList<ColoredSphere>();

	public CoordinatePreview() {
		this(100);
	}

	public CoordinatePreview(double scaling) {
		this.scaling = scaling;
		roundedUpRadius = ((int) Math.ceil(scaling)) + PADDING;
		Dimension dim = new Dimension(roundedUpRadius * 2, roundedUpRadius * 2);
		setMinimumSize(dim);
		setPreferredSize(dim);
	}

	public void add(ColoredSphere v) {
		coloredSpheres.add(v);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, roundedUpRadius * 2, roundedUpRadius * 2);

		AffineTransform at = g2d.getTransform();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.translate(roundedUpRadius, roundedUpRadius);

		// Draw borders
		g2d.setColor(Color.BLACK);
		g2d.drawOval(-roundedUpRadius, -roundedUpRadius,
				roundedUpRadius * 2 - 1, roundedUpRadius * 2 - 1);

		// Draw "center"
		g2d.drawLine(0, -CENTER_SIZE, 0, CENTER_SIZE);
		g2d.drawLine(-CENTER_SIZE, 0, CENTER_SIZE, 0);

		// Draw marker
		AffineTransform centered = g2d.getTransform();
		for (ColoredSphere c : coloredSpheres) {
			g2d.setTransform(centered);
			g2d.setColor(c.getColor());
			Vector v = c.getVector();
			g2d.translate(scaling * v.x, scaling * -v.y);
			g2d.drawOval(-c.getRadius() / 2, -c.getRadius() / 2, c.getRadius(),
					c.getRadius());
		}

		g2d.setTransform(at);
	}
}
