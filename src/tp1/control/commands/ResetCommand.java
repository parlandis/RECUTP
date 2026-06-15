//Grupo 15: Pablo Arlandis Ocaña - Pablo Pérez Rey
package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.logic.*;
import tp1.view.GameView;
import tp1.view.Messages;


public class ResetCommand extends AbstractCommand {
	
	private static final String NAME = Messages.COMMAND_RESET_NAME;
	private static final String SHORTCUT = Messages.COMMAND_RESET_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_RESET_DETAILS;
	private static final String HELP = Messages.COMMAND_RESET_HELP;

	private Integer level;
	private boolean hasLevel; 

	ResetCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	private ResetCommand(int level) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.level = level;
		this.hasLevel = true;
	}
	
	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
		boolean success = true;
		
		if(hasLevel) {
			success = game.reset(level);
			if(!success) {
				throw new CommandExecuteException(Messages.INVALID_LEVEL_NUMBER);
			}	
		}
		else game.resetConfig();	
		
		view.showGame();
	}
	

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		ResetCommand resetCommand = null;
		
		if(matchCommandName(commandWords[0])) {
			if(commandWords.length < 1) throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
			
			if(commandWords.length == 1) {
				resetCommand = new ResetCommand();
			}
			else if(commandWords.length == 2) {
				try {
					int level = Integer.parseInt(commandWords[1]);
					resetCommand = new ResetCommand(level);
				} catch (NumberFormatException e) {
					throw new CommandParseException(Messages.LEVEL_NOT_A_NUMBER_ERROR.formatted(commandWords[1]), e);
				}
			} else {
				
				throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
			}
		}
		return resetCommand;
	}
}
