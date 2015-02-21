package ar.uba.fi.game;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import ar.uba.fi.game.player.PlayerStatus;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Preview screen that shows stats and information about the player and the current level, before it
 * begins.
 *
 * @author nfantone
 */
public class LevelStartScreen extends AbstractScreen {
	private static final String THREE_DIGITS = "%03d";
	private static final String EIGHT_DIGITS = "%08d";
	private static final String TWO_DIGITS = "%02d";
	private static final String LIVES_FORMAT = "x " + TWO_DIGITS;
	private static final String LEVEL_LABEL = "Level";
	private static final String LEVEL_FORMAT = "%s - %s";

	private static final String TIME_REGION = "time";
	private static final String LIVES_REGION = "lives";
	private static final String SMALL_CARROT_REGION = "carrot-small";

	private final Stage stage;
	private Screen levelScreen;

	public LevelStartScreen(final NinjaRabbitGame game) {
		super(game);
		PlayerStatus playerStatus = game.getPlayerStatus();
		stage = new Stage(new ScreenViewport(), game.getBatch());

		Label.LabelStyle style = new Label.LabelStyle();
		style.fontColor = Color.WHITE;
		style.font = game.getAssetsManager().get(Assets.HUD_FONT);

		TextureAtlas hudAtlas = game.getAssetsManager().get(Assets.NINJA_RABBIT_ATLAS);

		Label collectiblesLabel = new Label(String.format(TWO_DIGITS, playerStatus.getCollectibles()), style);
		Label livesLabel = new Label(String.format(LIVES_FORMAT, playerStatus.getLives()), style);
		Label scoreLabel = new Label(String.format(EIGHT_DIGITS, playerStatus.getScore()), style);
		Label timeLabel = new Label(String.format(THREE_DIGITS, playerStatus.getTime()), style);

		Table status = new Table();
		status.add(new Image(hudAtlas.findRegion(SMALL_CARROT_REGION))).padRight(4.0f);
		status.add(collectiblesLabel).bottom();
		status.add(scoreLabel).expandX();
		status.add(new Image(hudAtlas.findRegion(TIME_REGION))).padRight(12.0f);
		status.add(timeLabel).row();
		status.setFillParent(true);
		status.top();
		status.pad(15.0f);
		stage.addActor(status);

		Table levelInfo = new Table();
		levelInfo.add(new Label(LEVEL_LABEL, style)).expandX().right().padRight(18.0f);
		levelInfo.add(new Label(String.format(LEVEL_FORMAT, playerStatus.getWorld(), playerStatus.getLevel()), style)).expandX()
				.left().padTop(18.0f).row();
		Image livesIcon = new Image(hudAtlas.findRegion(LIVES_REGION));
		levelInfo.add(livesIcon).expandX().right().spaceRight(25f);
		levelInfo.add(livesLabel).expandX().left().bottom();
		levelInfo.setFillParent(true);
		stage.addActor(levelInfo);

	}

	public LevelStartScreen(final NinjaRabbitGame game, final Screen levelScreen) {
		this(game);
		this.levelScreen = levelScreen;
	}

	@Override
	public void render(final float delta) {
		stage.act(delta);
		stage.draw();

	}

	@Override
	public void resize(final int width, final int height) {
		stage.getViewport().update(width, height, true);

	}

	@Override
	public void show() {
		// Fade in / fade out effect
		stage.addAction(sequence(fadeIn(0.75f), delay(1.75f), fadeOut(0.35f), new Action() {
			@Override
			public boolean act(final float delta) {
				// Last action will move to the next screen
				if (levelScreen == null) {
					game.setScreen(new LevelScreen(game));
				} else {
					game.setScreen(levelScreen);
				}
				return true;
			}
		}));
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
