package ortho4d.logic;

import ortho4d.Logger;
import ortho4d.logic.SingleGraphicsCanvas3D.AABB;
import ortho4d.math.Vector;

public class BoundedQueue extends RenderableQueue {
	private static final boolean DEBUG = false;
	
	// The bigger, the worse the filtering
	private static final double EPSILON = 10;

	private final AABB bounds;
	private final Vector tmp = new Vector();

	public BoundedQueue(AABB bounds) {
		this.bounds = bounds;
	}

	@Override
	public boolean isOkay(Renderable r) {
		tmp.set(r.getCenter());
		bounds.clamp(tmp);
		if (DEBUG) {
			StringBuilder sb = new StringBuilder();
			sb.append("Clamped ");
			r.getCenter().append(sb);
			sb.append(" to ");
			tmp.append(sb);
			Logger.println(sb.toString());
		}
		tmp.minus(r.getCenter());

		final double distance = tmp.getSquareLength();
		final double size = r.getSquareRadius();

		if (DEBUG) {
			Logger.println("distance=" + distance, "size=" + size);
		}

		return distance <= EPSILON || distance < size;
	}
}
