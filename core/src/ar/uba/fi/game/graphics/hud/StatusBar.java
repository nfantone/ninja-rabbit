package ar.uba.fi.game.graphics.hud;

import ar.uba.fi.game.AssetSystem;
import ar.uba.fi.game.player.PlayerStatusEvent;
import ar.uba.fi.game.player.PlayerStatusObserver;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 *
 * @author nfantone
 *
 */
public class StatusBar implements PlayerStatusObserver {
	private final Skin skin;
	private final Stage overlay;
	private final Label collectiblesLabel;

	@Override
	public void onPlayerStatusChange(final PlayerStatusEvent event) {
		collectiblesLabel.setText(String.format("%02d", event.getCollectibles()));
	}

	public StatusBar(final Batch batch, final AssetManager assets) {
		// A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin
		// is optional but strongly
		// recommended solely for the convenience of getting a texture, region, etc as a drawable,
		// tinted drawable, etc.
		skin = new Skin();
		overlay = new Stage(new ScreenViewport(), batch);

		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		// Store the default libgdx font under the name "default".
		skin.add("default", new BitmapFont());

		Label.LabelStyle style = new Label.LabelStyle();
		style.fontColor = Color.WHITE;
		style.font = assets.get(AssetSystem.HUD_FONT);
		skin.add("default", style);

		collectiblesLabel = new Label("00", skin);

		Table table = new Table(skin);
		table.add(new Image(assets.get(AssetSystem.CARROT_TEXTURE))).padLeft(15.0f);
		table.add(collectiblesLabel).padLeft(10.0f);
		table.left();

		overlay.addActor(table);

		// table.setDebug(true);
		table.setSize(overlay.getWidth(), overlay.getHeight() * 0.15f);
		table.setPosition(0.0f, overlay.getHeight() - table.getHeight());
	}

	public void resize(final int width, final int height) {
		overlay.getViewport().update(width, height, true);
	}

	public void render() {
		overlay.act();
		overlay.draw();
	}

	public void dispose() {
		skin.dispose();
		overlay.dispose();
	}

}
