/**
 * 
 */
package cn.com.hotmaze.model;

import java.util.ArrayList;
import java.util.List;

import cn.com.hotmaze.entity.Role;

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
	
	
	public void clear() {
		
		allUsers.clear();
		redUsers.clear();
		blueUsers.clear();
	}


	@Override
	public void initBattle() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
