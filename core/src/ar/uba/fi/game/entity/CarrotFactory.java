package ar.uba.fi.game.entity;

import ar.uba.fi.game.audio.AudioProcessor;
import ar.uba.fi.game.audio.CarrotAudioProcessor;
import ar.uba.fi.game.graphics.CarrotGraphicsProcessor;
import ar.uba.fi.game.graphics.CarrotPhysicsProcessor;
import ar.uba.fi.game.graphics.GraphicsProcessor;
import ar.uba.fi.game.physics.PhysicsProcessor;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Creates new instances of collectible {@link Collectible}.
 *
 * @author nfantone
 *
 */
public class CarrotFactory extends AbstractEntityFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.uba.fi.game.entity.EntityFactory#create(com.badlogic.gdx.physics.box2d.World,
	 * com.badlogic.gdx.assets.AssetManager)
	 */
	@Override
	public Entity create(final World world, final AssetManager assets) {
		PhysicsProcessor physics = new CarrotPhysicsProcessor();
		CONTACT_LISTENER.add(physics);
		world.setContactListener(CONTACT_LISTENER);
		GraphicsProcessor graphics = new CarrotGraphicsProcessor(assets);
		AudioProcessor audio = new CarrotAudioProcessor(assets);
		return new Collectible(graphics, physics, audio);
	}

}
