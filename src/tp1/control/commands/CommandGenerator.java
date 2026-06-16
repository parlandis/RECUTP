//Grupo 15: Pablo Arlandis Ocaña 
package tp1.control.commands;

import tp1.exceptions.*;
import java.util.Arrays;
import java.util.List;
import tp1.view.Messages;

public class CommandGenerator {

	private static final List<Command> availableCommands = Arrays.asList( // Siempre tenemos que declarar los comandos
			new TeleportationCommand(),
			new LoadCommand(),
			new SaveCommand(),
			new AddObjectCommand(),
			new ActionCommand(),
			new UpdateCommand(),
			new ResetCommand(),
			new HelpCommand(),
			new ExitCommand());

	public static Command parse(String[] commandWords) throws CommandParseException {
		if (commandWords == null)
			throw new CommandParseException(Messages.INVALID_COMMAND);
		for (Command c : availableCommands) {
			Command parsed = null;
			try {
				parsed = c.parse(commandWords);
			} catch (CommandParseException e) {
				e.printStackTrace();
			} catch (CommandExecuteException e) {
				e.printStackTrace();
			}
			if (parsed != null) {
				return parsed;
			}
		}
		throw new CommandParseException(Messages.UNKNOWN_COMMAND.formatted(commandWords[0]));
	}

	public static String commandHelp() {
		StringBuilder commands = new StringBuilder();

		commands.append(Messages.HELP_AVAILABLE_COMMANDS).append(Messages.LINE_SEPARATOR);

		for (Command c : availableCommands) {
			commands.append(c.helpText());
		}

		return commands.toString();
	}
}
