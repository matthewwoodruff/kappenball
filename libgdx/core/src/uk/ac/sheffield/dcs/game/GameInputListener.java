package uk.ac.sheffield.dcs.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class GameInputListener extends InputListener {

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        System.out.println("TOUCH DOWN");
        return super.touchDown(event, x, y, pointer, button);
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        System.out.println("TOUCH UP");
        super.touchUp(event, x, y, pointer, button);
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        System.out.println("TOUCH DRAGGED");
        super.touchDragged(event, x, y, pointer);
    }
}
