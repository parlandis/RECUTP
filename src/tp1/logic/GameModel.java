//Grupo 15: Pablo Arlandis Ocaña 
package tp1.logic;

import java.util.List;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;
import tp1.exceptions.GameParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;

public interface GameModel {
    void update();

    void reset();

    boolean reset(int level);

    void exit();

    boolean isFinished();

    void addActionList(List<Action> acciones);

    void addObject(String[] objWords) throws ObjectParseException, OffBoardException, GameParseException;

    void save(String fileName) throws GameModelException;

    void load(String fileName) throws GameLoadException;

    void resetConfig();
}