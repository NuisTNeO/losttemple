package com.mankp.utils.loader 
{
	import com.mankp.utils.loader.constants.LoaderMode;
	import com.mankp.utils.loader.events.LoaderAssetEvent;
	import com.mankp.utils.loader.events.LoaderEvent;
	
	import flash.events.EventDispatcher;
	import flash.utils.Dictionary;

	/**
	 * 资源管理类
	 * @author leon
	 */
	public class ResourceManger
	{
		private static var MANAGERS:Dictionary = new Dictionary();
		private var _name:String;
		private var _assets:Dictionary;
		private var _loaderAsset:AssetQueuedLoader;
		private var _loaderCompletFx:Function =null;
		
		
		public function ResourceManger(name:String,addGloblly:Boolean=true) 
		{
			_name = name;
			_assets = new Dictionary();
			_loaderAsset = new AssetQueuedLoader(LoaderMode.BATCH_MODE);
			_loaderAsset.addEventListener(LoaderAssetEvent.LOADER_ASSET_COMPLETE,onLoaderComplete,false,-1,true);
			if (addGloblly) {
				ResourceManger.addManager(this);
			}
			
			
		}
		
		
		private function onLoaderComplete(e:LoaderAssetEvent):void{
			var loadedAsset:Array = _loaderAsset.assetsComplete;
			var lass:LoaderAsset;
			var len:int = loadedAsset.length;
			for(var i:int=0;i<len;++i){
				lass = loadedAsset[i];
				if(_assets[lass.type]==null){
					_assets[lass.type] = new Object();
				}
				
				_assets[lass.type][lass.url] = lass.data;
			}
			
			if(_loaderCompletFx!=null) _loaderCompletFx(e)
			
			/*_loaderAsset = new AssetQueuedLoader(LoaderMode.BATCH_MODE);
			_loaderAsset.addEventListener(LoaderAssetEvent.LOADER_ASSET_COMPLETE,onLoaderComplete,false,-1,true);*/
		}
		
		
		/**
		 * 添加资源管理器
		 * @param	manger资源管理器
		 */
		public static function addManager(manger:ResourceManger):void {
			if (MANAGERS[manger.name] != null) {
				throw new Error("ResourceManger"+manger.name+"已经存在，请使用ResourceManger.getManger(name) 来创建资源管理器");
			}else {
				MANAGERS[manger.name] = manger;
			}
		}
		
		/**
		 * 清理缓存资源
		 */
		public function clearResource():void {
			_assets = new Dictionary();
		}
		
		/**
		 * 获取载入资源
		 * @param	url
		 * @param	dataFormat
		 * @return
		 */
		public function getAsset(url:String, dataFormat:String):Object {
			if (_assets[dataFormat] != null) return _assets[dataFormat][url];
			return null;
		}
		
		/**
		 * 根据资源管理器名称获取该资源管理器，如果资源管理器不存在则新实例一个资源管理器
		 * @param	name
		 * @return
		 */
		public static function getManager(name:String):ResourceManger {
			if (MANAGERS[name] != null) {
				return MANAGERS[name];
			}
			return new ResourceManger(name); 
		}
		/**
		 * 资源管理器名称
		 */
		public function get name():String 
		{
			return _name;
		}

		public function get loaderAsset():AssetQueuedLoader
		{
			return _loaderAsset;
		}

		/** 载入所有资源完成后执行方法*/
		public function get loaderCompletFx():Function
		{
			return _loaderCompletFx;
		}

		/**
		 * @private
		 */
		public function set loaderCompletFx(value:Function):void
		{
			_loaderCompletFx = value;
		}
		
		
	
		
		
		
		
		
	}

}