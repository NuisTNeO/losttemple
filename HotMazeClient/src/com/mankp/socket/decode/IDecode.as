package com.mankp.socket.decode
{
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.events.ProgressEvent;
	import flash.events.SecurityErrorEvent;

	public interface IDecode
	{
		
		/**
		 * socket连接时调用
		 * @param e
		 * @return 
		 * 
		 */		
		function onConnect(e:Event):void;
		/**
		 *  socket连接关闭时调用
		 * @param e
		 * @return 
		 * 
		 */		
		function onClose(e:Event):void;
		/**
		 * 接收到服务器端数据时调用
		 * @param e
		 * @return 
		 * 
		 */		
		function onRecives(e:ProgressEvent):void;
		/**
		 * 网络出错时调用
		 * @param e
		 * @return 
		 * 
		 */		
		function onIOError(e:IOErrorEvent):void;
		/**
		 *  出现安全沙箱时调用
		 * @param e
		 * @return 
		 * 
		 */		
		function onSecurity(e:SecurityErrorEvent):void;
	}
}