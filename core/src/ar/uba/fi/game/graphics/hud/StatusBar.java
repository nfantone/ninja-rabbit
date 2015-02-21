package ar.uba.fi.game.graphics.hud;

import ar.uba.fi.game.Assets;
import ar.uba.fi.game.player.PlayerStatus;
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
 * Gets updated every time a {@link PlayerStatus} event is fired.
 *
 * @author nfantone
 *
 */
public class StatusBar implements PlayerStatusObserver {
	private static final String THREE_DIGITS = "%03d";
	private static final String EIGHT_DIGITS = "%08d";
	private static final String TWO_DIGITS = "%02d";
	private static final String NUMBER_GLYPHS = "0123456789";
	private static final String TIME_REGION = "time";
	private static final String LIVES_REGION = "lives";
	private static final String SMALL_CARROT_REGION = "carrot-small";

	private final Stage overlay;
	private final Label collectiblesLabel;
	private final Label livesLabel;
	private final Label scoreLabel;
	private final Label timeLabel;

	public StatusBar(final Batch batch, final AssetManager assets) {
		overlay = new Stage(new ScreenViewport(), batch);

		Label.LabelStyle style = new Label.LabelStyle();
		style.fontColor = Color.WHITE;
		style.font = assets.get(Assets.HUD_FONT);
		style.font.setFixedWidthGlyphs(NUMBER_GLYPHS);

		collectiblesLabel = new Label(String.format(TWO_DIGITS, 0), style);
		livesLabel = new Label(String.format(TWO_DIGITS, 0), style);
		scoreLabel = new Label(String.format(EIGHT_DIGITS, 0), style);
		timeLabel = new Label(String.format(THREE_DIGITS, 0), style);

		TextureAtlas hudAtlas = assets.get(Assets.NINJA_RABBIT_ATLAS);

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

	@Override
	public void onPlayerStatusChange(final PlayerStatus event) {
		collectiblesLabel.setText(String.format(TWO_DIGITS, event.getCollectibles()));
		scoreLabel.setText(String.format(EIGHT_DIGITS, event.getScore()));
		timeLabel.setText(String.format(THREE_DIGITS, event.getTime()));
		livesLabel.setText(String.format(TWO_DIGITS, event.getLives()));
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
