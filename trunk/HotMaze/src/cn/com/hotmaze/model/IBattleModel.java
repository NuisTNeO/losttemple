/**
 * 
 */
package cn.com.hotmaze.model;

/**
 * @author ibm
 *
 */
public interface IBattleModel {

	public void clear();
	
	public boolean isGameOver();

	void initBattle(String map_id);
	
	
}
