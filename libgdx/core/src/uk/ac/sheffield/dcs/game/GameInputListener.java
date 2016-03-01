package uk.ac.sheffield.dcs.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import static com.badlogic.gdx.Input.Keys.DPAD_LEFT;
import static com.badlogic.gdx.Input.Keys.DPAD_RIGHT;

public class GameInputListener extends InputListener implements GameInputFacade {

    private boolean left = false;
    private boolean right = false;

    private void resolve(InputEvent event, float x, float y) {
        float width = event.getStage().getWidth() * 0.5f;
        left = x <= width;
        right = !left;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        resolve(event, x, y);
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
        left = right = false;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        super.touchDragged(event, x, y, pointer);
        resolve(event, x, y);
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        if (keycode == DPAD_RIGHT && left) left = false;
        if (keycode == DPAD_LEFT && right) right = false;
        return true;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        left = keycode == DPAD_RIGHT;
        right = keycode == DPAD_LEFT;
        return true;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
//        resolve(event, x, y);
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        super.exit(event, x, y, pointer, toActor);
        left = right = false;
    }

    @Override
    public boolean screenPressedRight() {
        return right;
    }

    @Override
    public boolean screenPressedLeft() {
        return left;
    }
}
