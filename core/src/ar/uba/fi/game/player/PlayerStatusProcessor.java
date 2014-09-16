package ar.uba.fi.game.player;

import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.NinjaRabbit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author nfantone
 *
 */
public class PlayerStatusProcessor implements PlayerStatusSubject {
	/**
	 * Time (in seconds) that should pass to make the countdown timer tick.
	 */
	private static final float IN_GAME_TIME_UNIT = 0.6f;

	/**
	 * Points earned by gathering a collectible.
	 */
	private static final int COLLECTIBLE_POINTS = 200;

	/**
	 * Metadata for the current gaming session of the player (typically displayed in the HUD or
	 * status bar).
	 */
	private final PlayerStatus status;
	private float elapsedTime;
	private final Array<PlayerStatusObserver> observers;

	public PlayerStatusProcessor() {
		status = new PlayerStatus();
		observers = new Array<>(2);
	}

	/**
	 * Updates the inner {@link PlayerStatus} according to the given {@link Entity} state.
	 *
	 * @param character
	 *            The {@link Entity} whose state is to be evaluated.
	 */
	public void update(final Entity character) {
		if (character.isExecuting(NinjaRabbit.COLLECT)) {
			status.setCollectibles((short) (status.getCollectibles() + 1));
			status.setScore(status.getScore() + COLLECTIBLE_POINTS);
			character.stop(NinjaRabbit.COLLECT);
		} else if (character.isExecuting(NinjaRabbit.DEAD)) {
			if (status.getLives() > 0) {
				status.setLives((short) (status.getLives() - 1));
			} else {
				// TODO Game over.
			}
			character.stop(NinjaRabbit.DEAD);
		}

		elapsedTime += Gdx.graphics.getDeltaTime();
		if (elapsedTime > IN_GAME_TIME_UNIT) {
			status.setTime((short) (status.getTime() - 1));
			elapsedTime = 0.0f;
			notifyObservers();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ar.uba.fi.game.player.PlayerStatusSubject#addObserver(ar.uba.fi.game.player.PlayerStatusObserver
	 * )
	 */
	@Override
	public void addObserver(final PlayerStatusObserver observer) {
		if (observer != null) {
			observers.add(observer);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.uba.fi.game.player.PlayerStatusSubject#removeObserver(ar.uba.fi.game.player.
	 * PlayerStatusObserver)
	 */
	@Override
	public void removeObserver(final PlayerStatusObserver observer) {
		if (observer != null) {
			observers.removeValue(observer, true);
		}
	}

	/**
	 * Invokes every observer's callback.
	 */
	private void notifyObservers() {
		for (PlayerStatusObserver o : observers) {
			o.onPlayerStatusChange(status);
		}
	}

}
