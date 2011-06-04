package com.mankp.socket.server
{
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.events.ProgressEvent;
	import flash.events.SecurityErrorEvent;
	import flash.net.Socket;
	import flash.utils.ByteArray;
	import com.mankp.socket.decode.IDecode;
	
	/**
	 * socket服务
	 * @author leon
	 * 
	 */	
	public class SocketBase  implements IServer
	{
		private var _host:String;
		private var _port:int;
		private var _serversocket:Socket;
		
		private static var _instance:SocketBase;
		
		private var _socketFx:IDecode;
		
		
		public function SocketBase(inter:InternalClass)
		{
			if(inter==null)throw new Error("请调用getInstance实例");
			
			_serversocket = new Socket();
			
		}
			
		public function setSocketFx(value:IDecode):void
		{
			_socketFx = value;
		}
		/**
		 *  单列实现客服端,只有一个socket连接服务器
		 * @return SocketBase
		 * 
		 */        
		public  static function getInstance():SocketBase{
			
			if(_instance==null)_instance=new SocketBase(new InternalClass());
			return _instance;
		}
		
		
		public function connet(host:String=null,port:int=0):void{
			_host = host;
			_port = port;
			if(_host && _port){
			if(_socketFx==null) throw new Error("请设置socket连接时所需要的接口类");
			   _serversocket.addEventListener(Event.CONNECT,_socketFx.onConnect);
			   _serversocket.addEventListener(Event.CLOSE,_socketFx.onClose);
			   _serversocket.addEventListener(IOErrorEvent.IO_ERROR,_socketFx.onIOError);
			   _serversocket.addEventListener(ProgressEvent.SOCKET_DATA,_socketFx.onRecives);
			   _serversocket.addEventListener(SecurityErrorEvent.SECURITY_ERROR,_socketFx.onSecurity);
			   _serversocket.connect(_host,_port);
			}
		}
		
		/**
		 * 删除socket连接事调用的接口 
		 * 
		 */		
		public function killListener():void{
			_serversocket.removeEventListener(Event.CONNECT,_socketFx.onConnect);
			_serversocket.removeEventListener(Event.CLOSE,_socketFx.onClose);
			_serversocket.removeEventListener(IOErrorEvent.IO_ERROR,_socketFx.onIOError);
			_serversocket.removeEventListener(ProgressEvent.SOCKET_DATA,_socketFx.onRecives);
			_serversocket.removeEventListener(SecurityErrorEvent.SECURITY_ERROR,_socketFx.onSecurity);
		}
		
			
		public function requestData(bytes:ByteArray):void {
			_serversocket.writeInt(bytes.length);
			_serversocket.writeBytes(bytes);
			_serversocket.flush();
		}
		
		
			
		public function close():void{
		   _serversocket.close();
		}
		
			
		public function get port():int
		{
			return _port;
		}
		       
		public function get host():String
		{
			return _host;
		}

	}
}

 class InternalClass{
	public function InternalClass(){}
}