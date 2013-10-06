package de.kjellski.games.fluidrts.core.tileutil;

import playn.core.Json;

import java.util.List;

public class TileLayer {
    public Json.Array data = null;
    public int height = 20;
    public String name = null;
    public float opacity = 1;
    public TileLayerType type = null;
    public boolean visible = true;
    public int width = 20;
    public int x = 0;
    public int y = 0;

    final List<TileSet> tileset;

    @SuppressWarnings("unused")
    private TileLayer() {
        throw new RuntimeException("private constructor called");
    }

    @Override
    public String toString() {
        return "TileLayer{" +
                "data=" + data +
                ", height=" + height +
                ", name='" + name + '\'' +
                ", opacity=" + opacity +
                ", type=" + type +
                ", visible=" + visible +
                ", width=" + width +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public TileLayer(Json.Object object, List<TileSet> tileset) {
        this.tileset = tileset;

        data = object.getArray("data");
        height = object.getInt("height");
        name = object.getString("name");
        opacity = object.getNumber("opacity");
        type = Enum.valueOf(TileLayerType.class, object.getString("type"));
        visible = object.getBoolean("visible");
        width = object.getInt("width");
        x = object.getInt("x");
        y = object.getInt("y");
    }

    public Tile getTile(int x, int y) {
        Integer pos = x + y * width;
        TileSet tilesTileSet;
        for (TileSet set : tileset) {
            if (set.contains(pos)) {
                tilesTileSet = set;
            }
        }

        return new Tile(tilesTileSet, tilesTileSet.tileproperties.get(pos.toString()), , x, y);
    }
}
