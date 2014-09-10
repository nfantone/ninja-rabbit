package ar.uba.fi.game.physics;

import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.NinjaRabbit;
import ar.uba.fi.game.physics.NinjaRabbitBodyFactory.BodyDirection;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author nfantone
 *
 */
public class NinjaRabbitBodyProcessor implements BodyProcessor {
	private final NinjaRabbitBodyFactory bodyFactory;
	private final World world;
	private BodyDirection currentDirection;

	public NinjaRabbitBodyProcessor(final World world) {
		if (world == null) {
			throw new IllegalArgumentException("'world' cannot be null");
		}
		this.bodyFactory = new NinjaRabbitBodyFactory();
		this.world = world;
	}

	/* (non-Javadoc)
	 * @see ar.uba.fi.game.physics.BodyProcessor#update(ar.uba.fi.game.entity.Entity)
	 */
	@Override
	public void update(final Entity character) {
		if (character.getBody() == null) {
			character.setBody(bodyFactory.createNinjaRabbitBody(world, BodyDirection.RIGHT));
			currentDirection = BodyDirection.RIGHT;
		} else {
			if (character.isExecuting(NinjaRabbit.RIGHT) && !BodyDirection.RIGHT.equals(currentDirection)) {
				changeBodyDirection(character, BodyDirection.RIGHT);
			} else if (character.isExecuting(NinjaRabbit.LEFT) && !BodyDirection.LEFT.equals(currentDirection)) {
				changeBodyDirection(character, BodyDirection.LEFT);
			}
		}
	}

	/**
	 * Sets a new body onto the {@link NinjaRabbit} instance, after destroying the current one. Sets
	 * the current direction to the given {@link BodyDirection}.
	 *
	 * @param character
	 *            The {@link NinjaRabbit} being updated.
	 * @param direction
	 *            The direction the {@link NinjaRabbit} is facing.
	 */
	private void changeBodyDirection(final Entity character, final BodyDirection direction) {
		Vector2 position = character.getBody().getPosition();
		Vector2 velocity = character.getBody().getLinearVelocity();
		float angle = character.getBody().getAngle();

		world.destroyBody(character.getBody());
		Body newBody = bodyFactory.createNinjaRabbitBody(world, direction);
		newBody.setTransform(position, angle);
		newBody.setLinearVelocity(velocity);
		character.setBody(newBody);

		currentDirection = direction;
	}
}
