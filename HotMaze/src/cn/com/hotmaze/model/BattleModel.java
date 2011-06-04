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
	//ս��Ψһ��ʶ,��1��ʼ����
	private int battleID = 0;
	
	//ս������
	private int battleType = 0;
	
	private List<Role> allUsers = new ArrayList<Role>();
	
	private List<Role> redUsers = new ArrayList<Role>();
	
	private List<Role> blueUsers = new ArrayList<Role>();
	
	private RoomMap	map;
	
	public BattleModel(int battleid) {
		this.setBattleID(battleid);
	}
	
	public void clear() {
		
		allUsers.clear();
		redUsers.clear();
		blueUsers.clear();
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

	public void setBattleID(int battleID) {
		this.battleID = battleID;
	}

	public int getBattleID() {
		return battleID;
	}
	
}