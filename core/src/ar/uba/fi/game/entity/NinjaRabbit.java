package ar.uba.fi.game.entity;

import ar.uba.fi.game.graphics.GraphicsProcessor;
import ar.uba.fi.game.physics.PhysicsProcessor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * An animated entity representing the main character in the game (a ninja rabbit). Holds the state
 * of the character at any given time, given the user input. Each state corresponds to some key
 * frame in a certain animation.
 *
 * @author nfantone
 *
 */
public class NinjaRabbit {
	public static final short JUMP = 2;
	public static final short LEFT = 4;
	public static final short RIGHT = 8;
	public static final short DUCK = 16;
	public static final short DEAD = 32;

	/**
	 * Actions currently being performed by this entity, represented as a stream of bits.
	 */
	private short actions;

	/**
	 * The Box2D {@link Body} containing all physical properties of this entity.
	 */
	private final Body body;

	/**
	 * A {@link GraphicsProcessor} used to draw the current sprite on the screen.
	 */
	private final GraphicsProcessor graphics;

	/**
	 * A {@link PhysicsProcessor} to handle collisions and movement.
	 */
	private final PhysicsProcessor physics;

	public NinjaRabbit(final Body body, final GraphicsProcessor graphics, final PhysicsProcessor physics) {
		if (body == null) {
			throw new IllegalArgumentException("'body' cannot be null");
		}
		this.body = body;
		this.graphics = graphics;
		this.physics = physics;
	}

	/**
	 * Draws the current {@link Sprite} and handles physics interaction according to this
	 * {@link NinjaRabbit} inner state (e.g. the action it is performing: jumping, ducking, walking,
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
	 *            The action to start. One of {@value NinjaRabbit#JUMP}, {@value NinjaRabbit#LEFT},
	 *            {@value NinjaRabbit#RIGHT}, {@value NinjaRabbit#DUCK} or {@link NinjaRabbit#DEAD}.
	 *
	 *            Note that using values other than those mentioned may result in undesired
	 *            animation behavior.
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
	 *            The action to stop. One of {@value NinjaRabbit#JUMP}, {@value NinjaRabbit#LEFT},
	 *            {@value NinjaRabbit#RIGHT}, {@value NinjaRabbit#DUCK} or {@link NinjaRabbit#DEAD}.
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

	public void dispose() {
		graphics.dispose();
	}
}
