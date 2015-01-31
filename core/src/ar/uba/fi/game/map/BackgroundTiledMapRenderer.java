package ar.uba.fi.game.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * {@link TiledMapRenderer} that allows drawing a background before rendering the tiled map layers,
 * inside the same {@link Batch#begin()} and {@link Batch#end()} block.
 *
 * The texture used as a background will be stretched to fill the whole viewport.
 *
 * @author nfantone
 *
 */
public class BackgroundTiledMapRenderer extends OrthogonalTiledMapRenderer {
	private final Texture background;

	public BackgroundTiledMapRenderer(final TiledMap map, final float unitScale, final Batch batch, final Texture background) {
		super(map, unitScale, batch);
		this.background = background;
	}

	@Override
	protected void beginRender() {
		super.beginRender();

		// Draw the background
		getBatch().draw(background, viewBounds.x, viewBounds.y, viewBounds.getWidth(), viewBounds.getHeight());
	}

}
