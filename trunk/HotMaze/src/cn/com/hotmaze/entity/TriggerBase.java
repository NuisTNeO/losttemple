/**
 * 
 */
package cn.com.hotmaze.entity;

import java.util.ArrayList;
import java.util.List;

import cn.com.hotmaze.util.Vec2;

/**
 * @author singerinsky
 *
 */
public abstract class TriggerBase {

	private String id;
	private int type;
	private long cd;
	private int state;
	private String mappingId;
	private List<Integer> actionPoints = new ArrayList();
	
	public String getMappingId() {
		return mappingId;
	}

	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}



	private Vec2 pos;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public TriggerBase(String id2, int type,Vec2 pos,int state) {
		super();
		this.id = id2;
		this.type = type;
		this.pos = pos;
		this.state = state;
	}

	public void addActionPoint(String offset){
		this.actionPoints.add(Integer.valueOf(offset));
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
	
	public TriggerBase clone(){
		TriggerBase tmp = null;
		try {
			tmp = (TriggerBase) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tmp;
	}
}
