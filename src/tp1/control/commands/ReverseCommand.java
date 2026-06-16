package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class ReverseCommand extends NoParamsCommand{
	
	private static final String NAME = Messages.COMMAND_REVERSE_NAME;
	private static final String SHORTCUT = Messages.COMMAND_REVERSE_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_REVERSE_DETAILS;
	private static final String HELP = Messages.COMMAND_REVERSE_HELP;
	
	ReverseCommand(){
		super(NAME, SHORTCUT, DETAILS, HELP); 
	}
	
	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException{
	    game.reverse(); 
	    //game.update();
		view.showGame();
	}
	

}
