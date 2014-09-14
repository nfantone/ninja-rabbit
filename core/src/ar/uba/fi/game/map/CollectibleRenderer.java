package ar.uba.fi.game.map;

import ar.uba.fi.game.entity.CarrotFactory;
import ar.uba.fi.game.entity.Collectible;
import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.EntityFactory;
import ar.uba.fi.game.physics.BodyFactory;
import ar.uba.fi.game.physics.CarrotBodyFactory;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author nfantone
 *
 */
public class CollectibleRenderer {
	private static final String CARROT_TYPE = "carrot";
	private static final String TYPE_PROPERTY = "type";
	private final Array<Entity> collectibles;
	private final Array<Entity> removed;
	private final float unitScale;

	public CollectibleRenderer() {
		this(1.0f);
	}

	public CollectibleRenderer(final float unitScale) {
		this.collectibles = new Array<>();
		this.removed = new Array<>();
		this.unitScale = unitScale;
	}

	public void load(final World world, final AssetManager assets, final MapLayer layer) {
		load(world, assets, layer, null);
	}

	public void load(final World world, final AssetManager assets, final MapLayer layer,
			final Object userData) {
		if (layer == null) {
			return;
		}

		for (MapObject mo : layer.getObjects()) {
			BodyDef bodyDefinition = new BodyDef();
			bodyDefinition.type = BodyType.KinematicBody;
			float x = (float) mo.getProperties().get("x") * unitScale;
			float y = (float) mo.getProperties().get("y") * unitScale;
			bodyDefinition.position.set(x, y);

			BodyFactory bodyFactory = null;
			EntityFactory entityFactory = null;

			switch (mo.getProperties().get(TYPE_PROPERTY, String.class)) {
			case CARROT_TYPE:
				bodyFactory = new CarrotBodyFactory();
				entityFactory = new CarrotFactory();
				break;
			default:
				throw new IllegalArgumentException("Unknown collectible type {" + mo.getProperties().get(TYPE_PROPERTY, String.class) + "}");
			}

			Body body = bodyFactory.create(world, bodyDefinition);

			Entity e = entityFactory.create(world, assets);
			e.setBody(body);
			body.setUserData(e);
			collectibles.add(e);
		}
	}

	public void update(final Batch batch) {
		for (Entity e : collectibles) {
			e.update(batch);
			if (e.isExecuting(Collectible.COLLECTED)) {
				removed.add(e);
				e.stop(Collectible.COLLECTED);
			}
		}

		collectibles.removeAll(removed, true);
		removed.clear();
	}
}
