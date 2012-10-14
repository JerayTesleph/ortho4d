package ortho4d.logic;

import ortho4d.math.Matrix;
import ortho4d.math.Vector;

/**
 * Note that the camera is NOT responsible for rendering in ANY way.<br>
 * It only contains and manipulates the data about where the camera is, how it
 * is positioned etc.
 */
public interface Camera {
	/**
	 * Returns the "origin" of the camera or "offset" of the new coordinate
	 * system, since this point is defined to be <code>(0, 0, 0, 0)</code> in
	 * the new coordinate system.<br>
	 * May be called on any thread, is used heavily on the Rendering thread
	 * (Entity, during registerRenderable).<br>
	 * May NOT change between two calls to cycleComplete()
	 * 
	 * @return the origin of the camera
	 */
	public Vector getOrigin();

	/**
	 * Returns the rotational matrix that represents the rotation involved in
	 * transferring from the actual coordinate system to the "camera" coordinate
	 * system. Note that, inherently, rotation means that
	 * <code>(0, 0, 0, 0)</code> stays exactly where it is.<br>
	 * Return value must be element of <code>SO(4)</code>, the special (
	 * <code>det M = 1</code>) orthogonal group.<br>
	 * May be called on any thread, is used heavily on the Rendering thread
	 * (Entity, during registerRenderable).<br>
	 * May NOT change between two calls to cycleComplete()
	 * 
	 * @return the rotational matrix representing the rotation
	 */
	public Matrix getMatrix();

	/**
	 * Returns the inverse matrix of getMatrix(). Otherwise, same rules apply.
	 * 
	 * @return the inverse matrix of getMatrix()
	 */
	public Matrix getInverseMatrix();

	/**
	 * Indicates that the rendering has completed and now, and ONLY NOW, new
	 * input data (like rotation, movement etc.) may be taken into account for
	 * getMatrix(), getOffset() etc.<br>
	 * Must be called on the Rendering thread.
	 */
	public void cycleComplete();
}
