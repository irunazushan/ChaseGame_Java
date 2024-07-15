package edu.school21;

public class ChaseLogic {
    public int[] pursuePlayer(int enemyX, int enemyY, int playerX, int playerY) {
        int[] newCoordinates = new int[2];
        if (playerX > enemyX) newCoordinates[0] = enemyX + 1;
        else if (playerX < enemyX) newCoordinates[0] = enemyX - 1;
        else newCoordinates[0] = enemyX;

        if (playerY > enemyY) newCoordinates[1] = enemyY + 1;
        else if (playerY < enemyY) newCoordinates[1] = enemyY - 1;
        else newCoordinates[1] = enemyY;

        return newCoordinates;
    }
}
