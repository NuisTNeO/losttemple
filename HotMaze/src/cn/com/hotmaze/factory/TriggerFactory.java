package cn.com.hotmaze.factory;

import cn.com.hotmaze.entity.BrigdeTrigger;
import cn.com.hotmaze.entity.TriggerBase;

public class TriggerFactory {

	public static TriggerBase createTrigger(String triggerId){
		if(triggerId.equals("1")){
			TriggerBase tmp =  new BrigdeTrigger(triggerId, 0, null, 0);
			return tmp;
		}
		return null;
	}
	
}
