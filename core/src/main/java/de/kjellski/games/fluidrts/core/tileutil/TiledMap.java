package de.kjellski.games.fluidrts.core.tileutil;

import de.kjellski.games.fluidrts.core.entities.Entity;
import playn.core.GroupLayer;
import playn.core.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Abstraction of the mapeditor.org generated .json files
 */
public class TiledMap {
    public GroupLayer BaseGroupLayer;

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
                "BaseGroupLayer=" + BaseGroupLayer +
                ", height=" + height +
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
        height = object.getInt("height");
        layers = parseLayers(object.getArray("layers"));
        orientation = Enum.valueOf(TiledMapOrientation.class, object.getString("orientation"));
        properties = parseProperties(object.getObject("properties"));
        tileheight = object.getInt("tileheight");
        tilesets = parseTilesets(object.getArray("tilesets"));
        tilewidth = object.getInt("tilewidth");
        version = object.getInt("version");
        width = object.getInt("width");
    }

    private List<TileSet> parseTilesets(Json.Array tilesets) {
        List<TileSet> result = new ArrayList<TileSet>();

        for (int i = 0; i < tilesets.length(); i++)
            result.add(new TileSet(tilesets.getObject(i)));

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

    private List<TileLayer> parseLayers(Json.Array jsonLayers) {
        List<TileLayer> result = new ArrayList<TileLayer>();

        for (int i = 0; i < jsonLayers.length(); i++)
            result.add(new TileLayer(jsonLayers.getObject(i), tilesets));

        return result;
    }
}
