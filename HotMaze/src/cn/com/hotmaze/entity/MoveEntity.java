package cn.com.hotmaze.entity;

import cn.com.hotmaze.util.Vec2;

public abstract class MoveEntity {

	private double speed;
	
	private Vec2 position;
	
	private Vec2 heading;
	
	public MoveEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract  boolean canMoveToTarget(Vec2 target);

	public void move_to(Vec2 target){
		if(canMoveToTarget(target)){
			this.position = target;
		}
	}
	
}
