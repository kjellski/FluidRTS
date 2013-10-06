package de.kjellski.games.fluidrts.core.entities;

import de.kjellski.games.fluidrts.core.FluidLevel;
import playn.core.Image;

public class GrassEntity extends Entity {
    @SuppressWarnings("hiding")
    public static String TYPE = "Grass";

    public GrassEntity(FluidLevel fluidLevel, float px, float py) {
        super(fluidLevel, px, py, 0);
    }

    @Override
    public void update(float delta) {
        x += delta * getVelocity();
        layer.setTranslation(x, y);

        if (x > getWidth() + getMaximumWidth()) {
            x = -getWidth();
            y = (float) (Math.random() * getMaximumHeight());
        }
    }

    @Override
    float getWidth() {
        return 32.0f;
    }

    @Override
    float getHeight() {
        return 32.0f;
    }

    float getMaximumWidth() {
        return 32.0f;
    }

    float getMaximumHeight() {
        return 32.0f;
    }

    float getVelocity() {
        return 0.000f;
    }

    @Override
    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
        layer.setTranslation(x, y);
    }

    @Override
    public void initPreLoad(FluidLevel FluidLevel) {
        FluidLevel.dynamicLayer.add(layer);
    }

    @Override
    public void initPostLoad(FluidLevel FluidLevel) {
    }

    @Override
    public Image getImage() {
        return image;
    }

    private static Image image = loadImage("terrain/Grass.png");
}
