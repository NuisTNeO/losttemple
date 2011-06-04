package com.mankp.utils.loader
{
	import com.mankp.utils.loader.constants.LoadAssetType;
	import com.mankp.utils.loader.constants.LoaderMode;
	import com.mankp.utils.loader.events.AssetsLoaderProgressEvent;
	import com.mankp.utils.loader.events.LoaderAssetEvent;
	import com.mankp.utils.loader.events.LoaderEvent;
	import com.mankp.utils.loader.events.LoaderProgressEvent;
	
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	import flash.sampler.pauseSampling;
	
	public class AssetQueuedLoader extends EventDispatcher implements IEventDispatcher
	{
		use namespace asl_internal;
		
		
		private static var _cacheBreakEnabled:Boolean;
		
		private static var _version:String;
		
		private var _mode:String;
		
		private var _assetsToLoad:Array;
		private var _assetsOpened:Array;
		private var _assetsComplete:Array;
		private var _assetsFailed:Array;
		private var _assetsPaused:Array;
		
		private var _completed:Boolean;
		private var _successful:Boolean;
		private var _isPaused:Boolean=false;
		private var _loadStarted:Boolean = false;
		
        private var _i:int;
		private var _len:int;
		
		
		public function AssetQueuedLoader(mode:String = LoaderMode.BATCH_MODE)
		{
			_mode = mode;
			
			_assetsComplete = new Array();
			_assetsFailed = new Array();
			_assetsOpened = new Array();
			_assetsPaused = new Array();
			_assetsToLoad = new Array();
			
			_successful = true;
			_completed = false;
			
		}
		
		
		public static function enableCacheBreaker(ver:String):void{
			_version = ver;
			_cacheBreakEnabled = true;
		}
		
		public static function disableCacheBreaker():void{
			_cacheBreakEnabled = false;
		}

		public function get isPaused():Boolean
		{
			return _isPaused;
		}

		public function get mode():String
		{
			return _mode;
		}

		public function set mode(value:String):void
		{
			_mode = value;
		}

		public function get assetsComplete():Array
		{
			return _assetsComplete;
		}

		public function get assetsFailed():Array
		{
			return _assetsFailed;
		}
		
		public function startQueuedLoad():void{
			
			if(_loadStarted) return;
			
			if(_mode==LoaderMode.BATCH_MODE){
				
				_len = (_assetsToLoad!=null)?_assetsToLoad.length:0;
				for(_i=0;_i<_len;_i++){
					LoaderAsset(_assetsToLoad[_i]).loader();
				}
				_loadStarted = true;
				
			}else if(_mode==LoaderMode.LINEAR_MODE){
				
				if((_assetsToLoad!=null) && (_assetsToLoad.length>0)){
					LoaderAsset(_assetsToLoad[0]).loader();
				}
				_loadStarted = true;
				
			}else if(_mode==LoaderMode.PRIORITY_MODE){
				
				if((_assetsToLoad!=null) && (_assetsToLoad.length>0)){
					_assetsToLoad.sortOn(["priority"],Array.NUMERIC);
					LoaderAsset(_assetsToLoad[0]).loader();
				}
				_loadStarted = true;
			}
			
		}
		
		
		
		public function queueAsset(url:String,dataFormat:String,prioriry:uint=0,bypassCacheBreaker:Boolean=false,context:Object=null):LoaderAsset{
			
			if(_cacheBreakEnabled && !bypassCacheBreaker) url+="?v="+_version;
			
			var loadasset:LoaderAsset;
			
			switch(_mode){
				
				case LoaderMode.FILES_AT_WILL_MODE:
					return loadAsset(url,dataFormat,bypassCacheBreaker,context);
					break;
				case LoaderMode.BATCH_MODE: case LoaderMode.LINEAR_MODE:
					if(_loadStarted){
						return loadAsset(url,dataFormat,bypassCacheBreaker,context);
						
					}else{
						loadasset = new LoaderAsset(url,dataFormat,context,false);
						loadasset.priority = 0;
						_assetsToLoad.push(loadasset);
					}
					break;
				case LoaderMode.PRIORITY_MODE:
					loadasset = new LoaderAsset(url,dataFormat,context,false);
					loadasset.priority=prioriry;
					if(_loadStarted){
						_len = (_assetsToLoad!=null)?_assetsToLoad.length:0;
						var insert:Boolean = false;
						for(_len=0;_i<_len;++_i){
							if(LoaderAsset(_assetsToLoad[_i]).priority>prioriry){
								insert = true;
								_assetsToLoad.splice(_i,0,loadasset);
								break;
							}
							
						}
						
					     if(!insert){
							 _assetsToLoad.push(loadasset);
						 }	
					}else{
						_assetsToLoad.push(loadasset);
					}
					
					break;
				    default:
						trace("-----没有找到载入模式------");
						break;
			}
			
			loadasset.asl_internal::_completeFx = onCompleteCallBack;
			loadasset.asl_internal::_failedFx = onFailedCallBack;
			loadasset.asl_internal::_openedFx = onOpenedCallBack;
			loadasset.asl_internal::_progressFx = onProgress;
			
			return loadasset;
			
		}
		
		
		private function onProgress(e:LoaderProgressEvent):void{
			
			var bt:int=0;
			var bl:int = 0;
			var lass:LoaderAsset = e.data;
			
			if(_mode==LoaderMode.BATCH_MODE){
				
				_len = _assetsComplete.length;
				for(_i=0;_i<_len;++_i){
					lass = _assetsComplete[_i];
					bt+=lass.bytesTotal;
					bl+=lass.bytesLoader;
				}
				
				
				_len = _assetsOpened.length;
				for(_i=0;_i<_len;++_i){
					lass = _assetsOpened[_i];
					bt+=lass.bytesTotal;
					bl+=lass.bytesLoader;
				}
				
			}else{
				lass = _assetsOpened[0];
				bt+=lass.bytesTotal;
				bl+=lass.bytesLoader;
			}
			
			var aprogress:LoaderProgressEvent = new LoaderProgressEvent(LoaderProgressEvent.LOADER_PROGRESS,e.bytesLoaded,e.bytesTotal,lass);
			dispatchEvent(aprogress);
			
			var assetsprogress:AssetsLoaderProgressEvent = new AssetsLoaderProgressEvent(AssetsLoaderProgressEvent.ASSET_PROGRESS);
			assetsprogress.bytesLoaded = bl;
			assetsprogress.bytesTotal = bt;
			assetsprogress.assetsFailed = _assetsFailed.length;
			assetsprogress.assetsTotal = totalAssets;
			assetsprogress.assetsComplete = _assetsComplete.length;
			
			dispatchEvent(assetsprogress);
			
			
			
		}
		
		
		public function get totalAssets():int{
			return _assetsToLoad.length+_assetsOpened.length+_assetsComplete.length+_assetsFailed.length;
		}
		
		
		private function onOpenedCallBack(lass:LoaderAsset):void{
			
			if(!_isPaused){
				removeAsset(_assetsToLoad,lass.url);
				_assetsOpened.push(lass);
			}else{
				pauseAsset(lass);
			}
			
			
		}
		
		private function pauseAsset(lass:LoaderAsset):void{
			lass.pause();
			
			_assetsPaused.push(lass);
		}
		
		private function onFailedCallBack(lass:LoaderAsset):void{
			removeAsset((lass.opened)?_assetsOpened:_assetsToLoad,lass.url);
			_assetsFailed.push(lass);
			
			var fail:LoaderEvent = new LoaderEvent(LoaderEvent.LOADER_COMPLETE,lass);
			dispatchEvent(fail);
			
			checkForAllComplete();
			
		}
		
		
		private function checkForAllComplete():void{
			
			if((!_assetsToLoad.length) && (!_assetsOpened.length)){
				
				var comp:LoaderAssetEvent = new LoaderAssetEvent(LoaderAssetEvent.LOADER_ASSET_COMPLETE,_assetsComplete.concat(),_assetsFailed.concat());
			    comp.success = !_assetsFailed.length;
				dispatchEvent(comp);
			}
			
		}
		
		
		private function onCompleteCallBack(lass:LoaderAsset):void{
			
			removeAsset(((lass.opened)?_assetsOpened:_assetsToLoad),lass.url);
			
			_assetsComplete.push(lass);
			
			var comp:LoaderEvent = new LoaderEvent(LoaderEvent.LOADER_COMPLETE,lass);
			dispatchEvent(comp);
			
			switch(_mode){
				case LoaderMode.LINEAR_MODE: case LoaderMode.PRIORITY_MODE:
					startNextDownload();
					break;
				case LoaderMode.BATCH_MODE:
					checkForAllComplete();
					break;
				default:
					break;
			}
			
			
		}
		
		
		
		private function startNextDownload():void{
			if(_assetsToLoad.length){
				(_assetsToLoad[0] as LoaderAsset).resume();
			}
		}
		
		private function removeAsset(assetList:Array,url:String):Boolean{
			
			var lass:LoaderAsset;
			var success:Boolean = false;
			_len = assetList.length;
			for(_i=0;_i<_len;++_i){
				lass = assetList[_i];
				if(lass.url==url){
					assetList.splice(_i,1);
					success = true;
					break;
				}
			}
			return success;
		}
		
		
		
		public function loadAsset(url:String,dataFormat:String,bypassCacheBreaker:Boolean=false,context:Object=null):LoaderAsset{
			
			var loadasset:LoaderAsset;
			
			if(_mode==LoaderMode.PRIORITY_MODE){
				loadasset = queueAsset(url,dataFormat,100000,bypassCacheBreaker,context);
				startQueuedLoad();
				return loadasset;
			}
			
			if(_cacheBreakEnabled && !bypassCacheBreaker){
				url+="?v="+_version;
			}
			
			
			loadasset = new LoaderAsset(url,dataFormat,context,false);
			loadasset.asl_internal::_completeFx = onCompleteCallBack;
			loadasset.asl_internal::_failedFx = onFailedCallBack;
			loadasset.asl_internal::_openedFx = onOpenedCallBack;
			loadasset.asl_internal::_progressFx = onProgress;
			
			loadasset.loader();
			
			if(_mode!=LoaderMode.FILES_AT_WILL_MODE){
				
				if(_mode==LoaderMode.LINEAR_MODE){
					loadasset.pause();
					if(!_loadStarted){
						loadasset.resume();
						_loadStarted = true;
					}
				}
				
			}
			
			_assetsToLoad.push(loadasset);
			
			return loadasset;
			
		}
		
		public function pause():void{
			
			if(_isPaused) return;
			var l:int = _assetsOpened.length;
			
			for(_i=0;_i<l;++_i){
				pauseAsset(_assetsOpened.shift() as LoaderAsset);
			}
			
			
		}
		public function reset():void{
			pause();
			var l:int = (_assetsPaused!=null)?_assetsPaused.length:0;
			for(_i;_i<l;++_i){
				LoaderAsset(_assetsPaused[_i]).destroy(true);
			}
			
			_assetsComplete.length = _assetsFailed.length = _assetsOpened.length=_assetsPaused.length=_assetsToLoad.length=0;
			_loadStarted = false;
			_successful = true;
			_completed = false;
			
		}
		
		
		
		public function resume():void{
			
			if(!_isPaused) return;
			
			_len = _assetsPaused.length;
			for(_i=0;_i<_len;++_i){
				(_assetsPaused.shift() as LoaderAsset).resume();
			}
			_isPaused = false;
		}
		
		
		public function destroy(all:Boolean=false):void{
			pause();
			destroyList(_assetsComplete,all);
			destroyList(_assetsFailed,all);
			destroyList(_assetsOpened,all);
			destroyList(_assetsPaused,all);
			destroyList(_assetsToLoad,all);
			
			_assetsComplete =_assetsFailed=_assetsOpened=_assetsPaused=_assetsToLoad = null;
			
		}
		
		
		public function destroyList(assetList:Array,all:Boolean=false):void{
			
			if(!assetList)return;
			var lass:LoaderAsset;
			var l:int = assetList.length;
			for(_i=0;_i<l;++_i){
				lass = assetList[_i];
				lass.destroy(all);
			}
			assetList.length = 0;
			assetList=null;
			
		}
		
	}
}