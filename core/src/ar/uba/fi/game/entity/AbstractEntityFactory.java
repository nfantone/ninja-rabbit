package ar.uba.fi.game.entity;

import ar.uba.fi.game.physics.ContactListenerMultiplexer;

import com.badlogic.gdx.physics.box2d.ContactListener;

/**
 * All regular implementations of {@link EntityFactory} should extend this instead of directly
 * implementing the interface if they have the need to declare a {@link ContactListener} for their
 * entities.
 * 
 * @author nfantone
 *
 */
public abstract class AbstractEntityFactory implements EntityFactory {
	protected static final ContactListenerMultiplexer CONTACT_LISTENER = new ContactListenerMultiplexer();
}
