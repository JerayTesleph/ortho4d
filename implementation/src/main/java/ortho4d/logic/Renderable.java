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
	public Vector getCenter();

	public double getRoughRadius();
	
	public void render(Canvas3D c);
}
