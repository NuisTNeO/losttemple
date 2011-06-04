package com.mankp.socket
{
	import com.mankp.socket.decode.IDecode;
	import com.mankp.socket.server.IServer;
	
	import flash.errors.IllegalOperationError;
	import flash.events.TimerEvent;
	import flash.utils.ByteArray;
	import flash.utils.Dictionary;
	import flash.utils.Timer;
	
	import flashx.textLayout.formats.Direction;

	/**
	 * socket服务抽象类不能实例 
	 * @author leon
	 * 
	 */	
	public class AbstractSocketFactory
	{
		private var _server:IServer;
		private var _bytes:ByteArray;
		private var _timer:Timer;
		/** 超时验证*/
		private var _timerOutValue:int=50000;
		
		public function createSocketServer(module:String,host:String=null,port:int=0,socketfx:IDecode=null):void{
			_server = this.createModuls(module);
			if(host && port && socketfx){
		        this.connect(host,port,socketfx);
			}
		}
		
		
		
		
		public function connect(host:String,port:int,socketfx:IDecode):void{
			_server.setSocketFx(socketfx);
			_server.connet(host,port);
			_timer = new Timer(_timerOutValue);
			_timer.start();
			_timer.addEventListener(TimerEvent.TIMER,onSendTimerPairod);
		}
		
		
		protected function onSendTimerPairod(e:TimerEvent):void{
			//send("timer_out",{});
		}
		
		public function close():void{
			_server.killListener();
			_server.close();
		}
		
		public function send(commond:String, data:*):void {
			trace("command:" + commond +",data:" + data);
			_bytes = new ByteArray();
			_bytes.writeUTF(commond);
			_bytes.writeObject(data);
			trace(_bytes.length);
			_server.requestData(_bytes);
		}
		
		protected function createModuls(m:String):IServer{
			
			throw new IllegalOperationError("抽象方法必须在子类中重写");
			return null;
		}
		
		
		
	}
}