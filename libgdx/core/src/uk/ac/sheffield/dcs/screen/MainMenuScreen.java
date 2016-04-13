package uk.ac.sheffield.dcs.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.badlogic.gdx.Input.Keys.ANY_KEY;
import static com.badlogic.gdx.Input.Keys.SPACE;

public class MainMenuScreen extends ScreenAdapter {

    private final SpriteBatch batch;
    private final BitmapFont font;

    private GameStartListener gameStartListener;
    private ConfigurationStartListener configurationStartListener;

    public MainMenuScreen(SpriteBatch batch) {
        this.batch = batch;
        this.font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, "Press space to start game key to continue...", 100, 50);
        batch.end();

        if (Gdx.input.isKeyPressed(SPACE) && configurationStartListener != null) {
          gameStartListener.startGame();
            dispose();
        }
        else if ((Gdx.input.isKeyPressed(ANY_KEY) || Gdx.input.isTouched()) && gameStartListener != null) {
          configurationStartListener.startConfiguration();
            dispose();
        }
    }

    public void setGameStartListener(GameStartListener gameStartListener) {
        this.gameStartListener = gameStartListener;
    }

    public void setConfigurationStartListener(ConfigurationStartListener configurationStartListener) {
        this.configurationStartListener = configurationStartListener;
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
