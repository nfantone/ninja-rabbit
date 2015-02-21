package ar.uba.fi.game.physics;

import ar.uba.fi.game.ai.fsm.CarrotState;
import ar.uba.fi.game.ai.msg.MessageType;
import ar.uba.fi.game.entity.Collectible;
import ar.uba.fi.game.entity.Entity;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 *
 * @author nfantone
 *
 */
public class CarrotPhysicsProcessor implements PhysicsProcessor {
	private static final float MAX_DISTANCE = 0.18f;
	private static final float VERTICAL_VELOCITY = 0.21f;
	public static final String CARROT_IDENTIFIER = "carrot";

	private float origin;

	@Override
	public void beginContact(final Contact contact) {
		if (CARROT_IDENTIFIER.equals(contact.getFixtureA().getUserData())) {
			collectCarrot(contact.getFixtureA());
		} else if (CARROT_IDENTIFIER.equals(contact.getFixtureB().getUserData())) {
			collectCarrot(contact.getFixtureB());
		}
	}

	/**
	 * @param contact
	 */
	private void collectCarrot(final Fixture fixture) {
		Collectible carrot = (Collectible) fixture.getBody().getUserData();
		if (!carrot.isCollected()) {
			carrot.setCollected(true);
			MessageManager.getInstance().dispatchMessage(null, MessageType.COLLECTED.code(), carrot);
		}
	}

	@Override
	public void endContact(final Contact contact) {

	}

	@Override
	public void preSolve(final Contact contact, final Manifold oldManifold) {

	}

	@Override
	public void postSolve(final Contact contact, final ContactImpulse impulse) {

	}

	@Override
	public void update(final Entity character) {
		Body body = character.getBody();

		if (((Collectible) character).isCollected()) {
			body.getWorld().destroyBody(body);
		} else {
			Vector2 position = character.getBody().getPosition();
			if (!character.isInState(CarrotState.UP)) {
				origin = position.y;
				body.setLinearVelocity(0.0f, VERTICAL_VELOCITY);
				character.changeState(CarrotState.UP);
			} else if (position.y - origin > MAX_DISTANCE) {
				body.setLinearVelocity(0.0f, -VERTICAL_VELOCITY);
			} else if (position.y <= origin) {
				character.changeState(CarrotState.DOWN);
			}
		}
	}

	@Override
	public void dispose() {

	}
}
