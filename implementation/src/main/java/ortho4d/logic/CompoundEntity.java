package ortho4d.logic;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import ortho4d.math.Vector;
import ortho4d.point.CoordinatePreview.SimpleSphere;

public class CompoundEntity implements Entity {
	private final List<Entity> entities = new LinkedList<Entity>();

	public int getEntityCount() {
		return entities.size();
	}

	public boolean add(Entity e) {
		return entities.add(e);
	}

	public boolean remove(Entity e) {
		return entities.remove(e);
	}

	public void clear() {
		entities.clear();
	}

	@Override
	public void registerRenderables(RenderableQueue q, Camera c) {
		for (Entity e : entities) {
			e.registerRenderables(q, c);
		}
	}
	
	public static final Entity createCoordEntity() {
		return createCoordEntity(100, 70);
	}
	
	public static final Entity createCoordEntity(double distance, int radius) {
		final CompoundEntity ret = new CompoundEntity();
		
		SimpleSphere c;
		
		c = new SimpleSphere(new Vector(distance, 0, 0, 0));
		c.setRadius(radius);
		c.setColor(Color.RED);
		ret.add(new SphereEntity(c));
		
		c = new SimpleSphere(new Vector(0, distance, 0, 0));
		c.setRadius(radius);
		c.setColor(Color.GREEN);
		ret.add(new SphereEntity(c));
		
		c = new SimpleSphere(new Vector(0, 0, distance, 0));
		c.setRadius(radius);
		c.setColor(Color.BLUE);
		ret.add(new SphereEntity(c));
		
		c = new SimpleSphere(new Vector(0, 0, 0, distance));
		c.setRadius(radius);
		c.setColor(Color.ORANGE);
		ret.add(new SphereEntity(c));
		
		return ret;
	}
}
