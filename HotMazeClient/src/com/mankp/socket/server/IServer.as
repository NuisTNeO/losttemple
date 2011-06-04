package com.mankp.socket.server
{
	import flash.utils.ByteArray;
	import com.mankp.socket.decode.IDecode;

	public interface IServer
	{
		/**
		 * 连接socket 
		 * @param host
		 * @param port
		 * 
		 */	
		function connet(host:String=null,port:int=0):void
		/**
		 * 请求数据 
		 * @param bytes
		 * 
		 */	
		function requestData(bytes:ByteArray):void
		/**
		 * 关闭socket连接 
		 * 
		 */		
		function  close():void
		/**
		 * 设置socket连接时需要调用的接口类
		 * @param value
		 * 
		 */	
		function setSocketFx(value:IDecode):void
		/**
		 * 连接主机socket服务端口
		 * @return 
		 * 
		 */	
		function get port():int
		/**
		 * socket连接主机IP 
		 * @return 
		 * 
		 */ 	
		function get host():String
			
		/**
		 * 删除socket连接事调用的接口 
		 * 
		 */	
		function killListener():void
			
	}
}