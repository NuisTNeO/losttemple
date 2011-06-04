package com.mankp.scene
{
	import flash.display.DisplayObjectContainer;
	import flash.events.Event;
	import flash.utils.Dictionary;
	
	import com.mankp.scene.events.SceneEvent;
	import com.mankp.scene.interfaces.ISceneManger;
	/**
	 * 场景管理器 
	 * @author leon
	 * 
	 */	
	public class SceneManger implements ISceneManger
	{
		public var dic:Dictionary;
		private var _currentSceneName:String;
		private var _stage:DisplayObjectContainer;
		private static var _instance:SceneManger;
		
		public function get stage():DisplayObjectContainer
		{
			return _stage;
		}
		
		public static function getInstance(target:DisplayObjectContainer=null):SceneManger{
			if(_instance==null) _instance=new SceneManger(target);
			return _instance;
		}
		
		public function SceneManger(target:DisplayObjectContainer)
		{
		   _stage = target;
		   dic = new Dictionary();	
		}
		
		public function get curentScene():SceneBase
		{
			return dic[_currentSceneName];
		}
		
		public function registerScene(name:String, scene:SceneBase):void
		{
			dic[name] = scene;
		}
		
		public function removeScenen(name:String):void
		{
			if(dic[name]) delete dic[name];
		}
		
		public function showScene(name:String):void
		{
			_currentSceneName = name;
			var scene:SceneBase = dic[name] as SceneBase;
			scene.addEventListener(SceneEvent.NEXT_SCENE,sleepCurrentScene);
			scene.initilze();
			if(!_stage.contains(scene)){
				_stage.addChild(scene);
			  }
			scene.start();
		}
		
		public function sleepCurrentScene(e:Event=null):void
		{
			var scene:SceneBase = dic[_currentSceneName] as SceneBase;
			if(_stage.contains(scene)){
				while(scene.numChildren>0) scene.removeChildAt(0);
				scene.removeEventListener(SceneEvent.NEXT_SCENE,sleepCurrentScene);
			    scene.destroy();
			    _stage.removeChild(scene);
				showScene(scene.nextScene);
			}
		}
		
		public function set stage(target:DisplayObjectContainer):void
		{
			_stage = target;
		}
		
		public function getScene(name:String):SceneBase
		{
			return dic[name] as SceneBase;
		}
		
		
	}
}