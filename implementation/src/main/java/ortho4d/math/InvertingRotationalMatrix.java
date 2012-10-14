package ortho4d.math;

/**
 * This represents a hard-coded inverse of a rotational matrix.<br>
 * <br>
 * <table>
 * <tr>
 * <td>
 * M_alpha^-1</td>
 * <td>
 * <table border="1">
 * <tr align="center">
 * <td>1</td>
 * <td>0</td>
 * <td>0</td>
 * <td>0</td>
 * </tr>
 * <tr align="center">
 * <td>0</td>
 * <td>cos alpha</td>
 * <td>0</td>
 * <td>-sin alpha</td>
 * </tr>
 * <tr align="center">
 * <td>0</td>
 * <td>0</td>
 * <td>1</td>
 * <td>0</td>
 * </tr>
 * <tr align="center">
 * <td>0</td>
 * <td>sin alpha</td>
 * <td>0</td>
 * <td>cos alpha</td>
 * </tr>
 * </table>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * M_beta<^-1/td>
 * <td>
 * <table border="1">
 * <tr align="center">
 * <td>cos alpha</td>
 * <td>0</td>
 * <td>0</td>
 * <td>-sin alpha</td>
 * </tr>
 * <tr align="center">
 * <td>0</td>
 * <td>1</td>
 * <td>0</td>
 * <td>0</td>
 * </tr>
 * <tr align="center">
 * <td>0</td>
 * <td>0</td>
 * <td>1</td>
 * <td>0</td>
 * </tr>
 * <tr align="center">
 * <td>sin alpha</td>
 * <td>0</td>
 * <td>0</td>
 * <td>cos alpha</td>
 * </tr>
 * </table>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * M_gamma^-1</td>
 * <td>
 * <table border="1">
 * <tr align="center">
 * <td>1</td>
 * <td>0</td>
 * <td>0</td>
 * <td>0</td>
 * </tr>
 * <tr align="center">
 * <td>0</td>
 * <td>1</td>
 * <td>0</td>
 * <td>0</td>
 * </tr>
 * <tr align="center">
 * <td>0</td>
 * <td>0</td>
 * <td>cos alpha</td>
 * <td>-sin alpha</td>
 * </tr>
 * <tr align="center">
 * <td>0</td>
 * <td>0</td>
 * <td>sin alpha</td>
 * <td>cos alpha</td>
 * </tr>
 * </table>
 * </td>
 * </tr>
 * <tr>
 * <td>M_alpha^-1 M_beta^-1 M_gamma^-1</td>
 * <td>
 * <table border="1">
 * <tr align="center">
 * <td>cos beta</td>
 * <td>0</td>
 * <td>-sin beta sin gamma</td>
 * <td>-sin beta cos gamma</td>
 * </tr>
 * <tr align="center">
 * <td>-sin alpha sin beta</td>
 * <td>cos alpha</td>
 * <td>-sin alpha cos beta sin gamma</td>
 * <td>-sin alpha cos beta cos gamma</td>
 * </tr>
 * <tr align="center">
 * <td>0</td>
 * <td>0</td>
 * <td>cos gamma</td>
 * <td>-sin gamma</td>
 * </tr>
 * <tr align="center">
 * <td>cos alpha sin beta</td>
 * <td>sin alpha</td>
 * <td>cos alpha cos beta sin gamma</td>
 * <td>cos alpha cos beta cos gamma</td>
 * </tr>
 * </table>
 * </td>
 * </tr>
 * </table>
 * <br>
 * I wasn't sure whether this is correct, especially since I somehow expected a
 * symmetric matrix, but here are some indications that this is correct:
 * <ol>
 * <li>The determinant is 1, independent of the angles (as expected)</li>
 * <li>Plugging in the standard basis, the resulting vectors will always be
 * perpendicular to each other (as defined by the scalar product)</li></ul> This
 * sounds pretty much correct.
 */
public final class InvertingRotationalMatrix extends Matrix {
	/*
	 * The naming convention is: From left to right, the first increases. From
	 * top to bottom, the second index increases.
	 */
	private double xx, /* yx=0 */zx, wx, xy, yy, zy, wy, /* xz=0,yz=0 */zz, wz,
			xw, yw, zw, ww;

	public InvertingRotationalMatrix() {
		zx = wx = xy = zy = wy = wz = xw = yw = zw = 0;

		xx = yy = zz = ww = 1;
	}

	public void setValues(InvertingRotationalMatrix other) {
		this.xx = other.xx;
		this.zx = other.zx;
		this.wx = other.wx;
		this.xy = other.xy;
		this.yy = other.yy;
		this.zy = other.zy;
		this.wy = other.wy;
		this.zz = other.zz;
		this.wz = other.wz;
		this.xw = other.xw;
		this.yw = other.yw;
		this.zw = other.zw;
		this.ww = other.ww;
	}

	public void setValues(final double alpha, final double beta,
			final double gamma) {
		// Hard-coded 4D rotational matrix.
		// Do not try to understand this before understanding above javadoc
		// table. (Hint: Look at the rendered version)

		// I do think this is faster than CustomMatrix
		// TODO: Create class CustomMatrix

		final double sinA = Math.sin(alpha);
		final double cosA = Math.cos(alpha);
		final double sinB = Math.sin(beta);
		final double cosB = Math.cos(beta);

		xx = cosB;
		xy = -sinA * sinB;
		yy = cosA;
		xw = cosB;
		yw = sinA;

		final double msacb = -sinA * cosB;
		final double cacb = cosA * cosB;
		final double sinC = Math.sin(gamma);
		final double cosC = Math.cos(gamma);

		zx = -sinB * sinC;
		wx = -sinB * cosC;
		zy = msacb * sinC;
		wy = msacb * cosC;
		zz = cosC;
		wz = -sinC;
		zw = cacb * sinC;
		zz = cacb * cosC;
	}

	@Override
	public void times(Vector with, Vector ret) {
		ret.x = xx * with.x + /* yx * with.y + */zx * with.z + wx * with.w;
		ret.y = xy * with.x + yy * with.y + zy * with.z + wy * with.w;
		ret.z = /* xz * with.x + yz * with.y + */zz * with.z + wz * with.w;
		ret.w = xw * with.x + yw * with.y + zw * with.z + ww * with.w;
	}
}
