package cn.com.hotmaze.entity;

import cn.com.hotmaze.util.Vec2;

public class MoveEntity {

	private double speed;
	
	private Vec2 position;
	
	private Vec2 heading;
	
	public MoveEntity() {
		// TODO Auto-generated constructor stub
	}

	public void move_to(Vec2 target){
		Vec2 tmp = position.sub(target);
	}
	
}
