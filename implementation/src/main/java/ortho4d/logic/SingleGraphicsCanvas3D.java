package ortho4d.logic;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeMap;

import ortho4d.math.Vector;

public abstract class SingleGraphicsCanvas3D implements Canvas3D {
	private final TreeMap<Double, SingleGraphicsCanvas2D> canvases = new TreeMap<Double, SingleGraphicsCanvas2D>();
	private final AABB bounds;
	private final Vector tmp;
	private Graphics2D g;

	public SingleGraphicsCanvas3D(Graphics2D g, double sightRange) {
		this.g = g;
		bounds = new AABB();
		tmp = new Vector();
		tmp.w = sightRange;
		bounds.extend(tmp);
		tmp.w = 0;
	}

	protected final void useGraphics(Graphics2D g) {
		this.g = g;
		for (SingleGraphicsCanvas2D c : canvases.values()) {
			c.setGraphics(g);
		}
	}

	public final void add(Rectangle2D clip, Rectangle2D relevant, double minZ,
			double maxZ) {
		add(new SingleGraphicsCanvas2D(clip, relevant, minZ, maxZ, g));
	}

	protected final void add(SingleGraphicsCanvas2D c) {
		Double d = Double.valueOf(c.getMinZ());
		canvases.put(d, c);
		Rectangle2D.Double r = new Rectangle2D.Double();
		c.getRelevant(r);
		tmp.x = r.getMinX();
		tmp.y = r.getMinY();
		tmp.z = c.getMinZ();
		bounds.extend(tmp);
		tmp.x = r.getMaxX();
		tmp.y = r.getMaxY();
		tmp.z = c.getMaxZ();
		bounds.extend(tmp);
	}

	@Override
	public final Collection<? extends Canvas2D> getRelevantCanvases(
			double fromZ, double toZ) {
		// To lazy to search for the start
		// => Don't use with >10 Canvas2D
		Collection<? extends Canvas2D> relevant = canvases.headMap(
				Double.valueOf(toZ)).values();
		return Collections.unmodifiableCollection(relevant);
	}

	@Override
	public final Collection<? extends Canvas2D> getAllCanvases() {
		return Collections.unmodifiableCollection(canvases.values());
	}

	@Override
	public final RenderableQueue createQueue() {
		return new BoundedQueue(bounds);
	}

	@Override
	public abstract void cycleComplete();

	public static final class AABB {
		private final Vector min = new Vector(), max = new Vector();

		public void extend(Vector mustContain) {
			min.min(mustContain);
			max.max(mustContain);
		}

		public void clamp(Vector given) {
			given.clampOn(min, max);
		}
	}
}
