package ortho4d.logic;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import ortho4d.Logger;
import ortho4d.math.Vector;

public final class SingleGraphicsCanvas2D implements Canvas2D {
	private static final boolean DEBUG = true;
	private static final AffineTransform IDENTITY = new AffineTransform();

	private final Rectangle2D clip;
	private final Rectangle2D relevant;
	private final AffineTransform transform;
	private final double minZ, maxZ;
	private final String debugInfo;

	private Graphics2D g;

	public SingleGraphicsCanvas2D(Rectangle2D clip, Rectangle2D relevant,
			double minZ, double maxZ, Graphics2D g, String debugInfo) {
		this.clip = clip;
		this.relevant = relevant;
		this.minZ = minZ;
		this.maxZ = maxZ;
		this.g = g;
		this.debugInfo = debugInfo;

		transform = new AffineTransform(g.getTransform());
		if (DEBUG) {
			Logger.println("Dumping " + debugInfo + ":");
			Logger.println("z \\in [" + minZ + ", " + maxZ + "]");
			Logger.println(transform.toString());
		}
		applyTransform(transform, clip, relevant);
		if (DEBUG) {
			Logger.println(transform.toString());
			Logger.println("clip=" + clip.toString());
		}
	}

	public static final void applyTransform(AffineTransform transform,
			Rectangle2D clip, Rectangle2D relevant) {
		transform.concatenate(AffineTransform.getTranslateInstance(
				clip.getMinX(), clip.getMinY()));
		double xScale = clip.getWidth() / relevant.getWidth();
		double yScale = clip.getHeight() / relevant.getHeight();
		transform.concatenate(AffineTransform.getScaleInstance(xScale, yScale));
		transform.concatenate(AffineTransform.getTranslateInstance(
				-relevant.getMinX(), -relevant.getMinY()));
	}

	public void getRelevant(Rectangle2D into) {
		into.setRect(relevant);
	}

	public void getClip(Rectangle2D into) {
		into.setRect(clip);
	}

	@Override
	public Graphics2D prepare() {
		// setClip() operates on relative coordinates
		// No idea what Graphics2D is currently set to
		// this.clip is absolute
		// => Reset Transform
		// Both this.clip and this.transform DO consider potential
		// pre-transformations.
		// This took me about two whole long days to figure out.
		// Thank you, Swing.
		g.setTransform(IDENTITY);
		g.setClip(clip);
		g.setTransform(transform);
		if (DEBUG) {
			Logger.println("Now painting on " + debugInfo,
					"clip=" + g.getClip());
		}
		return g;
	}

	public String getDebugInfo() {
		return debugInfo;
	}

	public void setGraphics(Graphics2D g) {
		this.g = g;
	}

	@Override
	public double getMinZ() {
		return minZ;
	}

	@Override
	public double getMaxZ() {
		return maxZ;
	}

	public void getDistance(Vector v) {
		v.x -= Math.min(relevant.getMaxX(), Math.max(relevant.getMinX(), v.x));
		v.y -= Math.min(relevant.getMaxY(), Math.max(relevant.getMinY(), v.y));
		v.z -= Math.min(maxZ, Math.max(minZ, v.z));
		v.w = 0;
	}
}