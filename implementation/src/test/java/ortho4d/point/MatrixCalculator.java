package ortho4d.point;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import ortho4d.math.RotationalMatrix;
import ortho4d.math.Vector;
import ortho4d.point.CoordinatePreview.DelegateSphere;
import ortho4d.point.CoordinatePreview.ColoredSphere;
import ortho4d.point.RotGUI.Calculator;

public class MatrixCalculator implements Calculator {
	private final List<MatrixSphere> coordinates;
	private final RotationalMatrix m = new RotationalMatrix();
	
	public MatrixCalculator(List<? extends ColoredSphere> coloredSpheres) {
		this.coordinates = new LinkedList<MatrixSphere>();
		
		for (ColoredSphere c : coloredSpheres) {
			this.coordinates.add(new MatrixSphere(c));
		}
	}

	@Override
	public void plug(double alpha, double beta, double gamma) {
		m.setValues(alpha, beta, gamma);
		
		for (MatrixSphere mc : coordinates) {
			mc.apply(m);
		}
	}

	@Override
	public List<MatrixSphere> getCoordinates() {
		return coordinates;
	}
	
	public static final class MatrixSphere extends DelegateSphere {
		private final Vector dummy = new Vector();

		public MatrixSphere(ColoredSphere backing) {
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
