package ar.uba.fi.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author nfantone
 *
 */
public class NinjaRabbitGame extends Game {
	public static final float PPM = 120.0f;
	public static final String GAME_TITLE = "FIUBA | Ninja Rabbit test [fps: %s]";

	private Batch batch;
	private AssetManager assetsManager;

	@Override
	public void create() {
		batch = new SpriteBatch();
		assetsManager = new AssetSystem();
		assetsManager.load(AssetSystem.NINJA_RABBIT_ATLAS);
		assetsManager.load(AssetSystem.NINJA_RABBIT_THEME);
		assetsManager.load(AssetSystem.JUMP_FX);
		assetsManager.load(AssetSystem.CRUNCH_FX);
		assetsManager.load(AssetSystem.HUD_FONT);
		assetsManager.finishLoading();

		setScreen(new LevelScreen(this));
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.graphics.setTitle(String.format(GAME_TITLE, Gdx.graphics.getFramesPerSecond()));
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		assetsManager.dispose();
	}

	public AssetManager getAssetsManager() {
		return assetsManager;
	}

	public Batch getBatch() {
		return batch;
	}
}
