package edu.school21.game.logic;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class GameArgs {

    @Parameter(names = "--enemiesCount", description = "A parameter for setting number of enemies")
    int enemiesCount;

    @Parameter(names = "--wallsCount", description = "A parameter for setting number of walls")
    int wallsCount;

    @Parameter(names = "--size", description = "A parameter for setting the map size")
    int size;

    @Parameter(names = "--profile", description = "A parameter for choosing development or production mode")
    String profile;

    public int getEnemiesCount() {
        return enemiesCount;
    }

    public int getWallsCount() {
        return wallsCount;
    }

    public int getSize() {
        return size;
    }

    public String getProfile() {
        return profile;
    }
}
