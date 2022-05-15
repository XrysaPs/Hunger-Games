import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class MinMaxPlayer extends Player{
    private ArrayList<int[]> path;

    public MinMaxPlayer() {
        super();
        path = new ArrayList<int[]> ();
    }

    public MinMaxPlayer(int id,String name,Board board,int score,int x,int y,Weapon bow,Weapon pistol,Weapon sword, int n) {
        super(id, name, board, score, x, y, bow, pistol, sword);
        path = new ArrayList<int[]> (n);
    }

    float forceKillValue(int xCurrentPos, int yCurrentPos, int xOpponentCurrentPos, int yOpponentCurrentPos) {  // we calculate the Euclidean distance of the players
        return (float)Math.sqrt(Math.pow(xCurrentPos-xOpponentCurrentPos, 2)+Math.pow(yCurrentPos-yOpponentCurrentPos, 2));
    }

    public float playerDistance(Player p) {  // we calculate the distance of the players in rings that wrap around the mmp
        int d;
        for(d=0; d<=20; d++) {
            int x1=x-d;
            int x2=x+d;
            int y1=y-d;
            int y2=y+d;
            if (x>0 && x-d<=0) {
                x1=x1-1;
            }
            if (x<0 && x+d>=0) {
                x2=x2+1;
            }
            if (y>0 && y-d<=0) {
                y1=y1-1;
            }
            if (y<0 && y+d>=0) {
                y2=y2+1;
            }
            for(int i=y1; i<=y2; i++) {
                if(i==0) {
                    continue;
                }
                for(int j=x1; j<=x2; j++) {
                    if(j==0) {
                        continue;
                    }
                    if (i>y1 && i<y2) {
                        if (j>x1 && j<x2) {
                            continue;
                        }
                    }
                    if(p.x==j && p.y==i) {
                        return d;
                    }
                }
            }
        }
        return -1;
    }

    public float weaponDistance(int x, int y) {  // we calculate the Euclidean distance pistol-MinMaxPlayer
        return (float)Math.sqrt(Math.pow(x - board.getWeapons()[4].getX(), 2) + Math.pow(y - board.getWeapons()[4].getY(), 2));
    }

    double evaluate(int dice, int xCurrentPos, int yCurrentPos, int xOpponentCurrentPos, int yOpponentCurrentPos) {// it is used by mmp only after mmp picks the gun
        double gainWeapons = 1/weaponDistance(xCurrentPos, yCurrentPos);
        int avoidTraps = 0;
        int gainPoints = 0;
        float forceKill = 0;
        for (int f=0; f<board.getF(); f++) {
            if (board.getFood()[f].getX()==xCurrentPos && board.getFood()[f].getY()==yCurrentPos) {// checking for food
                gainPoints=board.getFood()[f].getPoints();
                break;
            }
        }
        for (int t=0; t<board.getT(); t++) {
            if (board.getTraps()[t].getX()==xCurrentPos && board.getTraps()[t].getY()==yCurrentPos) {// checking for traps and counter weapons
                if (sword != null && board.getTraps()[t].getType() == "Rope") {
                    avoidTraps=0;
                    break;
                }
                else if (bow != null &&  board.getTraps()[t].getType() == "Animal") {
                    avoidTraps=0;
                    break;
                }
                avoidTraps=board.getTraps()[t].getPoints();
                break;
            }
        }
        for (int w=0; w<board.getW(); w++) {
            if (board.getWeapons()[w].getX()==xCurrentPos && board.getWeapons()[w].getY()==yCurrentPos && board.getWeapons()[w].getPlayerId()==id) {    // we check for weapons prioritizing them over the food (gainWeapons > gainPoints) and the pistol over the other weapons
                if (board.getWeapons()[w].getType() == "Bow" || board.getWeapons()[w].getType() == "Sword") {
                    gainWeapons=5;
                }
                break;
            }
        }
        forceKill = forceKillValue(xCurrentPos, yCurrentPos, xOpponentCurrentPos, yOpponentCurrentPos); // it assigns a value to forceKill helping mmp to get closer to it's opponent

        if (pistol!=null) {
            return (float) gainWeapons*0.1 + avoidTraps*0.2 + gainPoints*0.4 + (1/forceKill)*0.3; // if mmp is armed with the pistol then he will look for the kill on the other player
        }
        return (float) gainWeapons*0.5 + avoidTraps*0.2 + gainPoints*0.3;
    }

    public static int getMaxEntry(Map<Integer, Double> map){
        double max = Collections.max(map.values());    //we get the max value of the map
        ArrayList<Integer> a = new ArrayList<Integer> ();
        for(Entry<Integer, Double> entry : map.entrySet()) { // we check if there are other keys with the max value in the map
            if(max == entry.getValue()) {
                a.add(entry.getKey());  //if there are then we save all the keys that point to the max value
            }
        }
        Random rand = new Random();
        int randomValue=a.get(rand.nextInt(a.size())); // we choose one of the saved keys randomly
        return randomValue;
    }

    Integer finalMoveValue(Player opponent) {
        Map<Integer, Double> a =new HashMap<Integer, Double>();
        for(int i=0; i<availableMoves(x, y).size(); i++) {
            a.put(availableMoves(x,y).get(i), evaluate(availableMoves(x,y).get(i), moveSimulation(availableMoves(x,y).get(i), x, y)[0], moveSimulation(availableMoves(x,y).get(i), x, y)[1],opponent.getX(),opponent.getY()));
        }
        return getMaxEntry(a);
    }

    int chooseMinMaxMove(Node root) {
        int finalMove=0;
        for(int i=0; i<root.getChildren().size(); i++) {
            root.getChildren().get(i).setNodeEvaluation(200);
            for(int j=0; j<root.getChildren().get(i).getChildren().size(); j++) {
                if(root.getChildren().get(i).getNodeEvaluation() > root.getChildren().get(i).getChildren().get(j).getNodeEvaluation()) {
                    root.getChildren().get(i).setNodeEvaluation(root.getChildren().get(i).getChildren().get(j).getNodeEvaluation());
                }
            }
        }
        root.setNodeEvaluation(-100);
        for (int k=0; k<root.getChildren().size(); k++) {
            if(root.getNodeEvaluation() < root.getChildren().get(k).getNodeEvaluation()) {
                root.setNodeEvaluation(root.getChildren().get(k).getNodeEvaluation());
                finalMove = root.getChildren().get(k).getNodeMove()[2];
            }
        }
        return finalMove;
    }

    int[] getNextMove (int xCurrentPos, int yCurrentPos, int xOpponentCurrentPos, int yOpponentCurrentPos, Player opponent) {
        Node root = new Node(null, 0, board, 0);
        createMySubtree(root, 1, xCurrentPos, yCurrentPos, xOpponentCurrentPos, yOpponentCurrentPos, opponent);
        int finalMove = finalMoveValue(opponent);// using the new algorithm
        if(opponent.getPistol() != null) {// only if the opponent packs a gun because killing is the only factor that connects the 2 players and therefore making the new algorithm useful
            finalMove = chooseMinMaxMove(root);
        }
        int[] newPosition = moveSimulation(finalMove, x, y);
        setX(newPosition[0]);
        setY(newPosition[1]);
        System.out.println("MinMaxPlayer Move: " + finalMove);
        System.out.println("New position of " + getName() + " is: (x, y) = (" + newPosition[0] + ", " + newPosition[1] + ")");
        int previousScore=getScore();
        int[] array = move();
        array[0] = finalMove;
        array[1]=score-previousScore;
        path.add(array);
        return newPosition;
    }

    ArrayList<Integer> availableMoves(int x, int y){ // checking for exceptions in movement because of the confined board
        ArrayList<Integer> a = new ArrayList<Integer> ();
        if(Math.abs(x) != board.getN()/2 && Math.abs(y) != board.getM()/2) {
            for(int i=1; i<=8; i++) {
                a.add(i);
            }
        }
        if(x == -board.getN()/2){
            if(y == -board.getM()/2){ // if the MinMaxPlayer stands on the Left Up Corner
                a.add(3);
                a.add(4);
                a.add(5);
            }
            else if (y == board.getM()/2){ // if the MinMaxPlayer stands on the Left Down Corner
                a.add(1);
                a.add(2);
                a.add(3);
            }
            else{// if the mmp stands on a tile that is on the first column except the tiles that are located on the LUC and LDC
                a.add(1);
                a.add(2);
                a.add(3);
                a.add(4);
                a.add(5);
            }
        }

        if(x == board.getN()/2){ // if the mmp stands on Right Up Corner
            if(y == -board.getM()/2){
                a.add(5);
                a.add(6);
                a.add(7);
            }
            else if(y == board.getM()/2){ // if the mmp stands on the Right Down Corner
                a.add(1);
                a.add(7);
                a.add(8);
            }
            else{// if the mmp stands on a tile that is on the last column except the tiles that are located on the RUC and RDC
                a.add(1);
                a.add(5);
                a.add(6);
                a.add(7);
                a.add(8);
            }
        }

        if(y == -board.getM()/2 && Math.abs(x) != board.getN()/2){  // if the mmp stands on a tile that is on the first line except the tiles that are located on the LUC and RUC
            a.add(3);
            a.add(4);
            a.add(5);
            a.add(6);
            a.add(7);
        }

        if(y == board.getM()/2 && Math.abs(x) != board.getN()/2){ // if the mmp stands on a tile that is on the last line except the tiles that are located on the LDC and RDC
            a.add(1);
            a.add(2);
            a.add(3);
            a.add(7);
            a.add(8);
        }
        return a;
    }

    int[] moveSimulation(int dice, int x, int y) { // a method that moves the player on the cloned board
        int[] newPos = new int[3];
        newPos[2] = dice;
        switch(dice) {
            case 1:     // we dont want the coordinates to be equal to 0 so we take precautions
                newPos[0] = x;
                newPos[1] = y - 1;
                if (newPos[1] == 0) {
                    newPos[1] --;
                }
                break;
            case 2:
                newPos[0] = x + 1;
                newPos[1] = y - 1;
                if (newPos[1] == 0) {
                    newPos[1] --;
                }
                if (newPos[0] == 0) {
                    newPos[0] ++;
                }
                break;
            case 3:
                newPos[0] = x + 1;
                newPos[1] = y;
                if (newPos[0] == 0) {
                    newPos[0] ++;
                }
                break;
            case 4:
                newPos[0] = x + 1;
                newPos[1] = y + 1;
                if (newPos[0] == 0) {
                    newPos[0] ++;
                }
                if (newPos[1] == 0) {
                    newPos[1] ++;
                }
                break;
            case 5:
                newPos[0] = x;
                newPos[1] = y + 1;
                if (newPos[1] == 0) {
                    newPos[1] ++;
                }
                break;
            case 6:
                newPos[0] = x - 1;
                newPos[1] = y + 1;
                if (newPos[1] == 0) {
                    newPos[1] ++;
                }
                if (newPos[0] == 0) {
                    newPos[0] --;
                }
                break;
            case 7:
                newPos[0] = x - 1;
                newPos[1] = y;
                if (newPos[0] == 0) {
                    newPos[0] --;
                }
                break;
            case 8:
                newPos[0] = x - 1;
                newPos[1] = y - 1;
                if (newPos[0] == 0) {
                    newPos[0] --;
                }
                if (newPos[1] == 0) {
                    newPos[1] --;
                }
                break;
            default:
                System.out.println("None of available moves!");
        }
        return newPos;
    }

    void createMySubtree(Node root, int depth, int xCurrentPos, int yCurrentPos, int xOpponentCurrentPos, int yOpponentCurrentPos, Player opponent) {
        ArrayList<Integer> availableMoves = new ArrayList<Integer> ();
        availableMoves = availableMoves(xCurrentPos, yCurrentPos);
        ArrayList<Node> children = new ArrayList<Node> ();
        for(int i=0; i<availableMoves.size(); i++) {
            int[] moveSimulation = moveSimulation(availableMoves.get(i), xCurrentPos, yCurrentPos);
            Board boardClone = (Board)board.clone();
            int[] d = {moveSimulation[0], moveSimulation[1], availableMoves.get(i)};
            Node node = new Node(root, depth, d, boardClone, evaluate(availableMoves.get(i), moveSimulation[0], moveSimulation[1], xOpponentCurrentPos, yOpponentCurrentPos));
            node.setNodeBoard(boardClone);
            createOpponentSubtree(node, 2, node.getNodeMove()[0], node.getNodeMove()[1], xOpponentCurrentPos, yOpponentCurrentPos, opponent);
            children.add(node);
        }
        root.setChildren(children);
    }

    void createOpponentSubtree(Node parent, int depth, int xCurrentPos, int yCurrentPos, int xOpponentCurrentPos, int yOpponentCurrentPos, Player opponent) {
        ArrayList<Node> children = new ArrayList<Node> ();
        ArrayList<Integer> availableMoves = new ArrayList<Integer> ();
        availableMoves = availableMoves(xOpponentCurrentPos, yOpponentCurrentPos);
        for(int i=0; i<availableMoves.size(); i++) {
            int[] moveSimulation = moveSimulation(availableMoves.get(i), xOpponentCurrentPos, yOpponentCurrentPos);
            Board boardClone = (Board)parent.getNodeBoard().clone();
            int[] d = {moveSimulation[0], moveSimulation[1], availableMoves.get(i)};
            Node node = new Node(parent, depth, d, boardClone, parent.getNodeEvaluation()-evaluate(availableMoves.get(i), parent.getNodeMove()[0], parent.getNodeMove()[1], moveSimulation[0], moveSimulation[1]));
            node.setNodeBoard(boardClone);
            children.add(node);
        }
        parent.setChildren(children);
    }

    static boolean killP1(Player p1, MinMaxPlayer p2, double d){  // used by the mmp to determine if he is dead
        if (p2.playerDistance(p1)<=d && p1.pistol != null) {
            System.out.println("Player " +p2.id+ " is dead.");
            return true;
        }
        return false;
    }

    static boolean killP2(Player p1, MinMaxPlayer p2,double d){  // used by the mmp to check if he can kill the opponent
        if (p2.playerDistance(p1)<=d && p2.pistol != null) {
            System.out.println("Player " +p1.id+ " is dead.");
            return true;
        }
        return false;
    }

    public void statistics(int round) { // printing the values of the path variable
        System.out.println("In round " + round + " the MinMax Player rolled a " + path.get(round-1)[0] + ", gained " + path.get(round-1)[1] + " points, ate " + path.get(round-1)[3] + " food, interacted with " + path.get(round-1)[4] + " trap and picked up " + path.get(round-1)[2] + " weapon.");
    }
}
