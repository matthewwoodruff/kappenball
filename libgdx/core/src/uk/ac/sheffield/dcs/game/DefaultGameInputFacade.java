package uk.ac.sheffield.dcs.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;

import static com.badlogic.gdx.Gdx.graphics;
import static com.badlogic.gdx.Gdx.input;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;

public class DefaultGameInputFacade implements GameInputFacade {

    @Override
    public boolean screenPressedRight() {
        return screenPressed(input) && 2 * input.getX() > graphics.getWidth() || input.isKeyPressed(LEFT);
    }

    @Override
    public boolean screenPressedLeft() {
        return (screenPressed(input) && 2 * input.getX() <= graphics.getWidth()) || input.isKeyPressed(RIGHT);
    }

    private boolean screenPressed(Input input) {
        return input.isTouched() || input.isButtonPressed(Buttons.LEFT);
    }
}
