/**
 *
 */
package ar.uba.fi.game.graphics;

import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;
import ar.uba.fi.game.Assets;
import ar.uba.fi.game.NinjaRabbitGame;
import ar.uba.fi.game.entity.Entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

/**
 * @author nfantone
 *
 */
public class SlimeGraphicsProcessor implements GraphicsProcessor {
	private static final String WALK_REGION = "slime-walk";
	private static final String SQUASHED_REGION = "slime-squashed";
	private static final String DEAD_REGION = "slime-dead";

	private final AnimatedBox2DSprite walkSprite;
	private final Box2DSprite squashedSprite;
	private final Box2DSprite deadSprite;

	public SlimeGraphicsProcessor(final AssetManager assets) {
		final TextureAtlas textureAtlas = assets.get(Assets.ENEMIES_ATLAS);

		Array<Sprite> walkingSprites = textureAtlas.createSprites(WALK_REGION);
		Animation animation = new Animation(1 / 12.0f, walkingSprites, PlayMode.LOOP);
		walkSprite = new AnimatedBox2DSprite(new AnimatedSprite(animation));
		walkSprite.setAdjustSize(false);
		walkSprite.setSize(walkSprite.getWidth() / NinjaRabbitGame.PPM,
				walkSprite.getHeight() / NinjaRabbitGame.PPM);

		squashedSprite = new Box2DSprite(textureAtlas.createSprite(SQUASHED_REGION));
		squashedSprite.setSize(squashedSprite.getWidth() / NinjaRabbitGame.PPM,
				squashedSprite.getHeight() / NinjaRabbitGame.PPM);

		deadSprite = new Box2DSprite(textureAtlas.createSprite(DEAD_REGION));
		deadSprite.setSize(deadSprite.getWidth() / NinjaRabbitGame.PPM,
				deadSprite.getHeight() / NinjaRabbitGame.PPM);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.badlogic.gdx.utils.Disposable#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ar.uba.fi.game.graphics.GraphicsProcessor#draw(ar.uba.fi.game.entity.Entity,
	 * com.badlogic.gdx.graphics.g2d.Batch)
	 */
	@Override
	public void draw(final Entity slime, final Batch batch) {
		walkSprite.draw(batch, slime.getBody());

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ar.uba.fi.game.graphics.GraphicsProcessor#update(ar.uba.fi.game.entity.Entity,
	 * com.badlogic.gdx.graphics.Camera)
	 */
	@Override
	public void update(final Entity character, final Camera camera) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ar.uba.fi.game.graphics.GraphicsProcessor#resize(int, int)
	 */
	@Override
	public void resize(final int width, final int height) {
		// TODO Auto-generated method stub

	}

}
