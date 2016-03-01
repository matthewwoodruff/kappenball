package uk.ac.sheffield.dcs.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.utils.viewport.FitViewport;
import uk.ac.sheffield.dcs.game.DefaultGameInputFacade;
import uk.ac.sheffield.dcs.game.Environment;
import uk.ac.sheffield.dcs.game.GameInputListener;

import static java.lang.Math.round;

public class GameScreen extends ScreenAdapter {

    private final OrthographicCamera camera = new OrthographicCamera();
    private final Stage stage;

    public GameScreen(int width, int height) {

        DefaultGameInputFacade inputFacade = new DefaultGameInputFacade();
        int environmentHeight = round(height * .75f);

        Environment environment =
                new Environment(inputFacade, new GameInputListener(), width, environmentHeight);

        int menuHeight = round((height - environmentHeight) * .5f);

        Group group = new Group();
        group.addActor(environment);
        group.setPosition(0, menuHeight);

        HorizontalGroup top = new HorizontalGroup();
        top.setOrigin(0, height - menuHeight);
        top.setHeight(menuHeight);
        HorizontalGroup bottom = new HorizontalGroup();
        bottom.setOrigin(0, 0);
        bottom.setHeight(menuHeight);

//        Slider slider = new Slider(0, 100, 0.5f, false, (Skin) null);
//        slider.

        stage = new Stage(new FitViewport(width, height, camera));
        stage.addActor(top);
        stage.addActor(bottom);
        stage.addActor(group);
        stage.setDebugAll(true);

        Gdx.input.setInputProcessor(stage);
        stage.setKeyboardFocus(environment);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        stage.act(delta);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }
}
