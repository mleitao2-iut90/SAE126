package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;

public class Pawn extends GameElement {

    private int number;
    private int color;
    public static int PAWN_BLACK = 6;
    public static int PAWN_RED = 1;
    public static int PAWN_WHITE = 5;
    public static int PAWN_BLUE = 2;
    public static int PAWN_YELLOW = 3;
    public static int PAWN_GREEN = 4;
    public static int PAWN_CYAN = 7;
    public static int PAWN_PURPLE = 8;

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
                this.color = PAWN_BLACK;
                break;
            case 'R':
                this.color = PAWN_RED;
                break;
            case 'B':
                this.color = PAWN_BLUE;
                break;
            case 'J':
                this.color = PAWN_YELLOW;
                break;
            case 'V':
                this.color = PAWN_GREEN;
                break;
            case 'W':
                this.color = PAWN_WHITE;
                break;
            case 'C':
                this.color = PAWN_CYAN;
                break;
            case 'P':
                this.color = PAWN_PURPLE;
                break;
            default:
                System.out.println("mauvais changemant de couleur (erreur dans Pawn)");break;
        }
        this.lookChanged = true;
    }

}
