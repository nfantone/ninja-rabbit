package ar.uba.fi.game.physics;

import ar.uba.fi.game.entity.Entity;

import com.badlogic.gdx.physics.box2d.Body;

public interface BodyProcessor {

	/**
	 * Regenerates the character Box2D {@link Body} according to the action it is executing. It
	 * destroys the old body if necessary.
	 *
	 * @param character
	 *            The {@link Entity} whose body should be updated.
	 */
	public abstract void update(Entity character);

}
