/**
 *
 */
package ar.uba.fi.game;

import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.EntityFactory;
import ar.uba.fi.game.graphics.BoundedCamera;
import ar.uba.fi.game.graphics.hud.StatusBar;
import ar.uba.fi.game.map.LevelFactory;
import ar.uba.fi.game.map.LevelRenderer;
import ar.uba.fi.game.physics.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * @author nfantone
 *
 */
public class LevelScreen extends AbstractScreen {
	private static final String BODIES_DEFINITION_FILE = "bodies.json";
	private static final String LEVEL_MAP_FILE = "level.tmx";

	private static final float TIME_STEP = 1 / 45f;

	private final World world;
	private final ScreenViewport viewport;
	private final Box2DDebugRenderer b2dRenderer;
	private final Entity ninjaRabbit;
	private final LevelRenderer mapRenderer;
	private final StatusBar hud;

	public LevelScreen(final NinjaRabbitGame game) {
		super(game);

		world = new World(new Vector2(0.0f, -9.8f), true);

		hud = new StatusBar(game.getBatch(), game.getAssetsManager());

		BodyEditorLoader bodyLoader = new BodyEditorLoader(Gdx.files.internal(BODIES_DEFINITION_FILE));
		ninjaRabbit = EntityFactory.createNinjaRabbit(world, bodyLoader, game.getAssetsManager(), hud);
		mapRenderer = LevelFactory.create(world, bodyLoader, game.getBatch(), game.getAssetsManager(), LEVEL_MAP_FILE,
				1 / NinjaRabbitGame.PPM);

		viewport = new ScreenViewport();
		viewport.setUnitsPerPixel(1 / NinjaRabbitGame.PPM);
		viewport.setCamera(new BoundedCamera(0.0f, mapRenderer.getTiledMap().getProperties().get("width", Integer.class).floatValue()
				* mapRenderer.getTiledMap().getProperties().get("tilewidth", Integer.class).floatValue() / NinjaRabbitGame.PPM));

		b2dRenderer = new Box2DDebugRenderer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(final float delta) {
		world.step(TIME_STEP, 8, 3);

		viewport.getCamera().position.x = ninjaRabbit.getBody() == null ? 0.0f :
				ninjaRabbit.getBody().getPosition().x
						+ viewport.getWorldWidth() / 4.0f;
		viewport.getCamera().update();
		game.getBatch().setProjectionMatrix(viewport.getCamera().combined);
		mapRenderer.render((OrthographicCamera) viewport.getCamera());

		game.getBatch().begin();
		mapRenderer.update();
		ninjaRabbit.update(game.getBatch());
		game.getBatch().end();

		hud.render();

		// b2dRenderer.render(world, viewport.getCamera().combined);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(final int width, final int height) {
		viewport.update(width, height, true);
		hud.resize(width, height);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose() {
		b2dRenderer.dispose();
		world.dispose();
		hud.dispose();
	}
}
