package model;

import boardifier.model.GameStageModel;
import boardifier.model.GridElement;

public class HolePawnPot extends GridElement {
    public HolePawnPot(int x, int y, GameStageModel gameStageModel,int ligne) {
        // call the super-constructor to create a 4x1 grid, named "pawnpot", and in x,y in space
        super("pawnpot", x, y, ligne, 1, gameStageModel);
    }
}
