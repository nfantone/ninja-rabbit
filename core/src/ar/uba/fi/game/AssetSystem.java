package ar.uba.fi.game;

import java.io.File;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 *
 * @author nfantone
 *
 */
public class AssetSystem extends AssetManager {
	public static final AssetDescriptor<Sound> CRUNCH_FX = new AssetDescriptor<>("sfx" + File.separator + "crunch.ogg",
			Sound.class);

	public static final AssetDescriptor<TextureAtlas> NINJA_RABBIT_ATLAS = new AssetDescriptor<>("ninja-rabbit.pack",
			TextureAtlas.class);

	public static final AssetDescriptor<Music> NINJA_RABBIT_THEME = new AssetDescriptor<>("sfx" + File.separator + "theme.ogg",
			Music.class);

	public static final AssetDescriptor<Sound> JUMP_FX = new AssetDescriptor<>("sfx" + File.separator + "jump.ogg",
			Sound.class);

	public static final AssetDescriptor<BitmapFont> HUD_FONT = new AssetDescriptor<>("font" + File.separator + "last-ninja.fnt",
			BitmapFont.class);

	public static final AssetDescriptor<Texture> DEFAULT_BACKGROUND = new AssetDescriptor<>("bg.png",
			Texture.class);

	public static final AssetDescriptor<Texture> GRASSLAND_BACKGROUND = new AssetDescriptor<>("bg_grasslands.png",
			Texture.class);

	public static final AssetDescriptor<Texture> SWORD = new AssetDescriptor<>("sword.png",
			Texture.class);

	public static final AssetDescriptor<Sound> GAME_OVER_FX = new AssetDescriptor<>("sfx" + File.separator + "game-over.ogg",
			Sound.class);
}
