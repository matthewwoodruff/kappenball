package uk.ac.sheffield.dcs.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import uk.ac.sheffield.dcs.game.EnergyDisplay;
import uk.ac.sheffield.dcs.game.Environment;
import uk.ac.sheffield.dcs.game.Slider;
import uk.ac.sheffield.dcs.game.configuration.KappenballConfiguration;

import static java.lang.Math.round;

public class GameScreen extends ScreenAdapter {

    private final Stage stage;

    public GameScreen(final int width, final int height) {

        int environmentHeight = round(height * .75f);
        int menuHeight = round((height - environmentHeight) * .5f);

        EnergyDisplay energyDisplay = new EnergyDisplay((width - menuHeight) * .5f, environmentHeight + menuHeight, menuHeight, menuHeight);
        
        KappenballConfiguration config = new KappenballConfiguration(width, environmentHeight);
        Environment environment = new Environment(config, energyDisplay);

        Group group = new Group();
        group.addActor(environment);
        group.setPosition(0, menuHeight);

        Slider slider = new Slider(25, 2, 50, 5);
        slider.setSliderListener(config);

        OrthographicCamera camera = new OrthographicCamera();
        stage = new Stage(new FitViewport(width, height, camera));
        stage.addActor(slider);
        stage.addActor(group);
        stage.addActor(energyDisplay);
        stage.setDebugAll(false);

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
