package ortho4d.point;

import java.awt.Color;

public abstract class DelegateSphere implements ColoredSphere {
	protected final ColoredSphere backing;

	public DelegateSphere(ColoredSphere backing) {
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