package ar.uba.fi.game.physics;

import ar.uba.fi.game.FiubaGame;
import ar.uba.fi.game.entity.Direction;
import ar.uba.fi.game.graphics.CarrotPhysicsProcessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author nfantone
 *
 */
public final class CarrotBodyFactory implements BodyFactory {
	private static final float CARROT_SCALE = 61 / FiubaGame.PPM;
	private static final String CARROT_BODY_DEF_FILE = "carrot.json";

	private final BodyEditorLoader loader;
	private final BodyDef bdef;
	private final FixtureDef fdef;

	public CarrotBodyFactory() {
		loader = new BodyEditorLoader(Gdx.files.internal(CARROT_BODY_DEF_FILE));

		bdef = new BodyDef();
		bdef.type = BodyType.DynamicBody;
		bdef.position.set(0, 0);
		bdef.fixedRotation = true;

		fdef = new FixtureDef();
		fdef.isSensor = true;
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

	@Override
	public Body create(final World world, final BodyDef definition) {
		return create(world, definition, null);
	}

	@Override
	public Body create(final World world, final BodyDef definition, final Direction direction) {
		Body rabbitBody = world.createBody(definition);
		loader.attachFixture(rabbitBody, CarrotPhysicsProcessor.CARROT_IDENTIFIER, fdef, CARROT_SCALE);
		rabbitBody.getFixtureList().first().setUserData(CarrotPhysicsProcessor.CARROT_IDENTIFIER);
		return rabbitBody;
	}
}
