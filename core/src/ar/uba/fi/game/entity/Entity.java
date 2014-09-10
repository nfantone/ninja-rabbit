package ar.uba.fi.game.entity;

import ar.uba.fi.game.graphics.GraphicsProcessor;
import ar.uba.fi.game.physics.PhysicsProcessor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Any game object with any physics and/or graphics related behavior should extend this.
 *
 * @author nfantone
 *
 */
public abstract class Entity {

	/**
	 * Actions currently being performed by this entity, represented as a stream of bits.
	 */
	private short actions;
	/**
	 * The Box2D {@link Body} containing all physical properties of this entity.
	 */
	private Body body;
	/**
	 * A {@link GraphicsProcessor} used to draw the current sprite on the screen.
	 */
	protected final GraphicsProcessor graphics;
	/**
	 * A {@link PhysicsProcessor} to handle collisions and movement.
	 */
	protected final PhysicsProcessor physics;

	public Entity(final GraphicsProcessor graphics, final PhysicsProcessor physics) {
		this.graphics = graphics;
		this.physics = physics;
	}

	/**
	 * Draws the current {@link Sprite} and handles physics interaction according to this
	 * {@link Entity} inner state (e.g. the action it is performing: jumping, ducking, walking,
	 * etc.).
	 *
	 * Must be called in every render step inside a {@link Batch#begin()} and {@link Batch#end()}
	 * block.
	 *
	 * @param batch
	 *            The {@link Batch} to use for drawing.
	 */
	public void update(final Batch batch) {
		physics.update(this);
		graphics.draw(this, batch);
	}

	/**
	 * Sets an action to be performed in the next execution step for this character.
	 *
	 * @param action
	 *            The action identifier. Each {@link Entity} may define its own.
	 *
	 * @return A binary representation of all the actions being executed.
	 */
	public short execute(final short action) {
		actions |= action;
		return actions;
	}

	/**
	 * Halts the execution of the given action for the next rendering step.
	 *
	 * @param action
	 *            The action identifier. Each {@link Entity} may define its own.
	 * @return A binary representation of all the actions still being executed, after the exclusion
	 *         of the one being stopped.
	 */
	public short stop(final short action) {
		actions &= ~action;
		return actions;
	}

	public boolean isExecuting(final short action) {
		return (actions & action) == action;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(final Body body) {
		this.body = body;
	}

	public void dispose() {
		graphics.dispose();
	}
}
