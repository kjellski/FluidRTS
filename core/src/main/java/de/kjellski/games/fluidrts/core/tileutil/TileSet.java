package de.kjellski.games.fluidrts.core.tileutil;

import playn.core.Json;

import java.util.Properties;

public class TileSet {
    public int firstgid;
    public String image;
    public int imageheight;
    public int imagewidth;
    public int margin;
    public String name;
    public Properties properties;
    public int spacing;
    public int tileheight;
    public Properties tileproperties;
    public int tilewidth;

    private TileSet() {
    }

    public TileSet(Json.Object object) {
        firstgid = object.getInt("firstgid");
        image = object.getString("image");
        imageheight = object.getInt("imageheight");
        imagewidth = object.getInt("imagewidth");
        margin = object.getInt("margin");
        name = object.getString("name");
        properties = parseProperties(object.getObject("properties"));
        spacing = object.getInt("spacing");
        tileheight = object.getInt("tileheight");
        tileproperties = parseProperties(object.getObject("tileproperties"));
        tilewidth = object.getInt("tilewidth");
    }

    public boolean contains(int tileid)
    {
        return (imageheight / tileheight) * (imageheight / tileheight) <= tileid
                && firstgid >= tileid;
    }

    @Override
    public String toString() {
        return "TileSet{" +
                "firstgid=" + firstgid +
                ", image='" + image + '\'' +
                ", imageheight=" + imageheight +
                ", imagewidth=" + imagewidth +
                ", margin=" + margin +
                ", name='" + name + '\'' +
                ", properties=" + properties +
                ", spacing=" + spacing +
                ", tileheight=" + tileheight +
                ", tilewidth=" + tilewidth +
                '}';
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
}
