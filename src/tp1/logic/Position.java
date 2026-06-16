//Grupo 15: Pablo Arlandis Ocaña

package tp1.logic;

import tp1.exceptions.PositionParseException;
import tp1.view.Messages;

public class Position {
    private final int col;
    private final int row;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position move(Action dir) {
        return new Position(row + dir.getY(), col + dir.getX());
    }
    
    public boolean isValid() {
        return this.col >= 0 && this.col < Game.DIM_X && this.row >= 0 && this.row < Game.DIM_Y;
    }

    @Override
    public boolean equals(Object obj) {
    	return this == obj || obj != null && this.getClass() == obj.getClass() && col == ((Position) obj).col && row == ((Position) obj).row;
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }

    public static Position parse(String str) throws PositionParseException{
    	if(str == null || !str.startsWith("(") || !str.contains(",") || !str.endsWith(")")) {
    		throw new PositionParseException(Messages.INVALID_POSITION.formatted(str));
    	}
    	
    	 String contenido = str.replace("(", "").replace(")", "");
         String[] split = contenido.split(",");
         
        if(split.length != 2) {
        	throw new PositionParseException(Messages.INVALID_POSITION.formatted(String.join(" ", str)));
        }

        try{

            int row = Integer.parseInt(split[0].trim());
            int col = Integer.parseInt(split[1].trim());
    		
    		return  new Position(row, col);
    		
        } catch (NumberFormatException e) {
        	
            throw new PositionParseException(Messages.INVALID_POSITION.formatted(String.join("", str)), e);
        }
    }
    
    @Override
    public int hashCode() {
        return 31 * row + col;
    }

	public Position verticalReverse() {
		int x = this.col;
		int y = this.row; // este queda igual
		
		
		int variacionx = (15 - x);
		int nx = 15 + variacionx;
		if(nx == 30) {
			nx = 29;
		}
		
	

		return new Position(y, nx);
	}
}
