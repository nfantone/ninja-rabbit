/**
 *
 */
package ar.uba.fi.game.player;

/**
 * An immutable (read-only) version of {@link PlayerStatus}
 *
 * @author nfantone
 *
 */
public interface PlayerStatusEvent {
	/**
	 * Returns the count of gathered collectibles so far.
	 *
	 * @return The number of collectibles owned by the player.
	 */
	short getCollectibles();

	/**
	 * Returns the number of remaining lives. When lives reaches zero, it's game over.
	 *
	 * @return The number of lives left for the player.
	 */
	short getLives();

	/**
	 * Returns rhe current score of the player.
	 *
	 * @return The total amount of points.
	 */
	int getScore();

	/**
	 * Returns the number of remaining in-game time units to finish the level. When this reaches
	 * zero it's game over.
	 * 
	 * @return The time that remains to finish the current level.
	 */
	short getTime();
}
