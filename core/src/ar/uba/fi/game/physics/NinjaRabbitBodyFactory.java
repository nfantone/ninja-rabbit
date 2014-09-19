package ar.uba.fi.game.physics;

import ar.uba.fi.game.NinjaRabbitGame;
import ar.uba.fi.game.entity.Direction;

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
public final class NinjaRabbitBodyFactory implements BodyFactory {
	private static final float NINJA_RABBIT_SCALE = 186 / NinjaRabbitGame.PPM;
	private static final int FOOT_FIXTURE_INDEX = 9;
	private static final String RABBIT_IDENTIFIER = "rabbit";

	private final BodyEditorLoader loader;
	private final BodyDef bdef;
	private final FixtureDef fdef;

	public NinjaRabbitBodyFactory(final BodyEditorLoader loader) {
		if (loader == null) {
			throw new IllegalArgumentException("'loader' cannot be null");
		}
		this.loader = loader;

		bdef = new BodyDef();
		bdef.type = BodyType.DynamicBody;
		bdef.position.set(0, 0);
		bdef.fixedRotation = true;

		fdef = new FixtureDef();
		fdef.density = 1;
		fdef.friction = 1.4f;
	};

	/*
	 * (non-Javadoc)
	 *
	 * @see ar.uba.fi.game.physics.BodyFactory#create(com.badlogic.gdx.physics.box2d.World,
	 * ar.uba.fi.game.entity.Direction)
	 */
	@Override
	public Body create(final World world, final Direction direction) {
		return create(world, bdef, direction);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ar.uba.fi.game.physics.BodyFactory#create(com.badlogic.gdx.physics.box2d.World,
	 * com.badlogic.gdx.physics.box2d.BodyDef)
	 */
	@Override
	public Body create(final World world, final BodyDef definition) {
		return create(world, definition, Direction.RIGHT);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ar.uba.fi.game.physics.BodyFactory#create(com.badlogic.gdx.physics.box2d.World,
	 * com.badlogic.gdx.physics.box2d.BodyDef, ar.uba.fi.game.entity.Direction)
	 */
	@Override
	public Body create(final World world, final BodyDef definition, final Direction direction) {
		Body rabbitBody = world.createBody(definition);
		loader.attachFixture(rabbitBody, RABBIT_IDENTIFIER + "-" + direction.direction(), fdef, NINJA_RABBIT_SCALE);

		Fixture footSensor = rabbitBody.getFixtureList().get(FOOT_FIXTURE_INDEX);
		footSensor.setUserData(NinjaRabbitPhysicsProcessor.FOOT_IDENTIFIER);
		footSensor.setSensor(true);

		return rabbitBody;
	}
}
