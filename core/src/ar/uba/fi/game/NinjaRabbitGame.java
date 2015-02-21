package ar.uba.fi.game;

import ar.uba.fi.game.ai.msg.MessageType;
import ar.uba.fi.game.player.CurrentPlayerStatus;
import ar.uba.fi.game.player.PlayerStatus;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author nfantone
 *
 */
public class NinjaRabbitGame extends Game implements Telegraph {
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
		assetsManager = new AssetManager();
		assetsManager.load(Assets.NINJA_RABBIT_ATLAS);
		assetsManager.load(Assets.NINJA_RABBIT_THEME);
		assetsManager.load(Assets.JUMP_FX);
		assetsManager.load(Assets.LIFE_LOST_FX);
		assetsManager.load(Assets.GAME_OVER_FX);
		assetsManager.load(Assets.CRUNCH_FX);
		assetsManager.load(Assets.VICTORY_FX);
		assetsManager.load(Assets.HUD_FONT);
		assetsManager.load(Assets.DEFAULT_BACKGROUND);
		assetsManager.load(Assets.GRASSLAND_BACKGROUND);
		assetsManager.load(Assets.SWORD);
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
		super.dispose();
		batch.dispose();
		assetsManager.dispose();
	}

	/**
	 * Sets the state of the game to what it was when it first began. Player status is reset and the
	 * {@link TitleScreen} is shown.
	 */
	public void reset() {
		status.reset();
		getScreen().dispose();
		addListeners();
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
		addListeners();
		setScreen(new LevelStartScreen(this));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.badlogic.gdx.ai.msg.Telegraph#handleMessage(com.badlogic.gdx.ai.msg.Telegram)
	 */
	@Override
	public boolean handleMessage(final Telegram msg) {
		if (msg.message == MessageType.RESET.code()) {
			reset();
		} else if (msg.message == MessageType.DEAD.code()) {
			restartCurrentLevel();
		} else if (msg.message == MessageType.BEGIN_LEVEL.code()) {
			beginNextLevel();
		}
		return false;
	}

	/**
	 * Clears all remaining messages and listeners in the {@link MessageManager} instance and
	 * re-adds the ones from related to this {@link Telegraph}.
	 */
	private void addListeners() {
		MessageManager manager = MessageManager.getInstance();
		manager.clear();
		manager.addListeners(this, MessageType.GAME_OVER.code(),
				MessageType.RESET.code(), MessageType.BEGIN_LEVEL.code());
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
}
