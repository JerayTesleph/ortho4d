package ortho4d.logic;

import ortho4d.math.Vector;

public final class RotationalCamera extends DynamicCamera<RotationalConfig> {
	private static final double PI_2 = Math.PI / 2;

	private double alpha, beta, gamma;

	public RotationalCamera() {
		super(new RotationalConfig());
		alpha = beta = gamma = 0;
	}

	public void moveBy(Vector v) {
		getControlledConfiguration().getOrigin().plus(v);
	}

	public void modifyAlpha(double diff) {
		alpha += diff;
		// Very important, this way you can't accidentally
		// "look so far up that you look behind yourself"
		alpha = Math.min(PI_2, Math.max(-PI_2, alpha));
		updateMatrix();
	}

	public void modifyBeta(double diff) {
		beta += diff;
		// Not important, only prevents loss of precision
		// (After turning around 20000 times)
		beta = Math.min(PI_2, Math.max(-PI_2, beta));
	}

	public void modifyGamma(double diff) {
		gamma += diff;
		// Not important, only prevents loss of precision
		// (After turning around 20000 times)
		gamma = Math.min(Math.PI, Math.max(-Math.PI, gamma));
	}
	
	public void reset() {
		Vector v = getControlledConfiguration().getOrigin();
		v.x = v.y = v.z = v.w = 0;
		alpha = beta = gamma = 0;
		getControlledConfiguration().getMatrix().setValues(0, 0, 0);
	}
	
	private final void updateMatrix() {
		getControlledConfiguration().getMatrix().setValues(alpha, beta, gamma);
	}
}
