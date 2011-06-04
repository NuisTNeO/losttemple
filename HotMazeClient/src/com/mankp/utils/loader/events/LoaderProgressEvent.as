package com.mankp.utils.loader.events 
{
	import com.mankp.utils.loader.LoaderAsset;
	import flash.events.Event;
	
	/**
	 * ...
	 * @author leonhe
	 */
	public class LoaderProgressEvent extends Event 
	{
		
		private var _data:LoaderAsset;
		
		private var _bytesLoaded:int;
		private var _bytesTotal:int;
		
		
		public static const LOADER_PROGRESS:String = "loaderProgress";
		
		public function LoaderProgressEvent(type:String,bytesloaded:int,bytesTotal:int,loader:LoaderAsset ,bubbles:Boolean = false, cancelable:Boolean = false) 
		{
			_bytesLoaded = bytesloaded;
			_bytesTotal = bytesTotal;
			_data = loader;
			super(type, bubbles, cancelable);
			
		}
		
		public function get data():LoaderAsset 
		{
			return _data;
		}
		
		public function get bytesLoaded():int 
		{
			return _bytesLoaded;
		}
		
		public function get bytesTotal():int 
		{
			return _bytesTotal;
		}
		
	}

}