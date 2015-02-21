package ar.uba.fi.game.input;

import ar.uba.fi.game.ai.fsm.NinjaRabbitState;
import ar.uba.fi.game.ai.msg.MessageType;
import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.NinjaRabbit;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.mappings.Ouya;

/**
 * Handles input from an Ouya controller to change the inner state of a {@link NinjaRabbit}.
 *
 * @author nfantone
 *
 */
public class NinjaRabbitControllerProcessor extends ControllerAdapter {
	private final static int JUMP_KEY = Ouya.BUTTON_O;
	private final static int MOVE_AXIS = Ouya.AXIS_LEFT_X;
	// private final static int DUCK_KEY = Ouya.AXIS_LEFT_Y;
	private static final int RESET_KEY = Ouya.BUTTON_R2;

	private final Entity character;

	public NinjaRabbitControllerProcessor(final Entity ninjaRabbit) {
		if (ninjaRabbit == null) {
			throw new IllegalArgumentException("'character' cannot be null");
		}
		this.character = ninjaRabbit;
	}

	@Override
	public boolean buttonDown(final Controller controller, final int buttonCode) {
		if (buttonCode == JUMP_KEY) {
			character.changeState(NinjaRabbitState.JUMP);
		} else if (buttonCode == RESET_KEY) {
			MessageManager.getInstance().dispatchMessage(null, MessageType.DEAD.code(), character);
		}
		return super.buttonDown(controller, buttonCode);
	}

	@Override
	public boolean buttonUp(final Controller controller, final int buttonCode) {
		if (buttonCode == JUMP_KEY) {
			character.changeState(NinjaRabbitState.IDLE);
		}
		return super.buttonUp(controller, buttonCode);
	}

	@Override
	public boolean axisMoved(final Controller controller, final int axisIndex, final float value) {
		if (axisIndex == MOVE_AXIS) {
			float axisValue = 0.5f * value;
			if (Math.abs(axisValue) > Ouya.STICK_DEADZONE) {
				if (axisValue > 0) {
					character.changeState(NinjaRabbitState.RIGHT);
				} else {
					character.changeState(NinjaRabbitState.LEFT);
				}
			} else {
				character.changeState(NinjaRabbitState.IDLE);
			}
		}
		return super.axisMoved(controller, axisIndex, value);
	}
}
