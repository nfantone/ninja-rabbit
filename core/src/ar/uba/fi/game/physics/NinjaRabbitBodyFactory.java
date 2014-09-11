package ar.uba.fi.game.physics;

import ar.uba.fi.game.FiubaGame;
import ar.uba.fi.game.entity.Direction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author nfantone
 *
 */
public final class NinjaRabbitBodyFactory {
	private static final float NINJA_RABBIT_SCALE = 186 / FiubaGame.PPM;
	private static final String NINJA_RABBIT_BODY_DEF_FILE = "ninja-rabbit.json";
	private static final int FOOT_FIXTURE_INDEX = 9;
	private static final String RABBIT_IDENTIFIER = "rabbit";

	private final BodyEditorLoader loader;
	private final BodyDef bdef;
	private final FixtureDef fdef;

	public NinjaRabbitBodyFactory() {
		loader = new BodyEditorLoader(Gdx.files.internal(NINJA_RABBIT_BODY_DEF_FILE));

		bdef = new BodyDef();
		bdef.type = BodyType.DynamicBody;
		bdef.position.set(0, 0);
		bdef.fixedRotation = true;

		fdef = new FixtureDef();
		fdef.density = 1;
		fdef.friction = 1.4f;
	};

	public Body createNinjaRabbitBody(final World world, final Direction direction) {
		Body rabbitBody = world.createBody(bdef);
		loader.attachFixture(rabbitBody, RABBIT_IDENTIFIER + "-" + direction.direction(), fdef, NINJA_RABBIT_SCALE);

		Fixture footSensor = rabbitBody.getFixtureList().get(FOOT_FIXTURE_INDEX);
		footSensor.setUserData(NinjaRabbitPhysicsProcessor.FOOT_IDENTIFIER);
		footSensor.setSensor(true);

		rabbitBody.setUserData(RABBIT_IDENTIFIER);
		return rabbitBody;
	}
}
