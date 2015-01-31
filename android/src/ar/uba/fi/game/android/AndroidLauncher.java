package ar.uba.fi.game.android;

import android.os.Bundle;
import ar.uba.fi.game.NinjaRabbitGame;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 *
 * @author nfantone
 *
 */
public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new NinjaRabbitGame(), config);
	}
}
