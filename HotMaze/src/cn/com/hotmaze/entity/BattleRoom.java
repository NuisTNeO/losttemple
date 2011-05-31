/**
 * 
 */
package cn.com.hotmaze.entity;

import java.util.ArrayList;
import java.util.HashMap;

import cn.com.hotmaze.util.RoomMap;

/**
 * @author Administrator
 *
 */
public class BattleRoom {
	
	private int roomId;
	
	private  RoomMap map;
	
	private HashMap<String, TriggerBase> triggerMap;
	
	private ArrayList<Role> playerList;
	
	public Role getRoleById(int id){
		return null;
	}
	
	BattleRoom(){
		
	}	

	
	
	
	
	

}
