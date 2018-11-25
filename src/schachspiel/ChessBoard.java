package schachspiel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import schachspiel.figures.Bishop;
import schachspiel.figures.ChessFigure.ChessColor;
import schachspiel.figures.King;
import schachspiel.figures.Knight;
import schachspiel.figures.Pawn;
import schachspiel.figures.Queen;
import schachspiel.figures.Rook;
import schachspiel.util.Message;
import schachspiel.util.Message.Type;
import schachspiel.util.NetworkListener;
import schachspiel.util.NetworkUtils;

public class ChessBoard extends JPanel{
	private ChessTile[][] arr;
	private int n,m;
	private List<ChessTile> stepOptions=null;;
	private boolean playersTurn;
	private ChessTile lastPressed=null;
	private ChessColor playerSide;
	private ChessColor opponentSide;
	private NetworkUtils networkUtils;
	
	public ChessBoard(int n, int m) {
		super(new GridLayout(n, m));
		this.n=n;
		this.m=m;
		arr=new ChessTile[n][m];
		fillWithTiles();
		stepOptions=new ArrayList<ChessTile>();
	}
	
	public void startClient(String host, int port) {
		networkUtils=new NetworkUtils();
		networkUtils.setupClient(host,port);
		networkUtils.addListener(new MessageListener());
		networkUtils.startListening();
	}
	
	public void startServer(ChessColor serverSide, int port) {
		playerSide=serverSide;
		playersTurn=playerSide==ChessColor.BLACK?false:true;
		this.opponentSide=playerSide==ChessColor.BLACK?ChessColor.WHITE:ChessColor.BLACK;
		fillBoard();
		networkUtils=new NetworkUtils();
		networkUtils.setupServer(port);
		networkUtils.sendMessage(new Message(Type.SIDE, playerSide==ChessColor.WHITE?"BLACK":"WHITE"));
		networkUtils.addListener(new MessageListener());
		networkUtils.startListening();
	}
	
	public void closedGame() {
		networkUtils.sendMessage(new Message(Type.CLOSED));
		networkUtils.stopListening();
		networkUtils.closeAll();
	}
	
	protected void fillWithTiles() {
		TileListener tl=new TileListener();
		
		for (int i = 0; i < n; i++){
	        for (int j = 0; j < m; j++){
	            arr[i][j] = new ChessTile((i + j) % 2 == 0 ? ChessColor.WHITE : ChessColor.BLACK);
	            add(arr[i][j]);
	            arr[i][j].addMouseListener(tl);
	        }
	    }
	}
	
	protected void fillBoard()
	{
	    int queenColumn = (int)(m - 1) / 2;

	    //Rooks
	    arr[0][0].setFigure(new Rook(opponentSide));
	    arr[0][m - 1].setFigure(new Rook(opponentSide));
	    arr[n - 1][0].setFigure(new Rook(playerSide));
	    arr[n - 1][m - 1].setFigure(new Rook(playerSide));

	    //Bishops
	    arr[0][queenColumn - 1].setFigure(new Bishop(opponentSide));
	    arr[0][queenColumn + 2].setFigure(new Bishop(opponentSide));
	    arr[n - 1][queenColumn - 1].setFigure(new Bishop(playerSide));
	    arr[n - 1][queenColumn + 2].setFigure(new Bishop(playerSide));

	    //Kings
	    if(playerSide==ChessColor.WHITE) {
	    	arr[0][queenColumn + 1].setFigure(new King(opponentSide));
		    arr[n - 1][queenColumn + 1].setFigure(new King(playerSide));
	    }else {
	    	arr[0][queenColumn].setFigure(new King(opponentSide));
		    arr[n - 1][queenColumn].setFigure(new King(playerSide));
	    }

	    //Queens
	    if(playerSide==ChessColor.WHITE) {
	    	arr[0][queenColumn].setFigure(new Queen(opponentSide));
		    arr[n - 1][queenColumn].setFigure(new Queen(playerSide));
	    }else {
	    	arr[0][queenColumn+1].setFigure(new Queen(opponentSide));
		    arr[n - 1][queenColumn+1].setFigure(new Queen(playerSide));
	    }
	    

	    //Knights
	    arr[0][queenColumn - 2].setFigure(new Knight(opponentSide));
	    arr[0][queenColumn + 3].setFigure(new Knight(opponentSide));
	    arr[n - 1][queenColumn - 2].setFigure(new Knight(playerSide));
	    arr[n - 1][queenColumn + 3].setFigure(new Knight(playerSide));

	    //Pawns
	    for (int i = 0; i < m; i++)
	        arr[1][i].setFigure(new Pawn(opponentSide));
	    for (int i = 0; i < m; i++)
	        arr[n - 2][i].setFigure(new Pawn(playerSide));
	    for (int i = 1; i < queenColumn - 2; i++)
	        arr[0][i].setFigure(new Pawn(opponentSide));
	    for (int i = 1; i < queenColumn - 2; i++)
	        arr[n - 1][i].setFigure(new Pawn(playerSide));
	    for (int i = queenColumn + 4; i < m - 1; i++)
	        arr[0][i].setFigure(new Pawn(opponentSide));
	    for (int i = queenColumn + 4; i < m - 1; i++)
	        arr[n - 1][i].setFigure(new Pawn(playerSide));
	}
	
	public ChessTile getTile(int i, int j) {
		return arr[i][j];
	}
	
	public int getN() {
		return n;
	}
	
	public int getM() {
		return m;
	}
	
	class TileListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			ChessTile tile=(ChessTile) e.getSource();
			System.out.println(playersTurn);
			if (stepOptions.isEmpty())
		    {
		        if (tile.getFigure() == null) return;
		        if (!playersTurn) return;
		        if (tile.getFigure().getSide()!=playerSide) return;
		        lastPressed = tile;
		        stepOptions.addAll(tile.getFigure().getStepOptions(ChessBoard.this));
		        for (ChessTile t: stepOptions)
		        {
		            t.setBackground(Color.YELLOW);
		        }
		    }
		    else
		    {
	        	boolean won=false;
		        if (stepOptions.contains(tile))
		        {
		            if (tile.getFigure()!=null && tile.getFigure() instanceof King) won=true;
		            tile.setFigure(lastPressed.getFigure());
		            lastPressed.setFigure(null);
		            int[] fromPlace=getTilePlace(lastPressed);
		            int[] toPlace=getTilePlace(tile);
		            networkUtils.sendMessage(new Message(Type.STEP, fromPlace[0]+" "+fromPlace[1]+" "+toPlace[0]+" "+toPlace[1]));
		            nextPlayer();
		        }
		        for (ChessTile t: stepOptions)
		        {
		            t.resetColor();
		        }
		        stepOptions.clear();
		        if(won) {
		        	networkUtils.sendMessage(new Message(Type.WINNER, ""));
	                networkUtils.stopListening();
					networkUtils.closeAll();
					JOptionPane.showMessageDialog(null, "You have won the game");
					SwingUtilities.getWindowAncestor(ChessBoard.this).dispose();
		        }
		    }
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			((ChessTile)e.getSource()).setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			((ChessTile)e.getSource()).setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		}
		
	}
	
	class MessageListener implements NetworkListener{

		public void messageReceived(Message message) {
			if(message==null) return;
			if(message.getType()==Type.STEP) {
				String[] parts=message.getString().split(" ");
				if(parts.length<4) return;
				ChessTile from=getTile(getN()-1-Integer.parseInt(parts[0]), getM()-1-Integer.parseInt(parts[1]));
				ChessTile to=getTile(getN()-1-Integer.parseInt(parts[2]), getM()-1-Integer.parseInt(parts[3]));
				to.setFigure(from.getFigure());
				from.setFigure(null);
				nextPlayer();
			} else if(message.getType()==Type.SIDE) {
				if(message.getString().equals("WHITE")) playerSide=ChessColor.WHITE;
				else playerSide=ChessColor.BLACK;
				playersTurn=playerSide==ChessColor.BLACK?false:true;
				opponentSide=playerSide==ChessColor.BLACK?ChessColor.WHITE:ChessColor.BLACK;
				fillBoard();
				SwingUtilities.getWindowAncestor(ChessBoard.this).pack();
				
			} else if(message.getType()==Type.CLOSED) {
				networkUtils.stopListening();
				networkUtils.closeAll();
				JOptionPane.showMessageDialog(null, "The other player closed the game");
				SwingUtilities.getWindowAncestor(ChessBoard.this).dispose();
			} else if(message.getType()==Type.WINNER) {
				networkUtils.stopListening();
				networkUtils.closeAll();
				JOptionPane.showMessageDialog(null, "The other player has won the game");
				SwingUtilities.getWindowAncestor(ChessBoard.this).dispose();
			}
		}
		
	}
	
	public void nextPlayer() {
		playersTurn=!playersTurn;
	}
	
	public int[] getTilePlace(ChessTile tile) {
		int[] res=new int[2];
		res[0]=-1;
		res[1]=-1;
		for (int i = 0; i < getN(); i++)
	    {
	        for (int j = 0; j < getM(); j++)
	        {
	            if (getTile(i,j) == tile)
	            {
	                res[0] = i;
	                res[1] = j;
	            }
	        }
	    }
		return res;
	}
	
}
