package uk.ac.sheffield.dcs.world;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class ObstacleBuilder {

    private final WorldRegister worldRegister;
    private float x;
    private float y;
    private float width;
    private float height;

    public ObstacleBuilder(WorldRegister worldRegister) {
        this.worldRegister = worldRegister;
    }

    public ObstacleBuilder x(float x) {
        this.x = worldRegister.getWidth() * x;
        return this;
    }

    public ObstacleBuilder y(float y) {
        this.y = worldRegister.getHeight() * y;
        return this;
    }

    public ObstacleBuilder width(float width) {
        this.width = worldRegister.getWidth() * width;
        return this;
    }

    public ObstacleBuilder height(float height) {
        this.height = worldRegister.getHeight() * height;
        return this;
    }

    public Fixture build(FixtureType type) {
        float hx = width / 2f;
        float hy = height / 2f;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x + hx, y + hy);

        Body body = worldRegister.getWorld().createBody(bodyDef);

        PolygonShape box = new PolygonShape();
        box.setAsBox(hx, hy);

        Fixture fixture = body.createFixture(box, 0.0f);
        fixture.setUserData(type);
        box.dispose();
        return fixture;
    }

    public static ObstacleBuilder within(WorldRegister world) {
        return new ObstacleBuilder(world);
    }
}
