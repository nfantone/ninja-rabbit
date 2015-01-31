/**
 *
 */
package ar.uba.fi.game.player;

/**
 * An immutable (read-only) version of {@link CurrentPlayerStatus}
 *
 * @author nfantone
 *
 */
public interface PlayerStatus {
	public static final short DEFAULT_TIME = 400;
	public static final short DEFAULT_LIVES = 3;
	public static final short DEFAULT_WORLD = 1;
	public static final short DEFAULT_LEVEL = 1;

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
	 * Returns the current score of the player.
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

	/**
	 * Returns the number of the level relative to the current world. Each world may contain
	 * multiple levels.
	 *
	 * @return The number of the level the player is currently in.
	 */
	byte getLevel();

	/**
	 * Returns the number of the world currently being played. A world represents a collection of
	 * themed levels.
	 *
	 * @return The number of the world the player is currently in.
	 */
	byte getWorld();
}
