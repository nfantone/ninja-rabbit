package ar.uba.fi.game.physics;

import ar.uba.fi.game.entity.Direction;
import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.NinjaRabbit;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author nfantone
 *
 */
public class NinjaRabbitBodyProcessor implements BodyProcessor {
	private final BodyFactory bodyFactory;
	private final World world;
	private Direction lastKnownDirection;

	public NinjaRabbitBodyProcessor(final World world, final BodyEditorLoader loader) {
		if (world == null) {
			throw new IllegalArgumentException("'world' cannot be null");
		}
		this.bodyFactory = new NinjaRabbitBodyFactory(loader);
		this.world = world;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ar.uba.fi.game.physics.BodyProcessor#update(ar.uba.fi.game.entity.Entity)
	 */
	@Override
	public void update(final Entity character) {
		if (character.getBody() == null) {
			character.setDirection(Direction.RIGHT);
			Body body = bodyFactory.create(world, Direction.RIGHT);
			character.setBody(body);
			body.setUserData(character);
			lastKnownDirection = Direction.RIGHT;
		} else {
			if (!lastKnownDirection.equals(character.getDirection())) {
				changeDirection(character);
			}
		}
	}

	/**
	 * Sets a new body onto the {@link NinjaRabbit} instance, after destroying the current one. Sets
	 * the current direction to the given {@link Direction}.
	 *
	 * @param character
	 *            The {@link NinjaRabbit} being updated.
	 * @param direction
	 *            The direction the {@link NinjaRabbit} is facing.
	 */
	private void changeDirection(final Entity character) {
		Vector2 position = character.getBody().getPosition();
		Vector2 velocity = character.getBody().getLinearVelocity();
		float angle = character.getBody().getAngle();

		world.destroyBody(character.getBody());
		Body newBody = bodyFactory.create(world, character.getDirection());
		newBody.setTransform(position, angle);
		newBody.setLinearVelocity(velocity);
		character.setBody(newBody);
		newBody.setUserData(character);

		lastKnownDirection = character.getDirection();
	}
}
