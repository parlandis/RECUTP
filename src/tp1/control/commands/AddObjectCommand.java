//Grupo 15: Pablo Arlandis Ocaña 
package tp1.control.commands;

import tp1.exceptions.GameModelException;
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.logic.GameModel;
import tp1.view.Messages;
import tp1.view.GameView;

import java.util.Arrays;

public class AddObjectCommand extends AbstractCommand {

    private static final String NAME = Messages.COMMAND_ADDOBJECT_NAME;
    private static final String SHORTCUT = Messages.COMMAND_ADDOBJECT_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_ADDOBJECT_DETAILS;
    private static final String HELP = Messages.COMMAND_ADDOBJECT_HELP;

    private String[] objWords;

    AddObjectCommand() {
        super(NAME, SHORTCUT, DETAILS, HELP);
    }

    private AddObjectCommand(String[] words) {
        super(NAME, SHORTCUT, DETAILS, HELP);
        this.objWords = words;
    }

    @Override
    public Command parse(String[] words) throws CommandParseException {
        Command command = null;

        if (words != null && matchCommandName(words[0])) {

            if (words.length <= 2) {
                throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
            }
            String[] objWords = Arrays.copyOfRange(words, 1, words.length); // guarda los parametros
            command = new AddObjectCommand(objWords);
        }

        return command;
    }

    @Override
    public void execute(GameModel game, GameView view) throws CommandExecuteException {
        try {
            game.addObject(this.objWords);
            view.showGame();
        } catch (GameModelException e) {
            throw new CommandExecuteException(Messages.ERROR_COMMAND_EXECUTE, e);
        }
    }
}
