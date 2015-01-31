package ar.uba.fi.game.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class TexturePackerApp {

	public static void main(final String[] args) {
		Settings settings = new Settings();
		settings.edgePadding = true;
		settings.paddingX = 2;
		settings.paddingY = 2;
		settings.pot = false;
		settings.maxHeight = 2048;
		settings.maxWidth = 2048;
		TexturePacker.process(settings, "C:\\Users\\Nicolás\\Desktop\\tiles",
				"C:\\Users\\Nicolás\\Desktop\\tiles.pack",
				"tiles.pack");
	}
}
