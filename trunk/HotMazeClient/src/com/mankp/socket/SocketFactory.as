package com.mankp.socket
{
	import com.mankp.socket.server.IServer;
	import com.mankp.socket.server.SocketBase;
	import com.mankp.socket.server.SocketXML;

	public class SocketFactory extends AbstractSocketFactory
	{
		
		public static var SOCKET_BASE:String="SB";
		
		public static var SOCKET_XML:String="SX";
		
		override protected function createModuls(m:String):IServer
		{
			var srv:IServer=null;
			switch(m){
				case SOCKET_BASE:
					 srv = SocketBase.getInstance();
					 break;
				case SOCKET_XML:
					srv = SocketXML.getInstance();
					 break;
			}
			
			return srv;
		}
		
		
		
		
		
		
	}
}