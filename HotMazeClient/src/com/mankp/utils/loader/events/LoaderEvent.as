package com.mankp.utils.loader.events 
{
	import com.mankp.utils.loader.LoaderAsset;
	import flash.events.Event;
	
	/**
	 * ...
	 * @author leonhe
	 */
	public class LoaderEvent extends Event 
	{
		/** 载入资源完成*/
		public static const LOADER_COMPLETE:String = "LoaderComplete";
		/** 载入资源失败*/
		public static const LOADER_FAILED:String = "loaderFailed";
		
		private var _data:LoaderAsset;
		
		private var _success:Boolean;
		
		
		public function LoaderEvent(type:String,data:LoaderAsset) 
		{
			_data = data;
			_success = _data.success;
			
			super(type,false,false);
		}
		
		public function get data():LoaderAsset 
		{
			return _data;
		}
		
		public function get success():Boolean 
		{
			return _success;
		}
		
	}

}