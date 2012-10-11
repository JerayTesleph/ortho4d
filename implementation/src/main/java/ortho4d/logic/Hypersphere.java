package ortho4d.logic;

import ortho4d.math.Vector;

public class Hypersphere implements Renderable {
	private final Vector center = new Vector();
	private double size = 10;
	
	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
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
	public void render(Canvas3D c) {
		// FIXME: Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
