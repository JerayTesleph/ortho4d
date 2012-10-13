package ortho4d.logic;

import ortho4d.logic.SingleGraphicsCanvas3D.AABB;
import ortho4d.math.Vector;

public class BoundedQueue extends RenderableQueue {
	// The bigger, the worse the filtering
	private static final double EPSILON = 1;
	
	private final AABB bounds;
	private final Vector tmp = new Vector();
	
	public BoundedQueue(AABB bounds) {
		this.bounds = bounds;
	}

	@Override
	public boolean isOkay(Renderable r) {
		tmp.set(r.getCenter());
		bounds.clamp(tmp);
		tmp.minus(r.getCenter());
		
		final double distance = tmp.getSquareLength();
		final double size = r.getSquareRadius();
		
		return distance <= EPSILON || distance < size;
	}
}
