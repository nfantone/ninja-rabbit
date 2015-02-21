package ar.uba.fi.game.player;

import ar.uba.fi.game.ai.msg.MessageType;
import ar.uba.fi.game.entity.Entity;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;

/**
 *
 * @author nfantone
 *
 */
public class LevelPlayerStatusProcessor extends PlayerStatusProcessor implements Telegraph {
	private boolean gameOverSignaled;

	public LevelPlayerStatusProcessor(final CurrentPlayerStatus status) {
		super(status);
		MessageManager.getInstance().addListener(this, MessageType.FINISH_LEVEL.code());
	}

	@Override
	protected void doUpdate(final Entity entity) {
		// No time or lives left: game over
		if (getStatus().getTime() < 0 || getStatus().getLives() < 1 && !gameOverSignaled) {
			MessageManager.getInstance().dispatchMessage(this, MessageType.GAME_OVER.code());
			gameOverSignaled = true;
		}
	}

	@Override
	public boolean handleMessage(final Telegram msg) {
		getStatus().setLevel((byte) (getStatus().getLevel() + 1));
		getStatus().setTime(PlayerStatus.DEFAULT_TIME);
		MessageManager.getInstance().dispatchMessage(this, MessageType.BEGIN_LEVEL.code());
		return true;
	}
}
