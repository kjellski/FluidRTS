package de.kjellski.games.fluidrts.core.tileutil;

import playn.core.Image;

import java.util.Properties;

import static playn.core.PlayN.assets;

public final class Tile {
    //    public final  Properties properties;
    public final TileSet tileset;
    public final Image.Region image;
    public final int x, y;

    public Tile(TileSet tileset, int x, int y) {
        this.tileset = tileset;
        this.x = x;
        this.y = y;
        this.image = tileset.getRegion(x, y);
    }
}
