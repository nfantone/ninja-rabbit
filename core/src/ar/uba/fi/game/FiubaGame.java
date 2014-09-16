package ar.uba.fi.game;

import ar.uba.fi.game.entity.Entity;
import ar.uba.fi.game.entity.NinjaRabbitFactory;
import ar.uba.fi.game.graphics.BoundedCamera;
import ar.uba.fi.game.graphics.hud.StatusBar;
import ar.uba.fi.game.map.LevelFactory;
import ar.uba.fi.game.map.LevelRenderer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class FiubaGame extends ApplicationAdapter {
	private static final String LEVEL_MAP_FILE = "level.tmx";
	public static final String GAME_TITLE = "FIUBA | Ninja Rabbit test [fps: %s]";
	private static final float TIME_STEP = 1 / 45f;
	public static final float PPM = 120.0f;

	private World world;
	private ScreenViewport viewport;
	private SpriteBatch batch;
	private Box2DDebugRenderer b2dRenderer;
	private AssetManager assets;
	private Entity ninjaRabbit;
	private LevelRenderer mapRenderer;
	private StatusBar hud;

	@Override
	public void create() {
		batch = new SpriteBatch();
		world = new World(new Vector2(0, -9.8f), true);
		assets = new AssetSystem();
		assets.load(AssetSystem.NINJA_RABBIT_ATLAS);
		assets.load(AssetSystem.NINJA_RABBIT_THEME);
		assets.load(AssetSystem.JUMP_FX);
		assets.load(AssetSystem.CRUNCH_FX);
		assets.load(AssetSystem.HUD_FONT);
		assets.finishLoading();

		hud = new StatusBar(batch, assets);
		ninjaRabbit = new NinjaRabbitFactory().create(world, assets, hud);
		mapRenderer = LevelFactory.create(world, assets, LEVEL_MAP_FILE, 1 / PPM);

		viewport = new ScreenViewport();
		viewport.setUnitsPerPixel(1 / PPM);
		viewport.setCamera(new BoundedCamera(0.0f, mapRenderer.getTiledMap().getProperties().get("width", Integer.class).floatValue()
				* mapRenderer.getTiledMap().getProperties().get("tilewidth", Integer.class).floatValue() / PPM));

		b2dRenderer = new Box2DDebugRenderer();

	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.graphics.setTitle(String.format(GAME_TITLE, Gdx.graphics.getFramesPerSecond()));
		world.step(TIME_STEP, 8, 3);

		viewport.getCamera().position.x = ninjaRabbit.getBody() == null ? 0.0f :
				ninjaRabbit.getBody().getPosition().x
						+ viewport.getWorldWidth() / 4.0f;
		viewport.getCamera().update();
		batch.setProjectionMatrix(viewport.getCamera().combined);

		mapRenderer.render((OrthographicCamera) viewport.getCamera());

		batch.begin();
		mapRenderer.update(batch);
		ninjaRabbit.update(batch);
		batch.end();

		hud.render();

		// b2dRenderer.render(world, viewport.getCamera().combined);
	}

	@Override
	public void resize(final int width, final int height) {
		viewport.update(width, height, true);
		hud.resize(width, height);
	}

	@Override
	public void dispose() {
		assets.dispose();
		b2dRenderer.dispose();
		world.dispose();
		hud.dispose();
		batch.dispose();
	}
}
