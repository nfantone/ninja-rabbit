package ar.uba.fi.game.map;

import ar.uba.fi.game.Assets;
import ar.uba.fi.game.entity.Collectible;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;

/**
 * Renders a {@link TiledMap}, previously created from a <code>.tmx</code> file.
 *
 * @author nfantone
 *
 */
public class LevelRenderer {
	private static final String BACKGROUND_PROPERTY = "background";
	private final OrthogonalTiledMapRenderer renderer;
	private final Array<CollectibleRenderer> collectibles;

	public LevelRenderer(final TiledMap map, final AssetManager assets, final Batch batch, final float unitScale) {
		collectibles = new Array<CollectibleRenderer>(3);

		Texture background = assets.get(map.getProperties().get(BACKGROUND_PROPERTY,
				Assets.DEFAULT_BACKGROUND.fileName, String.class),
				Texture.class);

		renderer = new BackgroundTiledMapRenderer(map, unitScale, batch, background);
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
		float width = 1.12f * camera.viewportWidth * camera.zoom;
		float height = camera.viewportHeight * camera.zoom;
		renderer.setView(camera.combined, camera.position.x - width / 2, camera.position.y - height / 2, width, height);
		renderer.render();
	}

	/**
	 * Updates every {@link Collectible} in the level.
	 */
	public void update() {
		for (CollectibleRenderer collectible : collectibles) {
			collectible.update(renderer.getBatch(), renderer.getViewBounds());
		}
	}

	public TiledMap getTiledMap() {
		return renderer.getMap();
	}

	public float getUnitScale() {
		return renderer.getUnitScale();
	}
}
