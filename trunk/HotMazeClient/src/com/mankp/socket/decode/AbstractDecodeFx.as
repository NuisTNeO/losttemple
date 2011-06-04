package com.mankp.socket.decode
{
	
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.events.ProgressEvent;
	import flash.events.SecurityErrorEvent;
	import flash.system.ApplicationDomain;
	import flash.utils.Dictionary;
	import flash.utils.describeType;
	import flash.utils.getDefinitionByName;
	
	public class AbstractDecodeFx implements IDecode
	{
		
		protected var _decodeVc:Dictionary = new Dictionary();
		
		
		public function addDecode(type:Array,decode:Object):void{
			
			var len:int = type.length;
			var asxml:XML;
			while(len--){
				if(decode.hasOwnProperty(type[len])){
					
					asxml=describeType(decode);
					_decodeVc[type[len]] = asxml.appendChild("type").attribute("name");
					
				}else{
					trace(decode+"不存在"+type[len]+"方法");
				}
			}
		}
		
		/**
		 * 反射方法接受服务器返回的参数然后设置给对应的方法 
		 * @param command
		 * @param data
		 * 
		 */		
		protected function reponets(command:String,data:*):void{
			var Cls:Class = getDefinitionByName(_decodeVc[command]) as Class;
			var decode:Object = new Cls();
			decode[command](data);
		}
		
		public function onConnect(e:Event):void
		{
		}
		
		public function onClose(e:Event):void
		{
			trace("服务器断开连接.....")
		}
		
		public function onRecives(e:ProgressEvent):void
		{
		   	
		}
		
		public function onIOError(e:IOErrorEvent):void
		{
			trace(e.text);
		}
		
		public function onSecurity(e:SecurityErrorEvent):void
		{
		}
	}
}