/**
 *
 */
package ar.uba.fi.game.map;

import ar.uba.fi.game.physics.BodyEditorLoader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author nfantone
 *
 */
public final class LevelFactory {
	private static final String LEVEL_MAP_FILE = "map/level.%s.tmx";
	private static final String COLLECTIBLES_LAYER = "collectibles";

	private LevelFactory() {

	};

	/**
	 * Loads a tiled map described by the given level number. Creates {@link CollectibleRenderer}
	 * for collectible layers (every layer which name starts with "collectible").
	 *
	 * @param world
	 *            The Box2D {@link World} used to create bodies and fixtures from objects layers in
	 *            the map.
	 * @param loader
	 * @param batch
	 *            A {@link Batch} to use while rendering tiles.
	 * @param assets
	 *            {@link AssetManager} from where to get audio and graphics resources for the
	 *            collectibles.
	 * @param level
	 *            Number of the level the returned render should draw.
	 * @param unitScale
	 *            Pixels per unit.
	 *
	 * @return A new {@link LevelRenderer}, ready to render the map, its bodies and collectibles.
	 */
	public static LevelRenderer create(final World world, final BodyEditorLoader loader, final Batch batch,
			final AssetManager assets, final byte level,
			final float unitScale) {
		TiledMap tiledMap = new TmxMapLoader().load(String.format(LEVEL_MAP_FILE, level));
		LevelRenderer renderer = new LevelRenderer(tiledMap, assets, batch, unitScale);

		for (MapLayer ml : tiledMap.getLayers()) {
			if (ml.getName().toLowerCase().startsWith(COLLECTIBLES_LAYER)) {
				CollectibleRenderer carrots = new CollectibleRenderer(unitScale);
				carrots.load(world, loader, assets, ml);
				renderer.addCollectibleRenderer(carrots);
			}
		}
		return renderer;
	}
}
