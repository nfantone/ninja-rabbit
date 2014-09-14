/**
 *
 */
package ar.uba.fi.game.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Handles creation of new {@link Entity} instances;
 *
 * @author nfantone
 *
 */
public interface EntityFactory {

	/**
	 * Creates a new instance of a concrete {@link Entity}, defining its graphical, audio and
	 * physical properties.
	 *
	 * @param world
	 *            The Box2D {@link World} onto which to create the {@link Body} and {@link Fixture}
	 *            of the {@link Entity}.
	 * @param assets
	 *            The {@link AssetManager} from where to extract the graphical and audio resources.
	 *            Those resources should be loaded in the manager before calling this method.
	 * @return A ready to use instance of a new {@link Entity}.
	 */
	Entity create(final World world, final AssetManager assets);
}
