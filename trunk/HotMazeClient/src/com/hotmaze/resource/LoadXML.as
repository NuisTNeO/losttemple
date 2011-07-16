package com.hotmaze.resource
{
	import com.mankp.utils.loader.constants.LoadAssetType;
	
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.net.URLRequest;

	public class LoadXML extends Sprite
	{
		private var xmlPath:String = new String();
		
		private var loader:URLLoader = new URLLoader();
		
//		private var loadList:Vector = new Vector();
		
		public function LoadXML(string:String)
		{
			xmlPath = string;
			init();
		}
		
		private function init():void {
			
			loader.dataFormat = LoadAssetType.TEXT;
			loader.addEventListener(Event.COMPLETE, handleComplete);
			loader.load(new URLRequest(xmlPath));
		}
		
		public function handleComplete(event:Event):void{
		
		}
	}
}