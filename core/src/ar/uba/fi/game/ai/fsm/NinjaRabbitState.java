/**
 *
 */
package ar.uba.fi.game.ai.fsm;

import ar.uba.fi.game.entity.Entity;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

/**
 * @author nfantone
 *
 */
public enum NinjaRabbitState implements State<Entity> {
	/**
	 * The state this {@link Entity} is in when not moving at all.
	 */
	IDLE,
	/**
	 * The state this {@link Entity} is in when jumping.
	 */
	JUMP,
	/**
	 * The state this {@link Entity} is in when moving left.
	 */
	LEFT,

	/**
	 * The state this {@link Entity} is in when moving right.
	 */
	RIGHT,

	/**
	 * The state this {@link Entity} is in when ducking.
	 */
	DUCK;

	@Override
	public void enter(final Entity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exit(final Entity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(final Entity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onMessage(final Entity entity, final Telegram telegram) {
		// TODO Auto-generated method stub
		return false;
	}

}
