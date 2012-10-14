package ortho4d.math;

/**
 * Represents a 4x4 matrix. May change over time, but must guarantee that values
 * stay "coherent", i.e.: If it's used for rendering, it doesn't change during
 * rendering.
 */
public abstract class Matrix {
	// public abstract Matrix times(Matrix m);

	private final Vector tmp = new Vector();

	public Vector times(Vector with) {
		final Vector ret = new Vector();
		unsafeTimes(with, ret);
		return ret;
	}

	public void times(Vector with, Vector ret) {
		if (with == ret) {
			unsafeTimes(with, tmp);
			ret.set(tmp);
		} else {
			unsafeTimes(with, ret);
		}
	}

	protected abstract void unsafeTimes(Vector with, Vector into);

	protected abstract double[][] getCopy();

	private static final void appendRow(StringBuilder sb, int idx,
			double[][] data) {
		sb.append(data[idx][0]);
		sb.append('\n');
		sb.append('\t');
		sb.append(data[idx][1]);
		sb.append('\n');
		sb.append('\t');
		sb.append(data[idx][2]);
		sb.append('\n');
		sb.append('\t');
		sb.append(data[idx][3]);
		sb.append('\n');

	}

	public final void append(StringBuilder sb) {
		double[][] data = getCopy();
		appendRow(sb, 0, data);
		appendRow(sb, 1, data);
		appendRow(sb, 2, data);
		appendRow(sb, 3, data);
	}
}
