package uk.ac.sheffield.dcs.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import uk.ac.sheffield.dcs.game.configuration.KappenballConfiguration;
import uk.ac.sheffield.dcs.world.BallDefinitionBuilder;
import uk.ac.sheffield.dcs.world.WorldRegister;

import static com.badlogic.gdx.scenes.scene2d.Touchable.enabled;
import static uk.ac.sheffield.dcs.world.FixtureType.END;
import static uk.ac.sheffield.dcs.world.FixtureType.SPIKE;
import static uk.ac.sheffield.dcs.world.FixtureType.WALL;
import static uk.ac.sheffield.dcs.world.ObstacleBuilder.within;


public class Environment extends Actor {

    private static final Vector2 GRAVITY = new Vector2(0, 0);
    private static final float TIME_STEP = 1f / 300f;

    private final Texture img = new Texture("field.png");
    private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
    private final World world = new World(GRAVITY, true);

    private Ball ball;

    public Environment(KappenballConfiguration config) {
        int width = config.getWidth();
        int height = config.getHeight();

        setBounds(0, 0, width, height);
        setTouchable(enabled);

        GameInputListener gameInputListener = new GameInputListener();

        addListener(gameInputListener);

        world.setContactListener(new GameContactListener());

        WorldRegister worldRegister = new WorldRegister(world).height(height).width(width);

        within(worldRegister).x(.033f).y(.14f).width(.2f).height(.02f).build(SPIKE);
        within(worldRegister).x(.325f).y(.14f).width(.337f).height(.02f).build(SPIKE);
        within(worldRegister).x(.766f).y(.14f).width(.20f).height(.02f).build(SPIKE);

        within(worldRegister).width(.033f).height(1).build(WALL);
        within(worldRegister).x(.223f).width(.01f).height(.14f).build(WALL);
        within(worldRegister).x(.325f).width(.01f).height(.14f).build(WALL);
        within(worldRegister).x(.652f).width(.01f).height(.14f).build(WALL);
        within(worldRegister).x(.766f).width(.01f).height(.14f).build(WALL);
        within(worldRegister).x(.966f).width(.034f).height(1).build(WALL);

        within(worldRegister).y(-.02f).width(1).height(.02f).build(END);

        ball = BallDefinitionBuilder
                .within(worldRegister)
                .x(.5f)
                .y(1)
                .initialVelocity(0, -.2f)
                .radius(.02f)
                .build();
        ball.setInput(gameInputListener);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (ball.isDead())
            ball.reset();
        if (getDebug()) {
            Matrix4 mul = batch.getProjectionMatrix().mul(batch.getTransformMatrix());
            debugRenderer.render(world, mul);
        } else {
            batch.draw(img, getX(), getY(), getWidth(), getHeight());
            ball.render(batch);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (float accumulator = 0; accumulator < delta && ball.isAlive(); accumulator += TIME_STEP) {
            ball.updateVelocity(TIME_STEP);
            world.step(TIME_STEP, 6, 2);
        }
    }
}
