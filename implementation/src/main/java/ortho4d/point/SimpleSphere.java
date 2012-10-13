package ortho4d.point;

import java.awt.Color;

import ortho4d.math.Vector;

public final class SimpleSphere implements ColoredSphere {
	private Color c = new Color(128, 0, 0);
	// Should be divisible by 2, otherwise the display is 1 pixel off
	private int radius = 6;
	private final Vector v;

	public SimpleSphere(Vector v) {
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