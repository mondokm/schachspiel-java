package schachspiel.figures;

import java.util.ArrayList;
import java.util.List;

import schachspiel.ChessBoard;
import schachspiel.ChessTile;

/**
 * Knight figure
 * @author milan
 *
 */
public class Knight extends ChessFigure{

	/**
	 * The image path for the black figure
	 */
	private final static String BLACK_KNIGHT_PATH="images/BlackKnight.png";

	/**
	 * The image path for the white figure
	 */
	private final static String WHITE_KNIGHT_PATH="images/WhiteKnight.png";

	/**
	 * 
	 * @param side The side of the figure
	 */
	public Knight(ChessColor side) {
		super(side);
	}

	public String getPath() {
		if(side==ChessColor.BLACK) return BLACK_KNIGHT_PATH;
		else return WHITE_KNIGHT_PATH;
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

	    if (row >= 2)
	    {
	        if (column >= 1)
	            if (board.getTile(row - 2,column - 1).getFigure() == null || board.getTile(row - 2,column - 1).getFigure().getSide() != side)
	                steps.add(board.getTile(row - 2,column - 1));
	        if (column <= board.getM() - 2)
	            if (board.getTile(row - 2,column + 1).getFigure() == null || board.getTile(row - 2,column + 1).getFigure().getSide() != side)
	                steps.add(board.getTile(row - 2,column + 1));
	    }

	    if (row <= board.getN() - 3)
	    {
	        if (column >= 1)
	            if (board.getTile(row + 2,column - 1).getFigure() == null || board.getTile(row + 2,column - 1).getFigure().getSide() != side)
	                steps.add(board.getTile(row + 2,column - 1));
	        if (column <= board.getM() - 2)
	            if (board.getTile(row + 2,column + 1).getFigure() == null || board.getTile(row + 2,column + 1).getFigure().getSide() != side)
	                steps.add(board.getTile(row + 2,column + 1));
	    }

	    if (column >= 2)
	    {
	        if (row >= 1)
	            if (board.getTile(row - 1,column - 2).getFigure() == null || board.getTile(row - 1,column - 2).getFigure().getSide() != side)
	                steps.add(board.getTile(row - 1,column - 2));
	        if (row <= board.getN() - 2)
	            if (board.getTile(row + 1,column - 2).getFigure() == null || board.getTile(row + 1,column - 2).getFigure().getSide() != side)
	                steps.add(board.getTile(row + 1,column - 2));
	    }

	    if (column <= board.getM() - 3)
	    {
	        if (row >= 1)
	            if (board.getTile(row - 1,column + 2).getFigure() == null || board.getTile(row - 1,column + 2).getFigure().getSide() != side)
	                steps.add(board.getTile(row - 1,column + 2));
	        if (row <= board.getN() - 2)
	            if (board.getTile(row + 1,column + 2).getFigure() == null || board.getTile(row + 1,column + 2).getFigure().getSide() != side)
	                steps.add(board.getTile(row + 1,column + 2));
	    }

	    return steps;
	}

}
