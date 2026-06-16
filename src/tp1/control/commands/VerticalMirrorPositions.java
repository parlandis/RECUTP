package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class VerticalMirrorPositions extends NoParamsCommand {
	
	private static final String NAME = "verticalMirrorPositions";
	private static final String SHORTCUT = "MP";
	private static final String DETAILS = "vertical[M]irror[P]ositions";
	private static final String HELP = "reverses the game objects’ positions as if there were a vertical mirror";
	
	
	public VerticalMirrorPositions() {
		super(NAME, SHORTCUT, DETAILS, HELP); 
	}
	
	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException{
		game.verticalReverse();
		view.showGame();
	}
	
}
