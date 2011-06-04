package com.mankp.socket.decode
{
	import flash.events.IOErrorEvent;
	import flash.events.ProgressEvent;
	import flash.net.Socket;
	import flash.utils.describeType;
	import flash.utils.getDefinitionByName;

	public class SocketDecode extends AbstractDecodeFx
	{
		public function SocketDecode()
		{
			super();
		}
		
		override public function onRecives(e:ProgressEvent):void
		{
			
			var sck:Socket = e.target as Socket;
			var data:Array;
			var xmlf:XML;
			var Cls:Class;
			var decodeO:Object;
			while(sck.bytesAvailable){
				  data=sck.readObject();
				  this.reponets(data[0],data[1]);
			}
		}
		
	}
}