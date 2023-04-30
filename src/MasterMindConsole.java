import boardifier.model.GameException;
import boardifier.view.View;
import boardifier.control.StageFactory;
import boardifier.model.Model;
import control.MasterMindController;

public class MasterMindConsole {

    public static void main(String[] args) {

        int mode = 0;
        /**if (args.length == 1) {
            try {
                mode = Integer.parseInt(args[0]);
                if ((mode <0) || (mode>2)) mode = 0;
            }
            catch(NumberFormatException e) {
                mode = 0;
            }
        }**/
        if(args.length == 1){
            if(args[0].equals("1")){
                mode = 1;
            }else if(args[0].equals("2")){
                mode = 2;
            }else if(args[0].equals("3")){
                mode = 3;
            }else{
                mode = 0;
            }
        }else{
            mode = 0;
        }
        Model model = new Model();
        if(mode == 1){
            model.addComputerPlayer("computerDebile");
        }else if(mode == 2){
            model.addComputerPlayer("computerIntelligent1");
        }else if(mode == 3){
            model.addComputerPlayer("computerIntelligent2");
        }else{
            model.addHumanPlayer("player");
        }
        StageFactory.registerModelAndView("MasterMind", "model.MasterMindStageModel", "view.MasterMindView");
        View MasterMindView = new View(model);
        MasterMindController control = new MasterMindController(model,MasterMindView);
        control.setFirstStageName("MasterMind");
        try {
            control.startGame();
            control.stageLoop();
        }
        catch(GameException e) {
            System.out.println("Cannot start the game. Abort");
        }
    }
}
