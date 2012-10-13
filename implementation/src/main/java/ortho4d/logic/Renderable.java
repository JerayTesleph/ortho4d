package ortho4d.logic;

import ortho4d.math.Vector;

/**
 * Simple representation of what should be rendered on the screen, already
 * transformed into the camera system.<br>
 * Note that getCenter() and getRoughRadius() may be called very often during a
 * rendering cycle. Therefore, I'd suggest using always the same object / value
 * for that.<br>
 * Also, callers of getCenter() aren't allowed to cause modifications in the
 * returned object.
 */
public interface Renderable {
	/**
	 * Returns a rough estimate of the center of the bounding sphere. See
	 * getRoughRadius() for details.<br>
	 * Must be called on the Rendering thread.<br>
	 * Note that the return value may not change, unless this instance "knows"
	 * that the cycle has ended.
	 * 
	 * @return a rough estimate of the center
	 */
	public Vector getCenter();

	/**
	 * Returns a rough, rounded-up estimate of the radius from the center to
	 * create a bounding sphere. This is used by the Renderer (actually, the
	 * RenderableQueue) to sort out Renderables that definitely aren't going to
	 * be visible anyway<br>
	 * Must be called on the Rendering thread.<br>
	 * Note that the return value may not change, unless this instance "knows"
	 * that the cycle has ended.
	 * 
	 * @return a rough, rounded-up estimate of the radius
	 */
	public double getRoughRadius();

	public double getSquaredRadius();

	/**
	 * (Atomically) Renders this projection of this 4D object onto the given 3D
	 * canvas.<br>
	 * Must be called on the Rendering thread.
	 * 
	 * @param c
	 *            the canvas to paint the projection on
	 */
	public void render(Canvas3D c);
}
