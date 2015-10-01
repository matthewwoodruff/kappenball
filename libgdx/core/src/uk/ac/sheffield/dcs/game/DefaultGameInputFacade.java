package uk.ac.sheffield.dcs.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;

import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;

public class DefaultGameInputFacade implements GameInputFacade {

    private final float width;
    private final float height;

    public DefaultGameInputFacade() {
        this.width = Gdx.graphics.getWidth();
        this.height = Gdx.graphics.getHeight();
    }

    @Override
    public boolean screenPressedRight() {
        Input input = Gdx.input;
        return screenPressed(input) && 2*input.getX() > width || input.isKeyPressed(LEFT);
    }

    @Override
    public boolean screenPressedLeft() {
        Input input = Gdx.input;
        return (screenPressed(input) && 2*input.getX() <= width) || input.isKeyPressed(RIGHT);
    }

    private boolean screenPressed(Input input) {
        return input.isTouched() ||  input.isButtonPressed(Buttons.LEFT);
    }
}
