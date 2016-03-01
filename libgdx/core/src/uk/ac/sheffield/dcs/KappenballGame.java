package uk.ac.sheffield.dcs;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import uk.ac.sheffield.dcs.screen.ConfigurationScreen;
import uk.ac.sheffield.dcs.screen.ConfigurationStartListener;
import uk.ac.sheffield.dcs.screen.GameScreen;
import uk.ac.sheffield.dcs.screen.GameStartListener;
import uk.ac.sheffield.dcs.screen.MainMenuScreen;

public class KappenballGame extends Game implements GameStartListener, ConfigurationStartListener {
	SpriteBatch batch;

    private static final int WIDTH = 100;
    private static final int HEIGHT = 60;

    @Override
    public void create () {
        batch = new SpriteBatch();
        MainMenuScreen screen = new MainMenuScreen(batch);
        screen.setGameStartListener(this);
        screen.setConfigurationStartListener(this);
        setScreen(screen);
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }

    @Override
    public void startGame() {
        setScreen(new GameScreen(WIDTH, HEIGHT));
    }

    @Override
    public void startConfiguration() {
        setScreen(new ConfigurationScreen(WIDTH, HEIGHT));
    }
}
