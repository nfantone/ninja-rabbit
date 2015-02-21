package ar.uba.fi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Shows the game title and gives the player the option to start it or exit. This is the first
 * screen shown.
 *
 * @author nfantone
 *
 */
public class TitleScreen extends AbstractScreen {
	private static final String WALK_REGION = "walk";
	private static final String TITLE = "Ninja Rabbit";
	private static final String BEGIN_OPTION = "Start";
	private static final String EXIT_OPTION = "Exit game";

	private final Stage stage;

	public TitleScreen(final NinjaRabbitGame game) {
		super(game);

		Label.LabelStyle style = new Label.LabelStyle();
		style.fontColor = Color.WHITE;
		style.font = game.getAssetsManager().get(Assets.HUD_FONT);

		Image logo = new Image(game.getAssetsManager().get(Assets.NINJA_RABBIT_ATLAS).findRegion(WALK_REGION));

		Label titleLabel = new Label(TITLE, style);
		titleLabel.setFontScale(1.2f);
		final Table table = new Table();
		table.add(logo).colspan(2).row();
		table.add(titleLabel).padBottom(60.0f).colspan(2).row();

		final Image exitIcon = new Image(game.getAssetsManager().get(Assets.SWORD));
		exitIcon.setVisible(false);
		final Image beginIcon = new Image(game.getAssetsManager().get(Assets.SWORD));

		TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
		buttonStyle.font = game.getAssetsManager().get(Assets.HUD_FONT);

		TextButton beginButton = new TextButton(BEGIN_OPTION, buttonStyle);
		beginButton.addListener(new ClickListener() {
			@Override
			public void clicked(final InputEvent event, final float x, final float y) {
				game.setScreen(new LevelStartScreen(game));
			}

			@Override
			public void enter(final InputEvent event, final float x, final float y, final int pointer, final Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				beginIcon.setVisible(true);
				exitIcon.setVisible(false);
			}
		});

		TextButton exitButton = new TextButton(EXIT_OPTION, buttonStyle);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(final InputEvent event, final float x, final float y) {
				Gdx.app.exit();
			}

			@Override
			public void enter(final InputEvent event, final float x, final float y, final int pointer, final Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				beginIcon.setVisible(false);
				exitIcon.setVisible(true);
			}
		});

		table.add(beginIcon);
		table.add(beginButton).right().row();
		table.add(exitIcon);
		table.add(exitButton).right().row();
		table.setFillParent(true);

		table.addListener(new InputListener() {
			@Override
			public boolean keyDown(final InputEvent event, final int keycode) {
				switch (keycode) {
				case Keys.W:
				case Keys.UP:
				case Keys.S:
				case Keys.DOWN:
					beginIcon.setVisible(!beginIcon.isVisible());
					exitIcon.setVisible(!exitIcon.isVisible());
					break;
				case Keys.SPACE:
				case Keys.ENTER:
				case Keys.BUTTON_A:
					if (exitIcon.isVisible()) {
						Gdx.app.exit();
					} else if (beginIcon.isVisible()) {
						game.beginNextLevel();
					}
					break;
				}
				return super.keyDown(event, keycode);
			}
		});
		stage = new Stage(new ScreenViewport(), game.getBatch());
		stage.addActor(table);
		stage.setKeyboardFocus(table);

		Gdx.input.setInputProcessor(stage);
		// table.debug();
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
		// TODO Auto-generated method stub

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
