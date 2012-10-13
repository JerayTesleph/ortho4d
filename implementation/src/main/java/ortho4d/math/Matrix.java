package ortho4d.math;

/**
 * Represents a 4x4 matrix. May change over time, but must guarantee that values
 * stay "coherent", i.e.: If it's used for rendering, it doesn't change during
 * rendering.
 */
public abstract class Matrix {
	// public abstract Matrix times(Matrix m);
	public abstract Vector times(Vector with);
}
