/**
 *
 */
package ar.uba.fi.game.player;

/**
 * @author nfantone
 *
 */
public interface PlayerStatusSubject {
	/**
	 * Registers a new observer to the list of general {@link PlayerStatusObserver} that are
	 * watching changes on a certain instance of {@link CurrentPlayerStatus}.
	 *
	 * @param observer
	 *            The instance of a concrete implementation of {@link PlayerStatusObserver} that
	 *            will be notified of changes to the {@link CurrentPlayerStatus}. If <code>null</code>,
	 *            this will have no effect.
	 */
	void addObserver(final PlayerStatusObserver observer);

	/**
	 * Removes a previously registered observer. The removed observer won't receive further
	 * notifications. It is the {@link PlayerStatusObserver} implementor responsibility to remove
	 * itself from the list of observers when it no longer requires incoming updates.
	 *
	 * @param observer
	 *            The instance of a concrete implementation of {@link PlayerStatusObserver} to be
	 *            removed. If <code>null</code>, this will have no effect.
	 */
	void removeObserver(final PlayerStatusObserver observer);
}
