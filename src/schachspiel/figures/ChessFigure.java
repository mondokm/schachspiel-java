package schachspiel.figures;

import java.util.ArrayList;
import java.util.List;

import schachspiel.ChessBoard;
import schachspiel.ChessTile;

/**
 * A chess figure
 * @author milan
 *
 */
public abstract class ChessFigure {
	public enum ChessColor {WHITE, BLACK};
	
	/**
	 * The color of the figure
	 */
	protected ChessColor side;

	/**
	 * Creates a figure with the given side
	 * @param side The color of the figure
	 */
	public ChessFigure(ChessColor side) {
		this.side=side;
	}
	
	/**
	 * The color of the figure
	 * @return The color of the figure
	 */
	public ChessColor getSide() {
		return side;
	}
	
	/**
	 * Returns the tiles, where the figure could step
	 * @param board The board on which the figure is located
	 * @return A list of the possible tiles
	 */
	public abstract List<ChessTile> getStepOptions(ChessBoard board);
	
	/**
	 * Returns the path of the image of this figure
	 * @return The path of the image of this figure
	 */
	public abstract String getPath();
	
	/**
	 * For figures that can step horizontally
	 * @param board The board where the figure is located
	 * @param distance How far the figure is allowed to step
	 * @param row The row of the figure
	 * @param column The column of the figure
	 * @return A list of the possible tiles
	 */
	protected List<ChessTile> getHorizontalSteppingOptions(ChessBoard board, int distance, int row, int column){
		
		ArrayList<ChessTile> steps=new ArrayList<ChessTile>();

	    //Left
	    int border = Math.max(column - distance, 0);
	    for (int j = column - 1; j >= border; j--)
	    {
	        if (board.getTile(row,j).getFigure() == null)
	        {
	            steps.add(board.getTile(row,j));
	        }
	        else
	        {
	            if (board.getTile(row,j).getFigure().getSide() != side)
	                steps.add(board.getTile(row,j));
	            break;
	        }
	    }

	    //Right
	    border = Math.min(board.getM(), column + distance + 1);
	    for (int j = column + 1; j < border; j++)
	    {
	        if (board.getTile(row,j).getFigure() == null)
	        {
	            steps.add(board.getTile(row,j));
	        }
	        else
	        {
	            if (board.getTile(row,j).getFigure().getSide() != side)
	                steps.add(board.getTile(row,j));
	            break;
	        }
	    }

	    return steps;
	}
	
	/**
	 * For figures that can step vertically
	 * @param board The board where the figure is located
	 * @param dist How far the figure is allowed to step
	 * @param row The row of the figure
	 * @param column The column of the figure
	 * @return A list of the possible tiles
	 */
	protected List<ChessTile> getVerticalSteppingOptions(ChessBoard board, int dist, int row, int column)
	{
	    ArrayList<ChessTile> steps=new ArrayList<ChessTile>();

	    //Up
	    int border = Math.max(row - dist, 0);
	    for (int i = row - 1; i >= border; i--)
	    {
	        if (board.getTile(i,column).getFigure() == null)
	        {
	            steps.add(board.getTile(i,column));
	        }
	        else
	        {
	            if (board.getTile(i,column).getFigure().getSide() != side)
	                steps.add(board.getTile(i,column));
	            break;
	        }
	    }

	    //Down
	    border = Math.min(board.getN(), row + dist + 1);
	    for (int i = row + 1; i < border; i++)
	    {
	        if (board.getTile(i,column).getFigure() == null)
	        {
	            steps.add(board.getTile(i,column));
	        }
	        else
	        {
	            if (board.getTile(i,column).getFigure().getSide() != side)
	                steps.add(board.getTile(i,column));
	            break;
	        }
	    }
	    return steps;
	}

	/**
	 * For figures that can step diagonally
	 * @param board The board where the figure is located
	 * @param dist How far the figure is allowed to step
	 * @param row The row of the figure
	 * @param column The column of the figure
	 * @return A list of the possible tiles
	 */
	protected List<ChessTile> getDiagonalSteppingOptions(ChessBoard board, int dist, int row, int column)
	{
	    ArrayList<ChessTile> steps= new ArrayList<ChessTile>();

	    //Left-Up
	    int border = Math.min(dist, Math.min(row, column));
	    for (int i = 1; i <= border; i++)
	    {
	        if (board.getTile(row - i,column - i).getFigure() == null)
	        {
	            steps.add(board.getTile(row - i,column - i));
	        }
	        else
	        {
	            if (board.getTile(row - i,column - i).getFigure().getSide() != side)
	                steps.add(board.getTile(row - i,column - i));
	            break;
	        }
	    }

	    //Right-Down
	    border = Math.min(dist, Math.min(board.getN() - row - 1, board.getM() - column - 1));
	    for (int i = 1; i <= border; i++)
	    {
	        if (board.getTile(row + i,column + i).getFigure() == null)
	        {
	            steps.add(board.getTile(row + i,column + i));
	        }
	        else
	        {
	            if (board.getTile(row + i,column + i).getFigure().getSide() != side)
	                steps.add(board.getTile(row + i,column + i));
	            break;
	        }
	    }

	    //Left-Down
	    border = Math.min(dist, Math.min(board.getN() - row - 1, column));
	    for (int i = 1; i <= border; i++)
	    {
	        if (board.getTile(row + i,column - i).getFigure() == null)
	        {
	            steps.add(board.getTile(row + i,column - i));
	        }
	        else
	        {
	            if (board.getTile(row + i,column - i).getFigure().getSide() != side)
	                steps.add(board.getTile(row + i,column - i));
	            break;
	        }
	    }

	    //Right-Up
	    border = Math.min(dist, Math.min(row, board.getM() - column - 1));
	    for (int i = 1; i <= border; i++)
	    {
	        if (board.getTile(row - i,column + i).getFigure() == null)
	        {
	            steps.add(board.getTile(row - i,column + i));
	        }
	        else
	        {
	            if (board.getTile(row - i,column + i).getFigure().getSide() != side)
	                steps.add(board.getTile(row - i,column + i));
	            break;
	        }
	    }

	    return steps;
	}

}
