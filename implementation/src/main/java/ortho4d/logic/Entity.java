package ortho4d.logic;


/**
 * Entities are meant to represent the "high-level thingy" that should be
 * rendered. For example, the concept of a moving sphere, or a battleship, or
 * the player, or a tree, or something composed of multiple tinier entities
 */
public interface Entity {
	/**
	 * Creates any Renderables and inserts them in the given RenderableQueue.
	 * Note that implementors can easily create / use a dummy Hypersphere as a
	 * bounding sphere to check whether something is going to be displayed at
	 * all.<br>
	 * May only called on the Rendering thread.<br>
	 * This call only happens exactly once per rendering cycle, and the call
	 * implies that the cycle just has started. This allows implementors of very
	 * simple entities to re-use Renderable objects.
	 * 
	 * @param q
	 *            The queue where the Renderable objects should go
	 * @param c
	 *            The camera that contains the information of the camera's
	 *            coordinate system.
	 */
	public void registerRenderables(RenderableQueue q, Camera c);
}
