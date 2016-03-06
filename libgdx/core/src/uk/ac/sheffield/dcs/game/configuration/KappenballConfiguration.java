package uk.ac.sheffield.dcs.game.configuration;

import com.badlogic.gdx.math.Vector2;
import uk.ac.sheffield.dcs.game.SliderListener;

public class KappenballConfiguration implements BallConfiguration, SliderListener {

    private static final float DECAY_RATE = .999f;
    private static final float ACCELERATION = 50f;
    private static final float INTERVENTION_ACCELERATION_MAX = 20f;
    private static final int INTERVENTIONS_PER_SECOND = 5;
    public static final float INTERVENTION_PERSISTENCE = .4f;
    public static final float INTERVENTION_CHANGE = .6f;

    private final int width;
    private final int height;

    private float interventionAcceleration;

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
        return interventionAcceleration;
    }

    @Override
    public float getRadius() {
        return 0.025f * height;
    }

    @Override
    public int getInterventionsPerSecond() {
        return INTERVENTIONS_PER_SECOND;
    }

    @Override
    public float getInterventionPersistenceProbability() {
        return INTERVENTION_PERSISTENCE;
    }

    @Override
    public float getInterventionChangeProbability() {
        return INTERVENTION_CHANGE;
    }

    @Override
    public void sliderChanged(float value) {
        interventionAcceleration = INTERVENTION_ACCELERATION_MAX * value;
    }
}
