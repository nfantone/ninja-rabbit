package ar.uba.fi.game.entity;

import ar.uba.fi.game.audio.AudioProcessor;
import ar.uba.fi.game.graphics.GraphicsProcessor;
import ar.uba.fi.game.physics.PhysicsProcessor;

/**
 *
 * @author nfantone
 *
 */
public class Collectible extends Entity {
	public static final short COLLECTED = 2;
	public static final short UP = 4;

	public Collectible(final GraphicsProcessor graphics, final PhysicsProcessor physics, final AudioProcessor audio) {
		super(graphics, physics, audio);
	}

}
