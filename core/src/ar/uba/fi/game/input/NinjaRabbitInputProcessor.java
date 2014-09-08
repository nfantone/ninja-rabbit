package ar.uba.fi.game.input;

import ar.uba.fi.game.entity.NinjaRabbit;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

/**
 *
 * @author nfantone
 *
 */
public class NinjaRabbitInputProcessor extends InputAdapter {
	private final static int JUMP_KEY = Keys.W;
	private final static int LEFT_KEY = Keys.A;
	private final static int DUCK_KEY = Keys.S;
	private final static int RIGHT_KEY = Keys.D;

	private final NinjaRabbit character;

	public NinjaRabbitInputProcessor(final NinjaRabbit character) {
		if (character == null) {
			throw new IllegalArgumentException("'character' cannot be null");
		}
		this.character = character;
	}

	@Override
	public boolean keyDown(final int keycode) {
		switch (keycode) {
		case JUMP_KEY:
			character.execute(NinjaRabbit.JUMP);
			break;
		case LEFT_KEY:
			character.execute(NinjaRabbit.LEFT);
			break;
		case RIGHT_KEY:
			character.execute(NinjaRabbit.RIGHT);
			break;
		case DUCK_KEY:
			character.execute(NinjaRabbit.DUCK);
			break;
		default:
			break;
		}
		return super.keyDown(keycode);
	}

	@Override
	public boolean keyUp(final int keycode) {
		switch (keycode) {
		case JUMP_KEY:
			character.stop(NinjaRabbit.JUMP);
			break;
		case LEFT_KEY:
			character.stop(NinjaRabbit.LEFT);
			break;
		case RIGHT_KEY:
			character.stop(NinjaRabbit.RIGHT);
			break;
		case DUCK_KEY:
			character.stop(NinjaRabbit.DUCK);
			break;
		default:
			break;
		}
		return super.keyUp(keycode);
	}
}
