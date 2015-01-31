package ar.uba.fi.game.graphics;

import ar.uba.fi.game.entity.Entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;

/**
 *
 * @author nfantone
 *
 */
public interface GraphicsProcessor extends Disposable {

	/**
	 * Draws a {@link Sprite} using the position of a {@link Body}.
	 *
	 * @param character
	 *            The entity to be drawn.
	 * @param batch
	 *            The {@link Batch} that should be use to draw the corresponding {@link Sprite}.
	 */
	void draw(Entity character, Batch batch);

	/**
	 * Performs the necessary updates before drawing/rendering any {@link Sprite}. If needed, should
	 * be called before {@link GraphicsProcessor#draw(Entity, Batch)}.
	 *
	 * @param character
	 *            The entity that holds the state to be evaluated.
	 * @param camera
	 *            The instance of the camera to be updated.
	 */
	void update(Entity character, Camera camera);

	/**
	 *
	 * @param width
	 * @param height
	 */
	void resize(int width, int height);
}
