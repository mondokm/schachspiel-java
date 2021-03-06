package schachspiel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import schachspiel.figures.ChessFigure.ChessColor;

/**
 * The main class of the chess program
 * @author milan
 *
 */
public class Chess {

	JFrame frame;
	
	JPanel mainPanel;
	JPanel serverPanel;
	JPanel clientPanel;
	
	JLabel serverPortLabel;
	JTextField serverPort;
	JLabel serverSideLabel;
	JRadioButton serverSideWhite;
	JRadioButton serverSideBlack;
	ButtonGroup serverSideGroup;
	JButton serverButton;
	
	JLabel clientHostLabel;
	JTextField clientHost;
	JLabel clientPortLabel;
	JTextField clientPort;
	JButton clientButton;
	
	public static void main(String[] args) {
		(new Chess()).setupGUI();
	}
	
	class ServerListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			Thread t=new Thread(()-> {
				JFrame gameFrame=new JFrame("Schachspiel Server");
				
				
				ChessBoard board=new ChessBoard(8, 8);
				board.startServer(serverSideWhite.isSelected()?ChessColor.WHITE:ChessColor.BLACK, Integer.parseInt(serverPort.getText()));
				
				gameFrame.addWindowListener(new WindowAdapter() {
		            public void windowClosing(WindowEvent e) {
		                try {
		                	board.closedGame();
		                } catch(Exception ex) {
		                    ex.printStackTrace();
		                }
		            }
		        });
				gameFrame.setContentPane(board);
				gameFrame.pack();
				gameFrame.setResizable(false);
				gameFrame.setVisible(true);
			});
			t.start();
		}
		
	}
	
	class ClientListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			Thread t=new Thread(()-> {
				JFrame gameFrame=new JFrame("Schachspiel Client");
				
				ChessBoard board=new ChessBoard(8, 8);
				board.startClient(clientHost.getText(), Integer.parseInt(clientPort.getText()));
				
				gameFrame.addWindowListener(new WindowAdapter() {
		            public void windowClosing(WindowEvent e) {
		                try {
		                	board.closedGame();
		                } catch(Exception ex) {
		                    ex.printStackTrace();
		                }
		            }
		        });
				gameFrame.setContentPane(board);
				gameFrame.pack();
				gameFrame.setResizable(false);
				gameFrame.setVisible(true);
			});
			t.start();
			
		}
		
	}
	
	/**
	 * Initialises the user interface
	 */
	void setupGUI() {
		frame=new JFrame("Schachspiel Launcher");
		
		mainPanel=new JPanel();
		serverPanel=new JPanel();
		clientPanel=new JPanel();

		mainPanel.add(clientPanel);
		mainPanel.add(serverPanel);
		
		serverPanel.setLayout(new BoxLayout(serverPanel, BoxLayout.Y_AXIS));
		clientPanel.setLayout(new BoxLayout(clientPanel, BoxLayout.Y_AXIS));

		serverPortLabel = new JLabel("Port:");
		serverPort = new JTextField(20);
		serverPort.setText("5555");
		serverSideLabel = new JLabel("Choose a side:");
		serverSideWhite = new JRadioButton("White");
		serverSideBlack = new JRadioButton("Black");
		serverSideGroup = new ButtonGroup();
		serverSideWhite.setSelected(true);
		serverButton = new JButton("Host");
		
		serverPanel.add(serverPortLabel);
		serverPanel.add(serverPort);
		serverSideGroup.add(serverSideWhite);
		serverSideGroup.add(serverSideBlack);
		serverPanel.add(serverSideWhite);
		serverPanel.add(serverSideBlack);
		serverPanel.add(serverButton);
		serverButton.addActionListener(new ServerListener());
		
		clientHostLabel = new JLabel("Host:");
		clientHost = new JTextField(20);
		clientHost.setText("localhost");
		clientPortLabel = new JLabel("Port:");
		clientPort = new JTextField(20);
		clientPort.setText("5555");
		clientButton = new JButton("Connect");
		
		clientPanel.add(clientHostLabel);
		clientPanel.add(clientHost);
		clientPanel.add(clientPortLabel);
		clientPanel.add(clientPort);
		clientPanel.add(clientButton);
		clientButton.addActionListener(new ClientListener());
		
		
		frame.setContentPane(mainPanel);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
