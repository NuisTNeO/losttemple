/**
 * 
 */
package cn.com.hotmaze.context;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import cn.com.hotmaze.model.BattleModel;
import cn.com.hotmaze.model.IBattleModel;

/**
 * @author ibm
 *
 */
public class BattleContext {

	private static ConcurrentLinkedQueue<IBattleModel> idleBattleQueue = new ConcurrentLinkedQueue<IBattleModel>();
	
	
	private static ConcurrentLinkedQueue<IBattleModel> busyBattleQueue = new ConcurrentLinkedQueue<IBattleModel>();
	
	
	public IBattleModel getIdleBattleModel() {
		
		IBattleModel battleModel = idleBattleQueue.poll();
		if(null == battleModel) {
			battleModel = new BattleModel();
			busyBattleQueue.add(battleModel);
		}
		
		return battleModel;
	}
	
	public void releaseBattleModel(IBattleModel battleModel) {
		
		if(busyBattleQueue.contains(battleModel)) {
			busyBattleQueue.remove(battleModel);
		}
		
		if(!idleBattleQueue.contains(battleModel)) {
			idleBattleQueue.add(battleModel);
		}
	}
	
}
