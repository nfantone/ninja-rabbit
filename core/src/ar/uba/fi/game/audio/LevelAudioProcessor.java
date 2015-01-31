package ar.uba.fi.game.audio;

import ar.uba.fi.game.AssetSystem;
import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.Environment;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.maps.MapProperties;

/**
 *
 * @author nfantone
 *
 */
public class LevelAudioProcessor implements AudioProcessor {
	private static final float THEME_VOLUME = 0.4f;
	private static final String MUSIC_PROPERTY = "music";

	private final Music theme;
	private final Music gameOverMusic;
	private final Music exitMusic;

	public LevelAudioProcessor(final AssetManager assets, final MapProperties properties) {
		theme = assets.get(properties.get(MUSIC_PROPERTY,
				AssetSystem.NINJA_RABBIT_THEME.fileName, String.class),
				Music.class);
		theme.setVolume(THEME_VOLUME);
		theme.setLooping(true);
		theme.play();

		gameOverMusic = assets.get(AssetSystem.GAME_OVER_FX);
		exitMusic = assets.get(AssetSystem.VICTORY_FX);

	}

	public LevelAudioProcessor(final AssetManager assets) {
		theme = assets.get(AssetSystem.NINJA_RABBIT_THEME);
		theme.setVolume(THEME_VOLUME);
		theme.setLooping(true);
		theme.play();

		gameOverMusic = assets.get(AssetSystem.GAME_OVER_FX);
		exitMusic = assets.get(AssetSystem.VICTORY_FX);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ar.uba.fi.game.map.LevelProcessor#update(ar.uba.fi.game.entity.Entity)
	 */
	@Override
	public void update(final Entity entity) {
		if (entity.isExecuting(Environment.GAME_OVER) && !entity.isExecuting(Environment.RESET) && !gameOverMusic.isPlaying()) {
			theme.stop();
			gameOverMusic.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(final Music music) {
					entity.execute(Environment.RESET);
				}
			});
			gameOverMusic.play();
		}

		if (entity.isExecuting(Environment.EXIT) && !entity.isExecuting(Environment.NEXT_LEVEL)
				&& !entity.isExecuting(Environment.FINISH_LEVEL) && !exitMusic.isPlaying()) {
			theme.stop();
			entity.execute(Environment.NEXT_LEVEL);
			exitMusic.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(final Music music) {
					entity.execute(Environment.FINISH_LEVEL);
				}
			});
			exitMusic.play();
		}

	}

	@Override
	public void dispose() {

	}
}
