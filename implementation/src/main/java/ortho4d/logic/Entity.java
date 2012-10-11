package ortho4d.logic;

import ortho4d.math.Matrix;

/**
 * Entities are meant to represent the "high-level thingy" that should be
 * rendered. For example, the concept of a moving sphere, or a battleship, or
 * the player, or a tree, or something composed of multiple tinier entities
 */
public interface Entity {
	// Note that an Entity can create a "bounding sphere" and quickly check on
	// the Queue whether it's going to be displayed at all or not
	public void registerRenderables(RenderableQueue q, Matrix m);
}
