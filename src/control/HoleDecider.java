package control;

import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.GameElement;
import boardifier.model.GridElement;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;
import model.HoleBoard;
import model.HolePawnPot;
import model.HoleStageModel;
import model.Pawn;

import java.awt.*;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class HoleDecider extends Decider {
    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());
    private String line;

    public HoleDecider(Model model, Controller control, String line) {
        super(model, control);
        this.line = line;
    }

    @Override
    public ActionList decide() {
        // do a cast get a variable of the real type to get access to the attributes of HoleStageModel
        HoleStageModel stage = (HoleStageModel) model.getGameStage();
        HoleBoard board = stage.getBoard(); // get the board
        HolePawnPot blackPot = stage.getBlackPot(); // get the black pot
        HolePawnPot redPot = stage.getRedPot(); // get the red pot
        HolePawnPot pawnBoard = stage.getBoardPotPawn(); // get the pawn board
        HolePawnPot pot = pawnBoard; // the pot where to take a pawn
        ActionList actions = new ActionList(false);

        if (line.equals("STOP") || line.equals("stop") || line.equals("Stop")) {
            control.stopStage();
        }else if (!board.isEmptyAt(1, 0)) {
            control.stopStage();
        }
        moveLineUp(board, actions);
        stage.upPawnRedWhite();
        for (int i = 0; i < 4; i++) {
            int pawnAPoser = control.getPawnAPoser();
            Pawn p = (Pawn) pot.getElement(pawnAPoser, 0);
            if (p != null) {
                /**List<Point> valid = board.computeValidCells(p.getNumber());
                 if (valid.size() != 0) {
                 // choose at random one of the valid cells
                 int id = loto.nextInt(valid.size());
                 pawn = p;
                 rowDest = valid.get(id).y;
                 colDest = valid.get(id).x;
                 break; // stop the loop
                 }**/
                stage.setColors(line, pawnAPoser);
                GameAction move = new MoveAction(model, p, "holeboard", 11, i);
                actions.addSingleAction(move);
            }
            control.add1PawnAPoser();
        }
        return actions;
    }

    public void moveLineUp(GridElement board, ActionList actions) {
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
