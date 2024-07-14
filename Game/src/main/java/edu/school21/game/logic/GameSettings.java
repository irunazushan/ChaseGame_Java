package edu.school21.game.logic;

import com.diogonunes.jcdp.color.api.Ansi;
import edu.school21.game.error.IllegalParametersException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameSettings {
    private final int size;
    private final int numOfEnemies;
    private final int numOfWalls;
    private final boolean developmentMode;


    private char spaceChar;
    private char playerChar;
    private char goalChar;
    private char enemyChar;
    private char wallChar;

    private String spaceColor;
    private String playerColor;
    private String goalColor;
    private String enemyColor;
    private String wallColor;

    private final static Properties properties = new Properties();

    public GameSettings(GameArgs args) {
        size = args.getSize();
        numOfEnemies = args.getEnemiesCount();
        numOfWalls = args.getWallsCount();
        developmentMode = args.getProfile().equals("development");
        checkValidity();
        initCharacters();
    }

    private void checkValidity() {
        if (size * size < numOfEnemies + numOfWalls + 2)
            throw new IllegalParametersException("Number of the game elements cannot be more than the game board size");
    }

    private void initCharacters() {
        String propertiesFile = developmentMode ? "application-dev.properties" : "application-production.properties";
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(propertiesFile)) {
            if (input == null) {
                throw new IllegalParametersException("Sorry, unable to find " + propertiesFile);
            }
            properties.load(input);
            spaceChar = !properties.getProperty("empty.char").isEmpty() ? properties.getProperty("empty.char").charAt(0) : ' ';
            playerChar = !properties.getProperty("player.char").isEmpty() ? properties.getProperty("player.char").charAt(0) : 'o';
            goalChar = !properties.getProperty("goal.char").isEmpty() ? properties.getProperty("goal.char").charAt(0) : '0';
            enemyChar = !properties.getProperty("enemy.char").isEmpty() ? properties.getProperty("enemy.char").charAt(0) : 'X';
            wallChar = !properties.getProperty("wall.char").isEmpty() ? properties.getProperty("wall.char").charAt(0) : '#';


            spaceColor = !properties.getProperty("empty.color").isEmpty() ? properties.getProperty("empty.color") : "YELLOW";
            playerColor = !properties.getProperty("player.color").isEmpty() ? properties.getProperty("player.color") : "GREEN";
            goalColor = !properties.getProperty("goal.color").isEmpty() ? properties.getProperty("goal.color") : "BLUE";
            enemyColor = !properties.getProperty("enemy.color").isEmpty() ? properties.getProperty("enemy.color") : "RED";
            wallColor = !properties.getProperty("wall.color").isEmpty() ? properties.getProperty("wall.color") : "MAGENTA";

            checkValidityOfColors();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            throw new NullPointerException("properties file is incorrect");
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Something wrong with the arguments in the properties file");
        }
    }

    private void checkValidityOfColors() throws IllegalArgumentException {
        Ansi.BColor.valueOf(spaceColor);
        Ansi.BColor.valueOf(enemyColor);
        Ansi.BColor.valueOf(goalColor);
        Ansi.BColor.valueOf(wallColor);
        Ansi.BColor.valueOf(playerColor);
    }

    public int getSize() {
        return size;
    }

    public int getNumOfEnemies() {
        return numOfEnemies;
    }

    public int getNumOfWalls() {
        return numOfWalls;
    }

    public boolean isDevelopmentMode() {
        return developmentMode;
    }

    public char getSpaceChar() {
        return spaceChar;
    }

    public char getPlayerChar() {
        return playerChar;
    }

    public char getGoalChar() {
        return goalChar;
    }

    public char getEnemyChar() {
        return enemyChar;
    }

    public char getWallChar() {
        return wallChar;
    }

    public String getSpaceColor() {
        return spaceColor;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public String getGoalColor() {
        return goalColor;
    }

    public String getEnemyColor() {
        return enemyColor;
    }

    public String getWallColor() {
        return wallColor;
    }

    public Properties getProperties() {
        return properties;
    }
}
