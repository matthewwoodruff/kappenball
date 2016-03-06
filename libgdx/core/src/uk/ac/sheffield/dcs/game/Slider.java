package uk.ac.sheffield.dcs.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Slider extends Actor {

    private final Texture thumb = new Texture("thumb.png");
    private final float thumbSize;
    private final float halfThumbSize;
    private float thumbx;
    private SliderListener sliderListener;

    public Slider(float x, float y, int width, int height) {
        setBounds(x, y, width, height);

        thumbx = x;
        thumbSize = height;
        halfThumbSize = thumbSize / 2f;

        addListener(new InputListener() {
            private void doX(float x) {
                float width = getWidth();
                float maxX = width - halfThumbSize;
                float minx = halfThumbSize;
                float sliderValue = 0;
                float tempX;
                if (x > maxX) {
                    tempX = maxX;
                    sliderValue = 1;
                }
                else if (x < minx) {
                    tempX = minx;
                }
                else {
                    tempX = x;
                    sliderValue = x / width;
                }
                thumbx = tempX - halfThumbSize + getX();
                if (sliderListener != null) sliderListener.sliderChanged(sliderValue);
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                doX(x);
                return true;
            }
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                doX(x);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(thumb, thumbx, getY(), thumbSize, thumbSize);
    }

    public void setSliderListener(SliderListener sliderListener) {
        this.sliderListener = sliderListener;
    }
}
