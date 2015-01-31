package ar.uba.fi.game;

import ar.uba.fi.game.player.CurrentPlayerStatus;
import ar.uba.fi.game.player.PlayerStatus;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
	public static final float PPM = 92.0f;
	public static final String GAME_TITLE = "FIUBA | Ninja Rabbit test [fps: %s]";

	private Batch batch;
	private AssetManager assetsManager;
	/**
	 * The state of the current player. Placed here for convenient use and access.
	 */
	private final CurrentPlayerStatus status;

	public NinjaRabbitGame() {
		status = new CurrentPlayerStatus();
	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		assetsManager = new AssetSystem();
		assetsManager.load(AssetSystem.NINJA_RABBIT_ATLAS);
		assetsManager.load(AssetSystem.NINJA_RABBIT_THEME);
		assetsManager.load(AssetSystem.JUMP_FX);
		assetsManager.load(AssetSystem.LIFE_LOST_FX);
		assetsManager.load(AssetSystem.GAME_OVER_FX);
		assetsManager.load(AssetSystem.CRUNCH_FX);
		assetsManager.load(AssetSystem.VICTORY_FX);
		assetsManager.load(AssetSystem.HUD_FONT);
		assetsManager.load(AssetSystem.DEFAULT_BACKGROUND);
		assetsManager.load(AssetSystem.GRASSLAND_BACKGROUND);
		assetsManager.load(AssetSystem.SWORD);
		assetsManager.finishLoading();

		setScreen(new TitleScreen(this));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
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

	/**
	 * Provides a way of polling the latest {@link PlayerStatus}.
	 *
	 * @return The current status of the player.
	 */
	public CurrentPlayerStatus getPlayerStatus() {
		return status;
	}

	/**
	 * Sets the state of the game to what it was when it first began. Player status is reset and the
	 * {@link TitleScreen} is shown.
	 */
	public void reset() {
		status.reset();
		getScreen().dispose();
		setScreen(new TitleScreen(this));
	}

	/**
	 * Brings back the last {@link LevelStartScreen} shown and resets the level, maintaining its
	 * status. It does not dispose the current {@link Screen}.
	 */
	public void restartCurrentLevel() {
		setScreen(new LevelStartScreen(this, getScreen()));
	}

	/**
	 * Disposes of the current {@link Screen} and replaces it with a new {@link LevelStartScreen}.
	 */
	public void beginNextLevel() {
		getScreen().dispose();
		setScreen(new LevelStartScreen(this));
	}
}
