/**
 * 
 */
package cn.com.hotmaze.context;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import cn.com.hotmaze.model.BattleModel;
import cn.com.hotmaze.model.IBattleModel;

/**
 * @author neo
 *
 */
public class BattleContext {

	private static AtomicInteger ai = new AtomicInteger(1);
	
	private int  getInteger(){
		return ai.getAndIncrement();
	}
	
	private static Queue<IBattleModel> idleBattleQueue = new ConcurrentLinkedQueue<IBattleModel>();
	
	
	private static Queue<IBattleModel> busyBattleQueue = new ConcurrentLinkedQueue<IBattleModel>();
	
	private static BattleContext instance = new BattleContext();
	
	private BattleContext() {
		
	}
	
	public static BattleContext getInstance() {
		if(instance == null) {
			instance = new BattleContext();
		}
		return instance;
	}
	
	public IBattleModel getIdleBattleModel() {
		
		IBattleModel battleModel = idleBattleQueue.poll();
		if(null == battleModel) {
			int battleID = getInteger();
			System.err.println("新建BattleModel " + battleID);
			battleModel = new BattleModel(battleID);
			
		} else {
			System.out.println("返回空闲FightModel " + battleModel.getBattleID());
		}
		busyBattleQueue.add(battleModel);
		
		return battleModel;
	}
	
	public void releaseBattleModel(IBattleModel battleModel) {
		
		battleModel.clear();
		
		if(busyBattleQueue.contains(battleModel)) {
			busyBattleQueue.remove(battleModel);
		}
		
		if(!idleBattleQueue.contains(battleModel)) {
			idleBattleQueue.add(battleModel);
		}
		System.out.println("正常释放fm " + battleModel.getBattleID());
	}
	
	public Queue<IBattleModel> getBusyBattleQueue() {
		
		return busyBattleQueue;
	}
}
