package ar.uba.fi.game.player;

import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.Environment;

/**
 *
 * @author nfantone
 *
 */
public class LevelPlayerStatusProcessor extends PlayerStatusProcessor {

	public LevelPlayerStatusProcessor(final CurrentPlayerStatus status) {
		super(status);
	}

	@Override
	protected void doUpdate(final Entity entity) {
		// No time left: game over
		if (getStatus().getTime() < 0) {
			entity.execute(Environment.GAME_OVER);
		}

		// No lives left: game over
		if (getStatus().getLives() < 1) {
			entity.execute(Environment.GAME_OVER);
		}

		if (entity.isExecuting(Environment.NEXT_LEVEL)) {
			getStatus().setLevel((byte) (getStatus().getLevel() + 1));
			getStatus().setTime(PlayerStatus.DEFAULT_TIME);
			entity.stop(Environment.NEXT_LEVEL);
		}
	}
}
