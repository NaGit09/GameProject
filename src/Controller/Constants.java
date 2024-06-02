package Controller;

import View.GamePanel;

public final class Constants {
    public static final int originalTileSize = 16; // 16x16 tile
    public static final int scale = 3;

    public static final int tileSize = originalTileSize * scale; // 48x48 tile
    public static  final int maxScreenColumns = 20;
    public static final int maxScreenRows = 12;

    // Window mode
    public static final int screenWidth = tileSize * maxScreenColumns; // 960 px
    public static final int screenHeight = tileSize * maxScreenRows; // 576 px

    public  static final int FPS = 60;

    // WORLD SETTINGS
    public static final int maxWorldColumns = 50;
    public static final int maxWorldRows = 50;
    public static final int worldWidth = tileSize * maxWorldColumns;
    public static final int worldHeight = tileSize * maxWorldRows;
    public static final int maxMaps = 10;

    // Private constructor to prevent instantiation
    private Constants() {
        throw new AssertionError("Cannot instantiate Controller.Constants class");
    }

    public static void main(String[] args) {
        System.out.println(Constants.FPS);
    }
}
