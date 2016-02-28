package uk.ac.sheffield.dcs.world;

import com.badlogic.gdx.physics.box2d.World;

public class WorldRegister {

    private final World world;
    private float width;
    private float height;

    public WorldRegister(World world) {
        this.world = world;
    }

    public WorldRegister width(float width) {
        this.width = width;
        return this;
    }

    public WorldRegister height(float height) {
        this.height = height;
        return this;
    }

    public World getWorld() {
        return world;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

}
