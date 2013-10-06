package de.kjellski.games.fluidrts.core.tileutil;

import playn.core.Json;

public class TiledMapLoader {
    public static TiledMap load(Json.Object document) {
        return new TiledMap(document);
    }
}
