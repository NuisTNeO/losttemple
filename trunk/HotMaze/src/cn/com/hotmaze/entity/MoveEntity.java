package cn.com.hotmaze.entity;

import cn.com.hotmaze.util.Vec2;

public abstract class MoveEntity {

	private double speed;
	
	private Vec2 position;
	
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Vec2 getPosition() {
		return position;
	}

	public void setPosition(Vec2 position) {
		this.position = position;
	}

	public Vec2 getHeading() {
		return heading;
	}

	public void setHeading(Vec2 heading) {
		this.heading = heading;
	}

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
