package ortho4d.logic;

import ortho4d.math.InvertingRotationalMatrix;
import ortho4d.math.Matrix;
import ortho4d.math.RotationalMatrix;
import ortho4d.math.Vector;

public final class RotationalConfig extends Configuration<RotationalConfig> {
	private final RotationalMatrix m;
	// TODO: Rename class
	private final InvertingRotationalMatrix inverse;
	
	private double alpha = 0, beta = 0, gamma = 0;

	public RotationalConfig() {
		super(new Vector());
		m = new RotationalMatrix();
		inverse = new InvertingRotationalMatrix();
	}

	@Override
	public Matrix getMatrix() {
		return m;
	}
	
	@Override
	public Matrix getInverseMatrix() {
		return inverse;
	}
	
	public void setValues(double alpha, double beta, double gamma) {
		this.alpha = alpha;
		this.beta = beta;
		this.gamma = gamma;
		m.setValues(alpha, beta, gamma);
		inverse.setValues(alpha, beta, gamma);
	}

	public double getAlpha() {
		return alpha;
	}

	public double getBeta() {
		return beta;
	}

	public double getGamma() {
		return gamma;
	}

	@Override
	public void setMatrices(RotationalConfig c) {
		setValues(c.alpha, c.beta, c.gamma);
	}

	@Override
	public RotationalConfig copy() {
		RotationalConfig ret = new RotationalConfig();

		ret.getOrigin().set(getOrigin());
		ret.m.setValues(m);

		return ret;
	}
}