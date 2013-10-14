package de.kjellski.games.fluidrts.core.tileutil;

import playn.core.GroupLayer;
import playn.core.ImageLayer;
import playn.core.Json;
import playn.core.PlayN;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Abstraction of the mapeditor.org generated .json files
 */
public class TiledMap {
    // -----------------------------------------------------------------------------
    // Properties the .json has
    // -----------------------------------------------------------------------------
    public int height = -1;
    public List<TileLayer> layers = null;
    public TiledMapOrientation orientation = null;
    public Properties properties = null;
    public int tileheight = -1;
    public List<TileSet> tilesets = null;
    public int tilewidth = -1;
    public int version = -1;
    public int width = -1;

    @Override
    public String toString() {
        return "TiledMap{" +
                " height=" + height +
                ", layers=" + layers +
                ", orientation=" + orientation +
                ", properties=" + properties +
                ", tileheight=" + tileheight +
                ", tilesets=" + tilesets +
                ", tilewidth=" + tilewidth +
                ", version=" + version +
                ", width=" + width +
                '}';
    }

    private TiledMap() {
    }

    public TiledMap(Json.Object json) {
        this.parse(json);
    }

    private void parse(Json.Object object) {
        //log().debug("TiledMap(" + object.toString() + ")");
        height = object.getInt("height");
        tilesets = parseTilesets(object.getArray("tilesets", Json.Object.class));
        orientation = Enum.valueOf(TiledMapOrientation.class, object.getString("orientation"));
        properties = parseProperties(object.getObject("properties"));
        tileheight = object.getInt("tileheight");
        tilewidth = object.getInt("tilewidth");
        version = object.getInt("version");
        width = object.getInt("width");

        // this accesses tilesets, needs to be done last
        layers = parseLayers(object.getArray("layers"), tilesets);
    }

    private List<TileSet> parseTilesets(Json.TypedArray<Json.Object> objectTypedArray) {
        if (objectTypedArray == null)
            throw new NullPointerException("objectTypedArray was null");


        List<TileSet> result = new ArrayList<TileSet>();
        for (int i = 0; i < objectTypedArray.length(); i++) {
            Json.Object tmp = objectTypedArray.get(i);
//            log().info(i + ": "+ tmp.toString());
            result.add(new TileSet(tmp));
        }
        return result;
    }

    private Properties parseProperties(Json.Object object) {
        Properties result = new Properties();

        Json.TypedArray<String> jsonProperties = object.keys();
        for (int i = 0; i < jsonProperties.length(); i++) {
            String key = jsonProperties.get(i);
            result.put(key, object.getString(key));
        }
        return result;
    }

    private List<TileLayer> parseLayers(Json.Array jsonLayers, List<TileSet> tilesets) {
        List<TileLayer> result = new ArrayList<TileLayer>();

        for (int i = 0; i < jsonLayers.length(); i++) {
//            log().info(tilesets.toString());
            result.add(new TileLayer(jsonLayers.getObject(i), tilesets));
        }

        return result;
    }

    public GroupLayer getBaseGroupLayer() {
        GroupLayer result = PlayN.graphics().createGroupLayer();

        for (TileLayer layer : layers) {
            for (int y = 0; y < layer.height; y++) {
                for (int x = 0; x < layer.width; x++) {
                    Tile tmptile = layer.getTile(x, y);
                    ImageLayer tmpImageLayer =PlayN.graphics().createImageLayer(tmptile.image);
                    // go on here: the images are correctly retrieved from the map, but not correctly drawn.
                    // we now need to position them inside the areay we're ale to look at on the map.
                    // translation doesn't work on these coordinates, try multiplying them by physXUnit shit
                    tmpImageLayer.setTranslation(x,y);
                    result.add(tmpImageLayer);
                }
            }
        }

        return result;
    }
}
