package core.graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import core.math.IntRect;
import core.math.IntVector2;

public class Texture {
    BufferedImage img;
    private IntVector2 size_ = IntVector2.ZERO;

    Texture(BufferedImage img) {
        this.img = img;
        size_ = new IntVector2(img.getWidth(), img.getHeight()); 
    }

    public Texture(String filename) {
        try {
            img = ImageIO.read(getClass().getClassLoader().getResource(filename));
            size_ = new IntVector2(img.getWidth(), img.getHeight()); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final Texture getSubTexture(IntRect rect) {
        BufferedImage subimg = new BufferedImage(rect.width(), rect.height(), img.getType());
        float[] array = img.getRaster().getPixels(rect.left(), rect.top(), rect.width(), rect.height(), (float[])null);
        subimg.getRaster().setPixels(0, 0, rect.width(), rect.height(), array);
        return new Texture(subimg);
    }

    public TiledTexture createTiledTexture(IntVector2 tilesize) {
        return new TiledTexture(img, tilesize);
    }

    public TiledTexture createTiledTexture(int tilewidth, int tileheight) {
        return new TiledTexture(img, new IntVector2(tilewidth, tileheight));
    }

    public final IntVector2 getSize() {
        return size_;
    }

    public final int getWidth() {
        return size_.x;
    }

    public final int getHeight() {
        return size_.y;
    }
}