package uk.ac.sheffield.dcs;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import uk.ac.sheffield.dcs.screen.GameScreen;
import uk.ac.sheffield.dcs.screen.GameStartListener;
import uk.ac.sheffield.dcs.screen.MainMenuScreen;

public class KappenballGame extends Game implements GameStartListener {
	SpriteBatch batch;

    @Override
    public void create () {
        batch = new SpriteBatch();
        MainMenuScreen screen = new MainMenuScreen(batch);
        screen.setGameStartListener(this);
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
    public void launchGame() {
        setScreen(new GameScreen());
    }
}
