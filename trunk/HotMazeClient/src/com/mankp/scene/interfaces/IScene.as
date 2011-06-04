package com.mankp.scene.interfaces
{
	import flash.events.Event;

	public interface IScene
	{
		/**
		 * 初始化场景 
		 * 
		 */		
		function initilze():void;
		/**
		 * 对场景进行休眠 
		 * @param e
		 * 
		 */		
		function sleep(e:Event=null):void;
		/**
		 * 当前场景需要跳转的场景名称 
		 * @return 
		 * 
		 */		
		function get nextScene():String;
		function set nextScene(name:String):void;
		/**
		 * 对场景破环，休眠调用 
		 * 
		 */		
		function destroy():void;
		
		function start():void;
		
		
	}
}