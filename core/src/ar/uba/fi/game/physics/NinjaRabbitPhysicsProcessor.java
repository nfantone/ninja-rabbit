/**
 *
 */
package ar.uba.fi.game.physics;

import ar.uba.fi.game.entity.NinjaRabbit;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * @author nfantone
 *
 */
public class NinjaRabbitPhysicsProcessor implements PhysicsProcessor {
	private static final float DEATH_ALTITUDE = -5.0F;
	public static final String FOOT_IDENTIFIER = "foot";
	private static final String GROUND_IDENTIFIER = "ground";
	private static final float LINEAR_IMPULSE = 0.4f;
	private static final float JUMP_FORCE = 2.1f;
	private static final float MAX_VELOCITY = 3.2f;

	private int groundContacts;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.uba.fi.game.entity.PhysicsProcessor#update()
	 */
	@Override
	public void update(final NinjaRabbit character) {
		Vector2 position = character.getBody().getPosition();
		Vector2 velocity = character.getBody().getLinearVelocity();

		if (character.getBody().getPosition().y < DEATH_ALTITUDE) {
			character.execute(NinjaRabbit.DEAD);
		}

		if (character.isExecuting(NinjaRabbit.RIGHT) && velocity.x < MAX_VELOCITY) {
			character.getBody().applyLinearImpulse(LINEAR_IMPULSE, 0.0f, position.x, position.y, true);
		} else if (character.isExecuting(NinjaRabbit.LEFT) && velocity.x > -MAX_VELOCITY) {
			character.getBody().applyLinearImpulse(-LINEAR_IMPULSE, 0.0f, position.x, position.y, true);
		} else if (character.isExecuting(NinjaRabbit.JUMP) && groundContacts > 0) {
			// character.getBody().setLinearVelocity(velocity.x, 0);
			character.getBody().applyLinearImpulse(0.0f, JUMP_FORCE, position.x, position.y, true);
		}

	}

	@Override
	public void beginContact(final Contact contact) {
		if (FOOT_IDENTIFIER.equals(contact.getFixtureA().getUserData()) ||
				FOOT_IDENTIFIER.equals(contact.getFixtureB().getUserData()) &&
				(GROUND_IDENTIFIER.equals(contact.getFixtureA().getUserData()) ||
						GROUND_IDENTIFIER.equals(contact.getFixtureB().getUserData()))) {
			groundContacts++;
		}
	}

	@Override
	public void endContact(final Contact contact) {
		if (FOOT_IDENTIFIER.equals(contact.getFixtureA().getUserData()) ||
				FOOT_IDENTIFIER.equals(contact.getFixtureB().getUserData()) &&
				(GROUND_IDENTIFIER.equals(contact.getFixtureA().getUserData()) ||
						GROUND_IDENTIFIER.equals(contact.getFixtureB().getUserData()))) {
			groundContacts--;
		}
	}

	@Override
	public void postSolve(final Contact contact, final ContactImpulse impulse) {
		// TODO Auto-generated method stub
	}

	@Override
	public void preSolve(final Contact contact, final Manifold oldManifold) {
		// TODO Auto-generated method stub
	}
}
