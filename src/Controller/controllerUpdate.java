package Controller;

public class controllerUpdate {
    public GameController controller;

    public controllerUpdate(GameController controller) {
        this.controller = controller;
    }
    public void updateNPCs() {
        for (int i = 0; i < controller.npcs[1].length; i++) {
            if (controller.npcs[controller.currentMap][i] != null) {
                controller.npcs[controller.currentMap][i].update();
            }
        }
    }

    public void updateMonsters() {
        for (int i = 0; i < controller.monsters[1].length; i++) {
            if ( controller.monsters[ controller.currentMap][i] != null) {
                if ( controller.monsters[ controller.currentMap][i].isAlive() && ! controller.monsters[ controller.currentMap][i].isDying()) {
                     controller.monsters[ controller.currentMap][i].update();
                }

                if (! controller.monsters[ controller.currentMap][i].isAlive()) {
                     controller.monsters[ controller.currentMap][i].checkDrop();
                    removeMonster( controller.monsters[ controller.currentMap][i].getIndex());
                }
            }
        }
    }

    public void removeMonster(int index) {
         controller.monsters[ controller.currentMap][index] = null;
    }

    public void updateProjectiles() {
        for (int i = 0; i <  controller.projectiles.size(); i++) {
            if ( controller.projectiles.get(i) != null) {
                if ( controller.projectiles.get(i).isAlive()) {
                     controller.projectiles.get(i).update();
                }

                if (! controller.projectiles.get(i).isAlive()) {
                     controller.projectiles.remove( controller.projectiles.get(i));
                }
            }
        }
    }

    public void updateParticles() {
        for (int i = 0; i <  controller.particles.size(); i++) {
            if ( controller.particles.get(i) != null) {
                if ( controller.particles.get(i).isAlive()) {
                     controller.particles.get(i).update();
                }

                if (! controller.particles.get(i).isAlive()) {
                     controller.particles.remove( controller.particles.get(i));
                }
            }
        }
    }

    public void updateInteractiveTiles() {
        for (int i = 0; i <  controller.interactiveTiles[1].length; i++) {
            if ( controller.interactiveTiles[ controller.currentMap][i] != null) {
                controller.interactiveTiles[ controller.currentMap][i].update();
            }
        }
    }


}
