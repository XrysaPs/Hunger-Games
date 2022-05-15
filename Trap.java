/* �������� ��������:
    ���������� ���������
        ���: 9468
        web-mail: stsimrog@ece.auth.gr
        ��������:6977030504
    ������� ��������
        ���:9446
        web-mail:chrypsyr@ece.auth.gr
        ��������:6987242584
 */
public class Trap{
	private int id;
	private int x;
	private int y;
	private String type;
	private int points;


	public Trap() {//����� constructor
		id=0;
		x=0;
		y=0;
		type="";
		points=0;
	}
	public Trap(int id,int x,int y,String type,int points) {//constructor �� ��������
		this.id=id;
		this.x=x;
		this.y=y;
		if(type=="Rope"||type=="Animal") {
			this.type=type;
		}
		else {
			System.out.println("This is not a valid type of trap.I will set it as Rope");
			type="Rope";
		}
		this.points=points;
	}
	public Trap(Trap TrapObject) {//����������� ����� Trap
		id=TrapObject.id;
		x=TrapObject.x;
		y=TrapObject.y;
		type=TrapObject.type;
		points=TrapObject.points;
	}
	public void setId(int id) {//setters ��� ���������� ��� ������ 
		this.id=id;
		
	}
	public void setX(int x) {
		this.x=x;
	}
	public void setY(int y) {
		this.y=y;
	}
	public void setType(String type) {
		if(type=="Rope"||type=="Animal") {
			this.type=type;
		}
		else {
			System.out.println("This is not a valid type of trap.I will set it as Rope");
			type="Rope";
		}
		}
	public void setPoints(int points) {
		this.points=points;
		}
	public int getId() {//getters ��� ���������� ��� ������ 
		return id;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public String getType() {
		return type;
	}
	public int getPoints() {
		return points;
	}
}