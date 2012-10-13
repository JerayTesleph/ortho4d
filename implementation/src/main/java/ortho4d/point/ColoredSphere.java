package ortho4d.point;

import java.awt.Color;

import ortho4d.math.Vector;

public interface ColoredSphere {
	public abstract Vector getVector();

	// Should be divisible by 2, otherwise the display is 1 pixel off
	public abstract int getRadius();

	public abstract Color getColor();
}