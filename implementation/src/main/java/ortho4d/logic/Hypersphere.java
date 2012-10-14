package ortho4d.logic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collection;

import ortho4d.Logger;
import ortho4d.math.Vector;

/**
 * Represents a 4-sphere.<br>
 * Note that the methods setSize(), setColor() etc. may only be called if the
 * caller "knows" that the last cycle has ended.
 */
public final class Hypersphere implements Renderable {
	private static final boolean DEBUG = false, HIGH_DEBUG = false;

	private final Vector center = new Vector();
	private double size = 10, sqSize = 100;
	private Color color = Color.RED;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
		sqSize = size * size;
	}

	public void setCenter(Vector center) {
		this.center.set(center);
	}

	@Override
	public Vector getCenter() {
		return center;
	}

	@Override
	public double getRoughRadius() {
		return size;
	}

	@Override
	public void render(Canvas3D c3d) {
		final Collection<? extends Canvas2D> relevant = c3d
				.getRelevantCanvases(center.z - size, center.z + size);
		int count = 0;

		for (Canvas2D c : relevant) {
			final double intersectionZ;

			// In any case, display the biggest radius
			// Determine later if we're out-of-bounds
			if (c.getMaxZ() < center.z) {
				// Canvas does not contain center, and is "in front of" center
				// => use the one on the "maxZ" side
				intersectionZ = c.getMaxZ();
			} else if (c.getMinZ() > center.z) {
				// Canvas does not contain center, and is "behind of" center
				// => use the one on the "minZ" side
				intersectionZ = c.getMinZ();
			} else {
				// Canvas-Z-bounds lie on different sides of the center
				// => use our actual center
				intersectionZ = center.z;
			}

			final double zDiff = (center.z - intersectionZ);
			/**
			 * <pre>
			 *       +
			 *       |\   radius (of hypersphere)
			 *       | \     = this.size
			 * zDiff |  \
			 *       |   \
			 *       +----+
			 *       radius (of paint)
			 * </pre>
			 */
			final double sqRadius = size * size - zDiff * zDiff;

			if (sqRadius <= 0) {
				// Out of bounds, next one
				continue;
			}

			final double radius = Math.sqrt(sqRadius);

			// Yayy orthogonal projection
			final Graphics2D g = c.prepare();
			g.translate(center.x, center.y);
			g.setColor(color);
			g.scale(radius, radius);
			g.fillOval(-1, -1, 2, 2);

			if (HIGH_DEBUG) {
				Logger.println("radius=" + radius);
			}

			count = count + 1;
		}

		if (DEBUG) {
			Logger.println("Rendered on " + count + " canvases",
					this.toString());
		}
	}

	@Override
	public double getSquareRadius() {
		return sqSize;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Hypersphere@");
		center.append(sb);
		sb.append('(');
		sb.append(Double.toString(size));
		sb.append(')');
		return sb.toString();
	}
}
