package uk.ac.sheffield.dcs.game.configuration;

import com.badlogic.gdx.math.Vector2;

public interface BallConfiguration {
    Vector2 getInitialVelocity();
    Vector2 getInitialPosition();
    float getDecayRate();
    float getAcceleration();
    float getInterventionAcceleration();
    float getRadius();
    int getInterventionsPerSecond();
    float getInterventionPersistenceProbability();
    float getInterventionChangeProbability();
}
