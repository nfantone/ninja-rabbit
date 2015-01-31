/**
 *
 */
package ar.uba.fi.game.player;

import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.NinjaRabbit;

/**
 * @author nfantone
 *
 */
public class NinjaRabbitPlayerStatusProcessor extends PlayerStatusProcessor {
	/**
	 * Points earned by gathering a collectible.
	 */
	private static final int COLLECTIBLE_POINTS = 200;

	public NinjaRabbitPlayerStatusProcessor(final CurrentPlayerStatus status) {
		super(status);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ar.uba.fi.game.player.PlayerStatusProcessor#doUpdate(ar.uba.fi.game.entity.Entity)
	 */
	@Override
	protected void doUpdate(final Entity character) {
		if (character.isExecuting(NinjaRabbit.COLLECT)) {
			getStatus().setCollectibles((short) (getStatus().getCollectibles() + 1));
			getStatus().setScore(getStatus().getScore() + COLLECTIBLE_POINTS);
			character.stop(NinjaRabbit.COLLECT);
		} else if (character.isExecuting(NinjaRabbit.DEAD) && getStatus().getLives() > 0) {
			getStatus().setLives((short) (getStatus().getLives() - 1));
		}
	}

}
