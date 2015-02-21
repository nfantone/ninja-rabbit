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
public enum CarrotState implements State<Entity> {
	/**
	 * The state this {@link Entity} is in when moving up.
	 */
	UP,
	/**
	 * The state this {@link Entity} is in when moving down.
	 */
	DOWN;

	@Override
	public void enter(final Entity entity) {
		// TODO Auto-generated method stub
	}

	@Override
	public void exit(final Entity entity) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onMessage(final Entity entity, final Telegram telegram) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(final Entity entity) {
		// TODO Auto-generated method stub
	}
}
