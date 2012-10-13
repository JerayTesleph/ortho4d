package ortho4d.point;

import java.awt.Color;

import ortho4d.math.Vector;

public final class CoordRevertingSphere extends DelegateSphere {
	private final Vector dummy = new Vector();

	public CoordRevertingSphere(ColoredSphere backing) {
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