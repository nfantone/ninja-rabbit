/**
 *
 */
package ar.uba.fi.game.entity;

import ar.uba.fi.game.audio.AudioProcessor;
import ar.uba.fi.game.graphics.GraphicsProcessor;
import ar.uba.fi.game.physics.PhysicsProcessor;
import ar.uba.fi.game.player.PlayerStatusProcessor;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * An {@link Entity} representing the entire level as separate audio, physical and graphics
 * components.
 *
 * @author nfantone
 *
 */
public class Environment extends Entity {
	/**
	 * The state this {@link Entity} is in after there are no more remaining lives.
	 */
	public static final short GAME_OVER = 2;

	/**
	 * The state this {@link Entity} enters after the end of a level has been reached.
	 */
	public static final short EXIT = 4;

	/**
	 * The state this {@link Entity} is in immediately after {@value Environment#GAME_OVER},
	 * signaling that the game should be restarted.
	 */
	public static final short RESET = 8;

	/**
	 * The state this {@link Entity} is in following {@value Environment#EXIT}, signaling that the
	 * player status should be updated to show that the player has reached a new level.
	 */
	public static final short NEXT_LEVEL = 16;

	/**
	 *
	 */
	public static final short FINISH_LEVEL = 32;

	/**
	 * A component that updates the status of the player during the gaming session according to the
	 * Environments inner state.
	 */
	private final PlayerStatusProcessor player;

	public Environment(final GraphicsProcessor graphics, final PhysicsProcessor physics, final AudioProcessor audio,
			final PlayerStatusProcessor player) {
		super(graphics, physics, audio);
		this.player = player;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.uba.fi.game.entity.Entity#step(com.badlogic.gdx.graphics.g2d.Batch)
	 */
	@Override
	public void step(final Batch batch) {
		player.update(this);
		super.step(batch);
	}
}
