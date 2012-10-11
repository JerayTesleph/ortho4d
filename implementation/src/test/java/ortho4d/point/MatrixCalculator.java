package ortho4d.point;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import ortho4d.math.RotationalMatrix;
import ortho4d.math.Vector;
import ortho4d.point.CoordinatePreview.BackedCoordinate;
import ortho4d.point.CoordinatePreview.Coordinate;
import ortho4d.point.RotGUI.Calculator;

public class MatrixCalculator implements Calculator {
	private final List<MatrixCoordinate> coordinates;
	
	public MatrixCalculator(List<? extends Coordinate> coordinates) {
		this.coordinates = new LinkedList<MatrixCoordinate>();
		
		for (Coordinate c : coordinates) {
			this.coordinates.add(new MatrixCoordinate(c));
		}
	}

	@Override
	public void plug(double alpha, double beta, double gamma) {
		final RotationalMatrix m = new RotationalMatrix(alpha, beta, gamma);
		
		for (MatrixCoordinate mc : coordinates) {
			mc.apply(m);
		}
	}

	@Override
	public List<MatrixCoordinate> getCoordinates() {
		return coordinates;
	}
	
	public static final class MatrixCoordinate extends BackedCoordinate {
		private final Vector dummy = new Vector();

		public MatrixCoordinate(Coordinate backing) {
			super(backing);
		}
		
		public void apply(RotationalMatrix m) {
			m.times(backing.getVector(), dummy);
		}

		@Override
		public Vector getVector() {
			return dummy;
		}

		@Override
		public int getRadius() {
			return backing.getRadius();
		}

		@Override
		public Color getColor() {
			return backing.getColor();
		}
	}
}
