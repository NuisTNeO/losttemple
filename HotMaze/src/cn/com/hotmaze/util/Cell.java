/**
 * 
 */
package cn.com.hotmaze.util;

/**
 * @author singerinsky
 *
 */
public class Cell {
	//map the tile of map
	private int xIndex;
	private int yIndex;
	
	//terrain of the tile
	private int terrain;
	
	//position of left top corn
	Vec2 position;
	
	
	public Cell(int terrain, int index, int index2) {
		super();
		this.terrain = terrain;
		xIndex = index;
		yIndex = index2;
		position.x = (xIndex - 1) * 25;
		position.y = (yIndex - 1) * 25;
	}

	public int getXIndex() {
		return xIndex;
	}

	public void setXIndex(int index) {
		xIndex = index;
	}

	public int getYIndex() {
		return yIndex;
	}

	public void setYIndex(int index) {
		yIndex = index;
	}

	public int getTerrain() {
		return terrain;
	}

	public void setTerrain(int terrain) {
		this.terrain = terrain;
	}
}
