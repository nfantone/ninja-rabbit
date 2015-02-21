package ar.uba.fi.game.map;

import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.EntityFactory;
import ar.uba.fi.game.physics.BodyEditorLoader;
import ar.uba.fi.game.physics.BodyFactory;
import ar.uba.fi.game.physics.SlimeBodyFactory;

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
import com.badlogic.gdx.utils.ObjectSet;

/**
 *
 * @author nfantone
 *
 */
public class EnemyRenderer {
	private static final String SLIME_TYPE = "slime";
	private static final String TYPE_PROPERTY = "type";
	private final Array<Entity> enemies;
	private final ObjectSet<Entity> removed;
	private final float unitScale;

	public EnemyRenderer() {
		this(1.0f);
	}

	public EnemyRenderer(final float unitScale) {
		this.enemies = new Array<Entity>();
		this.removed = new ObjectSet<Entity>(16);
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
			bodyDefinition.type = BodyType.DynamicBody;
			float x = ((Float) mo.getProperties().get("x")).floatValue() * unitScale;
			float y = ((Float) mo.getProperties().get("y")).floatValue() * unitScale;
			bodyDefinition.position.set(x, y);

			BodyFactory bodyFactory = null;
			Entity entity = null;

			if (SLIME_TYPE.equals(mo.getProperties().get(TYPE_PROPERTY, SLIME_TYPE, String.class))) {
				bodyFactory = new SlimeBodyFactory(loader);
				entity = EntityFactory.createEnemy(world, assets);
			} else {
				throw new IllegalArgumentException("Unknown enemy type {" + mo.getProperties().get(TYPE_PROPERTY, String.class) + "}");
			}

			Body body = bodyFactory.create(world, bodyDefinition);

			entity.setBody(body);
			body.setUserData(entity);
			enemies.add(entity);
		}
	}

	public void update(final Batch batch, final Rectangle viewBounds) {
		for (Entity e : enemies) {
			if (viewBounds == null) {
				e.update(null);
				e.step(batch);
			} else {
				if (viewBounds.contains(e.getBody().getPosition())) {
					e.getBody().setActive(true);
					e.update(null);
					e.step(batch);
				} else {
					e.getBody().setActive(false);
				}
			}
		}

		for (Entity c : removed) {
			enemies.removeValue(c, true);
		}
		removed.clear();
	}

	public void update(final Batch batch) {
		update(batch, null);
	}
}
