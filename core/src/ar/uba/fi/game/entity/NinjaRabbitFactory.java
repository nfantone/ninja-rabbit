package ar.uba.fi.game.entity;

import ar.uba.fi.game.audio.AudioProcessor;
import ar.uba.fi.game.audio.NinjaRabbitAudioProcessor;
import ar.uba.fi.game.graphics.GraphicsProcessor;
import ar.uba.fi.game.graphics.NinjaRabbitGraphicsProcessor;
import ar.uba.fi.game.input.NinjaRabbitInputProcessor;
import ar.uba.fi.game.physics.BodyProcessor;
import ar.uba.fi.game.physics.NinjaRabbitBodyProcessor;
import ar.uba.fi.game.physics.NinjaRabbitPhysicsProcessor;
import ar.uba.fi.game.physics.PhysicsProcessor;
import ar.uba.fi.game.player.PlayerStatusObserver;
import ar.uba.fi.game.player.PlayerStatusProcessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Creates new instances of {@link NinjaRabbit}.
 *
 * @author nfantone
 *
 */
public class NinjaRabbitFactory extends AbstractEntityFactory {

	@Override
	public Entity create(final World world, final AssetManager assets) {
		return create(world, assets, null);
	}

	public Entity create(final World world, final AssetManager assets, final PlayerStatusObserver observer) {
		PhysicsProcessor physics = new NinjaRabbitPhysicsProcessor();
		CONTACT_LISTENER.add(physics);
		world.setContactListener(CONTACT_LISTENER);
		GraphicsProcessor graphics = new NinjaRabbitGraphicsProcessor(assets);
		BodyProcessor bodyProcessor = new NinjaRabbitBodyProcessor(world);
		AudioProcessor audio = new NinjaRabbitAudioProcessor(assets);
		PlayerStatusProcessor player = new PlayerStatusProcessor();
		player.addObserver(observer);
		NinjaRabbit ninjaRabbit = new NinjaRabbit(player, bodyProcessor, graphics, physics, audio);
		Gdx.input.setInputProcessor(new NinjaRabbitInputProcessor(ninjaRabbit));
		return ninjaRabbit;
	}

}
