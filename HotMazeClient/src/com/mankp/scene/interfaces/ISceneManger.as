package com.mankp.scene.interfaces
{
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.Stage;
	import flash.events.Event;
	
	import library.scene.SceneBase;

	public interface ISceneManger
	{
		/**
		 * 返回当前场景 
		 * @return 
		 * 
		 */		
		function get curentScene():SceneBase;
		/**
		 * 注册场景 
		 * @param name
		 * @param scene
		 * 
		 */		
		function registerScene(name:String,scene:SceneBase):void;
		/**
		 * 删除场景 
		 * @param name
		 * 
		 */		
		function removeScenen(name:String):void;
		/**
		 * 显示场景 
		 * @param name
		 * 
		 */		
		function showScene(name:String):void;
		/**
		 * 对当前场景休眠 
		 * 
		 */		
		function sleepCurrentScene(e:Event=null):void;
		/**
		 * 场景需要显示的目标stage 
		 * @param target
		 * 
		 */		
		function set stage(target:DisplayObjectContainer):void;
		
		function get stage():DisplayObjectContainer;
		/**
		 * 返回指定场景 
		 * @param name
		 * @return 
		 * 
		 */		
		function getScene(name:String):SceneBase;
		
	}
}