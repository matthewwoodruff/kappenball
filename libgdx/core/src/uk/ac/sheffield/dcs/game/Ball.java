package uk.ac.sheffield.dcs.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import uk.ac.sheffield.dcs.game.configuration.BallConfiguration;

import static java.lang.Math.random;

public class Ball {

    private static final Texture texture = new Texture("ball.png");

    private final Body body;
    private final float decayRate;
    private final float acceleration;
    private final float interventionAcceleration;
    private final int interventionsPerSecond;
    private final Vector2 initialPosition;
    private final Vector2 initialVelocity;
    private final float size;
    private final float halfSize;
    private GameInputFacade input;

    private boolean dead;
    private float currentInterveningAcceleration;
    private BallListener ballListener;

    public Ball(Body body, BallConfiguration configuration) {
        this.body = body;
        this.initialPosition = configuration.getInitialPosition();
        this.initialVelocity = configuration.getInitialVelocity();
        this.decayRate = configuration.getDecayRate();
        this.acceleration = configuration.getAcceleration();
        this.interventionAcceleration = configuration.getInterventionAcceleration();
        this.interventionsPerSecond = configuration.getInterventionsPerSecond();

        float radius = configuration.getRadius();
        this.halfSize = radius * 2;
        this.size = radius * 4;

        reset();
    }

    public void setInput(GameInputFacade input) {
        this.input = input;
    }

    public void updateVelocity(float delta) {
        Vector2 linearVelocity = body.getLinearVelocity();

        float accelerationInterval = getAcceleration() * delta;
        if (accelerationInterval == 0 && currentInterveningAcceleration == 0)
            linearVelocity.x *= decayRate * (1 - delta);

        if (canIntervene(delta)) {
            updateIntervention();
        }

        float currentInterveningAccelerationInterval = currentInterveningAcceleration * delta;

        float newXVelocity = linearVelocity.x + accelerationInterval + currentInterveningAccelerationInterval;

        body.setLinearVelocity(newXVelocity, linearVelocity.y);
    }

    private void updateIntervention() {
        currentInterveningAcceleration = currentInterveningAcceleration != 0 ? 0 : random() > .5 ? interventionAcceleration : -interventionAcceleration;
    }

    private boolean canIntervene(float delta) {
        return random() > (1 - (delta * interventionsPerSecond)) && random() > (currentInterveningAcceleration != 0 ? .5 : .2);
    }

    private float getAcceleration() {
        return input == null ? 0 : input.screenPressedLeft() ? acceleration : input.screenPressedRight() ? -acceleration : 0;
    }

    public void render(Batch batch) {
        Vector2 position = body.getPosition();
        batch.draw(texture, position.x - halfSize, position.y - halfSize, size, size);
    }

    public void spike() {
        die();
        if (ballListener != null)
            ballListener.spiked();
    }

    public boolean isAlive() {
        return !isDead();
    }

    public boolean isDead() {
        return dead;
    }

    public void end() {
        die();
        if (ballListener != null)
            ballListener.ended();
    }

    private void die() {
        dead = true;
    }

    public void reset() {
        dead = false;
        body.setLinearVelocity(initialVelocity);
        body.setTransform(initialPosition, 0);
    }
}
