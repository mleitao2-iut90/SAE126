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
public class MasterMind2Decider extends Decider {
    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());
    private String line;
    public MasterMind2Decider(Model model, Controller control, String line) {
        super(model, control);
        this.line = line;
    }
    @Override
    public ActionList decide(){
        ActionList actions = new ActionList(false);
        HoleStageModel gameStage = (HoleStageModel) model.getGameStage();
        HoleBoard board = gameStage.getBoard(); // get the board
        HolePawnPot pawnBoard = gameStage.getBoardPotPawn(); // get the pawn board
        if (line.equals("STOP") || line.equals("stop") || line.equals("Stop")) {
            control.stopStage();
        }else if (!board.isEmptyAt(1, 0)) {
            control.stopStage();
        }
        gameStage.moveLineUp(board, actions);
        gameStage.upPawnRedWhite();
        return actions;
    }
}
