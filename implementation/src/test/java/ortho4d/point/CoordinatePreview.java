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
	private final List<Coordinate> coordinates = new LinkedList<Coordinate>();

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

	public void add(Coordinate v) {
		coordinates.add(v);
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
		for (Coordinate c : coordinates) {
			g2d.setTransform(centered);
			g2d.setColor(c.getColor());
			Vector v = c.getVector();
			g2d.translate(scaling * v.x, scaling * -v.y);
			g2d.drawOval(-c.getRadius() / 2, -c.getRadius() / 2, c.getRadius(),
					c.getRadius());
		}

		g2d.setTransform(at);
	}

	public static interface Coordinate {
		public abstract Vector getVector();

		// Should be divisible by 2, otherwise the display is 1 pixel off
		public abstract int getRadius();

		public abstract Color getColor();
	}
	
	public static abstract class BackedCoordinate implements Coordinate {
		protected final Coordinate backing;

		public BackedCoordinate(Coordinate backing) {
			this.backing = backing;
		}

		@Override
		public int getRadius() {
			return backing.getRadius();
		}

		@Override
		public Color getColor() {
			return backing.getColor();
		}
	}

	public static final class RevertingCoordinate extends BackedCoordinate {
		private final Vector dummy = new Vector();

		public RevertingCoordinate(Coordinate backing) {
			super(backing);
		}

		@Override
		public Vector getVector() {
			Vector actual = backing.getVector();
			// Typo in the description
			// => To stay consistent, W is displayed on the X-axis
			dummy.x = actual.w;
			dummy.y = actual.z;
			return dummy;
		}

		@Override
		public int getRadius() {
			return backing.getRadius();
		}

		@Override
		public Color getColor() {
			return backing.getColor();
		}
	}

	public static final class SimpleCoordinate implements Coordinate {
		private Color c = new Color(128, 0, 0);
		// Should be divisible by 2, otherwise the display is 1 pixel off
		private int radius = 6;
		private final Vector v;

		public SimpleCoordinate(Vector v) {
			this.v = v;
		}

		@Override
		public Color getColor() {
			return c;
		}

		public void setColor(Color c) {
			this.c = c;
		}

		@Override
		public int getRadius() {
			return radius;
		}

		public void setRadius(int radius) {
			this.radius = radius;
		}

		@Override
		public Vector getVector() {
			return v;
		}
	}
}
