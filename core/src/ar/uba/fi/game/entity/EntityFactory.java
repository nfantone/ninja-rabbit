/**
 *
 */
package ar.uba.fi.game.entity;

import ar.uba.fi.game.audio.AudioProcessor;
import ar.uba.fi.game.audio.CarrotAudioProcessor;
import ar.uba.fi.game.audio.NinjaRabbitAudioProcessor;
import ar.uba.fi.game.graphics.CarrotGraphicsProcessor;
import ar.uba.fi.game.graphics.CarrotPhysicsProcessor;
import ar.uba.fi.game.graphics.GraphicsProcessor;
import ar.uba.fi.game.graphics.NinjaRabbitGraphicsProcessor;
import ar.uba.fi.game.input.NinjaRabbitInputProcessor;
import ar.uba.fi.game.physics.BodyEditorLoader;
import ar.uba.fi.game.physics.BodyProcessor;
import ar.uba.fi.game.physics.ContactListenerMultiplexer;
import ar.uba.fi.game.physics.NinjaRabbitBodyProcessor;
import ar.uba.fi.game.physics.NinjaRabbitPhysicsProcessor;
import ar.uba.fi.game.physics.PhysicsProcessor;
import ar.uba.fi.game.player.PlayerStatusObserver;
import ar.uba.fi.game.player.PlayerStatusProcessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Handles creation of new {@link Entity} instances.
 *
 * @author nfantone
 *
 */
public final class EntityFactory {
	private static final ContactListenerMultiplexer CONTACT_LISTENER = new ContactListenerMultiplexer();

	/**
	 * This should be used as a static factory. No instances allowed.
	 */
	private EntityFactory() {

	};

	/**
	 * Creates a new instance of {@link Collectible}, defining its graphical, audio and physical
	 * properties.
	 *
	 * @param world
	 *            The Box2D {@link World} onto which to create the {@link Body} and {@link Fixture}
	 *            of the {@link Entity}.
	 * @param assets
	 *            The {@link AssetManager} from where to extract the graphical and audio resources.
	 *            Those resources should be loaded in the manager before calling this method.
	 * @return A ready to use instance of a new {@link Collectible}.
	 */
	public static Entity createCollectible(final World world, final AssetManager assets) {
		PhysicsProcessor physics = new CarrotPhysicsProcessor();
		CONTACT_LISTENER.add(physics);
		world.setContactListener(CONTACT_LISTENER);
		GraphicsProcessor graphics = new CarrotGraphicsProcessor(assets);
		AudioProcessor audio = new CarrotAudioProcessor(assets);
		return new Collectible(graphics, physics, audio);
	}

	/**
	 * Creates a new instance of {@link NinjaRabbit}, defining its graphical, audio and physical
	 * properties.
	 *
	 * @param world
	 *            The Box2D {@link World} onto which to create the {@link Body} and {@link Fixture}
	 *            of the {@link Entity}.
	 * @param loader
	 *            A {@link BodyEditorLoader} to handle creation of the Entity body and fixtures.
	 * @param assets
	 *            The {@link AssetManager} from where to extract the graphical and audio resources.
	 *            Those resources should be loaded in the manager before calling this method and
	 *            won't be disposed.
	 * @param observers
	 *            An array of event receivers. Events will fire when the active player status
	 *            changes (such as losing lives, collecting items, etc.).
	 * @return A ready to use instance of a new {@link NinjaRabbit}.
	 */
	public static Entity createNinjaRabbit(final World world, final BodyEditorLoader loader, final AssetManager assets,
			final PlayerStatusObserver... observers) {
		PhysicsProcessor physics = new NinjaRabbitPhysicsProcessor();
		CONTACT_LISTENER.add(physics);
		world.setContactListener(CONTACT_LISTENER);
		GraphicsProcessor graphics = new NinjaRabbitGraphicsProcessor(assets);
		BodyProcessor bodyProcessor = new NinjaRabbitBodyProcessor(world, loader);
		AudioProcessor audio = new NinjaRabbitAudioProcessor(assets);
		PlayerStatusProcessor player = new PlayerStatusProcessor();
		if (observers != null) {
			for (PlayerStatusObserver o : observers) {
				player.addObserver(o);
			}
		}
		NinjaRabbit ninjaRabbit = new NinjaRabbit(player, bodyProcessor, graphics, physics, audio);
		Gdx.input.setInputProcessor(new NinjaRabbitInputProcessor(ninjaRabbit));
		return ninjaRabbit;
	}
}
