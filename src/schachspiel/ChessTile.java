package schachspiel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import schachspiel.figures.ChessFigure;
import schachspiel.figures.ChessFigure.ChessColor;

public class ChessTile extends JLabel{
	private ChessColor color;
	private ChessFigure figure=null;
	
	public ChessTile(ChessColor color) {
		super();
		this.color=color;
		resetColor();
		setMinimumSize(new Dimension(20, 20));
		setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		setOpaque(true);
	}
	
	public void resetColor() {
		setBackground(color==ChessColor.WHITE?new Color(255, 232, 102):new Color(139, 69, 19));
	}
	
	public ChessColor getColor() {
		return color;
	}
	
	public ChessFigure getFigure() {
		return figure;
	}
	
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
