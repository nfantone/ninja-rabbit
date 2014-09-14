package ar.uba.fi.game.audio;

import ar.uba.fi.game.AssetSystem;
import ar.uba.fi.game.entity.Collectible;
import ar.uba.fi.game.entity.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/**
 * Plays a sound when a {@link Collectible} is collected by the player.
 *
 * @author nfantone
 *
 */
public class CarrotAudioProcessor implements AudioProcessor {
	private final Sound collected;

	public CarrotAudioProcessor(final AssetManager manager) {
		collected = manager.get(AssetSystem.CRUNCH_FX);
	}

	@Override
	public void update(final Entity character) {
		if (character.isExecuting(Collectible.COLLECTED)) {
			collected.play();
		}
	}

}
