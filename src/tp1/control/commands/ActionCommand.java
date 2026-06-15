//Grupo 15: Pablo Arlandis Ocaña
package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.logic.Action;
import java.util.List;
import java.util.ArrayList;
import tp1.logic.*;
import tp1.view.GameView;
import tp1.view.Messages;

public class ActionCommand extends AbstractCommand {
	
	private static final String NAME = Messages.COMMAND_ACTION_NAME;
	private static final String SHORTCUT = Messages.COMMAND_ACTION_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_ACTION_DETAILS;
	private static final String HELP = Messages.COMMAND_ACTION_HELP;

	private List<Action> listaActions;  
	
	ActionCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	private ActionCommand(List<Action> listaActions) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.listaActions = new ArrayList<>(listaActions);
	}
	
	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
		game.addActionList(this.listaActions);
		game.update();
		view.showGame();
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException { 
		ActionCommand command = null;
		
		if(matchCommandName(commandWords[0])) {
			if(commandWords.length <= 1) throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
				
			List<Action>listaA  = new ArrayList<Action>();
			command = new ActionCommand(listaA);
			
			for(int i = 1; i < commandWords.length; i++) {
	            Action action = Action.parseActions(commandWords[i]);
	            if(action != null) {
	                command.listaActions.add(action);
	            }
	         }
			 
			 if(command.listaActions.isEmpty()) throw new CommandParseException(Messages.ACTION_INCORRECT_EMPTY_LIST);
			 
			}
			
	    return command;
	}	
}
