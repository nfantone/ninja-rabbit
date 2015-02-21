/**
 *
 */
package ar.uba.fi.game.graphics;

import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;
import net.dermetfan.gdx.physics.box2d.Box2DUtils;
import ar.uba.fi.game.Assets;
import ar.uba.fi.game.NinjaRabbitGame;
import ar.uba.fi.game.ai.fsm.NinjaRabbitState;
import ar.uba.fi.game.ai.msg.MessageType;
import ar.uba.fi.game.entity.Direction;
import ar.uba.fi.game.entity.Entity;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
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
public class NinjaRabbitGraphicsProcessor implements GraphicsProcessor, Telegraph {
	private static final String WALK_REGION = "walk";
	private static final String JUMP_REGION = "jump";
	// private static final String DUCK_REGION = "duck";
	private static final Vector2 RESPAWN_POSITION = new Vector2(0.6f, 3.2f);

	private final TextureAtlas textureAtlas;
	private final Box2DSprite standingSprite;
	private final AnimatedBox2DSprite walkRightSprite;
	private final AnimatedBox2DSprite walkLeftSprite;
	private final AnimatedBox2DSprite jumpSprite;

	// private final AnimatedBox2DSprite duckSprite;

	public NinjaRabbitGraphicsProcessor(final AssetManager assets) {
		textureAtlas = assets.get(Assets.NINJA_RABBIT_ATLAS);

		Array<Sprite> walkingSprites = textureAtlas.createSprites(WALK_REGION);
		standingSprite = new Box2DSprite(walkingSprites.first());
		standingSprite.setAdjustSize(false);
		standingSprite.setSize(standingSprite.getWidth() / NinjaRabbitGame.PPM,
				standingSprite.getHeight() / NinjaRabbitGame.PPM);

		Animation animation = new Animation(1 / 12.0f, walkingSprites, PlayMode.LOOP);
		walkRightSprite = new AnimatedBox2DSprite(new AnimatedSprite(animation));
		walkRightSprite.setAdjustSize(false);
		walkRightSprite.setSize(walkRightSprite.getWidth() / NinjaRabbitGame.PPM,
				walkRightSprite.getHeight() / NinjaRabbitGame.PPM);

		animation = new Animation(1 / 12.0f, textureAtlas.createSprites(WALK_REGION), PlayMode.LOOP);
		walkLeftSprite = new AnimatedBox2DSprite(new AnimatedSprite(animation));
		walkLeftSprite.flipFrames(true, false, true);
		walkLeftSprite.setAdjustSize(false);
		walkLeftSprite.setSize(walkLeftSprite.getWidth() / NinjaRabbitGame.PPM,
				walkLeftSprite.getHeight() / NinjaRabbitGame.PPM);

		animation = new Animation(1 / 10.0f, textureAtlas.createSprites(JUMP_REGION));
		jumpSprite = new AnimatedBox2DSprite(new AnimatedSprite(animation));
		jumpSprite.setAdjustSize(false);
		jumpSprite.setSize(jumpSprite.getWidth() / NinjaRabbitGame.PPM,
				jumpSprite.getHeight() / NinjaRabbitGame.PPM);

		// Array<Sprite> duckSprites = textureAtlas.createSprites(DUCK_REGION);
		//
		// for (Sprite duck : duckSprites) {
		// // duck.setRegionHeight((int) (duck.getHeight() / FiubaGame.PPM));
		// // duck.setRegionWidth((int) (duck.getWidth() / FiubaGame.PPM));
		// duck.setSize(duck.getWidth() / FiubaGame.PPM,
		// duck.getHeight() / FiubaGame.PPM);
		// }
		//
		// animation = new Animation(1 / 16.0f, duckSprites);
		// duckSprite = new AnimatedBox2DSprite(new AnimatedSprite(animation));
		// duckSprite.setAdjustSize(false);
		// duckSprite.setUseFrameRegionSize(true);
		// // duckSprite.setRegionHeight((int) (duckSprite.getHeight() / FiubaGame.PPM));
		// // duckSprite.setRegionWidth((int) (duckSprite.getWidth() / FiubaGame.PPM));
		// duckSprite.setSize(duckSprite.getWidth() / FiubaGame.PPM, duckSprite.getHeight() /
		// FiubaGame.PPM);

		MessageManager.getInstance().addListener(this, MessageType.DEAD.code());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.uba.fi.game.graphics.GraphicsProcessor#update(ar.uba.fi.game.entity.Entity,
	 * com.badlogic.gdx.graphics.Camera)
	 */
	@Override
	public void update(final Entity character, final Camera camera) {
		camera.position.x = character.getBody() == null ? 0.0f :
				character.getBody().getPosition().x + camera.viewportWidth * 0.25f;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ar.uba.fi.game.entity.GraphicsProcessor#draw(com.badlogic.gdx.graphics.g2d.Batch)
	 */
	@Override
	public void draw(final Entity character, final Batch batch) {
		Box2DSprite frame = null;

		if (character.isInState(NinjaRabbitState.JUMP)) {
			jumpSprite.flipFrames(!(Direction.RIGHT.equals(character.getDirection()) ^ jumpSprite.isFlipX()), false, false);
			frame = jumpSprite;
		} else if (character.isInState(NinjaRabbitState.RIGHT)) {
			frame = walkRightSprite;
			character.setDirection(Direction.RIGHT);
		} else if (character.isInState(NinjaRabbitState.LEFT)) {
			frame = walkLeftSprite;
			character.setDirection(Direction.LEFT);
		} else if (character.isInState(NinjaRabbitState.DUCK)) {
			// frame = duckSprite;
		} else {
			standingSprite.flip(!(Direction.RIGHT.equals(character.getDirection()) ^ standingSprite.isFlipX()), false);
			frame = standingSprite;
			// duckSprite.setTime(0.0f);
			jumpSprite.setTime(0.0f);
		}

		// Following numbers came from voodoo
		frame.setPosition(
				-frame.getWidth() * 0.5f +
						Box2DUtils.width(character.getBody()) / (Direction.RIGHT.equals(character.getDirection())
								? 2.8f : 1.55f),
				-frame.getHeight() * 0.5f + Box2DUtils.width(character.getBody()) + 0.36f);

		frame.draw(batch, character.getBody());
	}

	@Override
	public boolean handleMessage(final Telegram msg) {
		Entity character = (Entity) msg.extraInfo;
		character.getBody().setTransform(RESPAWN_POSITION, character.getBody().getAngle());
		character.changeState(NinjaRabbitState.IDLE);
		character.setDirection(Direction.RIGHT);
		return true;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void resize(final int width, final int height) {
	}
}
