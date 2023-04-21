package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.animation.AnimationStep;
import boardifier.view.GridGeometry;

public class Pawn extends GameElement {

    private int number;
    private int color;
    public static int PAWN_BLACK = 0;
    public static int PAWN_RED = 1;
    public static int PAWN_WHITE = 2;
    public static int PAWN_BLUE = 3;
    public static int PAWN_YELLOW = 4;
    public static int PAWN_GREEN = 5;
    public static int PAWN_CYAN = 6;
    public static int PAWN_PURPLE = 7;

    public Pawn(int number, int color, GameStageModel gameStageModel) {
        super(gameStageModel);
        // registering element types defined especially for this game
        ElementTypes.register("pawn", 50);
        type = ElementTypes.getType("pawn");
        this.number = number;
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public int getColor() {
        return color;
    }

    public void setNumber(int n){
        this.number = n;
        this.lookChanged = true;
    }

    public void setColor(char c) {
        switch(c){
            case 'N':
                this.color = 0;
                break;
            case 'R':
                this.color = 1;
                break;
            case 'B':
                this.color = 3;
                break;
            case 'J':
                this.color = PAWN_YELLOW;
                break;
            case 'V':
                this.color = 5;
                break;
            case 'W':
                this.color = 2;
                break;
            case 'C':
                this.color = 6;
                break;
            case 'P':
                this.color = 7;
                break;
            default:
                System.out.println("mauvais changemant de couleur (erreur dans Pawn)");break;
        }
        this.lookChanged = true;
    }

}
