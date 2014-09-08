package ar.uba.fi.game.graphics;

import ar.uba.fi.game.entity.NinjaRabbit;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author nfantone
 *
 */
public interface GraphicsProcessor {

	/**
	 * Draws a {@link Sprite} using the position of a {@link Body}.
	 *
	 * @param character
	 *            The entity to be drawn.
	 * @param batch
	 *            The {@link Batch} that should be use to draw the corresponding {@link Sprite}.
	 */
	void draw(NinjaRabbit character, Batch batch);

	/**
	 * Frees resources associated with this processor. Must be call when not needed anymore.
	 */
	void dispose();

}
