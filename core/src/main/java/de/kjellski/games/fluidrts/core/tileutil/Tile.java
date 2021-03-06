package de.kjellski.games.fluidrts.core.tileutil;

import playn.core.Image;

import static playn.core.PlayN.log;

public final class Tile {
    //    public final  Properties properties;
    public final TileSet tileset;
    public final Image.Region image;
    public final int x, y;

    public Tile(TileSet tileset, int tileId, int x, int y) {
        this.tileset = tileset;
        this.x = x;
        this.y = y;
        this.image = tileset.getTilesImage(tileId);

        log().debug("Tile(set=" + tileset.firstgid + ", x=" + x + ", y=" + y + ", " +
                "image={" + this.image.x() + ", "+ this.image.y() + "})");
    }
}
