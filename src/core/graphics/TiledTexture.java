package core.graphics;

import java.awt.image.BufferedImage;

import core.math.IntRect;
import core.math.IntVector2;

public final class TiledTexture extends Texture {
    private final IntVector2 tilesize;
    private Texture[][] tiles_;

    TiledTexture(BufferedImage img, IntVector2 tilesize) {
        super(img);
        if (tilesize == null || tilesize.x <= 0 || tilesize.y <= 0) {
            throw new IllegalArgumentException("tilesize");
        }
        this.tilesize = tilesize;
        generateTiles();
    }

    public TiledTexture(String filename, IntVector2 tilesize) {
        super(filename);
        if (tilesize == null || tilesize.x <= 0 || tilesize.y <= 0) {
            throw new IllegalArgumentException("tilesize");
        }
        this.tilesize = tilesize;
        generateTiles();
    }

    private void generateTiles() {
        int rows = getHeight() / tilesize.y;
        int cols = getWidth() / tilesize.x;
        tiles_ = new Texture[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                tiles_[i][j] = getSubTexture(
                        new IntRect(j * tilesize.x, i * tilesize.y, (j + 1) * tilesize.x, (i + 1) * tilesize.y));
            }
        }
    }

    public Texture getTexture() {
        return new Texture(img);
    }

    public Texture getTile(int row, int column) {
        return tiles_[row][column];
    }

    public Texture[] getAllTiles(boolean columnPriority) {
        Texture[] tiles = new Texture[getMaxTileCount()];
        for (int i = 0; i < getMaxRows(); i++) {
            for (int j = 0; j < getMaxCols(); j++) {
                if (columnPriority) {
                    tiles[j * getMaxRows() + i] = tiles_[i][j];
                } else {
                    tiles[i * getMaxCols() + j] = tiles_[i][j];
                }
            }
        }
        return tiles;
    }

    public int getMaxTileCount() {
        return tiles_.length * tiles_[0].length;
    }

    public int getMaxRows() {
        return tiles_.length;
    }

    public int getMaxCols() {
        return tiles_[0].length;
    }
}