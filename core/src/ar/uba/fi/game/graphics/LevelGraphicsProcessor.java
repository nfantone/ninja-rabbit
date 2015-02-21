/**
 *
 */
package ar.uba.fi.game.graphics;

import ar.uba.fi.game.GameOverOverlay;
import ar.uba.fi.game.ai.msg.MessageType;
import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.map.LevelRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * @author nfantone
 *
 */
public class LevelGraphicsProcessor implements GraphicsProcessor, Telegraph {
	private final LevelRenderer mapRenderer;
	private final GameOverOverlay gameOver;
	private boolean renderGameOver;

	public LevelGraphicsProcessor(final AssetManager assets, final Batch batch, final LevelRenderer mapRenderer) {
		gameOver = new GameOverOverlay(batch, assets);
		this.mapRenderer = mapRenderer;
		MessageManager.getInstance().addListeners(this, MessageType.GAME_OVER.code());
	}

	@Override
	public void update(final Entity character, final Camera camera) {
		mapRenderer.render((OrthographicCamera) camera);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ar.uba.fi.game.graphics.GraphicsProcessor#draw(ar.uba.fi.game.entity.Entity,
	 * com.badlogic.gdx.graphics.g2d.Batch)
	 */
	@Override
	public void draw(final Entity entity, final Batch batch) {
		mapRenderer.update();

		if (renderGameOver) {
			gameOver.render(Gdx.graphics.getDeltaTime());
		}
	}

	@Override
	public boolean handleMessage(final Telegram msg) {
		renderGameOver = msg.message == MessageType.GAME_OVER.code();
		return true;
	}

	@Override
	public void resize(final int width, final int height) {
		gameOver.resize(width, height);
	}

	@Override
	public void dispose() {
		gameOver.dispose();
	}
}
