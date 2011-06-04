package cn.com.hotmaze.entity;

import cn.com.hotmaze.util.Cell;
import cn.com.hotmaze.util.RoomMap;
import cn.com.hotmaze.util.Vec2;

public class Role extends MoveEntity{

	private int roleId;
	
	//TODO 结构要调整
	private RoomMap map;
	
	public Role(int roleId) {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.com.hotmaze.entity.MoveEntity#canMoveToTarget(cn.com.hotmaze.util.Vec2)
	 * check this player can move to this target
	 */
	@Override
	public boolean canMoveToTarget(Vec2 target) {
		// TODO Auto-generated method stub
		Cell cell = map.getCellInfoByVec(target);
		if(cell.getTerrain() == 0){
			return true;
		}else if(cell.getTerrain() == 1){
			return false;
		}
		
		return false;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getRoleId() {
		return roleId;
	}

}
