package Model.asset.tile.interactive;

import Controller.GameController;

public class IT_Trunk extends InteractiveTile {

    public IT_Trunk(GameController gameController) {
        super(gameController);

        setDown1(setup("/resources/images/tiles/interactive/trunk", getgameController().getTileSize(), getgameController().getTileSize()));
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
