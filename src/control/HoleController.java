package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.model.GameElement;
import boardifier.model.GridElement;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;
import boardifier.model.action.RemoveAction;
import boardifier.model.animation.AnimationTypes;
import boardifier.view.View;
import model.HolePawnPot;
import model.HoleStageModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HoleController extends Controller {

    BufferedReader consoleIn;
    boolean firstPlayer;

    public HoleController(Model model, View view) {
        super(model, view);
        firstPlayer = true;
    }

    /**
     * Defines what to do within the single stage of the single party
     * It is pretty straight forward to write :
     */
    public void stageLoop() {
        consoleIn = new BufferedReader(new InputStreamReader(System.in));
        update();
        while (!model.isEndStage()) {
            nextPlayer();
            update();
        }
        stopStage();
        endGame();
    }

    public void nextPlayer() {
        // for the first player, the id of the player is already set, so do not compute it
        /**if (!firstPlayer) {
         model.setNextPlayer();
         }
         else {
         firstPlayer = false;
         }**/
        // get the new player
        Player p = model.getCurrentPlayer();
        /**if (p.getType() == Player.COMPUTER) {
         System.out.println("COMPUTER PLAYS");
         HoleDecider decider = new HoleDecider(model,this);
         ActionPlayer play = new ActionPlayer(model, this, decider, null);
         play.start();
         }**/
        boolean ok = false;
        while (!ok) {
            System.out.print(p.getName() + " > ");
            try {
                String line = consoleIn.readLine();
                if (line.length() == 4) {
                    ok = analyseAndPlay2(line);
                }
                if (!ok) {
                    System.out.println("incorrect instruction. retry !");
                }
            } catch (IOException e) {
            }
        }
    }

    private boolean analyseAndPlay(String line) {
        HoleStageModel gameStage = (HoleStageModel) model.getGameStage();
        //get the pawn value from the first char
        int pawnIndex = (int) (line.charAt(0) - '1');
        if ((pawnIndex < 0) || (pawnIndex > 3)) return false;
        // get the ccords in the board
        int col = (int) (line.charAt(1) - 'A');
        int row = (int) (line.charAt(2) - '1');
        // check coords validity
        if ((row < 0) || (row > 2)) return false;
        if ((col < 0) || (col > 2)) return false;
        // check if the pawn is still in its pot
        GridElement pot = null;
        if (model.getIdPlayer() == 0) {
            pot = gameStage.getBlackPot();
        } else {
            pot = gameStage.getRedPot();
        }
        if (pot.isEmptyAt(pawnIndex, 0)) return false;
        GameElement pawn = pot.getElement(pawnIndex, 0);
        // compute valid cells for the chosen pawn
        gameStage.getBoard().setValidCells(pawnIndex + 1);
        if (!gameStage.getBoard().canReachCell(row, col)) return false;

        ActionList actions = new ActionList(true);
        GameAction move = new MoveAction(model, pawn, "holeboard", row, col);
        // add the action to the action list.
        actions.addSingleAction(move);
        ActionPlayer play = new ActionPlayer(model, this, actions);
        play.start();
        return true;
    }

    private boolean analyseAndPlay2(String line) {
        HoleStageModel gameStage = (HoleStageModel) model.getGameStage();
        //VERIF
        if(line.equals("STOP") || line.equals("stop") || line.equals("Stop")){
            stopStage();
            return true;
        }else if (!goodValEnter(line, false)) {
            return false;
        }
        GridElement board = gameStage.getBoard();
        HolePawnPot whitePot = gameStage.getBlackPot();
        HolePawnPot redPot = gameStage.getRedPot();
        if (!board.isEmptyAt(1, 0)) {
            stopStage();
        }

        // ACTIONS
        ActionList actions = new ActionList(false);
        // Actions qui montee les pions du board de 1 ligne
        moveLineUp2(board, actions, whitePot, redPot);
        // Actions mettre les "pawsBoard" sur le "board" (plateau)
        GridElement pawnBoard = gameStage.getBoardPotPawn();
        for (int i = 0; i < 4; i++) {
            GameElement pawn = pawnBoard.getElement(pawnAPoser, 0);
            gameStage.setColors(line, pawnAPoser);
            GameAction move = new MoveAction(model, pawn, "holeboard", 11, i);
            actions.addSingleAction(move);
            pawnAPoser++;
        }
        // Actions qui monte 1 cran les scores des pions blanc et rouge
        gameStage.upPawnRedWhite();

        ActionPlayer play = new ActionPlayer(model, this, actions);
        play.start();

        // Actions qui donne le score pour les pions rouge et blanc du bas
        gameStage.setNumberPawnDown(line, combinaison);

        if (gameStage.verifWin() == 4) {
            stopStage();
            model.setEnd(1);
        }
        return true;
    }

    public void moveLineUp2(GridElement board, ActionList actions, HolePawnPot whitePot, HolePawnPot redPot) {
        boolean find = false;
        for (int i = 0; i < 11; i++) {
            if ((board.isEmptyAt(i, 0) && !board.isEmptyAt(i + 1, 0)) || find) {
                find = true;
                for (int j = 0; j < 4; j++) {
                    GameElement pawn = board.getElement(i + 1, j);
                    GameAction move = new MoveAction(model, pawn, "holeboard", i, j);
                    actions.addSingleAction(move);
                }
            }
        }
    }
}
