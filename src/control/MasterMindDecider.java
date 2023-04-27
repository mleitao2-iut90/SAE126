package control;

import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;
import model.MasterMindBoard;
import model.MasterMindPawnPot;
import model.MasterMindStageModel;
import model.Pawn;

import java.util.Calendar;
import java.util.Random;

public class MasterMindDecider extends Decider {
    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());
    private String line;

    public MasterMindDecider(Model model, Controller control, String line) {
        super(model, control);
        this.line = line;
    }

    @Override
    public ActionList decide() {
        // do a cast get a variable of the real type to get access to the attributes of HoleStageModel
        MasterMindStageModel stage = (MasterMindStageModel) model.getGameStage();
        MasterMindBoard board = stage.getBoard(); // get the board
        MasterMindPawnPot whitePot = stage.getWhitePot(); // get the black pot
        MasterMindPawnPot redPot = stage.getRedPot(); // get the red pot
        MasterMindPawnPot pawnBoard = stage.getBoardPotPawn(); // get the pawn board
        MasterMindPawnPot pot = pawnBoard; // the pot where to take a pawn
        ActionList actions = new ActionList(false);

        if (line.equals("STOP") || line.equals("stop") || line.equals("Stop")) {
            control.stopStage();
        }else if (!board.isEmptyAt(1, 0)) {
            control.stopStage();
        }
        stage.moveLineUp(board, actions);
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
                GameAction move = new MoveAction(model, p, "mastermindboard", 11, i);
                actions.addSingleAction(move);
            }
            control.add1PawnAPoser();
        }
        return actions;
    }
}
