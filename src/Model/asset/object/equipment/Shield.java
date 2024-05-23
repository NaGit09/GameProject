package Model.asset.object.equipment;

import Controller.GameController;
import Model.asset.object.Object;

public class Shield extends Object {
    private int defenseValue;

    public Shield(GameController gameController) {
        super(gameController);
    }

    public int getDefenseValue() {
        return defenseValue;
    }

    public void setDefenseValue(int defenseValue) {
        this.defenseValue = defenseValue;
    }
}
