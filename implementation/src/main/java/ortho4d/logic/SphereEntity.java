package ortho4d.logic;

import ortho4d.math.Vector;
import ortho4d.point.CoordinatePreview.ColoredSphere;

public class SphereEntity implements Entity {
	private final ColoredSphere backing;
	private final Hypersphere renderable;
	
	public SphereEntity(ColoredSphere backing) {
		this.backing = backing;
		renderable = new Hypersphere();
	}

	@Override
	public void registerRenderables(RenderableQueue q, Camera c) {
		renderable.setSize(backing.getRadius());
		renderable.setColor(backing.getColor());
		
		Vector v = renderable.getCenter();
		v.set(backing.getVector());
		v.minus(c.getOrigin());
		c.getMatrix().times(v, v);
	}
}
