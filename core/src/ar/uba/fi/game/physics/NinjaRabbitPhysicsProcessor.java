/**
 *
 */
package ar.uba.fi.game.physics;

import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.NinjaRabbit;
import ar.uba.fi.game.graphics.CarrotPhysicsProcessor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * @author nfantone
 *
 */
public class NinjaRabbitPhysicsProcessor implements PhysicsProcessor {
	private static final int MAX_JUMP_TIMEOUT = 18;
	private static final float DEATH_ALTITUDE = -5.0F;
	public static final String FOOT_IDENTIFIER = "foot";
	private static final String GROUND_IDENTIFIER = "ground";
	public static final Object LEFT_SENSOR_IDENTIFIER = "left";
	public static final Object RIGHT_SENSOR_IDENTIFIER = "right";
	private static final float LINEAR_VELOCITY = 0.5f;
	private static final float JUMP_VELOCITY = 5.4f;
	private static final float MAX_VELOCITY = 3.2f;

	private int groundContacts;
	private int rightContacts;
	private int leftContacts;
	private int jumpTimeout;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.uba.fi.game.entity.PhysicsProcessor#update()
	 */
	@Override
	public void update(final Entity character) {
		Vector2 position = character.getBody().getWorldCenter();
		Vector2 velocity = character.getBody().getLinearVelocity();

		if (character.getBody().getPosition().y < DEATH_ALTITUDE) {
			character.execute(NinjaRabbit.DEAD);
		}

		if (character.isExecuting(NinjaRabbit.RIGHT) && velocity.x < MAX_VELOCITY && rightContacts <= 0) {
			character.getBody().applyLinearImpulse(LINEAR_VELOCITY * character.getBody().getMass(), 0.0f, position.x, position.y, true);
		} else if (character.isExecuting(NinjaRabbit.LEFT) && velocity.x > -MAX_VELOCITY && leftContacts <= 0) {
			character.getBody().applyLinearImpulse(-LINEAR_VELOCITY * character.getBody().getMass(), 0.0f, position.x, position.y, true);
		} else if (character.isExecuting(NinjaRabbit.JUMP) && groundContacts > 0 && jumpTimeout <= 0) {
			character.getBody().applyLinearImpulse(0.0f, JUMP_VELOCITY * character.getBody().getMass(), position.x, position.y, true);
			jumpTimeout = MAX_JUMP_TIMEOUT;
		}

		if (jumpTimeout > 0) {
			jumpTimeout--;
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

		if (RIGHT_SENSOR_IDENTIFIER.equals(contact.getFixtureA().getUserData()) ||
				RIGHT_SENSOR_IDENTIFIER.equals(contact.getFixtureB().getUserData())) {
			rightContacts++;
		}

		if (LEFT_SENSOR_IDENTIFIER.equals(contact.getFixtureA().getUserData()) ||
				LEFT_SENSOR_IDENTIFIER.equals(contact.getFixtureB().getUserData())) {
			leftContacts++;
		}

		// Player had picked a carrot
		if (CarrotPhysicsProcessor.CARROT_IDENTIFIER.equals(contact.getFixtureA().getUserData())) {
			((NinjaRabbit) contact.getFixtureB().getBody().getUserData()).execute(NinjaRabbit.COLLECT);
		} else if (CarrotPhysicsProcessor.CARROT_IDENTIFIER.equals(contact.getFixtureB().getUserData())) {
			((NinjaRabbit) contact.getFixtureA().getBody().getUserData()).execute(NinjaRabbit.COLLECT);
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

		if (RIGHT_SENSOR_IDENTIFIER.equals(contact.getFixtureA().getUserData()) ||
				RIGHT_SENSOR_IDENTIFIER.equals(contact.getFixtureB().getUserData())) {
			rightContacts--;
		}

		if (LEFT_SENSOR_IDENTIFIER.equals(contact.getFixtureA().getUserData()) ||
				LEFT_SENSOR_IDENTIFIER.equals(contact.getFixtureB().getUserData())) {
			leftContacts--;
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
