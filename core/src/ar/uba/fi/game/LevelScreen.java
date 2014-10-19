/**
 *
 */
package ar.uba.fi.game;

import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.EntityFactory;
import ar.uba.fi.game.entity.NinjaRabbit;
import ar.uba.fi.game.graphics.BoundedCamera;
import ar.uba.fi.game.graphics.hud.StatusBar;
import ar.uba.fi.game.map.LevelFactory;
import ar.uba.fi.game.map.LevelRenderer;
import ar.uba.fi.game.physics.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
	private static final float GRAVITY = -9.8f;
	private static final String BODIES_DEFINITION_FILE = "bodies.json";
	private static final String LEVEL_MAP_FILE = "level2.tmx";
	private static final String MUSIC_PROPERTY = "music";

	private static final float TIME_STEP = 1 / 300f;
	private static final int POSITION_ITERATIONS = 3;
	private static final int VELOCITY_ITERATIONS = 8;

	private final World world;
	private final ScreenViewport viewport;
	private final Box2DDebugRenderer b2dRenderer;
	private final Entity ninjaRabbit;
	private final LevelRenderer mapRenderer;
	private final Music theme;
	private final StatusBar hud;
	private final GameOverOverlay gameOver;
	private float accumulator;
	private float resetTime;

	public LevelScreen(final NinjaRabbitGame game) {
		super(game);

		world = new World(new Vector2(0.0f, GRAVITY), true);
		hud = new StatusBar(game.getBatch(), game.getAssetsManager());

		BodyEditorLoader bodyLoader = new BodyEditorLoader(Gdx.files.internal(BODIES_DEFINITION_FILE));
		ninjaRabbit = EntityFactory.createNinjaRabbit(world, bodyLoader, game.getAssetsManager(), hud);
		mapRenderer = LevelFactory.create(world, bodyLoader, game.getBatch(), game.getAssetsManager(), LEVEL_MAP_FILE,
				1 / NinjaRabbitGame.PPM);
		viewport = new ScreenViewport();
		viewport.setUnitsPerPixel(1 / NinjaRabbitGame.PPM);
		viewport.setCamera(new BoundedCamera(0.0f,
				mapRenderer.getTiledMap().getProperties().get("width", Integer.class).floatValue()
				* mapRenderer.getTiledMap().getProperties().get("tilewidth", Integer.class).floatValue()
				/ NinjaRabbitGame.PPM));

		theme = game.getAssetsManager().get(mapRenderer.getTiledMap().getProperties().get(MUSIC_PROPERTY,
				AssetSystem.NINJA_RABBIT_THEME.fileName, String.class),
				Music.class);
		theme.setVolume(0.5f);
		theme.setLooping(true);
		theme.play();

		b2dRenderer = new Box2DDebugRenderer();
		gameOver = new GameOverOverlay(game);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(final float delta) {
		accumulator += Math.min(delta, 0.25f);
		while (accumulator >= TIME_STEP) {
			world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
			accumulator -= TIME_STEP;
		}

		viewport.getCamera().position.x = ninjaRabbit.getBody() == null ? 0.0f :
			ninjaRabbit.getBody().getPosition().x + viewport.getWorldWidth() / 4.0f;
		viewport.getCamera().update();
		game.getBatch().setProjectionMatrix(viewport.getCamera().combined);
		mapRenderer.render((OrthographicCamera) viewport.getCamera());

		game.getBatch().begin();
		mapRenderer.update();
		ninjaRabbit.update(game.getBatch());
		game.getBatch().end();

		hud.render();

		if (ninjaRabbit.isExecuting(NinjaRabbit.GAME_OVER)) {
			theme.stop();
			gameOver.render(delta);
			if (resetTime > 5.0f) {
				game.reset();
			} else {
				resetTime += delta;
			}
		}

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
		gameOver.resize(width, height);
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
		gameOver.dispose();
	}
}
