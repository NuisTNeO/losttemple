/**
 * 
 */
package cn.com.hotmaze.entity;

import cn.com.hotmaze.util.Vec2;

/**
 * @author singerinsky
 *
 */
public class BrigdeTrigger extends TriggerBase {

	/**
	 * @param id
	 * @param type
	 * @param index
	 * @param index2
	 */
	public BrigdeTrigger(String id, int type, Vec2 pos,int state) {
		super(id, type, pos,state);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cn.com.hotmaze.entity.TriggerBase#execute(cn.com.hotmaze.entity.Role)
	 */
	@Override
	public void execute(Role role) {
		// TODO Auto-generated method stub
		if(this.getState() == 1){
			this.setState(2);
		}else{
			this.setState(1);
		}
	}

}
