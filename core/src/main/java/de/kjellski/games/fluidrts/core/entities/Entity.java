package de.kjellski.games.fluidrts.core.entities;

import de.kjellski.games.fluidrts.core.FluidLevel;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.util.Callback;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;

public abstract class Entity {
    public static final int TileHeight = 32;
    public static final int TileWidth = 32;

    final ImageLayer layer;
    float x, y, angle;

    public Entity(final FluidLevel fluidLevel, float px, float py, float pangle) {
        this.x = px;
        this.y = py;
        this.angle = pangle;
        layer = graphics().createImageLayer(getImage());
        initPreLoad(fluidLevel);

        getImage().addCallback(new Callback<Image>() {
            @Override
            public void onSuccess(Image image) {
                // since the image is loaded, we can use its width and height
                layer.setOrigin(image.width() / 2f, image.height() / 2f);
                layer.setScale(getWidth() / image.width(), getHeight() / image.height());
                layer.setTranslation(x, y);
                layer.setRotation(angle);
                initPostLoad(fluidLevel);
            }

            @Override
            public void onFailure(Throwable err) {
                log().error("Error loading image: " + err.getMessage());
            }
        });
    }

    public abstract void initPreLoad(final FluidLevel fluidLevel);

    public abstract void initPostLoad(final FluidLevel fluidLevel);

    public void paint(float alpha) {
    }

    public void update(float delta) {
    }

    public void setPos(float x, float y) {
        layer.setTranslation(x, y);
    }

    public void setAngle(float a) {
        layer.setRotation(a);
    }

    abstract float getWidth();

    abstract float getHeight();

    public abstract Image getImage();

    protected static Image loadImage(String name) {

        return assets().getImage("images/" + name);
    }
}
