package schachspiel.figures;

import java.util.ArrayList;
import java.util.List;

import schachspiel.ChessBoard;
import schachspiel.ChessTile;

/**
 * Rook figure
 * @author milan
 *
 */
public class Rook extends ChessFigure{

	/**
	 * The image path for the black figure
	 */
	private final static String BLACK_ROOK_PATH="images/BlackRook.png";

	/**
	 * The image path for the white figure
	 */
	private final static String WHITE_ROOK_PATH="images/WhiteRook.png";

	/**
	 * 
	 * @param side The side of the figure
	 */
	public Rook(ChessColor side) {
		super(side);
	}

	public String getPath() {
		if(side==ChessColor.BLACK) return BLACK_ROOK_PATH;
		else return WHITE_ROOK_PATH;
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

	    steps.addAll(getHorizontalSteppingOptions(board, Math.max(board.getN(), board.getM()), row, column));
	    steps.addAll(getVerticalSteppingOptions(board, Math.max(board.getN(), board.getM()), row, column));
	    return steps;
	}

}
