/**
 *
 */
package ar.uba.fi.game.physics;

import ar.uba.fi.game.entity.Entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * @author nfantone
 *
 */
public class SlimePhysicsProcessor implements PhysicsProcessor {
	public static final String SLIME_IDENTIFIER = "slime";
	private static final float LINEAR_VELOCITY = 0.1f;
	private static final float MAX_VELOCITY = 0.15f;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.badlogic.gdx.physics.box2d.ContactListener#beginContact(com.badlogic.gdx.physics.box2d
	 * .Contact)
	 */
	@Override
	public void beginContact(final Contact contact) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.badlogic.gdx.physics.box2d.ContactListener#endContact(com.badlogic.gdx.physics.box2d.
	 * Contact)
	 */
	@Override
	public void endContact(final Contact contact) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.badlogic.gdx.physics.box2d.ContactListener#preSolve(com.badlogic.gdx.physics.box2d.Contact
	 * , com.badlogic.gdx.physics.box2d.Manifold)
	 */
	@Override
	public void preSolve(final Contact contact, final Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.badlogic.gdx.physics.box2d.ContactListener#postSolve(com.badlogic.gdx.physics.box2d.Contact
	 * , com.badlogic.gdx.physics.box2d.ContactImpulse)
	 */
	@Override
	public void postSolve(final Contact contact, final ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.badlogic.gdx.utils.Disposable#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ar.uba.fi.game.physics.PhysicsProcessor#update(ar.uba.fi.game.entity.Entity)
	 */
	@Override
	public void update(final Entity slime) {
		Vector2 position = slime.getBody().getWorldCenter();
		Vector2 velocity = slime.getBody().getLinearVelocity();

		if (Math.abs(velocity.x) > MAX_VELOCITY) {
			velocity.x = Math.signum(velocity.x) * MAX_VELOCITY;
			slime.getBody().setLinearVelocity(velocity);
		} else {
			slime.getBody().applyLinearImpulse(-LINEAR_VELOCITY * slime.getBody().getMass(), 0f, position.x, position.y, true);
		}
	}

}
