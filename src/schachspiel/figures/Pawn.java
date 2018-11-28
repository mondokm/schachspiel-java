package schachspiel.figures;

import java.util.ArrayList;
import java.util.List;

import schachspiel.ChessBoard;
import schachspiel.ChessTile;

/**
 * Pawn figure
 * @author milan
 *
 */
public class Pawn extends ChessFigure {

	/**
	 * The image path for the black figure
	 */
	private final static String BLACK_PAWN_PATH = "images/BlackPawn.png";

	/**
	 * The image path for the white figure
	 */
	private final static String WHITE_PAWN_PATH = "images/WhitePawn.png";

	/**
	 * 
	 * @param side The side of the figure
	 */
	public Pawn(ChessColor side) {
		super(side);
	}

	public String getPath() {
		if (side == ChessColor.BLACK)
			return BLACK_PAWN_PATH;
		else
			return WHITE_PAWN_PATH;
	}

	public List<ChessTile> getStepOptions(ChessBoard board)
	{
		ArrayList<ChessTile> steps=new ArrayList<ChessTile>();
	    int row = -1, column = -1;
	    for (int i = 0; i < board.getN(); i++)
	    {
	        for (int j = 0; j < board.getM(); j++)
	        {
	            if (board.getTile(i,j).getFigure() == this)
	            {
	                row = i;
	                column = j;
	            }
	        }
	    }
	    if (row == -1)
	        return steps;

        if (row != 0)
        {
            if (board.getTile(row - 1,column).getFigure() == null)
                steps.add(board.getTile(row - 1,column));
            if (column != 0 && board.getTile(row - 1,column - 1).getFigure() != null && board.getTile(row - 1,column - 1).getFigure().getSide() != side)
                steps.add(board.getTile(row - 1,column - 1));
            if (column != board.getM() - 1 && board.getTile(row - 1,column + 1).getFigure() != null && board.getTile(row - 1,column + 1).getFigure().getSide() != side)
                steps.add(board.getTile(row - 1,column + 1));
            if (row == board.getN() - 2 && board.getTile(row - 1,column).getFigure() == null && board.getTile(row - 2,column).getFigure() == null)
                steps.add(board.getTile(row - 2,column));
        }
	    

	    return steps;
	}

}
