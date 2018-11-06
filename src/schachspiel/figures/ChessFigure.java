package schachspiel.figures;

import java.util.ArrayList;
import java.util.List;

import schachspiel.ChessBoard;
import schachspiel.ChessTile;

public abstract class ChessFigure {
	public enum ChessColor {WHITE, BLACK};
	
	protected ChessColor side;
	
	public ChessFigure(ChessColor side) {
		this.side=side;
	}
	
	public ChessColor getSide() {
		return side;
	}
	
	public abstract List<ChessTile> getStepOptions(ChessBoard board);
	
	public abstract String getPath();
	
	public List<ChessTile> getHorizontalSteppingOptions(ChessBoard board, int distance, int row, int column){
		
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
	
	List<ChessTile> getVerticalSteppingOptions(ChessBoard board, int dist, int row, int column)
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

	List<ChessTile> getDiagonalSteppingOptions(ChessBoard board, int dist, int row, int column)
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
