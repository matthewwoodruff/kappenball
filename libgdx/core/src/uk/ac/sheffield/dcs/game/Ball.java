package uk.ac.sheffield.dcs.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Ball {

    private static final float DECAY_RATE = 0.7f;
    private static final float ACCELERATION = 50f;
    private static final float RANDOM_INPUT = 0.0f;
    private static final float RANDOM_NUMBER_BETWEEN_MINUS_20_AND_20 = 0.0f;

    private final Body body;
    private final float size;
    private final float halfSize;
    private GameInputFacade input;
    private final Texture texture = new Texture("ball.png");
    private boolean dying;
    private boolean dead;
    private BallListener ballListener;

    public Ball(Body body, float radius) {
        this.body = body;
        this.halfSize = radius * 2;
        this.size = radius * 4;
    }

    public void setBallListener(BallListener ballListener) {
        this.ballListener = ballListener;
    }

    public void setInput(GameInputFacade input) {
        this.input = input;
    }

    public void updateVelocity(float delta) {
        float accelerationInterval = getAcceleration() * delta;

        Vector2 linearVelocity = body.getLinearVelocity();

        float decayedVelocity = linearVelocity.x * (1-(DECAY_RATE * delta));
        float randomVelocity = RANDOM_INPUT * RANDOM_NUMBER_BETWEEN_MINUS_20_AND_20;

        float newXVelocity = decayedVelocity + accelerationInterval + randomVelocity;

        body.setLinearVelocity(newXVelocity, linearVelocity.y);
    }

    private float getAcceleration() {
        return input == null ? 0 : input.screenPressedLeft() ? ACCELERATION : input.screenPressedRight() ? -ACCELERATION : 0;
    }

    public void render(Batch batch) {
        Vector2 position = body.getPosition();
        batch.draw(texture, position.x - halfSize, position.y - halfSize, size, size);
    }

    public void dispose() {
        texture.dispose();
    }

    public Body getBody() {
        return body;
    }

    public void spike() {
        dying = true;
        if (ballListener != null)
            ballListener.spiked();
    }

    public boolean isAlive() {
        return !(dead || dying);
    }

    public void end() {
        dead = true;
        if (ballListener != null)
            ballListener.succeeded();
    }
}
