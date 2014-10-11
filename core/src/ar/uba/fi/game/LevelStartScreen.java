package ar.uba.fi.game;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

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
 *
 */
public class LevelStartScreen extends AbstractScreen {
	private static final String TIME_REGION = "time";
	private static final String LIVES_REGION = "lives";
	private static final String SMALL_CARROT_REGION = "carrot-small";

	private final Stage stage;

	public LevelStartScreen(final NinjaRabbitGame game) {
		super(game);
		stage = new Stage(new ScreenViewport(), game.getBatch());

		Label.LabelStyle style = new Label.LabelStyle();
		style.fontColor = Color.WHITE;
		style.font = game.getAssetsManager().get(AssetSystem.HUD_FONT);

		TextureAtlas hudAtlas = game.getAssetsManager().get(AssetSystem.NINJA_RABBIT_ATLAS);

		Table status = new Table();
		status.add(new Image(hudAtlas.findRegion(SMALL_CARROT_REGION))).padRight(4.0f);
		status.add(new Label("16", style)).bottom();
		status.add(new Label("00001600", style)).expandX();
		status.add(new Image(hudAtlas.findRegion(TIME_REGION))).padRight(12.0f);
		status.add(new Label("300", style)).row();
		status.setFillParent(true);
		status.top();
		status.pad(15.0f);
		stage.addActor(status);

		Table levelInfo = new Table();
		levelInfo.add(new Label("Level", style)).expandX().right().padRight(18.0f);
		levelInfo.add(new Label("1-1", style)).expandX().left().row().padTop(18.0f);
		Image livesIcon = new Image(hudAtlas.findRegion(LIVES_REGION));
		levelInfo.add(livesIcon).expandX().right().spaceRight(25f);
		levelInfo.add(new Label("x 1", style)).expandX().left().bottom();
		levelInfo.setFillParent(true);
		stage.addActor(levelInfo);

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
		stage.addAction(sequence(fadeIn(0.75f), delay(1.75f), fadeOut(0.35f),
				new Action() {
			@Override
			public boolean act(
					final float delta)
			{
				// Last action will move to the next screen
				game.setScreen(new LevelScreen(game));
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
