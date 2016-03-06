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
    private final BallConfiguration config;
    private final float size;
    private final float halfSize;
    private GameInputFacade input;

    private boolean dead;
    private float interventionMultiplier;
    private BallListener ballListener;

    public Ball(Body body, BallConfiguration config) {
        this.body = body;
        this.config = config;
        float radius = config.getRadius();
        this.halfSize = radius * 2;
        this.size = radius * 4;

        reset();
    }

    public void setInput(GameInputFacade input) {
        this.input = input;
    }

    public void updateVelocity(float delta) {
        final Vector2 linearVelocity = body.getLinearVelocity();

        float accelerationInterval = getAcceleration() * delta;

        if (shouldIntervene(delta)) updateIntervention();

        float currentInterveningAccelerationInterval =
                interventionMultiplier * config.getInterventionAcceleration() * delta;

        if (accelerationInterval == 0 && currentInterveningAccelerationInterval == 0)
            linearVelocity.x *= config.getDecayRate() * (1 - delta);

        float newXVelocity = linearVelocity.x + accelerationInterval + currentInterveningAccelerationInterval;

        if (accelerationInterval != 0 && ballListener != null)
            ballListener.energyExpended(delta);

        body.setLinearVelocity(newXVelocity, linearVelocity.y);
    }

    private void updateIntervention() {
        interventionMultiplier = interventionMultiplier != 0 ? 0 : random() > .5 ? 1 : -1;
    }

    private boolean shouldIntervene(float delta) {
        return shouldTriggerIntervention(delta) && shouldChangeInterventionForCurrentState();
    }

    private boolean shouldTriggerIntervention(float delta) {
        float interventionsPerSecond = config.getInterventionsPerSecond();
        return random() > (1 - (delta * interventionsPerSecond));
    }

    private boolean shouldChangeInterventionForCurrentState() {
        float interventionPersistenceProbability = config.getInterventionPersistenceProbability();
        float interventionChangeProbability = config.getInterventionChangeProbability();
        return random() > (interventionMultiplier != 0 ? interventionPersistenceProbability : interventionChangeProbability);
    }

    private float getAcceleration() {
        float acceleration = config.getAcceleration();
        return input == null ? 0 : input.screenPressedLeft() ? acceleration : input.screenPressedRight() ? -acceleration : 0;
    }

    public void render(Batch batch) {
        final Vector2 position = body.getPosition();
        batch.draw(texture, position.x - halfSize, position.y - halfSize, size, size);
    }

    public void spike() {
        die();
        if (ballListener != null) ballListener.spiked();
    }

    public boolean isAlive() {
        return !isDead();
    }

    public boolean isDead() {
        return dead;
    }

    public void end() {
        die();
        if (ballListener != null) ballListener.ended();
    }

    private void die() {
        dead = true;
    }

    public void reset() {
        dead = false;
        body.setLinearVelocity(config.getInitialVelocity());
        body.setTransform(config.getInitialPosition(), 0);
    }

    public void setBallListener(BallListener ballListener) {
        this.ballListener = ballListener;
    }
}
