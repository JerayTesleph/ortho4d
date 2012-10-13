package ortho4d.logic;

import ortho4d.math.RotationalMatrix;
import ortho4d.math.Vector;

public final class RotationalConfig extends Configuration<RotationalConfig> {
	private final RotationalMatrix m;

	public RotationalConfig() {
		super(new Vector());
		this.m = new RotationalMatrix();
	}

	@Override
	public RotationalMatrix getMatrix() {
		return m;
	}

	@Override
	public void setMatrix(RotationalConfig c) {
		m.setValues(c.m);
	}

	@Override
	public RotationalConfig copy() {
		RotationalConfig ret = new RotationalConfig();

		ret.getOrigin().set(getOrigin());
		ret.m.setValues(m);

		return ret;
	}
}