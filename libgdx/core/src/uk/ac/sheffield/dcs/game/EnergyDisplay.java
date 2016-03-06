package uk.ac.sheffield.dcs.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EnergyDisplay extends Actor implements BallListener {

    private BitmapFont font;
    private float energy;

    public EnergyDisplay(float x, float y, int width, int height) {
        setBounds(x, y, width, height);

        System.out.println(x);
        System.out.println(y);
        System.out.println(width);
        System.out.println(height);

        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Noteworthy.ttc"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        font = freeTypeFontGenerator.generateFont(parameter);
        font.getData().setScale(0.07f, 0.07f);
        freeTypeFontGenerator.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        font.draw(batch, Integer.toString((int) energy), 0, getY() + (getHeight() * .7f));
    }

    @Override
    public void energyExpended(float delta) {
        energy += delta * 20;
    }

    @Override
    public void ended() {
        energy = 0;
    }

    @Override
    public void spiked() {
        energy = 0;
    }
}
