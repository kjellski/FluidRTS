package de.kjellski.games.fluidrts.core;

import playn.core.*;
import playn.core.util.Callback;

import static playn.core.PlayN.*;

public class FluidRTS extends Game.Default {

    // scale difference between screen space (pixels) and world space (physics).
    public static final float physUnitPerScreenUnit = 1 / 10f;
    public static final float GRAVITY_X = 0.0f;
    public static final float GRAVITY_Y = 0.0f;

    ImageLayer bgLayer;

    // main layer that holds the world. note: this gets scaled to world space
    GroupLayer worldLayer;

    // main world
    FluidLevel world = null;
    boolean worldLoaded = false;

    public FluidRTS() {
        super(33); // call update every 33ms (30 times per second)
    }

    @Override
    public void init() {
        // create and add background image layer
        Image bgImage = assets().getImage("images/bg.png");
        ImageLayer bgLayer = graphics().createImageLayer(bgImage);
        graphics().rootLayer().add(bgLayer);

        // create our world layer (scaled to "world space")
        worldLayer = graphics().createGroupLayer();
        worldLayer.setScale(1f / physUnitPerScreenUnit);
        graphics().rootLayer().add(worldLayer);

        FluidWorldLoader.CreateWorld("levels/level002.json", worldLayer, new Callback<FluidLevel>() {
            @Override
            public void onSuccess(FluidLevel resource) {
                world = resource;
                worldLoaded = true;
            }

            @Override
            public void onFailure(Throwable err) {
                PlayN.log().error("Error loading fluid world: " + err.getMessage());
            }
        });

        // hook up our pointer listener
        pointer().setListener(new Pointer.Adapter() {
            @Override
            public void onPointerStart(Pointer.Event event) {
                if (worldLoaded) {
//                    GrassEntity grassEntity = new GrassEntity(world,
//                            physUnitPerScreenUnit * event.x(),
//                            event.x(),
//                            physUnitPerScreenUnit * event.y()
//                            event.y());
//                    world.add(grassEntity);
                    log().info("onPointerStart(" + event.x() + ", " + event.y() + ")");
                    log().info("onPointerStart(" + physUnitPerScreenUnit * event.x() + ", "
                            + physUnitPerScreenUnit * event.y() + ")");
                }
            }
        });
    }

    @Override
    public void update(int delta) {
    }

    @Override
    public void paint(float alpha) {
        // the background automatically paints itself, so no need to do anything here!
    }
}
