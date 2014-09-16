package ar.uba.fi.game.graphics;

import net.dermetfan.utils.libgdx.graphics.Box2DSprite;
import ar.uba.fi.game.AssetSystem;
import ar.uba.fi.game.entity.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 *
 * @author nfantone
 *
 */
public class CarrotGraphicsProcessor implements GraphicsProcessor {
	private static final String CARROT_REGION = "carrot";
	private final Box2DSprite carrot;

	public CarrotGraphicsProcessor(final AssetManager manager) {
		carrot = new Box2DSprite(manager.get(AssetSystem.NINJA_RABBIT_ATLAS).findRegion(CARROT_REGION));
	}

	@Override
	public void draw(final Entity character, final Batch batch) {
		carrot.draw(batch, character.getBody());
	}

}
