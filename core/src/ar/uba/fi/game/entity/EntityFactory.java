/**
 *
 */
package ar.uba.fi.game.entity;

import ar.uba.fi.game.audio.AudioProcessor;
import ar.uba.fi.game.audio.NinjaRabbitAudioProcessor;
import ar.uba.fi.game.graphics.GraphicsProcessor;
import ar.uba.fi.game.graphics.NinjaRabbitGraphicsProcessor;
import ar.uba.fi.game.physics.BodyProcessor;
import ar.uba.fi.game.physics.NinjaRabbitBodyProcessor;
import ar.uba.fi.game.physics.NinjaRabbitPhysicsProcessor;
import ar.uba.fi.game.physics.PhysicsProcessor;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author nfantone
 *
 */
public final class EntityFactory {

	/**
	 * No instances of this class should be created. This is meant to be used as a static factory.
	 */
	private EntityFactory() {
	};

	/**
	 * Creates a new instance of a {@link NinjaRabbit}, defining its graphical and physical
	 * properties.
	 *
	 * @param world
	 *            The Box2D {@link World} onto which to create the {@link Body} and {@link Fixture}
	 *            of the {@link NinjaRabbit}.
	 * @return A ready to use instance of a new {@link NinjaRabbit}.
	 */
	public static NinjaRabbit createNinjaRabbit(final World world, final AssetManager assets) {
		PhysicsProcessor physics = new NinjaRabbitPhysicsProcessor();
		world.setContactListener(physics);
		GraphicsProcessor graphics = new NinjaRabbitGraphicsProcessor(assets);
		BodyProcessor bodyProcessor = new NinjaRabbitBodyProcessor(world);
		AudioProcessor audio = new NinjaRabbitAudioProcessor(assets);
		return new NinjaRabbit(bodyProcessor, graphics, physics, audio);
	}

}
