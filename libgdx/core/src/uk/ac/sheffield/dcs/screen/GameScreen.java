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

public class GameScreen extends ScreenAdapter {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 60;

    private final OrthographicCamera camera = new OrthographicCamera();
    private final Stage stage;

    public GameScreen() {
        DefaultGameInputFacade inputFacade = new DefaultGameInputFacade();
        Environment environment = new Environment(inputFacade, new GameInputListener(), 100, 50);

        Group group = new Group();

        group.addActor(environment);
        group.setPosition(0, 5);

        HorizontalGroup top = new HorizontalGroup();
        top.setOrigin(0, 55);
        top.setHeight(5);
        HorizontalGroup bottom = new HorizontalGroup();
        bottom.setOrigin(0, 0);
        bottom.setHeight(5);

        stage = new Stage(new FitViewport(WIDTH, HEIGHT, camera));
        stage.addActor(top);
        stage.addActor(bottom);
        stage.addActor(group);
        stage.setDebugAll(false);

//        Gdx.input.setInputProcessor(stage);
//        stage.setKeyboardFocus(environment);

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
