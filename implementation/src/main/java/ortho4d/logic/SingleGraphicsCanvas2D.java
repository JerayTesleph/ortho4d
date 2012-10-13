package ortho4d.logic;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import ortho4d.Logger;
import ortho4d.math.Vector;

public final class SingleGraphicsCanvas2D implements Canvas2D {
	private static final boolean DEBUG = true;

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
		applyTransform(transform, clip, relevant);
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
		if (DEBUG) {
			Logger.println("Now painting on " + debugInfo);
		}
		g.setClip(clip);
		g.setTransform(transform);
		return g;
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