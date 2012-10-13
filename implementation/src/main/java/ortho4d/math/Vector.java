package ortho4d.math;

/**
 * A four-spatial-dimensional vector, representing the distance from the origin
 * of the "current" coordinate system in left-right (x), top-bottom (y),
 * forth-back (z, also called "in front of"-"behind"), ana-kata (w) terms.
 */
public final class Vector {
	public double x, y, z, w;

	public Vector(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Vector() {
		x = y = z = w = 0;
	}

	public Vector(Vector v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
		this.w = v.w;
	}

	public void plus(Vector other) {
		x += other.x;
		y += other.y;
		z += other.z;
		w += other.w;
	}

	public void plus(Vector other, double scalar) {
		x += other.x * scalar;
		y += other.y * scalar;
		z += other.z * scalar;
		w += other.w * scalar;
	}

	public void minus(Vector other) {
		x -= other.x;
		y -= other.y;
		z -= other.z;
		w -= other.w;
	}

	public void min(Vector other) {
		x = Math.min(x, other.x);
		x = Math.min(x, other.x);
		x = Math.min(x, other.x);
		x = Math.min(x, other.x);
	}

	public void max(Vector other) {
		x = Math.max(x, other.x);
		x = Math.max(x, other.x);
		x = Math.max(x, other.x);
		x = Math.max(x, other.x);
	}
	
	public void clampOn(Vector min, Vector max) {
		x = clamp(min.x, x, max.x);
		y = clamp(min.y, y, max.y);
		z = clamp(min.z, z, max.z);
		w = clamp(min.w, w, max.w);
	}
	
	private static final double clamp(double min, double val, double max) {
		return Math.max(min, Math.min(val, max));
	}

	public void set(Vector other) {
		x = other.x;
		y = other.y;
		z = other.z;
		w = other.w;
	}

	public double getSquareLength() {
		return (x * x) + (y * y) + (z * z) + (w * w);
	}
	
	public void append(StringBuilder sb) {
		sb.append('[');
		sb.append(Double.toString(x));
		sb.append(',');
		sb.append(Double.toString(y));
		sb.append(',');
		sb.append(Double.toString(z));
		sb.append(',');
		sb.append(Double.toString(w));
		sb.append(']');
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		append(sb);
		return sb.toString();
	}
}
