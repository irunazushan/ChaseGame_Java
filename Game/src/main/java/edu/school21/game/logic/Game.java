package edu.school21.game.logic;

import edu.school21.ChaseLogic;
import edu.school21.game.render.RenderWindow;

import java.util.*;

public class Game {
    static private char[][] gameBoard;
    private final GameSettings gameSettings;
    static int playerX, playerY;
    static int targetX, targetY;
    private final Set<String> enemyCoordinates = new HashSet<>();
    private final static Random rand = new Random();
    private final RenderWindow rw;

    public Game(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        rw = new RenderWindow(gameSettings);

    }

    public void start() {
        initGameBoard();
        initPLayer();
        initTarget();
        initObstacles(gameSettings.getNumOfWalls());
        initEnemies(gameSettings.getNumOfEnemies());
        gameLoop();
    }


    public void initGameBoard() {
        gameBoard = new char[gameSettings.getSize()][gameSettings.getSize()];
        for (int i = 0; i < gameSettings.getSize(); ++i) {
            for (int j = 0; j < gameSettings.getSize(); ++j) {
                gameBoard[i][j] = gameSettings.getSpaceChar();
            }
        }
    }

    private void initPLayer() {
        playerX = rand.nextInt(gameSettings.getSize());
        playerY = rand.nextInt(gameSettings.getSize());
        gameBoard[playerX][playerY] = gameSettings.getPlayerChar();
    }

    private void initTarget() {
        do {
            targetX = rand.nextInt(gameSettings.getSize());
            targetY = rand.nextInt(gameSettings.getSize());
        }  while (gameBoard[targetX][targetY] != gameSettings.getSpaceChar());
        gameBoard[targetX][targetY] = gameSettings.getGoalChar();
    }

    private void initObstacles(int numOfObstacles) {
        for (int i = 0; i < numOfObstacles; i++) {
            int x, y;
            do {
                x = rand.nextInt(gameSettings.getSize());
                y = rand.nextInt(gameSettings.getSize());
            }
            while (gameBoard[x][y] != gameSettings.getSpaceChar());
            gameBoard[x][y] = gameSettings.getWallChar();
        }
    }

    private void initEnemies(int numOfEnemies) {
        for (int i = 0; i < numOfEnemies; i++) {
            int x, y;
            do {
                x = rand.nextInt(gameSettings.getSize());
                y = rand.nextInt(gameSettings.getSize());
            }
            while (gameBoard[x][y] != gameSettings.getSpaceChar());
            gameBoard[x][y] = gameSettings.getEnemyChar();
        }
    }

    public void gameLoop() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            rw.refreshWindow(gameBoard);
            System.out.print("Enter your move (A W D S, 9 to quit): ");
            char move = '0';
            if (scanner.hasNext()) {
                try {
                    move = scanner.nextLine().charAt(0);
                } catch (StringIndexOutOfBoundsException e) {
                    continue;
                }
            }
            if (move == '9') {
                System.out.println("You quit the game. See you later!");
                break;
            }
            makeMove(move);
            if (checkWin()) {
                rw.refreshWindow(gameBoard);
                System.out.println("YOU WIN! You reached the target point!");
                break;
            }
            if (isStuck()) {
                rw.refreshWindow(gameBoard);
                System.out.println("GAME OVER. You are stuck!");
                break;
            }
            moveEnemies(scanner);
            if (checkLoss()) {
                rw.refreshWindow(gameBoard);
                System.out.println("GAME OVER. An enemy caught you!");
                break;
            }
        }
        scanner.close();
    }

    private void makeMove(char move) {
        int newX = playerX, newY = playerY;
        switch (move) {
            case 'W':
            case 'w':
                newX--;
                break;
            case 'A':
            case 'a':
                newY--;
                break;
            case 'S':
            case 's':
                newX++;
                break;
            case 'D':
            case 'd':
                newY++;
                break;
        }
        if (isValidPlayerMove(newX, newY)) {
            gameBoard[playerX][playerY] = gameSettings.getSpaceChar();
            playerX = newX;
            playerY = newY;
            gameBoard[playerX][playerY] = gameSettings.getPlayerChar();
        } else {
            System.out.println("Invalid move. Try again on next turn.");
        }
    }

    boolean isValidPlayerMove(int x, int y) {
        return x >= 0 && x < gameSettings.getSize() && y >= 0 && y < gameSettings.getSize() && gameBoard[x][y] != gameSettings.getWallChar() && (gameBoard[x][y] == gameSettings.getSpaceChar() || gameBoard[x][y] == gameSettings.getGoalChar());
    }

    boolean isValidEnemyMove(int x, int y) {
        return x >= 0 && x < gameSettings.getSize() && y >= 0 && y < gameSettings.getSize() && gameBoard[x][y] != gameSettings.getWallChar() && gameBoard[x][y] != gameSettings.getGoalChar() && gameBoard[x][y] == gameSettings.getSpaceChar();
    }

    boolean isStuck() {
        return !(isValidPlayerMove(playerX + 1, playerY) || isValidPlayerMove(playerX - 1, playerY) || isValidPlayerMove(playerX, playerY + 1) || isValidPlayerMove(playerX, playerY - 1));
    }

    boolean checkWin() {
        return playerX == targetX && playerY == targetY;
    }

    boolean checkLoss() {
        for (int i = 0; i < gameSettings.getSize(); i++) {
            for (int j = 0; j < gameSettings.getSize(); j++) {
                if (gameBoard[i][j] == gameSettings.getEnemyChar() &&
                        ((Math.abs(i - playerX) <= 1 && j == playerY) || (Math.abs(j - playerY) <= 1 && i == playerX))) {
                    return true;
                }
            }
        }
        return false;
    }

    void moveEnemies(Scanner scanner) {
        ChaseLogic chaseLogic = new ChaseLogic();
        for (int i = 0; i < gameSettings.getSize(); i++) {
            for (int j = 0; j < gameSettings.getSize(); j++) {
                if (gameBoard[i][j] == gameSettings.getEnemyChar() && !enemyCoordinates.contains(i + "," + j)) {
                    int[] newCoordinates = chaseLogic.pursuePlayer(i, j, playerX, playerY);
                    if (newCoordinates[0] != i && newCoordinates[1] != j) {
                        int leftX = rand.nextInt(2);
                        if (leftX == 1) newCoordinates[1] = j;
                        else newCoordinates[0] = i;
                    }
                    int newX = newCoordinates[0];
                    int newY = newCoordinates[1];
                    if (isValidEnemyMove(newX, newY)) {
                        gameBoard[i][j] = gameSettings.getSpaceChar();
                        gameBoard[newX][newY] = gameSettings.getEnemyChar();
                        if (gameSettings.isDevelopmentMode()) {
                            rw.refreshWindow(gameBoard);
                            System.out.println("Confirm the enemy's step? 8");
                            String confirm = scanner.nextLine();
                            if (!confirm.equals("8")) {
                                gameBoard[i][j] = gameSettings.getEnemyChar();
                                gameBoard[newX][newY] = gameSettings.getSpaceChar();
                            }
                        }
                        enemyCoordinates.add(newX + "," + newY);
                    }
                }
            }
        }
        enemyCoordinates.clear();
    }
}
