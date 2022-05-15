import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Game{
    private int round;
    private String winner;
    
    public Game() {
        round = 0;
        winner = "";
    }

    public Game(int round, String winner) {
        this.round = round;
        this.winner = winner;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getRound() {
        return round;
    }
    
    public void setWinner(String winner) {
    	this.winner=winner;
    }
    
    public String getWinner() {
    	return winner;
    }
        
    public static void main(String[] args) {	
    	JFrame f = new JFrame("HungerGames");
		f.setSize(1350, 700);
		JPanel masterPanel = new JPanel(new BorderLayout());
		JPanel buttonsPanel=new JPanel();
		CircleObject co = new CircleObject(50,40);
    	masterPanel.add(co, BorderLayout.CENTER);
    	
        Game r =new Game();
        Board b = new Board(20, 20, 6, 10, 8);
        int[][] weaponLimits = {{-2,2}, {2,2}, {2,-2}, {-2,-2}};
        int[][] foodLimits = {{-3,3}, {3,3}, {3,-3}, {-3,-3}};
        int[][] trapLimits = {{-4,4}, {4,4}, {4,-4}, {-4,-4}};
        b.setWeaponAreaLimits(weaponLimits);
        b.setFoodAreaLimits(foodLimits);
        b.setTrapAriaLimits(trapLimits);
        Player p1 = new Player(1, "Player1", b, 15, 10, 10, null, null, null);
        HeuristicPlayer hp = new HeuristicPlayer(2, "HeuristicPlayer", b, 15, 10, 10, null, null, null, r.getRound());
        System.out.println();
        System.out.println();
        System.out.println("                                                        HUNGER GAMES");
        b.createBoard();
        
        Window w=new Window(b);
        JButton play = new JButton("Play");
        JButton quit = new JButton("Quit");
        JButton generateBoard = new JButton("Generate Board");        

        //action of button play
        play.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e){
        		JPanel masterPanel1 = new JPanel(new BorderLayout());
        		JPanel buttonsPanel1 = new JPanel();
        		
        		System.out.println();
        		System.out.println("________________________________________________________________________________________________________________________");
        		r.setRound(r.getRound() + 1);
        		System.out.println("Round: " + r.getRound());
        		if (r.getRound() % 3 == 0) {
        			b.resizeBoard(p1, hp);
        		}
        		p1.getRandomMove();
        		p1.move();
        		System.out.println("Distance from player Heuristic: "+hp.playerDistance(p1));
        		if(HeuristicPlayer.killP1(p1,hp,2) || hp.getScore()<0) {
        			b.getStringRepresentation(p1, hp);
        			r.setWinner(p1.getName());
        			System.out.println(hp.getName()+" is dead...");
        		}
        		System.out.println();
        		hp.MoveHeuristicPlayer(p1);
        		System.out.println("Distance from player Player1: "+hp.playerDistance(p1));
        		System.out.println();
        		hp.statistics(r.getRound());
        		if(HeuristicPlayer.killP2(p1,hp,2) || p1.getScore()<0 || r.getRound()==50) {
        			b.getStringRepresentation(p1, hp);
        			r.setWinner(hp.getName());
        			System.out.println(p1.getName()+" is dead...");
        		}
        		b.getStringRepresentation(p1, hp);
            
        		if (r.getWinner() == "") {
        			Window w1 = new Window(b, p1, hp);
        			JLabel label1 = new JLabel("<html>Player 1<br/>Total Score: <html>"+ p1.getScore());
        			JLabel label2 = new JLabel("<html>Player 2<br/>Total Score: <html>"+ hp.getScore());
        			label1.setForeground(Color.YELLOW);
        			label1.setBackground(Color.MAGENTA);
        			label1.setOpaque(true);
        			label2.setForeground(Color.YELLOW);
        			label2.setBackground(Color.MAGENTA);
        			label2.setOpaque(true);
        			masterPanel1.add(label1, BorderLayout.LINE_START);
        			masterPanel1.add(label2, BorderLayout.LINE_END);
        			masterPanel1.add(w1, BorderLayout.CENTER);
        			buttonsPanel1.add(play);
        			buttonsPanel1.add(quit);
        			
        			masterPanel1.add(buttonsPanel1, BorderLayout.SOUTH);
        			f.add(masterPanel1);
        			f.revalidate();
        		}
        		
        		if(r.getWinner() != "") {
        	        if (p1.getScore() > hp.getScore()) {
        	        	r.setWinner(p1.getName());
        	        }
        	        else if (p1.getScore() < hp.getScore()) {
        	        	r.setWinner(hp.getName());
        	        }
        	        else {
        	        	System.out.println("It's a draw!");
        	        	r.setWinner("It's a draw!");
        	        }
        			JLabel endingLabel = new JLabel("<html>Game Ended<br/>Winner is: <html>"+ r.getWinner());
        			endingLabel.setForeground(Color.YELLOW);
        			endingLabel.setBackground(Color.MAGENTA);
        			endingLabel.setOpaque(true);
        			masterPanel1.add(endingLabel, BorderLayout.CENTER);
        			play.setVisible(false);
        			masterPanel1.add(buttonsPanel1, BorderLayout.SOUTH);
        			f.add(masterPanel1);
        			f.revalidate();
        		}
        	}
        });
        
        //action of button quit
        quit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e){
        		System.out.println();
                System.out.println("________________________________________________________________________________________________________________________");
                System.out.println("Game ended");
                System.out.println("Number of rounds: "+r.getRound());
                System.out.println(p1.getName() + "'s score is: " + p1.getScore());
                System.out.println(hp.getName() + "'s score is: " + hp.getScore());
                f.dispose();
        	}
        });
        
        //action of button generateBoard
        generateBoard.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e){
        		masterPanel.add(w, BorderLayout.CENTER);
        		buttonsPanel.add(play);
        		buttonsPanel.add(quit);
        		masterPanel.add(buttonsPanel, BorderLayout.SOUTH);
        		f.add(masterPanel);
        		f.revalidate();
        		generateBoard.setVisible(false);
        		co.setVisible(false);
        	}
        });
        
        buttonsPanel.add(generateBoard, BorderLayout.SOUTH);
        buttonsPanel.add(play, BorderLayout.SOUTH);
        buttonsPanel.add(quit, BorderLayout.SOUTH);
        masterPanel.add(buttonsPanel, BorderLayout.SOUTH);
        masterPanel.setBackground(Color.YELLOW);
        f.add(masterPanel);
    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);          
    }
}
