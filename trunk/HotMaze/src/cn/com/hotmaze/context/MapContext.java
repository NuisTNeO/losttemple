package cn.com.hotmaze.context;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.dom4j.Attribute;
import org.dom4j.Element;


import cn.com.hotmaze.util.RoomMap;
import cn.com.hotmaze.util.XMLdoc;

public class MapContext {

	private static ConcurrentMap<String, RoomMap> map = new ConcurrentHashMap<String, RoomMap>();
	
	
	private MapContext() {
		init();
	}  
    
    private static MapContext single;  
    
    private void init(){
    	System.out.println("载入所有地图");
    	XMLdoc doc = new XMLdoc("res/map/map_list.xml");
    	doc.openXML();
    	Element root = doc.getRootElement();
    	for(Iterator<?> itr = root.elementIterator();itr.hasNext();){
    		Element em = (Element) itr.next();
    		Attribute index = em.attribute("map_name");
    		System.out.println("载入地图 "+index.getValue()+".....");
    		RoomMap map = new RoomMap("res/map/"+index.getValue()+".xml");
    		MapContext.map.putIfAbsent(index.getValue(),map);
    	}
    	doc.closeXML();
    }
      
    static{  
        single = new MapContext();  
    }  
      
    public synchronized  static MapContext getInstance() {  
         if (single == null) {    
             single = new MapContext();  
         }    
        return single;  
    }  
	
	public RoomMap createMapById(String map_id){
		RoomMap map = ((Map<String, RoomMap>) MapContext.map).get(map_id);
		System.out.println("can't find map "+map_id);
		assert(map != null);
		return map.clone();
	}
	
	public void reloadAllMap(){
		Iterator<String>  itr = MapContext.map.keySet().iterator();
		while(itr.hasNext()){
			RoomMap map = (RoomMap)MapContext.map.get(itr.next());
			map.Init((String)itr.next());
		}
	}
}
