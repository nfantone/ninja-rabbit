package ar.uba.fi.game.graphics;

import ar.uba.fi.game.entity.Collectible;
import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.physics.PhysicsProcessor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
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
			((Collectible) contact.getFixtureA().getBody().getUserData()).execute(Collectible.COLLECTED);
		} else if (CARROT_IDENTIFIER.equals(contact.getFixtureB().getUserData())) {
			((Collectible) contact.getFixtureB().getBody().getUserData()).execute(Collectible.COLLECTED);
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
		Vector2 position = character.getBody().getPosition();
		if (character.isExecuting(Collectible.COLLECTED)) {
			body.getWorld().destroyBody(body);
		} else {
			if (!character.isExecuting(Collectible.UP)) {
				origin = position.y;
				body.setLinearVelocity(0.0f, VERTICAL_VELOCITY);
				character.execute(Collectible.UP);
			} else if (position.y - origin > MAX_DISTANCE) {
				body.setLinearVelocity(0.0f, -VERTICAL_VELOCITY);
			} else if (position.y <= origin) {
				character.stop(Collectible.UP);
			}
		}

	}
}
