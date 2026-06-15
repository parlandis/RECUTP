// Grupo 15: Pablo Arlandis Ocaña 
package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameModelException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class SaveCommand extends AbstractCommand {

	private static final String NAME = Messages.COMMAND_SAVE_NAME;
	private static final String SHORTCUT = Messages.COMMAND_SAVE_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_SAVE_DETAILS;
	private static final String HELP = Messages.COMMAND_SAVE_HELP;

	private String fileName;

	SaveCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	public SaveCommand(String fileName) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.fileName = fileName;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		SaveCommand command = null;
		if (matchCommandName(commandWords[0])) {
			if (commandWords.length != 2) {
				throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
			}
			command = new SaveCommand(commandWords[1]);
		}
		return command;
	}

	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
		try {
			game.save(fileName);
			view.showMessage(Messages.FILE_SAVED.formatted(fileName));
		} catch (GameModelException e) {
			throw new CommandExecuteException(Messages.ERROR_COMMAND_EXECUTE, e);
		}
	}
}