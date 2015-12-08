package uk.ac.sheffield.dcs.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static uk.ac.sheffield.dcs.game.FixtureType.*;
import static uk.ac.sheffield.dcs.game.ObstacleBuilder.within;


public class Environment extends Actor {

    private static final Vector2 GRAVITY = new Vector2(0, 0);
    private static final float TIME_STEP = 1f / 300f;
    private static final boolean DEBUG = false;

    private final Texture img = new Texture("field.png");
    private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    private final World world = new World(GRAVITY, true);

    private final GameInputFacade inputFacade;
    private final float width;
    private final float height;
    private final WorldRegister worldRegister;

    private Ball ball;

    public Environment(GameInputFacade inputFacade, float width, float height) {
        this.inputFacade = inputFacade;
        this.width = width;
        this.height = height;

        world.setContactListener(new GameContactListener());

        worldRegister = new WorldRegister(world).height(height).width(width);

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

        initialiseBall();
    }

    private void initialiseBall() {
        if (ball != null) {
            world.destroyBody(ball.getBody());
            ball.dispose();
        }
        ball = BallDefinitionBuilder.within(worldRegister)
                .x(.5f)
                .y(1)
                .initialVelocity(0, -.2f)
                .radius(.02f)
                .build();
        ball.setInput(inputFacade);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!ball.isAlive())
            initialiseBall();
        if(DEBUG) {
            debugRenderer.render(world, batch.getProjectionMatrix());
        } else {
            batch.draw(img, 0, 0, width, height);
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

    public void dispose() {
        img.dispose();
        world.dispose();
        ball.dispose();
    }
}
