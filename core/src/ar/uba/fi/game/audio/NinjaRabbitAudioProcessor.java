package ar.uba.fi.game.audio;

import ar.uba.fi.game.Assets;
import ar.uba.fi.game.ai.fsm.NinjaRabbitState;
import ar.uba.fi.game.ai.msg.MessageType;
import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.NinjaRabbit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/**
 * Handles audios played by actions taken by a {@link NinjaRabbit} entity.
 *
 * @author nfantone
 *
 */
public class NinjaRabbitAudioProcessor implements AudioProcessor, Telegraph {
	private static final int MAX_JUMP_TIMEOUT = 30;

	private final AssetManager assets;
	private int jumpTimeout;
	private long jumpFxId;

	public NinjaRabbitAudioProcessor(final AssetManager assets) {
		this.assets = assets;
		MessageManager.getInstance().addListener(this, MessageType.DEAD.code());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ar.uba.fi.game.audio.AudioProcessor#update(ar.uba.fi.game.entity.Entity)
	 */
	@Override
	public void update(final Entity character) {
		if (character.isInState(NinjaRabbitState.JUMP) && character.getBody().getLinearVelocity().y > 0) {
			if (jumpTimeout <= 0) {
				Sound jumpFx = assets.get(Assets.JUMP_FX);
				jumpFx.stop(jumpFxId);
				jumpFxId = jumpFx.play();
				jumpTimeout = MAX_JUMP_TIMEOUT;
			} else {
				jumpTimeout -= Gdx.graphics.getDeltaTime();
			}
		} else {
			jumpTimeout = 0;
		}
	}

	@Override
	public boolean handleMessage(final Telegram msg) {
		assets.get(Assets.LIFE_LOST_FX).play();
		return true;
	}

	@Override
	public void dispose() {

	}
}
