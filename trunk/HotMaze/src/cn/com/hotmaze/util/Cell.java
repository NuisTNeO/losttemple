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
	private String placed_trigger;
	
	public String getPlaced_trigger() {
		return placed_trigger;
	}

	public void setPlaced_trigger(String placed_trigger) {
		this.placed_trigger = placed_trigger;
	}

	//position of left top corn
	Vec2 position = new Vec2();
	
	
	public Cell(int terrain, int index, int index2,String placed_trigger) {
		super();
		this.terrain = terrain;
		xIndex = index;
		yIndex = index2;
		position.x = (xIndex - 1) * 25;
		position.y = (yIndex - 1) * 25;
		this.placed_trigger = placed_trigger;
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
