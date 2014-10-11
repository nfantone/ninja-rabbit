package ar.uba.fi.game.map;

import ar.uba.fi.game.entity.Collectible;
import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.EntityFactory;
import ar.uba.fi.game.physics.BodyEditorLoader;
import ar.uba.fi.game.physics.BodyFactory;
import ar.uba.fi.game.physics.CarrotBodyFactory;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
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

	public void load(final World world, final BodyEditorLoader loader, final AssetManager assets, final MapLayer layer) {
		load(world, loader, assets, layer, null);
	}

	public void load(final World world, final BodyEditorLoader loader, final AssetManager assets, final MapLayer layer,
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
			Entity entity = null;

			switch (mo.getProperties().get(TYPE_PROPERTY, CARROT_TYPE, String.class)) {
			case CARROT_TYPE:
				bodyFactory = new CarrotBodyFactory(loader);
				entity = EntityFactory.createCollectible(world, assets);
				break;
			default:
				throw new IllegalArgumentException("Unknown collectible type {" + mo.getProperties().get(TYPE_PROPERTY, String.class) + "}");
			}

			Body body = bodyFactory.create(world, bodyDefinition);

			entity.setBody(body);
			body.setUserData(entity);
			collectibles.add(entity);
		}
	}

	public void update(final Batch batch, final Rectangle viewBounds) {
		for (Entity e : collectibles) {
			if (viewBounds == null) {
				renderEntity(batch, e);
			} else {
				if (viewBounds.contains(e.getBody().getPosition())) {
					e.getBody().setActive(true);
					renderEntity(batch, e);
				} else {
					e.getBody().setActive(false);
				}
			}
		}

		collectibles.removeAll(removed, true);
		removed.clear();
	}

	public void update(final Batch batch) {
		update(batch, null);
	}

	private void renderEntity(final Batch batch, final Entity e) {
		e.update(batch);
		if (e.isExecuting(Collectible.COLLECTED)) {
			removed.add(e);
			e.stop(Collectible.COLLECTED);
		}
	}
}
