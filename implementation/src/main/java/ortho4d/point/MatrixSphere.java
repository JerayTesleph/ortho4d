package ortho4d.point;

import java.awt.Color;

import ortho4d.math.RotationalMatrix;
import ortho4d.math.Vector;

public final class MatrixSphere extends DelegateSphere {
	private final Vector dummy = new Vector();

	public MatrixSphere(ColoredSphere backing) {
		super(backing);
	}
	
	public void apply(RotationalMatrix m) {
		m.times(backing.getVector(), dummy);
	}

	@Override
	public Vector getVector() {
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