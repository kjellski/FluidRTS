package de.kjellski.games.fluidrts.core.tileutil;

import playn.core.Json;

import java.util.List;
import static playn.core.PlayN.log;

public class TileLayer {
    public int[] data = null;
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

    public TileLayer(Json.Object object, final List<TileSet> tileset) {
        if (tileset == null)
            throw new NullPointerException("tileset was null");

        log().debug("tileset: " + tileset.toString());
        this.tileset = tileset;
        log().debug("this.tileset: " + this.tileset.toString());
        Json.TypedArray<Integer> arr = object.getArray("data", Integer.class);
        this.data = new int[arr.length()];
        for(int i = 0; i < arr.length(); i++)
            this.data[i] = arr.get(i);

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
        Integer tileDataPos = x + y * width;
        int tileId = data[tileDataPos];
//        log().info("getTile(" + x + ", " + y + ") => " + tileDataPos + " => " + tileId);
        Tile tmp = null;

        if (tileId == 0)
        {
            String log = "The map contains an undifined tile at position(" + x + ", " + y + "), which is illegal.";
            log().error(log);
            throw new RuntimeException(log);
        }

        for (TileSet set : tileset) {
            if (set.contains(tileId)) {
                tmp = new Tile(set, tileId, x, y);
            }
        }

        if (tmp == null)
            throw new RuntimeException("unable to find tileset for tile at position " +
                    tileId + " for coordinates (" + x + "," + y + ")");

        return tmp;
    }
}
