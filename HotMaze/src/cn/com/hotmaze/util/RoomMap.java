package cn.com.hotmaze.util;

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
	
	void InitCell(int xIndex,int yIndex,int cellTerrain){
		if(xIndex > widthCell || yIndex > heightCell){
			return;
		}
		this.mapCell[xIndex][yIndex] = new Cell(xIndex,yIndex,cellTerrain);		
	}
	
	boolean CanMoveToCell(int xIndex, int yIndex,){
		
	}
	
	boolean CanMoveTo(int xPos,int yPos){
		return true;
	}
	
}
