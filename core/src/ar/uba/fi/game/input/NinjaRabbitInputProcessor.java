package ar.uba.fi.game.input;

import ar.uba.fi.game.ai.fsm.NinjaRabbitState;
import ar.uba.fi.game.ai.msg.MessageType;
import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.NinjaRabbit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;

/**
 * Handles input from keyboard to change the inner state of a {@link NinjaRabbit}.
 *
 * @author nfantone
 *
 */
public class NinjaRabbitInputProcessor extends InputAdapter implements Telegraph {
	private final static int JUMP_KEY = Keys.W;
	private final static int LEFT_KEY = Keys.A;
	private final static int DUCK_KEY = Keys.S;
	private final static int RIGHT_KEY = Keys.D;
	private static final int RESET_KEY = Keys.BACKSPACE;

	private final Entity character;

	public NinjaRabbitInputProcessor(final Entity ninjaRabbit) {
		if (ninjaRabbit == null) {
			throw new IllegalArgumentException("'character' cannot be null");
		}
		this.character = ninjaRabbit;
		MessageManager.getInstance().addListener(this, MessageType.EXIT.code());
	}

	@Override
	public boolean keyDown(final int keycode) {
		switch (keycode) {
		case JUMP_KEY:
			character.changeState(NinjaRabbitState.JUMP);
			break;
		case LEFT_KEY:
			character.changeState(NinjaRabbitState.LEFT);
			break;
		case RIGHT_KEY:
			character.changeState(NinjaRabbitState.RIGHT);
			break;
		case DUCK_KEY:
			// character.execute(NinjaRabbit.DUCK);
			break;
		case RESET_KEY:
			MessageManager.getInstance().dispatchMessage(null, MessageType.DEAD.code(), character);
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
			if (Gdx.input.isKeyPressed(RIGHT_KEY)) {
				character.changeState(NinjaRabbitState.RIGHT);
			} else if (Gdx.input.isKeyPressed(LEFT_KEY)) {
				character.changeState(NinjaRabbitState.LEFT);
			} else {
				character.changeState(NinjaRabbitState.IDLE);
			}
			break;
		case LEFT_KEY:
			if (Gdx.input.isKeyPressed(RIGHT_KEY)) {
				character.changeState(NinjaRabbitState.RIGHT);
			} else {
				character.changeState(NinjaRabbitState.IDLE);
			}
			break;
		case RIGHT_KEY:
			if (Gdx.input.isKeyPressed(LEFT_KEY)) {
				character.changeState(NinjaRabbitState.LEFT);
			} else {
				character.changeState(NinjaRabbitState.IDLE);
			}
			break;
		case DUCK_KEY:
			// character.changeState(NinjaRabbitState.IDLE);
			break;
		default:
			break;
		}
		return super.keyUp(keycode);
	}

	@Override
	public boolean handleMessage(final Telegram msg) {
		Gdx.input.setInputProcessor(null);
		return true;
	}
}
