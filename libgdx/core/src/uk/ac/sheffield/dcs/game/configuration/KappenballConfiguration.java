package uk.ac.sheffield.dcs.game.configuration;

import com.badlogic.gdx.math.Vector2;

public class KappenballConfiguration implements BallConfiguration {

    private static final float DECAY_RATE = 0f;
    private static final float ACCELERATION = 50f;
    private static final float INTERVENTION_ACCELERATION = 30f;
    private static final int INTERVENTIONS_PER_SECOND = 5;

    private final int width;
    private final int height;

    public KappenballConfiguration(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public Vector2 getInitialVelocity() {
        return new Vector2(0, -.2f * height);
    }

    @Override
    public Vector2 getInitialPosition() {
        return new Vector2(.5f * width, 1f * height);
    }

    @Override
    public float getDecayRate() {
        return DECAY_RATE;
    }

    @Override
    public float getAcceleration() {
        return ACCELERATION;
    }

    @Override
    public float getInterventionAcceleration() {
        return INTERVENTION_ACCELERATION;
    }

    @Override
    public float getRadius() {
        return 0.025f * height;
    }

    @Override
    public int getInterventionsPerSecond() {
        return INTERVENTIONS_PER_SECOND;
    }
}
