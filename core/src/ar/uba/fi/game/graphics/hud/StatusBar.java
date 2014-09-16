package ar.uba.fi.game.graphics.hud;

import ar.uba.fi.game.AssetSystem;
import ar.uba.fi.game.player.PlayerStatusEvent;
import ar.uba.fi.game.player.PlayerStatusObserver;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Holds a {@link Stage} that renders a HUD layer above the player showing statistics such as the
 * number of lives left, remaining time, current score and so on.
 *
 * Gets updated every time a {@link PlayerStatusEvent} is fired.
 *
 * @author nfantone
 *
 */
public class StatusBar implements PlayerStatusObserver {
	private static final String NUMBER_GLYPHS = "0123456789";
	private static final String TIME_REGION = "time";
	private static final String LIVES_REGION = "lives";
	private static final String SMALL_CARROT_REGION = "carrot-small";

	private final Stage overlay;
	private final Label collectiblesLabel;
	private final Label livesLabel;
	private final Label scoreLabel;
	private final Label timeLabel;

	@Override
	public void onPlayerStatusChange(final PlayerStatusEvent event) {
		collectiblesLabel.setText(String.format("%02d", event.getCollectibles()));
		scoreLabel.setText(String.format("%08d", event.getScore()));
		timeLabel.setText(String.format("%03d", event.getTime()));
		livesLabel.setText(String.format("%02d", event.getLives()));
	}

	public StatusBar(final Batch batch, final AssetManager assets) {
		overlay = new Stage(new ScreenViewport(), batch);

		Label.LabelStyle style = new Label.LabelStyle();
		style.fontColor = Color.WHITE;
		style.font = assets.get(AssetSystem.HUD_FONT);
		style.font.setFixedWidthGlyphs(NUMBER_GLYPHS);

		collectiblesLabel = new Label(String.format("%02d", 0), style);
		livesLabel = new Label(String.format("%02d", 0), style);
		scoreLabel = new Label(String.format("%08d", 0), style);
		timeLabel = new Label(String.format("%03d", 0), style);

		TextureAtlas hudAtlas = assets.get(AssetSystem.NINJA_RABBIT_ATLAS);

		Table table = new Table();
		table.add(new Image(hudAtlas.findRegion(SMALL_CARROT_REGION))).padRight(8.0f);
		table.add(collectiblesLabel).bottom();
		table.add(new Image(hudAtlas.findRegion(LIVES_REGION))).padLeft(15.0f);
		table.add(livesLabel).bottom();
		table.add(scoreLabel).expandX();
		table.add(new Image(hudAtlas.findRegion(TIME_REGION))).padRight(12.0f);
		table.add(timeLabel);
		table.setFillParent(true);
		table.top();
		table.pad(15.0f);

		overlay.addActor(table);

	}

	public void resize(final int width, final int height) {
		overlay.getViewport().update(width, height, true);
	}

	public void render() {
		overlay.act();
		overlay.draw();
	}

	public void dispose() {
		overlay.dispose();
	}
}
