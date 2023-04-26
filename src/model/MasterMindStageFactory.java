package model;

import boardifier.model.GameStageModel;
import boardifier.model.StageElementsFactory;

public class MasterMindStageFactory extends StageElementsFactory {
    private MasterMindStageModel stageModel;

    public MasterMindStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (MasterMindStageModel) gameStageModel;
    }

    @Override
    public void setup() {
        // create the board
        stageModel.setBoard(new MasterMindBoard(7, 0, stageModel));
        //create the pots
        MasterMindPawnPot whitePot = new MasterMindPawnPot(0,0, stageModel, 12);
        stageModel.setWhitePot(whitePot);
        MasterMindPawnPot redPot = new MasterMindPawnPot(29,0, stageModel, 12);
        stageModel.setRedPot(redPot);

        MasterMindPawnPot pawnsBoardPot = new MasterMindPawnPot(40,0, stageModel, 48);
        stageModel.setBoardPotPawn(pawnsBoardPot);

        // create the pawns
        Pawn[] whitePawns = new Pawn[12];
        for(int i=0;i<12;i++) {
            whitePawns[i] = new Pawn(0, Pawn.PAWN_WHITE, stageModel);
        }
        stageModel.setWhitePawns(whitePawns);
        Pawn[] redPawns = new Pawn[12];
        for(int i=0;i<12;i++) {
            redPawns[i] = new Pawn(0, Pawn.PAWN_RED, stageModel);
        }
        stageModel.setRedPawns(redPawns);

        Pawn[] pawnsBoard = new Pawn[48];
        for(int i = 0; i<48;i++) {
            pawnsBoard[i] = new Pawn(0, Pawn.PAWN_WHITE, stageModel);
        }
        stageModel.setPawnsBoard(pawnsBoard);


        // assign pawns to their pot : they will be put at the center
        for (int i=0;i<12;i++) {
            whitePot.putElement(whitePawns[i], i,0);
            redPot.putElement(redPawns[i], i,0);
        }



        for (int i=0;i<48;i++) {
            pawnsBoardPot.putElement(pawnsBoard[i], i,0);
        }
    }
}
