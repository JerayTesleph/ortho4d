package ortho4d.logic;

import java.awt.Graphics2D;

public interface Canvas2D {
	/**
	 * Prepares and returns a Graphics2D object. Remember to call done() when
	 * you're done.<br>
	 * This is required because the underlying implementation might share a
	 * single BufferedImage with other Canvas2D instances.<br>
	 * Must be called on the Rendering thread.
	 * 
	 * @return
	 */
	public Graphics2D prepare();

	/**
	 * Returns the minimum (closest to negative infinity) value on the Z-axis
	 * for a point to be still "relevant" for this canvas.<br>
	 * Note that this results in a closed set over
	 * <code>[getMinZ(), getMaxZ()]</code><br>
	 * Must be called on the Rendering thread.
	 * 
	 * @return the minimum "relevant" Z-ordinate.
	 */
	public double getMinZ();

	/**
	 * Returns the maximum (closest to positive infinity) value on the Z-axis
	 * for a point to be still "relevant" for this canvas.<br>
	 * Note that this results in a closed set over
	 * <code>[getMinZ(), getMaxZ()]</code><br>
	 * Must be called on the Rendering thread.
	 * 
	 * @return the maximum "relevant" Z-ordinate.
	 */
	public double getMaxZ();
}
