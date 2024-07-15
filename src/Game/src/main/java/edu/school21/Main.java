package edu.school21;

import com.beust.jcommander.JCommander;
import edu.school21.game.logic.Game;
import edu.school21.game.logic.GameArgs;
import edu.school21.game.logic.GameSettings;

public class Main {
    public static void main(String[] args) {

        GameArgs gameArgs = new GameArgs();
        JCommander.newBuilder()
                .addObject(gameArgs)
                .build()
                .parse(args);
        GameSettings gameSettings = new GameSettings(gameArgs);
        Game game = new Game(gameSettings);
        game.start();
    }

}

