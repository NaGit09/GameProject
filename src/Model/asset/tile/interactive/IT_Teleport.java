package Model.asset.tile.interactive;

import Controller.GameController;

public class IT_Teleport  extends  InteractiveTile{
    public IT_Teleport(GameController gameController) {
        super(gameController);
        setDown1(setup("/resources/images/tiles/teleport", getgameController().getTileSize(), getgameController().getTileSize()));
        removeCollisionArea();
    }

    private void removeCollisionArea() {
        getCollisionArea().x = 0;
        getCollisionArea().y = 0;
        getCollisionArea().width = 0;
        getCollisionArea().height = 0;
        setCollisionDefaultX(getCollisionArea().x);
        setCollisionDefaultY(getCollisionArea().y);
    }
}
