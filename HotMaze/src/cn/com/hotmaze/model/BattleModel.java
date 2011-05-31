/**
 * 
 */
package cn.com.hotmaze.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.hotmaze.context.MapContext;
import cn.com.hotmaze.entity.Role;
import cn.com.hotmaze.entity.TriggerBase;
import cn.com.hotmaze.util.RoomMap;

/**
 * @author ibm
 *
 */
public  class BattleModel implements IBattleModel {
	//战斗唯一标识,由1开始自增
	private int battleID = 0;
	
	//战斗类型
	private int battleType = 0;
	
	private List<Role> allUsers = new ArrayList<Role>();
	
	private List<Role> redUsers = new ArrayList<Role>();
	
	private List<Role> blueUsers = new ArrayList<Role>();
	
	private RoomMap	map;
	
	private List<TriggerBase> triggers = new ArrayList<TriggerBase>();
	
	private HashMap<Integer, TriggerBase> triggerMap = new HashMap<Integer, TriggerBase>();
	
	public void clear() {
		
		allUsers.clear();
		redUsers.clear();
		blueUsers.clear();
		triggerMap.clear();
	}


	@Override
	public void initBattle(String map_id) {
		// TODO Auto-generated method stub
		this.map = MapContext.getInstance().createMapById(map_id);
	}


	@Override
	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
