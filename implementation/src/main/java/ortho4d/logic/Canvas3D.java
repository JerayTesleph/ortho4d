package ortho4d.logic;

import java.util.Collection;

public interface Canvas3D {
	/**
	 * Returnes the "relevant" Canvas2D objects, erring on the side of
	 * "too many".<br>
	 * A valid implementation is to return every single known Canvas2D object,
	 * since the caller (probably a {@link Renderable}) should check the
	 * getMinZ() and getMaxZ() in any case.<br>
	 * Must be called on the Rendering thread.
	 * 
	 * @param fromZ
	 *            the minimum (closest to negative infinity) Z value that might
	 *            be painted on
	 * @param toZ
	 *            the maximum (closest to positive infinity) Z value that might
	 *            be painted on
	 * @return A Collection of all possibly relevant Canvas2D objects
	 */
	Collection<? extends Canvas2D> getRelevantCanvases(double fromZ, double toZ);

	Collection<? extends Canvas2D> getAllCanvases();

	/**
	 * Creates and returns a new RenderableQueue object, with its isOkay()
	 * method specialized and optimized for this Canvas3D instance.<br>
	 * Must be called on the Rendering thread.
	 * 
	 * @return a new RenderableQueue object
	 */
	RenderableQueue createQueue();

	/**
	 * Notifies that the last cycle of rendering has finished.<br>
	 * This can be used, for example, for flipping the double-buffer.<br>
	 * Must be called on the Rendering thread.
	 */
	void cycleComplete();

	// No need for a start signal
	// Initially, painting must be enabled
	// ... and painting must still be allowed after a cacleComplete()
}
