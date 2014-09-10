/**
 *
 */
package ar.uba.fi.game.graphics;

import net.dermetfan.utils.libgdx.box2d.Box2DUtils;
import net.dermetfan.utils.libgdx.graphics.AnimatedBox2DSprite;
import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;
import net.dermetfan.utils.libgdx.graphics.Box2DSprite;
import ar.uba.fi.game.FiubaGame;
import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.NinjaRabbit;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * @author nfantone
 *
 */
public class NinjaRabbitGraphicsProcessor implements GraphicsProcessor {
	private static final String NINJA_RABBIT_ATLAS = "ninja-rabbit.pack";
	private static final String WALK_REGION = "walk";
	private static final String JUMP_REGION = "jump";
	private static final String DUCK_REGION = "duck";
	private static final Vector2 RESPAWN_POSITION = new Vector2(70 / FiubaGame.PPM, 150 / FiubaGame.PPM);

	private final TextureAtlas textureAtlas;
	private final Box2DSprite standingSprite;
	private final AnimatedBox2DSprite walkRightSprite;
	private final AnimatedBox2DSprite walkLeftSprite;
	private final AnimatedBox2DSprite jumpSprite;
	private final AnimatedBox2DSprite duckSprite;
	private boolean facingRight;

	public NinjaRabbitGraphicsProcessor() {
		facingRight = true;
		textureAtlas = new TextureAtlas(NINJA_RABBIT_ATLAS);

		Array<Sprite> walkingSprites = textureAtlas.createSprites(WALK_REGION);
		standingSprite = new Box2DSprite(walkingSprites.first());
		standingSprite.setAdjustSize(false);
		standingSprite.setSize(standingSprite.getWidth() / FiubaGame.PPM,
				standingSprite.getHeight() / FiubaGame.PPM);

		Animation animation = new Animation(1 / 12.0f, walkingSprites, PlayMode.LOOP);
		walkRightSprite = new AnimatedBox2DSprite(new AnimatedSprite(animation));
		walkRightSprite.setAdjustSize(false);
		walkRightSprite.setSize(walkRightSprite.getWidth() / FiubaGame.PPM,
				walkRightSprite.getHeight() / FiubaGame.PPM);

		animation = new Animation(1 / 12.0f, textureAtlas.createSprites(WALK_REGION), PlayMode.LOOP);
		walkLeftSprite = new AnimatedBox2DSprite(new AnimatedSprite(animation));
		walkLeftSprite.flipFrames(true, false, true);
		walkLeftSprite.setAdjustSize(false);
		walkLeftSprite.setSize(walkLeftSprite.getWidth() / FiubaGame.PPM,
				walkLeftSprite.getHeight() / FiubaGame.PPM);

		animation = new Animation(1 / 10.0f, textureAtlas.createSprites(JUMP_REGION));
		jumpSprite = new AnimatedBox2DSprite(new AnimatedSprite(animation));
		jumpSprite.setAdjustSize(false);
		jumpSprite.setSize(jumpSprite.getWidth() / FiubaGame.PPM,
				jumpSprite.getHeight() / FiubaGame.PPM);

		Array<Sprite> duckSprites = textureAtlas.createSprites(DUCK_REGION);

		for (Sprite duck : duckSprites) {
			// duck.setRegionHeight((int) (duck.getHeight() / FiubaGame.PPM));
			// duck.setRegionWidth((int) (duck.getWidth() / FiubaGame.PPM));
			duck.setSize(duck.getWidth() / FiubaGame.PPM,
					duck.getHeight() / FiubaGame.PPM);
		}

		animation = new Animation(1 / 16.0f, duckSprites);
		duckSprite = new AnimatedBox2DSprite(new AnimatedSprite(animation));
		duckSprite.setAdjustSize(false);
		duckSprite.setUseFrameRegionSize(true);
		// duckSprite.setRegionHeight((int) (duckSprite.getHeight() / FiubaGame.PPM));
		// duckSprite.setRegionWidth((int) (duckSprite.getWidth() / FiubaGame.PPM));
		duckSprite.setSize(duckSprite.getWidth() / FiubaGame.PPM, duckSprite.getHeight() /
				FiubaGame.PPM);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.uba.fi.game.entity.GraphicsProcessor#draw(com.badlogic.gdx.graphics.g2d.Batch)
	 */
	@Override
	public void draw(final Entity character, final Batch batch) {
		Box2DSprite frame = null;

		if (character.isExecuting(NinjaRabbit.DEAD)) {
			character.getBody().setTransform(RESPAWN_POSITION, character.getBody().getAngle());
			frame = standingSprite;
			facingRight = true;
			character.stop(NinjaRabbit.DEAD);
		} else {
			if (character.isExecuting(NinjaRabbit.JUMP)) {
				jumpSprite.flipFrames(!(facingRight ^ jumpSprite.isFlipX()), false, false);
				frame = jumpSprite;
			} else if (character.isExecuting(NinjaRabbit.RIGHT)) {
				frame = walkRightSprite;
				facingRight = true;
			} else if (character.isExecuting(NinjaRabbit.LEFT)) {
				frame = walkLeftSprite;
				facingRight = false;
			} else if (character.isExecuting(NinjaRabbit.DUCK)) {
				frame = duckSprite;
			} else {
				standingSprite.flip(!(facingRight ^ standingSprite.isFlipX()), false);
				frame = standingSprite;
				duckSprite.setTime(0.0f);
				jumpSprite.setTime(0.0f);
			}

		}
		frame.setX(
				-frame.getWidth() / 2.0f +
						Box2DUtils.width(character.getBody()) / (facingRight ? 2.8f : 1.55f));

		frame.draw(batch, character.getBody());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.uba.fi.game.entity.GraphicsProcessor#dispose()
	 */
	@Override
	public void dispose() {
		textureAtlas.dispose();
	}
}
