package ar.uba.fi.game.audio;

import ar.uba.fi.game.entity.Entity;

import com.badlogic.gdx.utils.Disposable;

/**
 * Plays or stops audios and sfx according to a given {@link Entity} state.
 *
 * @author nfantone
 *
 */
public interface AudioProcessor extends Disposable {
	/**
	 * Starts or stops audio related to actions being performed by the {@link Entity}.
	 *
	 * @param character
	 *            The entity whose state should be evaluated for the audios to play.
	 */
	void update(final Entity character);
}
