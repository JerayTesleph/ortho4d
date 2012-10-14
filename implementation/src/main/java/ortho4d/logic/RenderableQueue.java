package ortho4d.logic;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

import ortho4d.math.Vector;

public abstract class RenderableQueue implements Iterable<Renderable> {
	private final TreeSet<Renderable> queue;

	public RenderableQueue() {
		queue = new TreeSet<Renderable>(COMPARATOR);
	}

	// == Public interface

	// Yep, may also be called from outer classes
	public abstract boolean isOkay(Renderable r);

	public void clear() {
		queue.clear();
	}

	public int size() {
		return queue.size();
	}

	@Override
	public final Iterator<Renderable> iterator() {
		return Collections.unmodifiableSet(queue).iterator();
	}

	public final void add(Renderable r) {
		if (r == null) {
			throw new NullPointerException("NULL not allowed o.O");
		}
		if (isOkay(r)) {
			queue.add(r);
		}
	}

	// == Sorting algorithm

	private static final int BEFORE_IN_QUEUE = -1, AFTER_IN_QUEUE = 1;
	private static final int FURTHER_AWAY = BEFORE_IN_QUEUE,
			CLOSER = AFTER_IN_QUEUE;

	private static final class RenderableComparator implements
			Comparator<Renderable> {
		public RenderableComparator() {
			// Nothing to do here
		}

		@Override
		public int compare(Renderable r1, Renderable r2) {
			if (r1 == r2) {
				// hotpath
				return 0;
			}
			final Vector v1, v2;
			v1 = r1.getCenter();
			v2 = r2.getCenter();

			if (v1.w < v2.w) {
				return CLOSER;
			} else if (v1.w > v2.w) {
				return FURTHER_AWAY;
			} else if (r1.getRoughRadius() < r2.getRoughRadius()) {
				return CLOSER;
			} else if (r1.getRoughRadius() > r2.getRoughRadius()) {
				return FURTHER_AWAY;
			} else /* Huh */if (v1.x < v2.x) {
				// Note that checking for the X value isn't necessary
				// I do that only to try to stay "consistent"
				// That is, keeping up the
				return CLOSER;
			} else if (v1.x > v2.x) {
				return FURTHER_AWAY;
			} else /* Eh, fuck it */{
				final int o1 = System.identityHashCode(r1), o2 = System
						.identityHashCode(r2);
				if (o1 < o2) {
					return CLOSER;
				} else {
					// Don't care when o1 == o2
					// We'd be anyway
					return FURTHER_AWAY;
				}
			}
		}
	}

	private static final RenderableComparator COMPARATOR = new RenderableComparator();
}
