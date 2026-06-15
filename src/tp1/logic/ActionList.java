//Grupo 15: Pablo Arlandis Ocaña 
package tp1.logic;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActionList implements Iterable<Action> {
    private List<Action> actions;

    public ActionList() {
        this.actions = new ArrayList<>();
    }
		
	public void add(Action accion) {
		if (isValid(accion)) {
            actions.add(accion);
        }
	}
	
	public void addAll(List<Action> acciones) {
		if(acciones != null) {
			for(Action accion : acciones) {
				add(accion);
			}
		}
	}
	
	private boolean isValid(Action accion) {
        return !actions.contains(accion.opuesta()) && this.times(accion) < 4;
	}
	
	private int times(Action action) {
        int count = 0;
        for (Action a : actions) {
            if (a == action) {
                count++;
            }
        }
        return count;
    }

	public boolean isEmpty() {
	    return actions.isEmpty();
	}
	
	public void clear() {
	        actions.clear();
	}

	public boolean horizontal(Action action) {
		return (action == Action.LEFT || action == Action.RIGHT);
	}
	
	public boolean vertical(Action action) {
		return (action == Action.UP || action == Action.DOWN);
	}

	@Override
	public Iterator<Action> iterator() {
	    return actions.iterator();
	}	
}
