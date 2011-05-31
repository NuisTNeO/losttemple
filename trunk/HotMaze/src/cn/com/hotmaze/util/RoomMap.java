package cn.com.hotmaze.util;

import java.util.Iterator;

import org.dom4j.Element;

public class RoomMap {
	
	private String mapId;
	
	private String mapName;
	
	private int widthCell;
	
	private int heightCell;
	
	private int mapWidth;

	private int mapHeight;
	
	private int defaultCellTerrain;
	
	//玩家进入房间的等级限制
	private int userLevelLimit;
	
	private Cell[][] mapCell;
	
	public RoomMap(String mapFilePath) {
		// TODO Auto-generated constructor stub
		Init(mapFilePath);
	}

	public void Init(String mapPath){
	   	XMLdoc doc = new XMLdoc(mapPath);
    	doc.openXML();
		Element root = doc.getRootElement();
		this.mapId = root.attributeValue("index");
		this.mapName = root.attributeValue("map_name");
		this.widthCell = Integer.valueOf(root.attributeValue("width"));
		this.heightCell = Integer.valueOf(root.attributeValue("height"));
		this.defaultCellTerrain = Integer.valueOf(root.attributeValue("default_terrain"));
		this.userLevelLimit = Integer.valueOf(root.attributeValue("user_level_limit"));
		mapCell = new Cell[25][25];
		
		for(int height=0;height<this.heightCell;height++)
		{
			for(int width=0;width<this.widthCell;width++)
			{
				mapCell[height][width] = new Cell(defaultCellTerrain,height,width);
			}
		}
		
		for(Iterator itr = root.elementIterator();itr.hasNext();){
			Element el = (Element) itr.next();
			int x = Integer.valueOf(el.attributeValue("xIndex"));
			int y = Integer.valueOf(el.attributeValue("yIndex"));
			int terrain = Integer.valueOf(el.attributeValue("terrain"));
			mapCell[y][x].setTerrain(terrain);
			System.out.println("地形x:"+x+" y:"+y+" 为"+terrain);
		}
		doc.closeXML();
		
	}
	
	//TODO
	public void reSet(){
		
	}
	
	void InitCell(int xIndex,int yIndex,int cellTerrain){
		if(xIndex > widthCell || yIndex > heightCell){
			return;
		}
		this.mapCell[xIndex][yIndex] = new Cell(xIndex,yIndex,cellTerrain);		
	}
	
	public Cell getCellInfoByVec(Vec2 target){
		return getCellInfoByXY((int)target.x,(int)target.y);
	}
	
	public Cell getCellInfoByXY(int x,int y){
		int xIndex = x/25;
		int yIndex = y/25;
		return getCellInfoByIndex(xIndex,yIndex);
	}
	
	public Cell getCellInfoByIndex(int x,int y){
		return this.mapCell[x][y];
	}
	
}
