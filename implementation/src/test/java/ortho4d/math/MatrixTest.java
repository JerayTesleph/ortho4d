package ortho4d.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class MatrixTest {
	public static final double EPSILON = 0.00001;

	private static final double DEGREE = Math.PI / 180;

	private static final Vector[] SOME_VECTORS = new Vector[] {
			new Vector(0, 0, 0, 0), new Vector(1, 0, 0, 0),
			new Vector(0, 1, 0, 0), new Vector(0, 0, 1, 0),
			new Vector(0, 0, 0, 1), new Vector(1, 1, 1, 1),
			new Vector(2, 7, 3, 6), new Vector(-82.5, 42.1, 8.491, -99.9) };

	// === Actual Tests

	@Test
	public void testIdentityProjections() {
		final double[][] from = new double[][] { { 0, 0, 0, 0 },
				{ 0, 0, 0, 1 }, { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 },
				{ 18, 2, -3, 1 } };
		final double[][] to = new double[][] { { 0, 0, 0, 0 }, { 0, 0, 0, 1 },
				{ 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 },
				{ 18, 2, -3, 1 } };

		assertRotationalProjections(0, 0, 0, from, to);
	}

	@Test
	public void testIdentityConsistency() {
		assertRotationalConsistency(0, 0, 0, SOME_VECTORS);
	}

	@Test
	public void testBasicConsistency() {
		assertRotationalConsistency(15 * DEGREE, 0, 0, SOME_VECTORS);
		assertRotationalConsistency(0, 10 * DEGREE, 0, SOME_VECTORS);
		assertRotationalConsistency(0, 0, 10 * DEGREE, SOME_VECTORS);
	}

	@Test
	public void testComplexConsistency() {
		assertRotationalConsistency(-123, 456, -789, SOME_VECTORS);
		assertRotationalConsistency(-5.3 * DEGREE, 4.2 * DEGREE, 1.01 * DEGREE,
				SOME_VECTORS);
		assertRotationalConsistency(-185.3 * DEGREE, 4.2 * DEGREE,
				1.01 * DEGREE, SOME_VECTORS);
		assertRotationalConsistency(-5.3 * DEGREE, 184.2 * DEGREE,
				1.01 * DEGREE, SOME_VECTORS);
		assertRotationalConsistency(-5.3 * DEGREE, 4.2 * DEGREE,
				181.01 * DEGREE, SOME_VECTORS);
		assertRotationalConsistency(-123, 456, 789, SOME_VECTORS);
		assertRotationalConsistency(123, 456, -789, SOME_VECTORS);
	}

	// === Helpers

	public static final void assertRotationalConsistency(double alpha,
			double beta, double gamma, Vector... totest) {
		RotationalMatrix forth = new RotationalMatrix();
		forth.setValues(alpha, beta, gamma);
		assertOrthogonalMatrix(forth, SOME_VECTORS);
		
		InverseRotationalMatrix back = new InverseRotationalMatrix();
		back.setValues(alpha, beta, gamma);
		assertOrthogonalMatrix(back, SOME_VECTORS);
		
		assertInverseMatrix(forth, back, totest);
	}

	public static final void assertInverseMatrix(Matrix m, Matrix inverse,
			Vector... toTest) {
		final Vector forth, back, both, vDiff;
		forth = new Vector();
		back = new Vector();
		both = new Vector();
		vDiff = new Vector();

		for (Vector orig : toTest) {
			m.times(orig, forth);
			inverse.times(orig, back);

			inverse.times(forth, both);
			vDiff.set(both);
			vDiff.minus(orig);

			double diff = Math.sqrt(vDiff.getSquareLength());
			if (diff > EPSILON) {
				StringBuilder sb = new StringBuilder();
				sb.append("Values differed by ");
				sb.append(Double.toString(diff));
				sb.append(", but only ");
				sb.append(Double.toString(EPSILON));
				sb.append(" is allowed\nTransformed ");
				orig.append(sb);
				sb.append(" to ");
				forth.append(sb);
				sb.append(" and back to ");
				both.append(sb);
				fail(sb.toString());
			}

			m.times(back, both);
			vDiff.set(both);
			vDiff.minus(orig);

			diff = Math.sqrt(vDiff.getSquareLength());
			if (diff > EPSILON) {
				StringBuilder sb = new StringBuilder();
				sb.append("Values differed by ");
				sb.append(Double.toString(diff));
				sb.append(", but only ");
				sb.append(Double.toString(EPSILON));
				sb.append(" is allowed\nTransformed ");
				orig.append(sb);
				sb.append(" back to ");
				forth.append(sb);
				sb.append(" and forth to ");
				both.append(sb);
				fail(sb.toString());
			}
		}
	}

	public static final void assertOrthogonalMatrix(Matrix m, Vector... vectors) {
		final Vector tmp = new Vector();

		for (Vector v : vectors) {
			m.times(v, tmp);
			double diff = Math
					.sqrt(tmp.getSquareLength() / v.getSquareLength()) - 1;
			diff = Math.abs(diff);
			if (diff > EPSILON) {
				StringBuilder sb = new StringBuilder();
				sb.append("Values differed by ");
				sb.append(Double.toString(diff));
				sb.append(", but only ");
				sb.append(Double.toString(EPSILON));
				sb.append(" is allowed!\nTransformed ");
				v.append(sb);
				sb.append(" into ");
				tmp.append(sb);
				sb.append(", which has a different length!\nMatrix was:\n");
				m.append(sb);
				
				String msg = sb.toString();
				
				System.out.println("MatrixTest.assertOrthogonalMatrix():");
				System.out.println(msg);
				fail(msg);
			}
		}
	}

	public static final void assertRotationalProjections(double alpha,
			double beta, double gamma, double[][] from, double[][] to) {
		RotationalMatrix m = new RotationalMatrix();
		m.setValues(alpha, beta, gamma);
		assertProjections(m, from, to);
	}

	public static final void assertProjections(Matrix m, double[][] from,
			double[][] to) {
		assertTrue(from.length == to.length);

		final Vector vFrom, vTo, vActual, vTmp;

		vFrom = new Vector();
		vTo = new Vector();
		vActual = new Vector();
		vTmp = new Vector();

		for (int i = 0; i < from.length; i++) {
			assertEquals("Malformed 'from' entry #" + i, 4, from[i].length);
			assertEquals("Malformed 'to' entry #" + i, 4, to[i].length);

			vFrom.x = from[i][0];
			vFrom.y = from[i][1];
			vFrom.z = from[i][2];
			vFrom.w = from[i][3];
			vTo.x = to[i][0];
			vTo.y = to[i][1];
			vTo.z = to[i][2];
			vTo.w = to[i][3];

			m.times(vFrom, vActual);

			vTmp.set(vActual);
			vTmp.minus(vTo);

			double diff = Math.sqrt(vTmp.getSquareLength());
			if (diff > EPSILON) {
				StringBuilder sb = new StringBuilder();
				sb.append("Values differed by ");
				sb.append(Double.toString(diff));
				sb.append(", but only ");
				sb.append(Double.toString(EPSILON));
				sb.append(" is allowed!\nTransformed ");
				vFrom.append(sb);
				sb.append(" into ");
				vActual.append(sb);
				sb.append(", but expected ");
				vTo.append(sb);
				fail(sb.toString());
			}
		}
	}
}
