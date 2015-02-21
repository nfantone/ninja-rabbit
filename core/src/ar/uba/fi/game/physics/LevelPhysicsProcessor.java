package ar.uba.fi.game.physics;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;
import ar.uba.fi.game.ai.fsm.NinjaRabbitState;
import ar.uba.fi.game.ai.msg.MessageType;
import ar.uba.fi.game.entity.Entity;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Loads and updates objects as Box2D bodies and fixtures as a {@link Box2DMapObjectParser} would
 * from a {@link Map}.
 *
 * @author nfantone
 */
public class LevelPhysicsProcessor implements PhysicsProcessor {
	public static final String EXIT_IDENTIFIER = "exit";
	public static final String ENVIRONMENT_IDENTIFIER = "environment";

	private final Box2DDebugRenderer b2dRenderer;
	private final Box2DMapObjectListener mapObjectListener;
	private boolean exitSignaled;

	private static final class Box2DMapObjectListener extends Box2DMapObjectParser.Listener.Adapter {
		private Body body;

		@Override
		public void created(final Body body, final MapObject mapObject) {
			if (ENVIRONMENT_IDENTIFIER.equals(body.getUserData())) {
				this.body = body;
			}
		}

		public Body getBody() {
			return body;
		}
	}

	public LevelPhysicsProcessor(final World world, final Map map, final float unitScale) {
		b2dRenderer = new Box2DDebugRenderer();
		mapObjectListener = new Box2DMapObjectListener();
		Box2DMapObjectParser objectParser = new Box2DMapObjectParser(mapObjectListener, unitScale);
		objectParser.load(world, map);
	}

	@Override
	public void update(final Entity entity) {
		// b2dRenderer.render(world, viewport.getCamera().combined);
		if (entity.getBody() == null) {
			mapObjectListener.getBody().setUserData(entity);
			entity.setBody(mapObjectListener.getBody());
		}
	}

	@Override
	public void beginContact(final Contact contact) {
		if (!exitSignaled) {
			// Player has reach the end of the level
			if (EXIT_IDENTIFIER.equals(contact.getFixtureA().getUserData())) {
				Entity character = (Entity) contact.getFixtureB().getBody().getUserData();
				MessageManager.getInstance().dispatchMessage(null, MessageType.EXIT.code(), character);
				character.changeState(NinjaRabbitState.JUMP);
				exitSignaled = true;
			} else if (EXIT_IDENTIFIER.equals(contact.getFixtureB().getUserData())) {
				Entity character = (Entity) contact.getFixtureA().getBody().getUserData();
				MessageManager.getInstance().dispatchMessage(null, MessageType.EXIT.code(), character);
				character.changeState(NinjaRabbitState.JUMP);
				exitSignaled = true;
			}
		}
	}

	@Override
	public void endContact(final Contact contact) {
		// TODO Auto-generated method stub
	}

	@Override
	public void preSolve(final Contact contact, final Manifold oldManifold) {
		// TODO Auto-generated method stub
	}

	@Override
	public void postSolve(final Contact contact, final ContactImpulse impulse) {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		if (b2dRenderer != null) {
			b2dRenderer.dispose();
		}
	}
}
