package ar.uba.fi.game.map;

import ar.uba.fi.game.ai.msg.MessageType;
import ar.uba.fi.game.entity.Collectible;
import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.EntityFactory;
import ar.uba.fi.game.physics.BodyEditorLoader;
import ar.uba.fi.game.physics.BodyFactory;
import ar.uba.fi.game.physics.CarrotBodyFactory;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
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
public class CollectibleRenderer implements Telegraph {
	private static final String CARROT_TYPE = "carrot";
	private static final String TYPE_PROPERTY = "type";
	private final Array<Entity> collectibles;
	private final ObjectSet<Entity> removed;
	private final float unitScale;

	public CollectibleRenderer() {
		this(1.0f);
	}

	public CollectibleRenderer(final float unitScale) {
		this.collectibles = new Array<Entity>();
		this.removed = new ObjectSet<Entity>(16);
		this.unitScale = unitScale;
		MessageManager.getInstance().addListener(this, MessageType.COLLECTED.code());
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
			float x = ((Float) mo.getProperties().get("x")).floatValue() * unitScale;
			float y = ((Float) mo.getProperties().get("y")).floatValue() * unitScale;
			bodyDefinition.position.set(x, y);

			BodyFactory bodyFactory = null;
			Entity entity = null;

			if (CARROT_TYPE.equals(mo.getProperties().get(TYPE_PROPERTY, CARROT_TYPE, String.class))) {
				bodyFactory = new CarrotBodyFactory(loader);
				entity = EntityFactory.createCollectible(world, assets);
			} else {
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
			collectibles.removeValue(c, true);
		}
		removed.clear();
	}

	public void update(final Batch batch) {
		update(batch, null);
	}

	@Override
	public boolean handleMessage(final Telegram msg) {
		Collectible collectible = (Collectible) msg.extraInfo;
		removed.add(collectible);
		return true;
	}
}
