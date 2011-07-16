package com.hotmaze.resource
{
	import flashx.textLayout.elements.OverflowPolicy;
	import flash.events.Event;

	public class MapXmlLoader extends LoadXML
	{
		public function MapXmlLoader(string:String)
		{
			//TODO: implement function
			super(string);
		}
		
		public override  function handleComplete(event:Event):void{
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