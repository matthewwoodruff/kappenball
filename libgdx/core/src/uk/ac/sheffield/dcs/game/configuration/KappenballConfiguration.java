package uk.ac.sheffield.dcs.game.configuration;

import com.badlogic.gdx.math.Vector2;

public class KappenballConfiguration implements BallConfiguration {

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
        return null;
    }

    @Override
    public Vector2 getInitialPosition() {
        return null;
    }

    @Override
    public float getDecayRate() {
        return 0;
    }

    @Override
    public float getAcceleration() {
        return 0;
    }

    @Override
    public float getRandomInput() {
        return 0;
    }

    @Override
    public float getRandomNumberBetween20AndMinus20() {
        return 0;
    }
}
