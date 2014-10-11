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

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.uba.fi.game.entity.PhysicsProcessor#update()
	 */
	@Override
	public void update(final Entity character) {
		if (!character.isExecuting(NinjaRabbit.GAME_OVER)) {
			Vector2 position = character.getBody().getWorldCenter();
			Vector2 velocity = character.getBody().getLinearVelocity();

			if (character.getBody().getPosition().y < DEATH_ALTITUDE) {
				character.execute(NinjaRabbit.DEAD);
			}

			if (Math.abs(velocity.x) > MAX_VELOCITY.x) {
				velocity.x = Math.signum(velocity.x) * MAX_VELOCITY.x;
				character.getBody().setLinearVelocity(velocity);
			}

			if (character.isExecuting(NinjaRabbit.RIGHT) && velocity.x < MAX_VELOCITY.x) {
				character.getBody().applyLinearImpulse(LINEAR_VELOCITY * character.getBody().getMass(), 0.0f, position.x, position.y, true);
			} else if (character.isExecuting(NinjaRabbit.LEFT) && velocity.x > -MAX_VELOCITY.x) {
				character.getBody()
						.applyLinearImpulse(-LINEAR_VELOCITY * character.getBody().getMass(), 0.0f, position.x, position.y, true);
			}

			if (!character.isExecuting(NinjaRabbit.RIGHT) && !character.isExecuting(NinjaRabbit.LEFT)) {
				character.getBody().setLinearVelocity(velocity.x * 0.9f, velocity.y);
			}

			if (character.isExecuting(NinjaRabbit.JUMP) && groundContacts > 0) {
				// Initial jumping impulse (on ground)
				character.getBody().setLinearVelocity(velocity.x, 0.0f);
				position.y += 0.1f;
				character.getBody().applyLinearImpulse(0.0f, JUMP_VELOCITY * character.getBody().getMass() + Math.abs(velocity.x) * 0.2f,
						position.x,
						position.y, true);
				jumpTimeout = 0;
			} else if (character.isExecuting(NinjaRabbit.JUMP)
					&& velocity.y < MAX_VELOCITY.y && velocity.y > 0) {
				// Incrementally decreased impulse added while mid-air the longer the jump button is
				// held, the higher the character jumps)
				character.getBody().applyLinearImpulse(0.0f,
						0.13f * JUMP_VELOCITY * character.getBody().getMass() / ++jumpTimeout,
						position.x,
						position.y, true);
			}

			if (groundContacts > 0) {
				for (Fixture f : character.getBody().getFixtureList()) {
					f.setFriction(0.8f);
				}
			} else {
				for (Fixture f : character.getBody().getFixtureList()) {
					f.setFriction(0.1f);
				}
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

		// Player has picked up a carrot
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
