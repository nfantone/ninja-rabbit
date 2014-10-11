package ar.uba.fi.game.audio;

import ar.uba.fi.game.AssetSystem;
import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.NinjaRabbit;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/**
 * Handles audios played by actions taken by a {@link NinjaRabbit}.
 *
 * @author nfantone
 *
 */
public class NinjaRabbitAudioProcessor implements AudioProcessor {
	private static final int MAX_JUMP_TIMEOUT = 35;
	private static final int MAX_GAME_OVER_TIMEOUT = 600;
	private final AssetManager assets;
	private int jumpTimeout;
	private int gameOverTimeout;
	private long jumpFxId;

	public NinjaRabbitAudioProcessor(final AssetManager assets) {
		this.assets = assets;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ar.uba.fi.game.audio.AudioProcessor#update(ar.uba.fi.game.entity.Entity)
	 */
	@Override
	public void update(final Entity character) {
		if (character.isExecuting(NinjaRabbit.JUMP) && jumpTimeout <= 0 && character.getBody().getLinearVelocity().y > 0) {
			Sound jumpFx = assets.get(AssetSystem.JUMP_FX);
			jumpFx.stop(jumpFxId);
			jumpFxId = jumpFx.play();
			jumpTimeout = MAX_JUMP_TIMEOUT;
		} else {
			jumpTimeout--;
		}

		if (character.isExecuting(NinjaRabbit.GAME_OVER) && gameOverTimeout < 0) {
			Sound gameOverFx = assets.get(AssetSystem.GAME_OVER_FX);
			gameOverFx.play();
			gameOverTimeout = MAX_GAME_OVER_TIMEOUT;
		} else {
			gameOverTimeout--;
		}
	}

}
