package ortho4d.math;

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
	
	public void set(Vector other) {
		x = other.x;
		y = other.y;
		z = other.z;
		w = other.w;
	}
}
