package ortho4d.math;

/**
 * This represents a hard-coded rotational matrix, using the
 * latitude-longitude-system -- just that it now takes three angles instead of
 * two.<br>
 * Each matrix represents the effects of fiddling with a single angle. They are
 * determined by the behavior specified in RotGUI, and are pretty intuitive.<br>
 * <br>
 * <table>
 * <tr>
 * <td>
 * M_alpha</td>
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
 * <td>sin alpha</td>
 * </tr>
 * <tr align="center">
 * <td>0</td>
 * <td>0</td>
 * <td>1</td>
 * <td>0</td>
 * </tr>
 * <tr align="center">
 * <td>0</td>
 * <td>-sin alpha</td>
 * <td>0</td>
 * <td>cos alpha</td>
 * </tr>
 * </table>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * M_beta</td>
 * <td>
 * <table border="1">
 * <tr align="center">
 * <td>cos alpha</td>
 * <td>0</td>
 * <td>0</td>
 * <td>sin alpha</td>
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
 * <td>-sin alpha</td>
 * <td>0</td>
 * <td>0</td>
 * <td>cos alpha</td>
 * </tr>
 * </table>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * M_gamma</td>
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
 * <td>sin alpha</td>
 * </tr>
 * <tr align="center">
 * <td>0</td>
 * <td>0</td>
 * <td>-sin alpha</td>
 * <td>cos alpha</td>
 * </tr>
 * </table>
 * </td>
 * </tr>
 * <tr>
 * <td>M_gamma M_beta M_alpha<br>
 * (Here was a typo,<br>
 * the calculations are correct)</td>
 * <td>
 * <table border="1">
 * <tr align="center">
 * <td>cos beta</td>
 * <td>-sin alpha sin beta</td>
 * <td>0</td>
 * <td>sin beta cos alpha</td>
 * </tr>
 * <tr align="center">
 * <td>0</td>
 * <td>cos alpha</td>
 * <td>0</td>
 * <td>sin alpha</td>
 * </tr>
 * <tr align="center">
 * <td>-sin gamma sin beta</td>
 * <td>-sin alpha cos beta sin gamma</td>
 * <td>cos gamma</td>
 * <td>cos alpha cos beta sin gamma</td>
 * </tr>
 * <tr align="center">
 * <td>-cos gamma sin beta</td>
 * <td>-sin alpha cos beta cos gamma</td>
 * <td>-sin gamma</td>
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
 * <li>Given the W vector, it behaves as in RotGUI specified</li>
 * <li>Plugging in the standard basis, the resulting vectors will always be
 * perpendicular to each other (as defined by the scalar product)</li></ul> This
 * sounds pretty much correct.
 */
public final class RotationalMatrix extends Matrix {
	/*
	 * The naming convention is: From left to right, the first increases. From
	 * top to bottom, the second index increases.
	 */
	private double xx, yx, /* zx=0 */wx, /* xy=0 */yy, /* zy=0 */wy, xz, yz, zz,
			wz, xw, yw, zw, ww;

	public RotationalMatrix() {
		yx = wx = wy = xz = yz = wz = xw = yw = zw = 0;

		xx = yy = zz = ww = 1;
	}

	public void setValues(RotationalMatrix other) {
		this.xx = other.xx;
		this.yx = other.yx;
		this.wx = other.wx;
		this.yy = other.yy;
		this.wy = other.wy;
		this.xz = other.xz;
		this.yz = other.yz;
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
		yx = -sinA * sinB;
		/* zx = 0 */
		wx = sinB * cosA;

		/* xy = 0 */
		yy = cosA;
		/* zy = 0 */
		wy = sinA;

		final double msacb = -sinA * cosB;
		final double cacb = cosA * cosB;
		final double sinC = Math.sin(gamma);
		final double cosC = Math.cos(gamma);

		xz = -sinC * sinB;
		yz = msacb * sinC;
		zz = cosC;
		wz = cacb * sinC;

		xw = -cosC * sinB;
		yw = msacb * cosC;
		zw = -sinC;
		ww = cacb * cosC;
	}

	@Override
	protected void unsafeTimes(Vector with, Vector ret) {
		ret.x = xx * with.x + yx * with.y + /* zx * with.z + */wx * with.w;
		ret.y = /* xy * with.x + */yy * with.y + /* zy * with.z + */wy * with.w;
		ret.z = xz * with.x + yz * with.y + zz * with.z + wz * with.w;
		ret.w = xw * with.x + yw * with.y + zw * with.z + ww * with.w;
	}

	@Override
	protected double[][] getCopy() {
		return new double[][] { { xx, yx, 0, wx }, { 0, yy, 0, wy },
				{ xz, yz, zz, wz }, { xw, yw, zw, ww } };
	}
}
