package Model.asset.tile;

import Controller.GameController;
import Controller.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    public final GameController gameController;
    public final Tile[] tiles;
    public final int[][][] mapTileNumbers;

    boolean drawPath = true;

    public TileManager(GameController gameController) {
        this.gameController = gameController;

        this.tiles = new Tile[50];
        this.mapTileNumbers = new int[gameController.getMaxMaps()][gameController.getMaxWorldColumns()][gameController.getMaxWorldRows()];

        getTileImage();
        loadMap("/resources/maps/worldV3.txt", 0);
        loadMap("/resources/maps/interior01.txt", 1);
    }

    public void getTileImage() {
        // PLACEHOLDER
        setup(0, "grass00", false);
        setup(1, "grass00", false);
        setup(2, "grass00", false);
        setup(3, "grass00", false);
        setup(4, "grass00", false);
        setup(5, "grass00", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(9, "grass00", false);
        // PLACEHOLDER

        // TILES LOAD IN
        setup(10, "grass00", false);
        setup(11, "grass01", false);
        setup(12, "water00", true);
        setup(13, "water01", true);
        setup(14, "water02", true);
        setup(15, "water03", true);
        setup(16, "water04", true);
        setup(17, "water05", true);
        setup(18, "water06", true);
        setup(19, "water07", true);
        setup(20, "water08", true);
        setup(21, "water09", true);
        setup(22, "water10", true);
        setup(23, "water11", true);
        setup(24, "water12", true);
        setup(25, "water13", true);
        setup(26, "road00", false);
        setup(27, "road01", false);
        setup(28, "road02", false);
        setup(29, "road03", false);
        setup(30, "road04", false);
        setup(31, "road05", false);
        setup(32, "road06", false);
        setup(33, "road07", false);
        setup(34, "road08", false);
        setup(35, "road09", false);
        setup(36, "road10", false);
        setup(37, "road11", false);
        setup(38, "road12", false);
        setup(39, "earth", false);
        setup(40, "wall", true);
        setup(41, "tree", true);
        setup(42, "hut", false);
        setup(43, "floor01", false);
        setup(44,"teleport",false);

        setup(45,"tree_2",true);



    }

    public void setup(int index, String imageName, boolean collision) {
        try {
            tiles[index] = new Tile();
            tiles[index].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/images/tiles/" + imageName + ".png"))));
            tiles[index].setImage(UtilityTool.scaleImage(tiles[index].getImage(), gameController.getTileSize(), gameController.getTileSize()));
            tiles[index].setCollision(collision);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapPath, int map) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(mapPath);
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int column = 0;
            int row = 0;

            while (column < gameController.getMaxWorldColumns() && row < gameController.getMaxWorldRows()) {
                String line = bufferedReader.readLine();

                while (column < gameController.getMaxWorldColumns()) {
                    String[] numbers = line.split(" ");
                    int number = Integer.parseInt(numbers[column]);

                    mapTileNumbers[map][column][row] = number;
                    column++;
                }
                if (column == gameController.getMaxWorldColumns()) {
                    column = 0;
                    row++;
                }
            }

            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {
        int worldColumn = 0;
        int worldRow = 0;

        while (worldColumn < gameController.getMaxWorldColumns() && worldRow < gameController.getMaxWorldRows()) {

            int tileNumber = mapTileNumbers[gameController.getCurrentMap()][worldColumn][worldRow];

            int worldX = worldColumn * gameController.getTileSize();
            int worldY = worldRow * gameController.getTileSize();
            int screenX = worldX - gameController.getPlayer().getWorldX() + gameController.getPlayer().getScreenX();
            int screenY = worldY - gameController.getPlayer().getWorldY() + gameController.getPlayer().getScreenY();

            // Stop moving the camera at world edge
            int rightOffset = gameController.getScreenWidth() - gameController.getPlayer().getScreenX();
            screenX = checkIfAtEdgeOfXAxis(worldX, screenX, rightOffset);

            int bottomOffset = gameController.getScreenHeight() - gameController.getPlayer().getScreenY();
            screenY = checkIfAtEdgeOfYAxis(worldY, screenY, bottomOffset);

            if (UtilityTool.isInsidePlayerView(worldX, worldY, gameController)) {
                graphics2D.drawImage(tiles[tileNumber].getImage(), screenX, screenY, null);

            } else if (gameController.getPlayer().getScreenX() > gameController.getPlayer().getWorldX()
                    || gameController.getPlayer().getScreenY() > gameController.getPlayer().getWorldY()
                    || rightOffset > gameController.getWorldWidth() - gameController.getPlayer().getWorldX()
                    || bottomOffset > gameController.getWorldHeight() - gameController.getPlayer().getWorldY()) {
                graphics2D.drawImage(tiles[tileNumber].getImage(), screenX, screenY, null);
            }

            worldColumn++;

            if (worldColumn == gameController.getMaxWorldColumns()) {
                worldColumn = 0;
                worldRow++;
            }
        }
        if (drawPath) {
            graphics2D.setColor(new Color(255, 0, 0, 70));
            for (int i = 0; i < gameController.pFinder.pathList.size(); i++) {
                int wordX = gameController.pFinder.pathList.get(i).col + gameController.getTileSize();

                int wordY = gameController.pFinder.pathList.get(i).row + gameController.getTileSize();

                int screenX = wordX - gameController.player.worldX + gameController.player.getScreenX();
                int screenY = wordY - gameController.player.worldY + gameController.player.getScreenX();
                graphics2D.fillRect(screenX, screenY, gameController.getTileSize(), gameController.getTileSize());

            }
        }
    }

    private int checkIfAtEdgeOfXAxis(int worldX, int screenX, int rightOffset) {
        if (gameController.getPlayer().getScreenX() > gameController.getPlayer().getWorldX()) {
            return worldX;
        }

        if (rightOffset > gameController.getWorldWidth() - gameController.getPlayer().getWorldX()) {
            return gameController.getScreenWidth() - (gameController.getWorldWidth() - worldX);
        }

        return screenX;
    }

    private int checkIfAtEdgeOfYAxis(int worldY, int screenY, int bottomOffset) {
        if (gameController.getPlayer().getScreenY() > gameController.getPlayer().getWorldY()) {
            return worldY;
        }

        if (bottomOffset > gameController.getWorldHeight() - gameController.getPlayer().getWorldY()) {
            return gameController.getScreenHeight() - (gameController.getWorldHeight() - worldY);
        }

        return screenY;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public int[][][] getMapTileNumbers() {
        return mapTileNumbers;
    }
}
