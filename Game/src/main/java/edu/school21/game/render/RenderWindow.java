package edu.school21.game.render;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import edu.school21.game.logic.GameSettings;

public class RenderWindow {

    private final ColoredPrinter cp;
    private final GameSettings gameSettings;

    public RenderWindow(GameSettings gameSettings) {
        cp = new ColoredPrinter();
        this.gameSettings = gameSettings;

    }

    public void refreshWindow(char[][] gameBoard) {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        for (int i = 0; i < gameSettings.getSize(); ++i) {
            for (int j = 0; j < gameSettings.getSize(); ++j) {
                if (gameBoard[i][j] == gameSettings.getSpaceChar())
                    cp.print(gameSettings.getSpaceChar(), Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(gameSettings.getSpaceColor()));
                else if (gameBoard[i][j] == gameSettings.getPlayerChar())
                    cp.print(gameSettings.getPlayerChar(), Ansi.Attribute.NONE, Ansi.FColor.BLACK, Ansi.BColor.valueOf(gameSettings.getPlayerColor()));
                else if (gameBoard[i][j] == gameSettings.getEnemyChar())
                    cp.print(gameSettings.getEnemyChar(), Ansi.Attribute.NONE, Ansi.FColor.BLACK, Ansi.BColor.valueOf(gameSettings.getEnemyColor()));
                else if (gameBoard[i][j] == gameSettings.getGoalChar())
                    cp.print(gameSettings.getGoalChar(), Ansi.Attribute.NONE, Ansi.FColor.BLACK, Ansi.BColor.valueOf(gameSettings.getGoalColor()));
                else if (gameBoard[i][j] == gameSettings.getWallChar())
                    cp.print(gameSettings.getWallChar(), Ansi.Attribute.NONE, Ansi.FColor.BLACK, Ansi.BColor.valueOf(gameSettings.getWallColor()));
            }
            System.out.println();
        }
    }
}
