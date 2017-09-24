import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;
import javax.swing.text.JTextComponent.KeyBinding;
public class GuiVersion extends JFrame {
	private int startSize = 0;
	private static final int UNDERLINE = 10;
	private static final int CENTER = 25;
	private int score = 10000000;
	private static int limitMoves = 0;
	private int xPlace = 0;
	private int yPlace = 0;
	private Random rnd = new Random();
	private Color c;
	private Color mC;
	static Game17 game;
	JLabel bestScore = new JLabel();
	CanvasPanel main = new CanvasPanel();
	JPanel control = new JPanel();
	JButton start = new JButton();
	JButton exit = new JButton();
	JTextField numberOfTiles = new JTextField();
	int amount = 0;
	private int clickStart = 0;
	Timer timer;
	GuiVersion(){
//		timer = new Timer(game.speedTimer, new TimerActionListener());
		game = new Game17();
		c = Color.YELLOW;
		mC = Color.LIGHT_GRAY;
		bestScore.setText("The best score: " + amount);
		bestScore.setForeground(Color.white);
		control.setFocusable(true);
		setLayout(new BorderLayout());
		numberOfTiles.setPreferredSize(new Dimension(60,20));
		numberOfTiles.setEnabled(false);
		exit.setText("Exit");
		exit.addActionListener(new ButtonListener());
		start.setText("Start");
		start.addActionListener(new ButtonListener());
		main.setBackground(Color.red);
		main.setFocusable(true);
		main.addKeyListener(new KeyListen());
		main.addMouseListener(new MouseListen());
		control.setBackground(Color.DARK_GRAY);
		control.add(exit);
		control.add(start);
		control.add(numberOfTiles);
		control.add(bestScore);
		add(control, BorderLayout.SOUTH);
		add(main, BorderLayout.CENTER);
	}
	class TimerActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
		}
		
	}
	public void changeSize(){
		String set = JOptionPane.showInputDialog("Size: ");
		game.setSize(Integer.parseInt(set));
		repaint();
	}
	public static void main(String[] args){
		run();
	}
	
	public static void run(){
		GuiVersion frame = new GuiVersion();
		frame.setTitle("Puzzle 15");
		frame.setSize(600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == exit){
				int ex = JOptionPane.showConfirmDialog(null, "Are you sure?");
				if(ex == 0){
					System.exit(0);
				}
			}
			if(e.getSource() == start){
				start();
			}
			main.requestFocus();
			repaint();
		}	
	}
	public void start(){
		changeSize();
		boolean isOkay = false;
		String number = JOptionPane.showInputDialog(null, "Number of Moves?");
		if(number == null){
			System.exit(0);
		}
		if(number.length() > 0){
			for(int i = 0;i < number.length();i++){
				isOkay = (number.charAt(i) >= 48 && number.charAt(i) < 58) ? true : false;
				if(!isOkay){
					break;
				}
			}
		}
		if(!isOkay){
			JOptionPane.showMessageDialog(null, "Incorrect input: " + number);
			start();
		}else{
			limitMoves = Integer.parseInt(number);
		}
		start.setText("Restart");
		amount = 0;
		numberOfTiles.setText(amount + "");
		game.start(limitMoves);
		clickStart = 1;
	}
	class KeyListen extends KeyAdapter {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(clickStart == 1){
				switch(e.getKeyCode()){
				case KeyEvent.VK_UP:
					game.getUp();
					break;
				case KeyEvent.VK_DOWN:
					game.getDown();
					break;
				case KeyEvent.VK_LEFT:
					game.getLeft();
					
					break;
				case KeyEvent.VK_RIGHT:
					game.getRight();
					break;
				case KeyEvent.VK_F1:
					String fp = "Programming 2, Project 2,";
					String sp = "Shostak Dmitry (AUCA)";
					JOptionPane.showMessageDialog(null, String.format("%s\n%s", fp, sp), "About", 1);
					break;
				case KeyEvent.VK_C:
					changeColor();
					game.isMoved = false;
					break;
				case KeyEvent.VK_Z:
					restoreColor();
					game.isMoved = false;
					break;
				default:
					game.isMoved = false;
				}
				if(game.isMoved){
					amount++;
					numberOfTiles.setText("" + amount);	
				}
				repaint();
				win();
				gameOver();
				
				}
			}		
	}
	public void restoreColor(){
		c = Color.YELLOW;
		mC = Color.LIGHT_GRAY;
	}
	public void changeColor(){
		c = new Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
		mC = new Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
	}
	class MouseListen extends MouseAdapter{
		@Override
		public void mousePressed(MouseEvent e) {
			if(clickStart == 1){
			boolean click = false;
			int x4 = getWidth() / 4 - CENTER;
			int y4 = getHeight() / 4 - CENTER;
			int coX = e.getX() - xPlace;
			int coY = e.getY() - yPlace;
			if(coY < 0 && coY > -y4){
				if(coX < x4 && coX > 0){
					game.getDown();
					click = true;
				}
			}
			else if(coX < 0 && coX > -x4){
				if(coY < y4 && coY > 0){
					game.getRight();
					click = true;
				}
			}
			else if(coX >= x4 && coX < x4 * 2){
				if(coY < y4 && coY > 0){
					game.getLeft();
					click = true;
				}
			}
			else if(coY >= y4 && coY < y4 * 2){
				if(coX < x4 && coX > 0){
					game.getUp();
					click = true;
				}
			}
			if(click){
				amount++;
				numberOfTiles.setText("" + amount);
			}
			repaint();
			win();
			gameOver();
			}
		}	
	}
	public void gameOver(){
		if(amount >= limitMoves && game.checkWin() == false){
			String answer = "Number of Moves: ";
			JOptionPane.showMessageDialog(null, String.format("You are not winner!\n%s%d", answer, amount));
			int restart = JOptionPane.showConfirmDialog(null, "Do you want to restart?");
			if(restart == 0){
				start();
				repaint();
			}
			else{
				System.exit(0);
			}
		}
	}
	public void win(){
		if(game.checkWin()){
			score = (amount <= score) ? amount : score;
			JOptionPane.showMessageDialog(null, "You are winner!");
			bestScore.setText("The best score: " + score);
			
			int restart = JOptionPane.showConfirmDialog(null, "Do you want to restart?");
			if(restart == 0){
				start();
				repaint();
			}
			else{
				System.exit(0);
			}
		}
	}
	
	class CanvasPanel extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			if(clickStart == 1){
				game.start = game.start;
			}
			int size = game.SIZE;
			int x = getWidth() / size;
			int y = getHeight() / size;
			for(int i = 0;i < size;i++){
				for(int j = 0;j < size;j++){
					String sGame = game.start[j][i];
					Font font = new Font("Times Roman", Font.BOLD, 70);
					g2.setFont(font);
					g2.setColor(mC);
					g2.drawRect(x * i, y * j, x, y);
					g2.setColor(mC);
					g2.fillRect(x * i + 5, y * j + 5, x - UNDERLINE, y - UNDERLINE);
					g2.setColor(c);
					int x1 = x * i + (x / 2 - CENTER);   //((x - UNDERLINE) / 2);
					int y1 = y * j + (y / 2 + CENTER);   //((y - UNDERLINE) / 2);
					if(!sGame.equals("")){
						if(Integer.parseInt(sGame) > 9){
							x1 = x * i - (CENTER * 2) + (x / 2);  //((x - UNDERLINE) / 2);
						}
					}
					else{
						xPlace = x * i;
						yPlace = y * j;
					}
					g2.drawString(sGame, x1, y1);
				}
			}
		}	
	}
}
