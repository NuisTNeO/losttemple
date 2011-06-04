package com.mankp.scene
{
	import flash.display.Sprite;
	import flash.events.Event;
	
	import com.mankp.scene.events.SceneEvent;
	import com.mankp.scene.interfaces.IScene;
	/**
	 * 场景抽象类，每个场景必须继承 
	 * @author leon
	 * 
	 */	
	public class SceneBase extends Sprite implements  IScene
	{
	
		private var _nextSceneName:String;
		public function SceneBase()
		{
			super();
		}
		
		
		public function initilze():void
		{
		}
		
		public function sleep(e:Event=null):void
		{
			this.dispatchEvent(new SceneEvent(SceneEvent.NEXT_SCENE));
		}
		
		public function get nextScene():String
		{
			return _nextSceneName;
		}
		public function set nextScene(name:String):void
		{
			_nextSceneName=name;
		}
		
		public function destroy():void
		{
			
		}
		
		
		public function start():void
		{
			
		}
		
	}
}