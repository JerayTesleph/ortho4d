package ortho4d.logic;

import java.awt.geom.Rectangle2D;

public final class GridSlicer {
	private final Rectangle2D relevant;
	private final double minZ, maxZ;

	public GridSlicer(Rectangle2D relevant, double minZ, double maxZ) {
		this.relevant = relevant;
		this.minZ = minZ;
		this.maxZ = maxZ;
	}

	public void addSlices(SingleGraphicsCanvas3D c, Rectangle2D size, int rows,
			int cols) {
		final int slices = rows * cols;
		final double xStep = size.getWidth() / cols;
		final double yStep = size.getHeight() / rows;
		final double zStep = (maxZ - minZ) / slices;

		int sliceNr = 0;
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				final Rectangle2D clip = new Rectangle2D.Double(xStep * x,
						yStep * y, xStep, yStep);

				final double zStart = minZ + zStep * sliceNr;
				sliceNr++;
				final double zEnd = minZ + zStep * sliceNr;

				c.add(clip, relevant, zStart, zEnd);
			}
		}
	}
	
	public static void addSlices(int rows,
			int cols, SingleGraphicsCanvas3D c, Rectangle2D bounds) {
		final Rectangle2D relevant = new Rectangle2D.Double(-bounds.getWidth()/2,
				-bounds.getHeight()/2, bounds.getWidth(), bounds.getHeight());
		final double zHalfRange = Math.sqrt(bounds.getWidth() * bounds.getHeight())/2;
		
		GridSlicer gs = new GridSlicer(relevant, -zHalfRange, zHalfRange);
		
		gs.addSlices(c, bounds, rows, cols);
	}
}
