package ortho4d.math;


/**
 * Note that the camera is NOT responsible for rendering in ANY way.<br>
 * It only contains and manipulates the data about where the camera is, how it
 * is positioned etc.
 */
public abstract class Camera {
	public abstract Vector getOrign();

	public abstract Matrix getMatrix();
}
