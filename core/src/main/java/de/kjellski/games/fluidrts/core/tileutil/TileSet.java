package de.kjellski.games.fluidrts.core.tileutil;

import playn.core.Image;
import playn.core.Json;

import java.util.Properties;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.log;

public class TileSet {
    public int firstgid;
    public String imagepath;
    public int imageheight;
    public int imagewidth;
    public int margin;
    public String name;
    public Properties properties;
    public int spacing;
    public int tileheight;
    public Properties tileproperties;
    public int tilewidth;

    Image image;

    private TileSet() {
    }

    public TileSet(Json.Object object) {
        firstgid = object.getInt("firstgid");
        imagepath = object.getString("image").replaceAll("\\.\\./", "");
        //log().info("imagepath: " + imagepath);
        image = assets().getImage(imagepath);
        imageheight = object.getInt("imageheight");
        imagewidth = object.getInt("imagewidth");
        margin = object.getInt("margin");
        name = object.getString("name");
        properties = parseProperties(object.getObject("properties"));
        spacing = object.getInt("spacing");
        tileheight = object.getInt("tileheight");

        Json.Object tmp = object.getObject("tileproperties");
        if (tmp != null) {
            // This can be null if noone assigned any properties for this tilesets tileproperties
            //log().info(object.toString());
            tileproperties = parseTileProperties(tmp);
        }

        tilewidth = object.getInt("tilewidth");
    }

    public boolean contains(int pos) {
        int upper = (imageheight / tileheight) * (imageheight / tileheight);
        int lower = firstgid;
        return pos <= upper && pos >= lower;
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
        if (object == null)
            throw new NullPointerException("object was null");

        Properties result = new Properties();
        log().info(object.toString());

        Json.TypedArray<String> keys = object.keys();
        for (int i = 0; i < keys.length(); i++) {
            String key = keys.get(i);
//            log().info(key);
            result.put(key, object.getString(key));
        }
        return result;
    }

    public Properties parseTileProperties(Json.Object object) {
        if (object == null)
            throw new NullPointerException("object was null");
        Properties result = new Properties();

//        log().info("parseTileProperties");
//        log().info(object.toString());

        Json.TypedArray<String> keys = object.keys();
        for (int i = 0; i < keys.length(); i++) {
            String key = keys.get(i);
//            log().info(key);
            result.put(key, parseProperties(object.getObject(key)));
        }

        return result;
    }

    private Image.Region getRegion(int x, int y) {
        return this.image.subImage(x * tilewidth, y * tileheight, tilewidth, tileheight);
    }

    public Image.Region getTilesImage(int tileId) {
        tileId -= 1;
        int x = (tileId % tilewidth);
        int y = (int) Math.floor(tileId / tilewidth);
        log().info("getTilesImage(" + tileId + ") => (" + x + ", " + y + ")");
        return getRegion(x, y);
    }
}
