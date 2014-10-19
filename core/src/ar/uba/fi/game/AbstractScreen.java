package ar.uba.fi.game;

import com.badlogic.gdx.Screen;

/**
 * Base class for every {@link Screen} in the game. Holds a reference to the main game instance.
 *
 * @author nfantone
 *
 */
public abstract class AbstractScreen implements Screen {
	protected final NinjaRabbitGame game;

	public AbstractScreen(final NinjaRabbitGame game) {
		this.game = game;
	}

	@Override
	public void hide() {
		dispose();
	}
}
