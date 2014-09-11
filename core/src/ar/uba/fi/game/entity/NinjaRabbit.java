package ar.uba.fi.game.entity;

import ar.uba.fi.game.audio.AudioProcessor;
import ar.uba.fi.game.graphics.GraphicsProcessor;
import ar.uba.fi.game.physics.BodyProcessor;
import ar.uba.fi.game.physics.PhysicsProcessor;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * An animated entity representing the main character in the game (a ninja rabbit). Holds the state
 * of the character at any given time, given the user input. Each state corresponds to some key
 * frame in a certain animation.
 *
 * @author nfantone
 *
 */
public class NinjaRabbit extends Entity {
	public static final short JUMP = 2;
	public static final short LEFT = 4;
	public static final short RIGHT = 8;
	public static final short DUCK = 16;
	public static final short DEAD = 32;

	/**
	 * A component used to change the body of this {@link Entity} according this the action being
	 * executed, if necessary.
	 */
	protected final BodyProcessor bodyProcessor;

	public NinjaRabbit(final BodyProcessor bodyProcessor, final GraphicsProcessor graphics, final PhysicsProcessor physics,
			final AudioProcessor audio) {
		super(graphics, physics, audio);
		this.bodyProcessor = bodyProcessor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.uba.fi.game.entity.Entity#update(com.badlogic.gdx.graphics.g2d.Batch)
	 */
	@Override
	public void update(final Batch batch) {
		bodyProcessor.update(this);
		super.update(batch);
	}
}
