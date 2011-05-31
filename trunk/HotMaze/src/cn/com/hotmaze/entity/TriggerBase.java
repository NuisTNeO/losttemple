/**
 * 
 */
package cn.com.hotmaze.entity;

/**
 * @author singerinsky
 *
 */
public abstract class TriggerBase {

	private int id;
	private int type;
	private long cd;
	private int state;
	private Vec2 pos;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getCd() {
		return cd;
	}

	public void setCd(long cd) {
		this.cd = cd;
	}

	public Vec2 getPos(){
		return this.pos;
	}

	public void setPos(Vec2 pos){
		this.pos = pos;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public TriggerBase(int id, int type,Vec2 pos,int state) {
		super();
		this.id = id;
		this.type = type;
		this.pos = pos;
		this.state = state;
	}


	
	private long lastExecuteTime;
	
	private boolean isTimeOut(){
		long currentTime = System.currentTimeMillis();
		if( currentTime < lastExecuteTime + cd){
			return false;
		}
		lastExecuteTime = System.currentTimeMillis();
		return true;
	}
	
	/**
	 * @param role
	 * will do the detail by it's children class 
	 */
	public abstract void execute(Role role);
	
	public void action(Role role){
		if(!isTimeOut()){
			return;
		}
		execute(role);
	}
	
	
}
