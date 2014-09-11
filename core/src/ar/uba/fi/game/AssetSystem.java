package ar.uba.fi.game;

import java.io.File;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 *
 * @author nfantone
 *
 */
public class AssetSystem extends AssetManager {
	public static AssetDescriptor<TextureAtlas> NINJA_RABBIT_ATLAS = new AssetDescriptor<>("ninja-rabbit.pack",
			TextureAtlas.class);

	public static AssetDescriptor<Music> NINJA_RABBIT_THEME = new AssetDescriptor<>("sfx" + File.separator + "theme.ogg",
			Music.class);

	public static AssetDescriptor<Sound> JUMP_FX = new AssetDescriptor<>("sfx" + File.separator + "jump.ogg",
			Sound.class);

}
