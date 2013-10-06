package de.kjellski.games.fluidrts.core.tileutil;

import playn.core.Image;

import java.util.Properties;

public final class Tile {
    public final  Properties properties;
    public final TileSet tileset;
    public final Image image;
    public final int x,y;

    public Tile(TileSet tileset, Properties properties, Image image, int x, int y) {
        this.properties = properties;
        this.tileset = tileset;
        this.image = image;
        this.x = x;
        this.y = y;
    }
}
