package ortho4d.logic;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import ortho4d.Logger;
import ortho4d.math.Vector;

public abstract class SingleGraphicsCanvas3D implements Canvas3D {
	private static final boolean DEBUG = false;
	
	private final TreeMap<Double, SingleGraphicsCanvas2D> canvases = new TreeMap<Double, SingleGraphicsCanvas2D>();
	private final AABB bounds;
	private final Vector tmp;
	private final List<AfterPainter> painters = new LinkedList<AfterPainter>();
	private Graphics2D g;

	public SingleGraphicsCanvas3D(Graphics2D g) {
		this.g = g;
		bounds = new AABB();
		tmp = new Vector();
		tmp.w = 0;
		setSightRange(2000);
		prepareGraphics();
	}
	
	public void addPainter(AfterPainter p) {
		painters.add(p);
	}
	
	public void removePainter(AfterPainter p) {
		painters.remove(p);
	}
	
	public void clearAfterpainters() {
		painters.clear();
	}
	
	@SuppressWarnings("synthetic-access")
	public final void setSightRange(double sightRange) {
		bounds.setSightRange(sightRange);
	}
	
	protected final Graphics2D getCurrentGraphics() {
		return g;
	}

	protected final void useGraphics(Graphics2D g) {
		if (g == null) {
			throw new NullPointerException();
		}
		this.g = g;
		for (SingleGraphicsCanvas2D c : canvases.values()) {
			c.setGraphics(g);
		}
		prepareGraphics();
	}
	
	protected abstract void prepareGraphics();
	
	@Override
	public final void restart() {
		prepareGraphics();
	}

	public final void add(Rectangle2D clip, Rectangle2D relevant, double minZ,
			double maxZ) {
		add(new SingleGraphicsCanvas2D(clip, relevant, minZ, maxZ, g, clip.toString()));
	}
	
	public final void add(Rectangle2D clip, Rectangle2D relevant, double minZ,
			double maxZ, String debugInfo) {
		add(new SingleGraphicsCanvas2D(clip, relevant, minZ, maxZ, g, debugInfo));
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
	public Collection<? extends Canvas2D> getRelevantCanvases(
			double fromZ, double toZ) {
		// To lazy to search for the start
		// => Don't use with >10 Canvas2D
		Collection<? extends Canvas2D> relevant = canvases.headMap(
				Double.valueOf(toZ)).values();
		return Collections.unmodifiableCollection(relevant);
	}

	@Override
	public final Collection<SingleGraphicsCanvas2D> getAllCanvases() {
		return Collections.unmodifiableCollection(canvases.values());
	}

	@Override
	public final RenderableQueue createQueue() {
		return new BoundedQueue(bounds);
	}
	
	protected final void doAfterpainting() {
		for (AfterPainter p : painters) {
			p.paint(g);
		}
	}

	@Override
	public abstract void cycleComplete();

	public static final class AABB {
		private final Vector min = new Vector(), max = new Vector();

		public void extend(Vector mustContain) {
			// TODO: Remove for performance
			final StringBuilder sb = new StringBuilder();
			if (DEBUG) {
				sb.append("Extending to ");
				mustContain.append(sb);
			}
			min.min(mustContain);
			max.max(mustContain);
			if (DEBUG) {
				sb.append(" -> min=");
				min.append(sb);
				sb.append(", max=");
				max.append(sb);
				Logger.println(sb.toString());
			}
		}

		public void clamp(Vector given) {
			given.clampOn(min, max);
		}
		
		private void setSightRange(double maxW) {
			max.w = maxW;
		}
	}
	
	public static interface AfterPainter {
		public void paint(Graphics2D g);
	}
}
