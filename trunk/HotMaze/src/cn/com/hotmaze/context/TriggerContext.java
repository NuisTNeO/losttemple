package cn.com.hotmaze.context;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.dom4j.Attribute;
import org.dom4j.Element;


import cn.com.hotmaze.entity.TriggerBase;
import cn.com.hotmaze.factory.TriggerFactory;
import cn.com.hotmaze.util.RoomMap;
import cn.com.hotmaze.util.XMLdoc;

public class TriggerContext {

	private static ConcurrentMap<String, TriggerBase> map = new ConcurrentHashMap<String, TriggerBase>();
	
	
	private TriggerContext() {
		init();
	}  
    
    private static TriggerContext single;  
    
    private void init(){
    	System.out.println("‘ÿ»Îª˙πÿ");
    	XMLdoc doc = new XMLdoc("res/map/all_trigger.xml");
    	doc.openXML();
    	Element root = doc.getRootElement();
    	for(Iterator<?> itr = root.elementIterator();itr.hasNext();){
    		Element em = (Element) itr.next();
    		String id = em.attributeValue("id");
    		int type = Integer.valueOf(em.attributeValue("type"));
    		String mapping_id = em.attributeValue("mapping");
    		int cd = Integer.valueOf(em.attributeValue("cd"));
    		TriggerBase trigger = TriggerFactory.createTrigger(id);
    		trigger.setCd(cd);
    		trigger.setMappingId(mapping_id);
    		trigger.setType(type);
    		for (Iterator itrChild = em.elementIterator(); itrChild.hasNext();) {
    			Element el = (Element) itrChild.next();
    			String offset = el.attributeValue("action_offset");
    			System.out.println(offset);
    			trigger.addActionPoint(offset);
    		}
    		
    		TriggerContext.map.putIfAbsent(id, trigger);
    		
    	}
    	doc.closeXML();
    }
      
    static{  
        single = new TriggerContext();  
    }  
      
    public synchronized  static TriggerContext getInstance() {  
         if (single == null) {    
             single = new TriggerContext();  
         }    
        return single;  
    }  
	
    public TriggerBase createTriggerById(String id){
    	return this.map.get(id).clone();
    }
    
}