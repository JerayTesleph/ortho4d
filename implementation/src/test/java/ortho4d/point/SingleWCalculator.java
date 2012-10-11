package ortho4d.point;

import java.util.Collections;
import java.util.List;

import ortho4d.math.Vector;
import ortho4d.point.CoordinatePreview.Coordinate;
import ortho4d.point.CoordinatePreview.SimpleCoordinate;
import ortho4d.point.RotGUI.Calculator;

public final class SingleWCalculator implements Calculator {
	private final Vector v = new Vector();

	@Override
	public final void plug(double alpha, double beta, double gamma) {
		// 2D: alpha \in [-180, 180]
		// y = sin alpha
		// x = cos alpha

		// 3D: alpha \in [-90, 90], beta \in [-180, 180]
		// y = sin alpha
		// x = cos alpha * sin beta
		// z = cos alpha * cos beta

		// 4D: alpha \in [-90, 90], beta \in [-90, 90], gamma \in [-180,
		// 180]
		// y = sin alpha
		// x = cos alpha * sin beta
		// z = cos alpha * cos beta * sin gamma
		// w = cos alpha * cos beta * cos gamma

		final double cosA, cosAcosB;

		cosA = Math.cos(alpha);
		cosAcosB = cosA * Math.cos(beta);

		v.y = Math.sin(alpha);
		v.x = cosA * Math.sin(beta);
		v.z = cosAcosB * Math.sin(gamma);
		v.w = cosAcosB * Math.cos(gamma);
	}

	@Override
	public List<Coordinate> getCoordinates() {
		Coordinate c = new SimpleCoordinate(v);
		return Collections.singletonList(c);
	}
}