package ortho4d.logic;

import ortho4d.math.Matrix;
import ortho4d.math.Vector;

public abstract class Configuration<C extends Configuration<C>> {
	private final Vector origin;

	protected Configuration(Vector origin) {
		this.origin = origin;
	}

	public abstract Matrix getMatrix();

	public abstract void setMatrix(C c);

	public final Vector getOrigin() {
		return origin;
	}

	public abstract C copy();
}