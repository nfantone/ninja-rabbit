package ar.uba.fi.game.audio;

import ar.uba.fi.game.AssetSystem;
import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.NinjaRabbit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/**
 * Handles audios played by actions taken by a {@link NinjaRabbit} entity.
 *
 * @author nfantone
 *
 */
public class NinjaRabbitAudioProcessor implements AudioProcessor {
	private static final int MAX_JUMP_TIMEOUT = 30;
	private static final int MAX_LIFE_LOST_TIMEOUT = 1000;

	private final AssetManager assets;
	private int jumpTimeout;
	private int lifeLostTimeout;
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
		if (character.isExecuting(NinjaRabbit.JUMP) && character.getBody().getLinearVelocity().y > 0) {
			if (jumpTimeout <= 0) {
				Sound jumpFx = assets.get(AssetSystem.JUMP_FX);
				jumpFx.stop(jumpFxId);
				jumpFxId = jumpFx.play();
				jumpTimeout = MAX_JUMP_TIMEOUT;
			} else {
				jumpTimeout -= Gdx.graphics.getDeltaTime();
			}
		} else {
			jumpTimeout = 0;
		}

		if (character.isExecuting(NinjaRabbit.DEAD)) {
			if (lifeLostTimeout <= 0) {
				assets.get(AssetSystem.LIFE_LOST_FX).play();
				lifeLostTimeout = MAX_LIFE_LOST_TIMEOUT;
			} else {
				lifeLostTimeout -= Gdx.graphics.getDeltaTime();
			}
		} else {
			lifeLostTimeout = 0;
		}
	}

	@Override
	public void dispose() {

	}
}
