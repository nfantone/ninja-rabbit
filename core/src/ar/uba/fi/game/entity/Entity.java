package ar.uba.fi.game.entity;

import ar.uba.fi.game.audio.AudioProcessor;
import ar.uba.fi.game.graphics.GraphicsProcessor;
import ar.uba.fi.game.physics.PhysicsProcessor;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;

/**
 * Any game object with any physics, audio and/or graphics related behavior should extend this.
 *
 * @author nfantone
 *
 */
public abstract class Entity implements Disposable {
	/**
	 * Actions currently being performed by this entity, represented as a stream of bits.
	 */
	private short actions;

	/**
	 * The Box2D {@link Body} containing all physical properties of this entity.
	 */
	private Body body;

	/**
	 * The direction the body of this entity if facing (left or right).
	 */
	private Direction direction;

	/**
	 * A {@link GraphicsProcessor} used to draw the current sprite on the screen.
	 */
	private final GraphicsProcessor graphics;

	/**
	 * A {@link PhysicsProcessor} to handle collisions and movement.
	 */
	private final PhysicsProcessor physics;

	/**
	 * An {@link AudioProcessor} that handles audio events according to this entity's state.
	 */
	private final AudioProcessor audio;

	public Entity(final GraphicsProcessor graphics, final PhysicsProcessor physics, final AudioProcessor audio) {
		this.graphics = graphics;
		this.physics = physics;
		this.audio = audio;
	}

	/**
	 * Readies the state of this entity before the next step, updating before rendering. Should be
	 * called before {@link Entity#step(Batch)}.
	 *
	 * @param camera
	 *            The instance of the {@link Camera} used to show things on the screen.
	 */
	public void update(final Camera camera) {
		graphics.update(this, camera);
	}

	/**
	 * Draws the current {@link Sprite} and handles physics and audio interaction according to this
	 * {@link Entity} inner state (e.g. the action it is performing: jumping, ducking, walking,
	 * etc.).
	 *
	 * Must be called in every render step inside a {@link Batch#begin()} and {@link Batch#end()}
	 * block.
	 *
	 * @param batch
	 *            The {@link Batch} to use for drawing.
	 */
	public void step(final Batch batch) {
		physics.update(this);
		audio.update(this);
		graphics.draw(this, batch);
	}

	/**
	 * Updates the sizes of the elements drawn on the screen to correspond to the new width and
	 * height.
	 *
	 * @param width
	 *            The new width of the viewport (in pixels).
	 * @param height
	 *            The new height of the viewport (in pixels).
	 */
	public void resize(final int width, final int height) {
		graphics.resize(width, height);
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

	/**
	 * Whether this entity is performing the given action or not.
	 *
	 * @param action
	 *            The action to test against the current actions of the entity.
	 * @return true if this entity is executing the action; false, otherwise.
	 */
	public boolean isExecuting(final short action) {
		return (actions & action) == action;
	}

	/**
	 * Reset all actions to the initial state, as if none were being executed.
	 */
	public void clearActions() {
		actions = 0;
	}

	@Override
	public void dispose() {
		audio.dispose();
		graphics.dispose();
		physics.dispose();

	}

	public Body getBody() {
		return body;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setBody(final Body body) {
		this.body = body;
	}

	public void setDirection(final Direction direction) {
		this.direction = direction;
	}
}
