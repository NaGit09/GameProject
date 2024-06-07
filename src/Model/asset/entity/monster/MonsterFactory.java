package Model.asset.entity.monster;

import Controller.GameController;

public class MonsterFactory {
    public GameController controller ;

    public MonsterFactory(GameController controller) {
        this.controller = controller;
    }
    public Monster createMonster (String type) {
        switch (type.toLowerCase()) {
            case "slime" -> {
                return new MON_Slime(controller);

            }
            case "machine" -> {
                return new BOSS(controller);
            }
            default -> {
                return null;
            }
        }
    }
}
