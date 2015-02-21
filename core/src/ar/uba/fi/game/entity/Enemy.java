package ar.uba.fi.game.entity;

import ar.uba.fi.game.audio.AudioProcessor;
import ar.uba.fi.game.graphics.GraphicsProcessor;
import ar.uba.fi.game.physics.PhysicsProcessor;

/**
 *
 * @author nfantone
 *
 */
public class Enemy extends Entity {

	public Enemy(final GraphicsProcessor graphics, final PhysicsProcessor physics, final AudioProcessor audio) {
		super(graphics, physics, audio);
	}

}
