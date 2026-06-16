package tp1.control.commands;

import tp1.logic.*;
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.PositionParseException;
import tp1.view.GameView;
import tp1.view.Messages;

public class TeleportationCommand extends AbstractCommand{

	private static final String NAME = "teleportation";
	private static final String SHORTCUT = "t";
	private static final String DETAILS = "[t]eleportation (ROW_INI,COL_INI) (ROW_END,COL_END)";
	private static final String HELP = "teleports all objects in initial position (INI) the end position (END)";
	
	private Position posIni;
	private Position posFin;
	
	TeleportationCommand(){
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	private TeleportationCommand(Position ini, Position fin) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.posIni = ini;
		this.posFin = fin;
	}
	
	public Command parse(String[] commandWords) throws CommandParseException, CommandExecuteException { 
		TeleportationCommand command = null;
	
		if(matchCommandName(commandWords[0])) {
			if(commandWords.length <= 2) throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
			
			try {
				posIni = Position.parse(commandWords[1]);
			} catch (PositionParseException ppe) {
				throw new CommandParseException(Messages.INVALID_OBJECT_POSITION.formatted(String.join(" ", commandWords)),ppe);
			}
			
			try {
				posFin = Position.parse(commandWords[2]);
			} catch (PositionParseException ppe) {
				throw new CommandParseException(Messages.INVALID_OBJECT_POSITION.formatted(String.join(" ", commandWords)),ppe);
			}
			
			if(!posIni.isValid()) {
				throw new CommandExecuteException(Messages.INVALID_COMMAND_PARAMETERS);
			}
			if(!posFin.isValid()) { //TODO: No se como hacer el isSolid sin liarla
				throw new CommandExecuteException(Messages.INVALID_COMMAND_PARAMETERS);
			}
			
			command = new TeleportationCommand(posIni, posFin);
		}
		return command;
	}

	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
		game.teleport(posIni, posFin);
		view.showGame();
	}
	
}
