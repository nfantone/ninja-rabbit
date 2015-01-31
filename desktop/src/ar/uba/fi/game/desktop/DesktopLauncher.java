
package ar.uba.fi.game.desktop;

import ar.uba.fi.game.NinjaRabbitGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/** @author nfantone */
public class DesktopLauncher {

	public static void main (final String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1067;
		config.height = 600;
		config.vSyncEnabled = true;
		new LwjglApplication(new NinjaRabbitGame(), config);
	}
}
