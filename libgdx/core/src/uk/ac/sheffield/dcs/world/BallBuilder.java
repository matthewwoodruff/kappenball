package uk.ac.sheffield.dcs.world;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import uk.ac.sheffield.dcs.game.Ball;
import uk.ac.sheffield.dcs.game.configuration.BallConfiguration;

import static uk.ac.sheffield.dcs.world.FixtureType.BALL;

public class BallBuilder {

    private final WorldRegister worldRegister;
    private BallConfiguration configuration;

    private BallBuilder(WorldRegister worldRegister) {
        this.worldRegister = worldRegister;
    }

    public static BallBuilder within(WorldRegister world) {
        return new BallBuilder(world);
    }

    public BallBuilder configuration(BallConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }

    public Ball build() {
        BodyDef ballDef = new BodyDef();

        ballDef.type = BodyDef.BodyType.DynamicBody;

        CircleShape circle = new CircleShape();
        circle.setRadius(configuration.getRadius());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 10f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0.2f;

        Body body = worldRegister.getWorld().createBody(ballDef);

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(BALL);
        circle.dispose();
        Ball ball = new Ball(body, configuration);
        body.setUserData(ball);
        return ball;
    }

}
