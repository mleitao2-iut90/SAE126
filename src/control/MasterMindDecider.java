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
        return super.action(line);
    }
}
