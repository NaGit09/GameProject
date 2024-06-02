package Model.ai;

import Controller.GameController;
import Model.asset.entity.Entity;
import Model.asset.tile.interactive.InteractiveTile;

import java.util.ArrayList;

public class PathFinder {
    GameController gameController;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GameController gameController) {
        this.gameController = gameController;
    }

    public void instantiateNode() {
        node = new Node[gameController.getMaxWorldColumns()][gameController.getMaxWorldRows()];
        int col = 0;
        int row = 0;
        while (col < gameController.getMaxWorldColumns() && row < gameController.getMaxWorldRows()) {
            node[col][row] = new Node(col, row);
            col++;
            if (col == gameController.getMaxWorldColumns()) {
                col = 0;
                row++;
            }
        }
    }

    public void resetNode() {
        int col = 0;
        int row = 0;
        while (col < gameController.getMaxWorldColumns() && row < gameController.getMaxWorldRows()) {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;
            col++;
            if (col == gameController.getMaxWorldColumns()) {
                col = 0;
                row++;
            }
        }
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;

    }

    public void getCost(Node node) {

        int XDistance = Math.abs(node.col - startNode.col);
        int YDistance = Math.abs(node.row - startNode.row);
        node.gCost = XDistance + YDistance;
        XDistance = Math.abs(node.col - goalNode.col);
        YDistance = Math.abs(node.row - goalNode.row);
        node.hCost = XDistance + YDistance;
        node.fCost = node.gCost + node.hCost;
    }

    public void setNode(int startCol, int startRow, int goalCol, int goalRow, Entity entity) {
        resetNode();
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);
        int col = 0;
        int row = 0;
        while (col < gameController.getMaxWorldColumns() && row < gameController.getMaxWorldRows()) {
            int tileNum = gameController.tileManager.mapTileNumbers[gameController.update.currentMap][col][row];

            if (gameController.tileManager.tiles[tileNum].collision) {
                node[col][row].solid = true;
            }
            for (int i = 0; i < gameController.update.interactiveTiles[1].length; i++) {
                InteractiveTile IT = gameController.update.interactiveTiles[gameController.update.currentMap][i];
                if (IT != null && IT.isDestructible()) {
                    int iCol = IT.getWorldX() / gameController.getTileSize();
                    int iRow = IT.getWorldY() / gameController.getTileSize();
                    node[iCol][iRow].solid = true;

                }
            }
            getCost(node[col][row]);
            col++;
            if (col == gameController.getMaxWorldColumns()) {
                col = 0;
                row++;
            }


        }
    }
    public void openNode (Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);

        }
    }
    public void trackThePath (){
        Node current = goalNode;
        while(current != startNode) {
            pathList.add(0,current);
            current = current.parent;

        }
    }
    public boolean search() {
        while (!goalReached && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;
            currentNode.checked = true;
            openList.remove(currentNode);

            if (row-1 >=0) {
                openNode(node[col][row-1]);

            }
            if (col-1 >=0) {
                openNode(node[col-1][row]);

            }
            if (row+1 < gameController.getMaxWorldRows()) {
                openNode(node[col][row+1]);

            }
            if (col+1 < gameController.getMaxWorldColumns()) {
                openNode(node[col+1][row]);

            }
            int bestNodeIndex =0;
            int bestNodefCost = 999;

            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;

                }
                else if (openList.get(i).fCost == bestNodefCost ) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }
            if (openList.size() > 0) {
                break;
            }
            currentNode = openList.get(bestNodeIndex);
            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }
}

