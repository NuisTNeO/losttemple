package com.hotmaze.resource
{
	import com.mankp.utils.loader.constants.LoadAssetType;
	
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.net.URLRequest;

	public class LoadXML extends Sprite
	{
		
		private var loader:URLLoader = new URLLoader();
		
//		private var loadList:Vector = new Vector();
		
		public function LoadXML()
		{
			init();
		}
		
		private function init():void {
			
			loader.dataFormat = LoadAssetType.TEXT;
			loader.addEventListener(Event.COMPLETE, handleComplete);
			loader.load(new URLRequest("../assets/map/map_list.xml"));
		}
		
		private function handleComplete(event:Event):void {
			
			try{
				var xml:XML = new XML(event.target.data);
				trace(xml.toString());
				var elements:XMLList = xml.elements();
				
				for each(var e:XML in elements) {
					trace(e.@index);
					trace(e.@map_name);
				}
			} catch(e:TypeError) {
				trace("Could not parse text into XML");
				trace(e.message);
			} finally {
				
			}
			
		}
	}
}