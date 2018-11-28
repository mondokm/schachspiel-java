package schachspiel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import schachspiel.figures.ChessFigure;
import schachspiel.figures.ChessFigure.ChessColor;

/**
 * Represents a tile of a board
 * @author milan
 *
 */
public class ChessTile extends JLabel{
	/**
	 * The color of the tile
	 */
	private ChessColor color;
	
	/**
	 * The figure that is placed on the tile 
	 */
	private ChessFigure figure=null;
	
	/**
	 * Creates the tile with the given color
	 * @param color The color of the tile
	 */
	public ChessTile(ChessColor color) {
		super();
		this.color=color;
		resetColor();
		setMinimumSize(new Dimension(20, 20));
		setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		setOpaque(true);
	}
	
	/**
	 * Resets the background color of the JLabel
	 */
	public void resetColor() {
		setBackground(color==ChessColor.WHITE?new Color(255, 232, 102):new Color(139, 69, 19));
	}
	
	/**
	 * The color of the tile
	 * @return The color of the tile
	 */
	public ChessColor getColor() {
		return color;
	}
	
	/**
	 * The figure placed on this tile
	 * @return The figure placed on this tile
	 */
	public ChessFigure getFigure() {
		return figure;
	}
	
	/**
	 * Places a figure on the tile
	 * @param figure The figure
	 */
	public void setFigure(ChessFigure figure) {
		this.figure=figure;
		if(figure==null) {
			setIcon(null);
		} else {
			ImageIcon image=new ImageIcon(ChessTile.class.getResource("/"+figure.getPath()));
			setIcon(image);
		}
	}
}
