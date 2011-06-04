package com.mankp.utils.loader.events
{
	import flash.events.Event;
	
	public class LoaderAssetEvent extends Event
	{
		
		public static const LOADER_ASSET_COMPLETE:String="loaderAssetComplete";
		/** 载入所有资源完成*/
		public static const LOADER_ALL_COMPLETE:String="LoaderAllComplete";
		
		public var complete:Array;
		public var failed:Array;
	    public var success:Boolean;
		
		public function LoaderAssetEvent(type:String,comp:Array=null,fial:Array=null)
		{
				complete = (comp==null)?new Array():comp;
				failed = (fial==null)?new Array():fial;
		
			
			super(type,false,false);
		}
	}
}