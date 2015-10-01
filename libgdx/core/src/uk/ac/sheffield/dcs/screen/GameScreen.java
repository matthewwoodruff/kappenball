package uk.ac.sheffield.dcs.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import uk.ac.sheffield.dcs.game.DefaultGameInputFacade;
import uk.ac.sheffield.dcs.game.Environment;

public class GameScreen extends ScreenAdapter {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 50;

    private final OrthographicCamera camera = new OrthographicCamera();
    private final Stage stage;

    public GameScreen() {
        DefaultGameInputFacade inputFacade = new DefaultGameInputFacade();
        Environment environment = new Environment(inputFacade, WIDTH, HEIGHT);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();

        TextButton button1 = new TextButton("Button 1", style);

        Container<Environment> environmentContainer = new Container<>(environment).maxHeight(200f);

        environmentContainer.setDebug(true);

        HorizontalGroup top = new HorizontalGroup().top();
        top.setHeight(5);
        HorizontalGroup bottom = new HorizontalGroup().bottom();
        bottom.setHeight(5);

        stage = new Stage(new FitViewport(WIDTH, HEIGHT, camera));
        stage.setDebugAll(true);
        stage.addActor(top);
        stage.addActor(bottom);
        stage.addActor(environmentContainer);
//        bottom.addActor(button1);
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
