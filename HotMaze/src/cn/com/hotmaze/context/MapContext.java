package cn.com.hotmaze.context;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import cn.com.hotmaze.util.RoomMap;

public class MapContext {

	private static ConcurrentMap<String, RoomMap> map = new ConcurrentHashMap<String, RoomMap>();
	
	
	private MapContext() {}  
    
    private static MapContext single;  
      
    static{  
        single = new MapContext();  
    }  
      
    public synchronized  static MapContext getInstance() {  
         if (single == null) {    
             single = new MapContext();  
         }    
        return single;  
    }  
	
	public RoomMap getRoomMapById(String mapName){
		RoomMap map = ((Map<String, RoomMap>) MapContext.map).get(mapName);
		System.out.println("can't find map "+mapName);
		assert(map != null);
		return map;
	}
	
	public void reloadAllMap(){
		Iterator<String>  itr = MapContext.map.keySet().iterator();
		while(itr.hasNext()){
			RoomMap map = (RoomMap)MapContext.map.get(itr.next());
			map.Init((String)itr.next());
		}
	}
}
