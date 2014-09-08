/**
 *
 */
package ar.uba.fi.game.entity;

import ar.uba.fi.game.FiubaGame;
import ar.uba.fi.game.graphics.GraphicsProcessor;
import ar.uba.fi.game.graphics.NinjaRabbitGraphicsProcessor;
import ar.uba.fi.game.physics.NinjaRabbitPhysicsProcessor;
import ar.uba.fi.game.physics.PhysicsProcessor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author nfantone
 *
 */
public final class EntityFactory {
	private static final String RABBIT_IDENTIFIER = "rabbit";

	/**
	 * No instances of this classs should be created. This is meant to be used as a static factory.
	 */
	private EntityFactory() {
	};

	/**
	 * Creates a new instance of a {@link NinjaRabbit}, defining its graphical and physical
	 * properties.
	 *
	 * @param world
	 *            The Box2D {@link World} onto which to create the {@link Body} and {@link Fixture}
	 *            of the {@link NinjaRabbit}.
	 * @return A ready to use instance of a new {@link NinjaRabbit}.
	 */
	public static NinjaRabbit createNinjaRabbit(final World world) {
		PhysicsProcessor physics = new NinjaRabbitPhysicsProcessor();
		world.setContactListener(physics);
		GraphicsProcessor graphics = new NinjaRabbitGraphicsProcessor();
		Body body = creatNinjaRabbitBody(world);
		return new NinjaRabbit(body, graphics, physics);
	}

	private static Body creatNinjaRabbitBody(final World world) {
		// create bodydef
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.DynamicBody;
		bdef.position.set(70 / FiubaGame.PPM, 150 / FiubaGame.PPM);
		bdef.fixedRotation = true;

		// create body from bodydef
		Body rabbitBody = world.createBody(bdef);

		// create box shape for player collision box
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(30 / FiubaGame.PPM, 95 / FiubaGame.PPM);

		// create fixturedef for player collision box
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 1;
		fdef.friction = 1.4f;

		// create player collision box fixture
		rabbitBody.createFixture(fdef);
		shape.dispose();

		// create box shape for player foot
		shape = new PolygonShape();
		shape.setAsBox(30 / FiubaGame.PPM, 6 / FiubaGame.PPM, new Vector2(0, -95 / FiubaGame.PPM), 0);

		// create fixturedef for player foot
		fdef.shape = shape;
		fdef.isSensor = true;

		// create player foot fixture
		rabbitBody.createFixture(fdef).setUserData(NinjaRabbitPhysicsProcessor.FOOT_IDENTIFIER);
		shape.dispose();

		rabbitBody.setUserData(RABBIT_IDENTIFIER);

		// final tweaks, manually set the player body mass to 1 kg
		MassData md = rabbitBody.getMassData();
		md.mass = 1.0f;
		rabbitBody.setMassData(md);

		return rabbitBody;
	}
}
