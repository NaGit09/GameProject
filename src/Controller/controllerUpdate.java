package Controller;

import Model.asset.Asset;
import Model.asset.entity.Entity;
import Model.asset.object.Object;
import Model.asset.tile.interactive.InteractiveTile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class controllerUpdate {
    public GameController controller;
    
    public final Asset[][] npcs = new Entity[Constants.maxMaps][10];
    public final Asset[][] monsters = new Entity[Constants.maxMaps][20];
    public final InteractiveTile[][] interactiveTiles = new InteractiveTile[Constants.maxMaps][50];
    public final List<Asset> projectiles = new ArrayList<>();
    public final List<Asset> particles = new ArrayList<>();
    public final Asset[][] objects = new Object[Constants.maxMaps][20];
    public final List<Asset> assets = new ArrayList<>();

    public int currentMap = 0;

    public controllerUpdate(GameController controller) {
        this.controller = controller;
    }
    public void updateNPCs() {
        for (int i = 0; i < npcs[1].length; i++) {
            if (npcs[currentMap][i] != null) {
                npcs[currentMap][i].update();
            }
        }
    }

    public void updateMonsters() {
        for (int i = 0; i < monsters[1].length; i++) {
            if ( monsters[ currentMap][i] != null) {
                if ( monsters[ currentMap][i].isAlive() && ! monsters[ currentMap][i].isDying()) {
                     monsters[ currentMap][i].update();
                }

                if (! monsters[ currentMap][i].isAlive()) {
                     monsters[ currentMap][i].checkDrop();
                    removeMonster( monsters[ currentMap][i].getIndex());
                }
            }
        }
    }

    public void removeMonster(int index) {
         monsters[ currentMap][index] = null;
    }

    public void updateProjectiles() {
        for (int i = 0; i <  projectiles.size(); i++) {
            if ( projectiles.get(i) != null) {
                if ( projectiles.get(i).isAlive()) {
                     projectiles.get(i).update();
                }

                if (! projectiles.get(i).isAlive()) {
                     projectiles.remove( projectiles.get(i));
                }
            }
        }
    }

    public void updateParticles() {
        for (int i = 0; i <  particles.size(); i++) {
            if ( particles.get(i) != null) {
                if ( particles.get(i).isAlive()) {
                     particles.get(i).update();
                }

                if (! particles.get(i).isAlive()) {
                     particles.remove( particles.get(i));
                }
            }
        }
    }

    public void updateInteractiveTiles() {
        for (int i = 0; i <  interactiveTiles[1].length; i++) {
            if ( interactiveTiles[ currentMap][i] != null) {
                interactiveTiles[ currentMap][i].update();
            }
        }
    }
    public void addAssets() {
        assets.add(controller.player);

        for (int i = 0; i < npcs[1].length; i++) {
            if (npcs[currentMap][i] != null) {
                assets.add(npcs[currentMap][i]);
            }
        }

        for (int i = 0; i < objects[1].length; i++) {
            if (objects[currentMap][i] != null) {
                assets.add(objects[currentMap][i]);
            }
        }

        for (int i = 0; i < monsters[1].length; i++) {
            if (monsters[currentMap][i] != null) {
                assets.add(monsters[currentMap][i]);
            }
        }

        for (Asset projectile : projectiles) {
            if (projectile != null) {
                assets.add(projectile);
            }
        }

        for (Asset particle : particles) {
            if (particle != null) {
                assets.add(particle);
            }
        }
    }

    public void sortAssets() {
        assets.sort(Comparator.comparingInt(Asset::getWorldY));
    }
    public Asset[][] getObjects() {
        return objects;
    }

    public Asset[][] getNpcs() {
        return npcs;
    }

    public Asset[][] getMonsters() {
        return monsters;
    }
    public List<Asset> getProjectiles() {
        return projectiles;
    }

    public InteractiveTile[][] getInteractiveTiles() {
        return interactiveTiles;
    }

    public List<Asset> getParticles() {
        return particles;
    }
    public int getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(int currentMap) {
        this.currentMap = currentMap;

    }
}
