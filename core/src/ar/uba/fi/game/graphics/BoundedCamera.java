package ar.uba.fi.game.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * An {@link OrthographicCamera} that cannot go beyond an specified segment area given its
 * coordinates on the horizontal axis.
 *
 * @author nfantone
 *
 */
public class BoundedCamera extends OrthographicCamera {
	private float xmin;
	private float xmax;

	public BoundedCamera() {
		this(0.0f, 0.0f);
	}

	public BoundedCamera(final float xmin, final float xmax) {
		super();
		setBounds(xmin, xmax);
	}

	public void setBounds(final float xmin, final float xmax) {
		this.xmin = xmin;
		this.xmax = xmax;
	}

	@Override
	public void update(final boolean updateFrustum) {
		fixBounds();
		super.update(updateFrustum);
	}

	private void fixBounds() {
		if (position.x < xmin + viewportWidth / 2.0f) {
			position.x = xmin + viewportWidth / 2.0f;
		}
		if (position.x > xmax - viewportWidth / 2.0f) {
			position.x = xmax - viewportWidth / 2.0f;
		}
	}
}
