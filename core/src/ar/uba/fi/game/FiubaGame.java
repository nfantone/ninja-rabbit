package ar.uba.fi.game;

import net.dermetfan.utils.libgdx.box2d.Box2DMapObjectParser;
import ar.uba.fi.game.entity.EntityFactory;
import ar.uba.fi.game.entity.NinjaRabbit;
import ar.uba.fi.game.graphics.BoundedCamera;
import ar.uba.fi.game.input.NinjaRabbitInputProcessor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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
	private TiledMapRenderer tileMapRenderer;
	private Box2DDebugRenderer b2dRenderer;
	private AssetManager assets;
	private NinjaRabbit ninjaRabbit;

	@Override
	public void create() {
		batch = new SpriteBatch();
		world = new World(new Vector2(0, -9.8f), true);
		assets = new AssetSystem();
		assets.load(AssetSystem.NINJA_RABBIT_ATLAS);
		assets.load(AssetSystem.NINJA_RABBIT_THEME);
		assets.load(AssetSystem.JUMP_FX);
		assets.finishLoading();

		Music theme = assets.get(AssetSystem.NINJA_RABBIT_THEME);
		theme.setVolume(0.5f);
		theme.play();

		ninjaRabbit = EntityFactory.createNinjaRabbit(world, assets);

		InputProcessor inputProcessor = new InputMultiplexer(new
				NinjaRabbitInputProcessor(ninjaRabbit),
				new InputAdapter() {
			@Override
			public boolean scrolled(final int amount) {
				viewport.setUnitsPerPixel(viewport.getUnitsPerPixel() + viewport.getUnitsPerPixel() /
						amount / 8f);
				viewport.update(viewport.getScreenWidth(), viewport.getScreenHeight());
				return true;
			}
		});

		Gdx.input.setInputProcessor(inputProcessor);

		TiledMap tileMap = new TmxMapLoader().load(LEVEL_MAP_FILE);
		Box2DMapObjectParser objectParser = new Box2DMapObjectParser(1 / PPM);
		objectParser.load(world, tileMap);
		tileMapRenderer = new OrthogonalTiledMapRenderer(tileMap, 1 / PPM);

		viewport = new ScreenViewport();
		viewport.setUnitsPerPixel(1 / PPM);
		viewport.setCamera(new BoundedCamera(0.0f, tileMap.getProperties().get("width", Integer.class).floatValue()
				* tileMap.getProperties().get("tilewidth", Integer.class).floatValue() / PPM));

		b2dRenderer = new Box2DDebugRenderer();
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.graphics.setTitle(String.format(GAME_TITLE, Gdx.graphics.getFramesPerSecond()));
		world.step(TIME_STEP, 8, 3);

		viewport.getCamera().position.x = ninjaRabbit.getBody() == null ? 0.0f : ninjaRabbit.getBody().getPosition().x
				+ viewport.getWorldWidth() / 4.0f;
		viewport.getCamera().update();
		batch.setProjectionMatrix(viewport.getCamera().combined);

		tileMapRenderer.setView((OrthographicCamera) viewport.getCamera());
		tileMapRenderer.render();

		batch.begin();
		ninjaRabbit.update(batch);
		batch.end();

		b2dRenderer.render(world, viewport.getCamera().combined);
	}

	@Override
	public void resize(final int width, final int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void dispose() {
		assets.dispose();
		b2dRenderer.dispose();
		world.dispose();
	}
}
