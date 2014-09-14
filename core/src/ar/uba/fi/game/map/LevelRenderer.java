package ar.uba.fi.game.map;

import ar.uba.fi.game.entity.Collectible;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;

/**
 * Renders a {@link TiledMap}, previously created from a <code>.tmx</code> file.
 *
 * @author nfantone
 *
 */
public class LevelRenderer {
	private final TiledMapRenderer renderer;
	private final TiledMap tiledMap;
	private final Array<CollectibleRenderer> collectibles;

	public LevelRenderer(final TiledMap map, final float unitScale) {
		collectibles = new Array<>(3);
		this.tiledMap = map;
		renderer = new OrthogonalTiledMapRenderer(map, unitScale);
	}

	public void addCollectibleRenderer(final CollectibleRenderer renderer) {
		collectibles.add(renderer);
	}

	/**
	 * Renders the tiled map for the current frame. Sets its view as the given
	 * {@link OrthographicCamera}.
	 *
	 * @param camera
	 *            The camera used to show the map.
	 */
	public void render(final OrthographicCamera camera) {
		renderer.setView(camera);
		renderer.render();
	}

	/**
	 * Updates every {@link Collectible} in the level.
	 *
	 * @param batch
	 *            The {@link Batch} used to draw the sprites of the {@link Collectible}.
	 */
	public void update(final Batch batch) {
		for (CollectibleRenderer collectible : collectibles) {
			collectible.update(batch);
		}

	}

	public TiledMap getTiledMap() {
		return tiledMap;
	}
}
