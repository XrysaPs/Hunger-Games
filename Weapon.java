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
public class Weapon{
	private int id ;
	private int x;
	private int y;
	private int playerId;
	private String type;

	public Weapon() {//����� constructor
		id=0;
		x=0;
		y=0;
		playerId=0;
		type=null;
	}
	public Weapon(int id,int x,int y,int playerId,String type) {//constructor �� ��������
		this.id=id;
		this.x=x;
		this.y=y;
		this.playerId=playerId;
		if(type=="Pistol"||type=="Bow"||type=="Sword") {
			this.type=type;
		}
		else {
			System.out.println(type + " is not a valid type of weapon.I will set it as a Pistol");
			type="Pistol";
		}	
	}
	public Weapon(Weapon WeaponObject) {//����������� ����� Weapon 
		this.id=WeaponObject.id;
		this.x=WeaponObject.x;
		this.y=WeaponObject.y;
		this.playerId=WeaponObject.playerId;
		this.type=WeaponObject.type;	
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
	public void setPlayerId(int playerId) {
		this.playerId=playerId;
	}
	public void setType(String type) {
		if(type=="Pistol"||type=="Bow"||type=="Sword") {
			this.type=type;
		}
		else {
			System.out.println(type + " is not a valid type of weapon.I will set it as a Pistol");
			type="Pistol";
		}	
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
	public int getPlayerId() {
		return playerId;
	}
	public String getType() {
		return type;
	}
}