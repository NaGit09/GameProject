package Controller.util;

import Controller.GameController;
import Model.asset.Asset;
import Model.asset.entity.Entity;
import Model.asset.tile.Tile;

public class CollisionChecker {
    private final GameController gameController;
    public CollisionChecker(GameController gameController) {

        this.gameController = gameController;
    }
// KIỂM TRA XEM PLAYER ĐÃ CHẠM VÀO TILE HAY CHƯA
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getCollisionArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getCollisionArea().x + entity.getCollisionArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getCollisionArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getCollisionArea().y + entity.getCollisionArea().height;

        int entityLeftCol = entityLeftWorldX / gameController.getTileSize();
        int entityRightCol = entityRightWorldX / gameController.getTileSize();
        int entityTopRow = entityTopWorldY / gameController.getTileSize();
        int entityBottomRow = entityBottomWorldY / gameController.getTileSize();
        // EXTRA
        int [][][] map = gameController.getTileManager().getMapTileNumbers();
        int currentMap = gameController.update.getCurrentMap();
        int tileSize = gameController.getTileSize();
        int tileNum1, tileNum2;
        Tile[] tile = gameController.getTileManager().getTiles();


        switch (entity.getDirection()) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / tileSize;

                tileNum1 = map[currentMap][entityLeftCol][entityTopRow];
                tileNum2 = map[currentMap][entityRightCol][entityTopRow];

                if (tile[tileNum1].isCollision() ||tile[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / tileSize;

                tileNum1 = map[currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = map[currentMap][entityRightCol][entityBottomRow];

                if (tile[tileNum1].isCollision() || tile[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / tileSize;

                tileNum1 = map[currentMap][entityLeftCol][entityTopRow];
                tileNum2 = map[currentMap][entityLeftCol][entityBottomRow];

                if (tile[tileNum1].isCollision() || tile[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / tileSize;

                tileNum1 = map[currentMap][entityRightCol][entityTopRow];
                tileNum2 = map[currentMap][entityRightCol][entityBottomRow];

                if (tile[tileNum1].isCollision() || tile[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
        }
    }
    // KIỂM TRA XEM NGƯỜI CHƠI ĐÃ CHẠM VÀO OBJ CHƯA
    public int checkObject(Entity entity, boolean isPlayer) {
        int index = 999;

        Asset[][] objects = gameController.update.getObjects();
        for (int i = 0; i < objects[1].length; i++) {
            Asset object = objects[gameController.update.getCurrentMap()][i];
            if (object != null) {
                entity.getCollisionArea().x = entity.getWorldX() + entity.getCollisionArea().x;
                entity.getCollisionArea().y = entity.getWorldY() + entity.getCollisionArea().y;

                object.getCollisionArea().x = object.getWorldX() + object.getCollisionArea().x;
                object.getCollisionArea().y = object.getWorldY() + object.getCollisionArea().y;

                checkFutureMovement(entity);

                if (entity.getCollisionArea().intersects(object.getCollisionArea())) {
                    if (object.isCollision()) {
                        entity.setCollisionOn(true);
                    }
                    // NẾU PLAYER CHẠM VÀO THÌ TRẢ VỀ VỊ TRÍ CỦA OBJ ĐÓ TRONG GAME CONTROLLER CHỨA LIST OBJECT Đ
                    if (isPlayer) {
                        index = object.getIndex();
                    }
                }

                entity.getCollisionArea().x = entity.getCollisionDefaultX();
                entity.getCollisionArea().y = entity.getCollisionDefaultY();
                object.getCollisionArea().x = object.getCollisionDefaultX();
                object.getCollisionArea().y = object.getCollisionDefaultY();
            }
        }

        return index;
    }
    // KIỂM TRA XEM ENTITY CÓ VA CHẠM NHAU KHÔNG
    public int checkEntity(Entity entity, Asset[][] targets) {

        int index = 999;

        for (int i = 0; i < targets[1].length; i++) {
            Asset target = targets[gameController.update.getCurrentMap()][i];
            if (target != null) {
                entity.getCollisionArea().x = entity.getWorldX() + entity.getCollisionArea().x;
                entity.getCollisionArea().y = entity.getWorldY() + entity.getCollisionArea().y;

                target.getCollisionArea().x = target.getWorldX() + target.getCollisionArea().x;
                target.getCollisionArea().y = target.getWorldY() + target.getCollisionArea().y;

                checkFutureMovement(entity);

                if (entity.getCollisionArea().intersects(target.getCollisionArea())) {
                    if (target != entity) {
                        entity.setCollisionOn(true);
                        index = target.getIndex();
                    }
                }

                entity.getCollisionArea().x = entity.getCollisionDefaultX();
                entity.getCollisionArea().y = entity.getCollisionDefaultY();
                target.getCollisionArea().x = target.getCollisionDefaultX();
                target.getCollisionArea().y = target.getCollisionDefaultY();
            }
        }

        return index;
    }
    // KIỂM TRA XEM PLAYER CÓ CHẠM VÀO ENTITY HAY KHÔNG
    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;
        Asset player = gameController.getPlayer();
        entity.getCollisionArea().x = entity.getWorldX() + entity.getCollisionArea().x;
        entity.getCollisionArea().y = entity.getWorldY() + entity.getCollisionArea().y;

        player.getCollisionArea().x = player.getWorldX() + player.getCollisionArea().x;
        player.getCollisionArea().y = player.getWorldY() + player.getCollisionArea().y;

        checkFutureMovement(entity);

        if (entity.getCollisionArea().intersects(player.getCollisionArea())) {
            entity.setCollisionOn(true);
            contactPlayer = true;
        }

        entity.getCollisionArea().x = entity.getCollisionDefaultX();
        entity.getCollisionArea().y = entity.getCollisionDefaultY();
        player.getCollisionArea().x = player.getCollisionDefaultX();
        player.getCollisionArea().y = player.getCollisionDefaultY();

        return contactPlayer;
    }
    // THIẾT LẬP HƯỚNG ĐI MỚI CHO ENTITY KHI XẢY RA VA CHẠM
    public  void checkFutureMovement(Entity entity) {
        switch (entity.getDirection()) {
            case "up" -> entity.getCollisionArea().y -= entity.getSpeed();
            case "down" -> entity.getCollisionArea().y += entity.getSpeed();
            case "left" -> entity.getCollisionArea().x -= entity.getSpeed();
            case "right" -> entity.getCollisionArea().x += entity.getSpeed();
        }
    }
}
