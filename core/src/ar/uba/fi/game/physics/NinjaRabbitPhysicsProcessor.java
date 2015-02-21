/**
 *
 */
package ar.uba.fi.game.physics;

import ar.uba.fi.game.ai.fsm.NinjaRabbitState;
import ar.uba.fi.game.ai.msg.MessageType;
import ar.uba.fi.game.entity.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * @author nfantone
 *
 */
public class NinjaRabbitPhysicsProcessor implements PhysicsProcessor {
	private static final float DEATH_ALTITUDE = -5.0F;
	public static final String FOOT_IDENTIFIER = "foot";
	private static final String GROUND_IDENTIFIER = "ground";
	private static final float LINEAR_VELOCITY = 2.0f;
	private static final float JUMP_VELOCITY = 5.33f;
	private static final Vector2 MAX_VELOCITY = new Vector2(3.3f, 2.99f);

	private int groundContacts;
	private int jumpTimeout;
	private float stillTime;

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
			MessageManager.getInstance().dispatchMessage(null, MessageType.DEAD.code(), character);
		} else {
			// Cap maximum velocity on X axis
			if (Math.abs(velocity.x) > MAX_VELOCITY.x) {
				velocity.x = Math.signum(velocity.x) * MAX_VELOCITY.x;
				character.getBody().setLinearVelocity(velocity);
				velocity = character.getBody().getLinearVelocity();
			}

			// Linear velocity dampening
			if (character.isInState(NinjaRabbitState.IDLE)) {
				velocity = character.getBody().getLinearVelocity();
				stillTime += Gdx.graphics.getDeltaTime();
				character.getBody().setLinearVelocity(velocity.x * 0.9f, velocity.y);
			} else {
				stillTime = 0.0f;
			}

			// Disable friction if character is not on ground
			float newFriction = 0.05f;
			if (groundContacts > 0) {
				if (character.isInState(NinjaRabbitState.IDLE) && stillTime > 0.2f) {
					newFriction = 300.0f;
				} else {
					newFriction = 0.8f;
				}
			}

			for (Fixture f : character.getBody().getFixtureList()) {
				f.setFriction(newFriction);
			}

			// Move character left or right
			if (character.isInState(NinjaRabbitState.RIGHT) && velocity.x < MAX_VELOCITY.x) {
				character.getBody().applyLinearImpulse(LINEAR_VELOCITY * character.getBody().getMass(), 0.0f, position.x, position.y, true);
			} else if (character.isInState(NinjaRabbitState.LEFT) && velocity.x > -MAX_VELOCITY.x) {
				character.getBody()
						.applyLinearImpulse(-LINEAR_VELOCITY * character.getBody().getMass(), 0.0f, position.x, position.y, true);
			}

			// Jump
			if (character.isInState(NinjaRabbitState.JUMP) && groundContacts > 0) {
				position = character.getBody().getPosition();

				// Initial jumping impulse (on ground)
				character.getBody().setLinearVelocity(velocity.x, 0.0f);
				character.getBody().setTransform(position.x, position.y + 0.01f, 0);
				character.getBody().applyLinearImpulse(0.0f, JUMP_VELOCITY * character.getBody().getMass() + Math.abs(velocity.x) * 0.2f,
						position.x,
						position.y, true);
				jumpTimeout = 0;
			} else if (character.isInState(NinjaRabbitState.JUMP)
					&& velocity.y < MAX_VELOCITY.y && velocity.y > 0) {
				// Incrementally decreased impulse added while mid-air the longer the jump button is
				// held, the higher the character jumps)
				character.getBody().applyLinearImpulse(0.0f,
						0.13f * JUMP_VELOCITY * character.getBody().getMass() / ++jumpTimeout,
						position.x,
						position.y, true);
			}
		}
	}

	@Override
	public void beginContact(final Contact contact) {
		// Foot sensor is touching the ground
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

	@Override
	public void dispose() {

	}
}
