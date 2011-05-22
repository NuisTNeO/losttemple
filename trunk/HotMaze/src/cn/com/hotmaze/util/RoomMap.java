package cn.com.hotmaze.util;

import cn.com.hotmaze.entity.MoveEntity;

public class RoomMap {
	
	private int mapId;
	
	private int widthCell;
	
	private int heightCell;
	
	private int mapWidth;
	
	private int mapHeight;
	
	private int defaultCellTerrain;
	
	//��ҽ��뷿��ĵȼ�����
	private int userLevelLimit;
	
	private Cell[][] mapCell;
	
	public RoomMap(int mapId) {
		// TODO Auto-generated constructor stub
		Init(mapId);
	}

	void Init(int mapId){
		
	}
	
	//TODO
	void reSet(){
		
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
