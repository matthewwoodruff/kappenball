package uk.ac.sheffield.dcs.game;

public interface BallListener {
    void ended();
    void spiked();
    void energyExpended(float delta);
}

