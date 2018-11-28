package schachspiel.figures;

import java.util.ArrayList;
import java.util.List;

import schachspiel.ChessBoard;
import schachspiel.ChessTile;

/**
 * Bishop figure
 * @author milan
 *
 */
public class Bishop extends ChessFigure{
	
	/**
	 * The image path for the black figure
	 */
	private final static String BLACK_BISHOP_PATH="images/BlackBishop.png";

	/**
	 * The image path for the white figure
	 */
	private final static String WHITE_BISHOP_PATH="images/WhiteBishop.png";

	/**
	 * 
	 * @param side The side of the figure
	 */
	public Bishop(ChessColor side) {
		super(side);
	}

	public String getPath() {
		if(side==ChessColor.BLACK) return BLACK_BISHOP_PATH;
		else return WHITE_BISHOP_PATH;
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

	    steps.addAll(getDiagonalSteppingOptions(board, Math.max(board.getN(), board.getM()), row, column));
	    return steps;
	}

}
