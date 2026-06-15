//Grupo 15: Pablo Arlandis Ocaña 
package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.logic.*;
import tp1.view.GameView;
import tp1.view.Messages;

public class UpdateCommand extends NoParamsCommand  {

	private static final String NAME = Messages.COMMAND_UPDATE_NAME;
	private static final String SHORTCUT = Messages.COMMAND_UPDATE_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_UPDATE_DETAILS;
	private static final String HELP = Messages.COMMAND_UPDATE_HELP;

	UpdateCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	@Override
	public boolean matchCommandName(String name) {
		String vacio = "";
		return super.matchCommandName(name) || vacio.equals(name);
	}
	
	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
		game.update();
		view.showGame();
	}
}
