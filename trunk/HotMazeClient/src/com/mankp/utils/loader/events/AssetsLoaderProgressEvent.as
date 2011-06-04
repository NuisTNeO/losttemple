package com.mankp.utils.loader.events
{
	import flash.events.Event;
	
	public class AssetsLoaderProgressEvent extends Event
	{
		
		public static const ASSET_PROGRESS:String = "assetprogress";
		
		public var bytesLoaded:int;
		public var bytesTotal:int;
		public var assetsTotal:int;
		
		public var assetsComplete:int;
		public var assetsFailed:int;
		
		
		public function AssetsLoaderProgressEvent(type:String)
		{
			super(type);
		}
	}
}