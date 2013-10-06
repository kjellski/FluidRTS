package de.kjellski.games.fluidrts.core;

import playn.core.AssetWatcher;
import playn.core.GroupLayer;
import playn.core.Json;
import playn.core.PlayN;
import playn.core.util.Callback;

public class FluidWorldLoader {
    public final static int TILE_WIDTH = 32;
    public final static int TILE_HEIGHT = 32;

    public static void CreateWorld(String level, GroupLayer worldLayer, final Callback<FluidLevel> callback) {
        final FluidLevel fluidLevel = new FluidLevel(worldLayer);

        // load the level
        PlayN.assets().getText(level, new Callback.Chain<String>(callback) {
            @Override
            public void onSuccess(String resource) {
                // create an asset watcher that will call our callback when all assets
                // are loaded
                AssetWatcher assetWatcher = new AssetWatcher(new AssetWatcher.Listener() {
                    @Override
                    public void done() {
                        callback.onSuccess(fluidLevel);
                    }

                    @Override
                    public void error(Throwable e) {
                        callback.onFailure(e);
                    }
                });

                // parse the level
                Json.Object document = PlayN.json().parse(resource);
                fluidLevel.loadMap(document, assetWatcher);

                // start the watcher (it will call the callback when everything is
                // loaded)
                assetWatcher.start();
            }
        });
    }
}
