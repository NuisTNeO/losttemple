package com.mankp.scene.events
{
	import flash.events.Event;
	
	public class SceneEvent extends Event
	{
		/**
		 *  跳转下一个场景
		 */		
		public static const NEXT_SCENE:String = "nextScene";
		
		public function SceneEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
	}
}