package ar.uba.fi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Shows a transparent overlay splash screen with a "Game Over" legend every time the player runs
 * out of lives.
 *
 * @author nfantone
 *
 */
public class GameOverOverlay implements Disposable {
	private static final String GAME_OVER_TEXT = "GAME OVER";

	private final Stage stage;
	private final ShapeRenderer overlay;

	public GameOverOverlay(final Batch batch, final AssetManager assets) {
		stage = new Stage(new ScreenViewport(), batch);
		Label.LabelStyle style = new Label.LabelStyle();
		style.fontColor = Color.WHITE;
		style.font = assets.get(Assets.HUD_FONT);
		Label gameOver = new Label(GAME_OVER_TEXT, style);

		Table table = new Table();
		table.setFillParent(true);
		table.add(gameOver).expand();

		stage.addActor(table);
		overlay = new ShapeRenderer();

	}

	public void render(final float delta) {
		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		overlay.setProjectionMatrix(stage.getViewport().getCamera().combined);
		overlay.begin(ShapeType.Filled);
		overlay.setColor(0.1f, 0.1f, 0.1f, 0.33f);
		overlay.rect(-10, -10, stage.getViewport().getWorldWidth() + 20, stage.getViewport().getWorldHeight() + 20);
		overlay.end();
		Gdx.gl20.glDisable(GL20.GL_BLEND);

		stage.getBatch().end();
		stage.act(delta);
		stage.draw();
		stage.getBatch().begin();
	}

	public void resize(final int width, final int height) {
		stage.getViewport().update(width, height);
	}

	@Override
	public void dispose() {
		overlay.dispose();
	}
}
