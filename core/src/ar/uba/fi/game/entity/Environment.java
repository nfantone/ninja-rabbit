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
