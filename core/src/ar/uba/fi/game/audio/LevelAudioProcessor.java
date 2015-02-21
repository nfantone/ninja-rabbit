package ar.uba.fi.game.audio;

import ar.uba.fi.game.Assets;
import ar.uba.fi.game.ai.msg.MessageType;
import ar.uba.fi.game.entity.Entity;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.maps.MapProperties;

/**
 *
 * @author nfantone
 *
 */
public class LevelAudioProcessor implements AudioProcessor, Telegraph {
	private static final float THEME_VOLUME = 0.4f;
	private static final String MUSIC_PROPERTY = "music";

	private final Music theme;
	private final Music gameOverMusic;
	private final Music exitMusic;

	public LevelAudioProcessor(final AssetManager assets, final MapProperties properties) {
		if (properties == null) {
			theme = assets.get(Assets.NINJA_RABBIT_THEME);
		} else {
			theme = assets.get(properties.get(MUSIC_PROPERTY,
					Assets.NINJA_RABBIT_THEME.fileName, String.class),
					Music.class);
		}
		theme.setVolume(THEME_VOLUME);
		theme.setLooping(true);
		theme.play();

		final Telegraph that = this;
		gameOverMusic = assets.get(Assets.GAME_OVER_FX);
		gameOverMusic.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(final Music music) {
				MessageManager.getInstance().dispatchMessage(that, MessageType.RESET.code());
			}
		});

		exitMusic = assets.get(Assets.VICTORY_FX);
		exitMusic.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(final Music music) {
				MessageManager.getInstance().dispatchMessage(that, MessageType.FINISH_LEVEL.code());
			}
		});
		MessageManager.getInstance().addListeners(this, MessageType.GAME_OVER.code(), MessageType.EXIT.code());
	}

	public LevelAudioProcessor(final AssetManager assets) {
		this(assets, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.uba.fi.game.map.LevelProcessor#update(ar.uba.fi.game.entity.Entity)
	 */
	@Override
	public void update(final Entity entity) {

	}

	@Override
	public boolean handleMessage(final Telegram msg) {
		if (msg.message == MessageType.GAME_OVER.code()) {
			theme.stop();
			gameOverMusic.play();
		} else if (msg.message == MessageType.EXIT.code()) {
			theme.stop();
			exitMusic.play();
		}
		return true;
	}

	@Override
	public void dispose() {

	}
}
