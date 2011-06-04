package com.mankp.socket.server
{
	import flash.utils.ByteArray;
	import com.mankp.socket.decode.IDecode;

	public class SocketXML implements IServer
	{
		
		private static var _instance:SocketXML;
		
		public function SocketXML()
		{
		}
		
		public static function getInstance():SocketXML{
			if(_instance==null) _instance= new SocketXML();
			return _instance;
		}
		
		public function connet(host:String=null,port:int=0):void
		{
		}
		
		public function requestData(bytes:ByteArray):void
		{
		}
		
		public function close():void
		{
		}
		
		public function setSocketFx(value:IDecode):void
		{
		}
		
		public function get port():int
		{
			return 0;
		}
		
		public function get host():String
		{
			return null;
		}
		
		public function killListener():void
		{
		}
	}
}