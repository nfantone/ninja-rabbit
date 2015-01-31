package ar.uba.fi.game.player;

import ar.uba.fi.game.entity.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author nfantone
 *
 */
public abstract class PlayerStatusProcessor implements PlayerStatusSubject {
	/**
	 * Time (in seconds) that should pass to make the countdown timer tick.
	 */
	private static final float IN_GAME_TIME_UNIT = 0.6f;

	/**
	 * Metadata for the current gaming session of the player (typically displayed in the HUD or
	 * status bar).
	 */
	private final CurrentPlayerStatus status;
	private float elapsedTime;
	private final Array<PlayerStatusObserver> observers;

	public PlayerStatusProcessor() {
		this(new CurrentPlayerStatus());
	}

	/**
	 * Creates a new {@link PlayerStatusProcessor}. This constructor is useful when the management
	 * of the {@link CurrentPlayerStatus} is a also the responsibility of some other layer or a
	 * global state.
	 *
	 * @param status
	 *            The instance of {@link CurrentPlayerStatus} that will be used internally by this
	 *            processor. Be warned that modifications of this instance outside the scope of this
	 *            class will affect its behavior.
	 */
	public PlayerStatusProcessor(final CurrentPlayerStatus status) {
		this.status = status;
		observers = new Array<PlayerStatusObserver>(2);
	}

	/**
	 * Updates the inner {@link CurrentPlayerStatus} according to the given {@link Entity} state.
	 *
	 * @param character
	 *            The {@link Entity} whose state is to be evaluated.
	 */
	public void update(final Entity character) {
		doUpdate(character);

		elapsedTime += Gdx.graphics.getDeltaTime();
		if (elapsedTime > IN_GAME_TIME_UNIT) {
			status.setTime((short) (status.getTime() - 1));
			elapsedTime = 0.0f;
			notifyObservers();
		}
	}

	protected abstract void doUpdate(final Entity character);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ar.uba.fi.game.player.PlayerStatusSubject#addObserver(ar.uba.fi.game.player.PlayerStatusObserver
	 * )
	 */
	@Override
	public void addObserver(final PlayerStatusObserver observer) {
		if (observer != null && !observers.contains(observer, true)) {
			observers.add(observer);
			observer.onPlayerStatusChange(status);
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
	protected void notifyObservers() {
		for (PlayerStatusObserver o : observers) {
			o.onPlayerStatusChange(status);
		}
	}

	protected CurrentPlayerStatus getStatus() {
		return status;
	}
}
