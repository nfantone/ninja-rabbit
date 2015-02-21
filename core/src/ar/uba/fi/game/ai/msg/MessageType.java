package ar.uba.fi.game.ai.msg;

/**
 * Every possible type of message delivered through a
 * {@link com.badlogic.gdx.ai.msg.MessageDispatcher}. Inner integer codes should be used as
 * identifiers. Avoid using the ordinal.
 *
 * @author nfantone
 *
 */
public enum MessageType {
	/**
	 * Message dispatched when the character gathers a collectible
	 */
	COLLECTED(1),
	/**
	 * Message dispatched when the player runs out of lives or time.
	 */
	GAME_OVER(2),
	/**
	 * Message dispatched when the main character reaches the end of the level.
	 */
	EXIT(3),
	/**
	 * Message dispatched after FINISH_LEVEL but before starting next level.
	 */
	BEGIN_LEVEL(4),
	/**
	 * Message dispatched just before starting a new level (e.g.: after reaching the exit).
	 */
	FINISH_LEVEL(5),
	/**
	 * Message dispatched when the player loses a life.
	 */
	DEAD(6),
	/**
	 * Message dispatched when the game is reset and goes back to the title screen (e.g.: after game
	 * over).
	 */
	RESET(7);

	/**
	 * The inner code of the message.
	 */
	private int code;

	private MessageType(final int code) {
		this.code = code;
	}

	/**
	 * Returns the integer code of the message type.
	 *
	 * @return The message code.
	 */
	public int code() {
		return code;
	}
}
