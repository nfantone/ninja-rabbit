/**
 *
 */
package ar.uba.fi.game.player;

import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Keeps track of player associated data, such as total score, count of collectibles gathered,
 * number of lives and time left.
 *
 * @author nfantone
 *
 */
public final class CurrentPlayerStatus implements PlayerStatus, Poolable {
	private static final short DEFAULT_TIME = 400;
	private static final short DEFAULT_LIVES = 3;
	private static final short DEFAULT_WORLD = 1;
	private static final short DEFAULT_LEVEL = 1;

	/**
	 * How many collectibles have the player gathered.
	 */
	private short collectibles;

	/**
	 * How many lives are left for the left.
	 */
	private short lives;

	/**
	 * The current score points.
	 */
	private int score;

	/**
	 * Remaining time to complete the level (in seconds).
	 */
	private short time;

	/**
	 * Current level number relative to the world.
	 */
	private byte level;

	/**
	 * The current world number.
	 */
	private byte world;

	public CurrentPlayerStatus() {
		lives = DEFAULT_LIVES;
		time = DEFAULT_TIME;
		level = DEFAULT_LEVEL;
		world = DEFAULT_WORLD;
	}

	@Override
	public short getCollectibles() {
		return collectibles;
	}

	protected void setCollectibles(final short collectibles) {
		this.collectibles = collectibles;
	}

	@Override
	public short getLives() {
		return lives;
	}

	protected void setLives(final short lives) {
		this.lives = lives;
	}

	@Override
	public int getScore() {
		return score;
	}

	protected void setScore(final int score) {
		this.score = score;
	}

	@Override
	public short getTime() {
		return time;
	}

	protected void setTime(final short time) {
		this.time = time;
	}

	@Override
	public byte getLevel() {
		return level;
	}

	protected void setLevel(final byte level) {
		this.level = level;
	}

	@Override
	public byte getWorld() {
		return world;
	}

	protected void setWorld(final byte world) {
		this.world = world;
	}

	@Override
	public void reset() {
		lives = DEFAULT_LIVES;
		time = DEFAULT_TIME;
		collectibles = 0;
		level = DEFAULT_LEVEL;
		world = DEFAULT_WORLD;
		score = 0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[collectibles=").append(collectibles).append(", lives=").append(lives).append(", score=")
				.append(score).append(", time=").append(time).append("]");
		return builder.toString();
	}

}
