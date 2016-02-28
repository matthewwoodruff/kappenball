package uk.ac.sheffield.dcs.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import uk.ac.sheffield.dcs.game.Ball;

import static uk.ac.sheffield.dcs.world.FixtureType.BALL;

public class BallDefinitionBuilder {

    private final WorldRegister worldRegister;
    private float x;
    private float y;
    private Vector2 initialVelocity;
    private float radius;

    public BallDefinitionBuilder(WorldRegister worldRegister) {
        this.worldRegister = worldRegister;
    }

    public static BallDefinitionBuilder within(WorldRegister world) {
        return new BallDefinitionBuilder(world);
    }

    public BallDefinitionBuilder x(float x) {
        this.x = worldRegister.getWidth() * x;
        return this;
    }

    public BallDefinitionBuilder y(float y) {
        this.y = worldRegister.getHeight() * y;
        return this;
    }

    public BallDefinitionBuilder radius(float radius) {
        this.radius = worldRegister.getHeight() * radius;
        return this;
    }

    public BallDefinitionBuilder initialVelocity(float x, float y) {
        initialVelocity = new Vector2(worldRegister.getWidth() * x, worldRegister.getHeight() * y);
        return this;
    }

    public Ball build() {
        BodyDef ballDef = new BodyDef();

        ballDef.type = BodyDef.BodyType.DynamicBody;
        ballDef.position.x = x;
        ballDef.position.y = y;

        CircleShape circle = new CircleShape();
        circle.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 10f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0.2f;

        Body body = worldRegister.getWorld().createBody(ballDef);

        body.setLinearVelocity(initialVelocity);

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(BALL);
        circle.dispose();
        Vector2 initialPosition = new Vector2(x, y);
        Ball ball = new Ball(body, initialPosition, radius, initialVelocity);
        body.setUserData(ball);
        return ball;
    }

}
