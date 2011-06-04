package com.mankp.utils.loader 
{
	import com.mankp.utils.loader.constants.LoadAssetType;
	import com.mankp.utils.loader.events.LoaderEvent;
	import com.mankp.utils.loader.events.LoaderProgressEvent;
	
	import flash.display.Loader;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.HTTPStatusEvent;
	import flash.events.IEventDispatcher;
	import flash.events.IOErrorEvent;
	import flash.events.ProgressEvent;
	import flash.events.SecurityErrorEvent;
	import flash.media.Sound;
	import flash.media.SoundLoaderContext;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.system.ApplicationDomain;
	import flash.system.LoaderContext;
	
	/**
	 * 载入资源类
	 * @author leonhe
	 */
	public class LoaderAsset extends EventDispatcher implements IEventDispatcher 
	{
		
		use namespace asl_internal;
		
		public function get load():Loader
		{
			return _load;
		}

		asl_internal var _completeFx:Function;
		asl_internal var _failedFx:Function;
		asl_internal var _openedFx:Function;
		asl_internal var _progressFx:Function;
		
		private var _priority : uint ;
		private var _url:String;
		private var _type:String;
		
		private var _load:Loader;
		private var _urlload:URLLoader;
		private var _data:Object;
		private var _loadingContext:Object;
		
		private var _errorStrings:Vector.<String>
		private var _paused:Boolean;
		
		private var _isLoaded:Boolean;
		
		private var _sound:Sound;
		
		private var _isFailure:Boolean;
		private var _isOpened:Boolean;
		
		private var _bytesLoader:int;
		private var _bytesTotal:int;
		
		private var _httpStatusCode:int;
		private var _receivedHTTPStatusEvent:Boolean;
		
		/**
		 *  
		 * @param	url: 加载文件地址
		 * @param	type: 加载文件的数据格式
		 * @param	loadingContext：加载域
		 * @param	autoStart：自动开始加载，默认为：true
		 */
		public function LoaderAsset(url:String,type:String,loadingContext:Object = null,autoStart:Boolean = true) 
		{
		   	_url = url;
			_type = type;
			
			_loadingContext = loadingContext;
			
			_errorStrings = new Vector.<String>();
			
			_isOpened = _isLoaded = _isFailure = _receivedHTTPStatusEvent = false;
			_paused = !autoStart;
			
			if (autoStart) setAsset(url, type, loadingContext);
		}
		
		
		override public function addEventListener(type:String, listener:Function, useCapture:Boolean = false, priority:int = 0, useWeakReference:Boolean = false):void 
		{
			if (_completeFx != null || _failedFx != null || _openedFx != null || _progressFx != null) {
				return;
			}
			super.addEventListener(type, listener, useCapture, priority, useWeakReference);
		}		
		
		
		public function loader(force:Boolean = false):void {
			if (!_isLoaded || force) setAsset(_url, _type, _loadingContext); 
		}
		
	    /**
	     * 
		 * 
	     * @param	url 加载文件的地址
	     * @param	type 加载文件的数据类型
	     * @param	loadingContext 加载域
	     */
		public function setAsset(url:String, type:String, loadingContext:Object):void {
			_isLoaded = true;
			
			var urlRequest:URLRequest = new  URLRequest(url);
			
			switch(type) {
			       case LoadAssetType.BINARY: case LoadAssetType.TEXT: case LoadAssetType.VARIABLES: case LoadAssetType.XML:
					   _urlload = new URLLoader();
					   
					   _urlload.addEventListener(Event.COMPLETE, completeHandler);
					   _urlload.addEventListener(ProgressEvent.PROGRESS, progressHandler);
					   _urlload.addEventListener(Event.OPEN, openHandler);
					   _urlload.addEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatusHandler);
					   _urlload.addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
					   _urlload.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
					   
					   _urlload.dataFormat = type;
					   _urlload.load(urlRequest);
					   break;
					   
					case LoadAssetType.IMAGES: case LoadAssetType.SWF:
						var context:LoaderContext;
						
						_load = new Loader();
						_load.contentLoaderInfo.addEventListener(Event.COMPLETE, completeHandler);
						_load.contentLoaderInfo.addEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatusHandler);
						_load.contentLoaderInfo.addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
						_load.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
						_load.contentLoaderInfo.addEventListener(Event.OPEN, openHandler);
						_load.contentLoaderInfo.addEventListener(ProgressEvent.PROGRESS, progressHandler);
						
						context = LoaderContext((loadingContext as LoaderContext) == null?new LoaderContext(false, new ApplicationDomain(ApplicationDomain.currentDomain)):loadingContext);
						_load.load(urlRequest, context);
				   break;
			   case LoadAssetType.SOUND:
				    var sContext:SoundLoaderContext
					_sound = new Sound();
					_sound.addEventListener(Event.COMPLETE, completeHandler);
					_sound.addEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatusHandler);
					_sound.addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
					_sound.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
					_sound.addEventListener(Event.OPEN, openHandler);
					_sound.addEventListener(ProgressEvent.PROGRESS, progressHandler);
					
					sContext = SoundLoaderContext(((_loadingContext as SoundLoaderContext) == null)?new SoundLoaderContext(1000, false):loadingContext);
					_sound.load(urlRequest, sContext);
					break;
			
				default:
					 throw new Error('资源类型不存在：'+_type);
					 break;
			}
			
			_paused = false;
			
			
		}
		
		
		public function pause():void {
			if (!(_type == LoadAssetType.TEXT || _type == LoadAssetType.BINARY || _type == LoadAssetType.VARIABLES || _type == LoadAssetType.IMAGES || _type == LoadAssetType.SWF || _type == LoadAssetType.SOUND)) {
				throw new Error('找不到加载资源类型:' + _type);
			}
			
			((_type == LoadAssetType.TEXT || _type == LoadAssetType.BINARY || _type == LoadAssetType.VARIABLES)?_urlload:((_type == LoadAssetType.SWF || _type == LoadAssetType.IMAGES)?_load:_sound)).close();
			
			_paused = true;
		}
		
		
		
		public function resume():void {
			if (!_paused) return;
			setAsset(_url, _type, _loadingContext);
		}
		
		
		private function ioErrorHandler(e:IOErrorEvent):void 
		{
			loadFail(e);
		}
		
		private function securityErrorHandler(e:SecurityErrorEvent):void 
		{
			loadFail(e);
		}
		
		
		private function loadFail(e:Event):void {
			
			errorStrings.push(e.toString());
			
			if (!_isFailure) {
				_isFailure = true;
				killListeners();
				_isLoaded = false;
				if (_failedFx != null) {
					_failedFx(this);
				}else {
					dispatchEvent(new LoaderEvent(LoaderEvent.LOADER_FAILED, this));
				}
			}
		}
		
		
		private function httpStatusHandler(e:HTTPStatusEvent):void 
		{
			_httpStatusCode = e.status;
			
			_receivedHTTPStatusEvent = true;
		}
		
		private function openHandler(e:Event):void 
		{
			_isOpened = true;
			
			if (_openedFx != null) {
				_openedFx(this);
			}
		}
		
		
		
		private function progressHandler(e:ProgressEvent):void 
		{
			_bytesLoader = e.bytesLoaded;
			_bytesTotal = e.bytesTotal;
			
			
			var ape:LoaderProgressEvent = new LoaderProgressEvent(LoaderProgressEvent.LOADER_PROGRESS, _bytesLoader, _bytesTotal, this);
		
			if (_progressFx != null) {
				_progressFx(ape);
			}else {
				dispatchEvent(ape);
			}
		}
		
		private function completeHandler(e:Event):void 
		{
			if (!(_type == LoadAssetType.TEXT || _type == LoadAssetType.BINARY || _type == LoadAssetType.VARIABLES || _type == LoadAssetType.IMAGES || _type == LoadAssetType.SWF || _type == LoadAssetType.SOUND || _type==LoadAssetType.XML)) {
				throw new Error('找不到加载资源类型:' + _type);
			}
			killListeners();
			_isLoaded = false;
			
			
			_data = (((_type == LoadAssetType.TEXT || _type == LoadAssetType.BINARY || _type == LoadAssetType.VARIABLES || _type==LoadAssetType.XML))?_urlload.data:(_type == LoadAssetType.IMAGES || _type == LoadAssetType.SWF)?_load.content:_sound) as Object;
			_data = (_type==LoadAssetType.XML)?new XML(_data):_data;
			if (_completeFx != null) {
				_completeFx(this);
			}else {
				dispatchEvent(new LoaderEvent(LoaderEvent.LOADER_COMPLETE, this));
			}
			
		}
		
		
		/**
		 * 释放监听事件
		 */
		public function killListeners():void {
			
			switch(_type) {
				case LoadAssetType.TEXT: case LoadAssetType.BINARY: case LoadAssetType.VARIABLES:
					  if (_urlload != null) {
						  _urlload.removeEventListener(Event.COMPLETE, completeHandler);
					   _urlload.removeEventListener(ProgressEvent.PROGRESS, progressHandler);
					   _urlload.removeEventListener(Event.OPEN, openHandler);
					   _urlload.removeEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatusHandler);
					   _urlload.removeEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
					   _urlload.removeEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
						  
					  }
					
					break;
				case LoadAssetType.IMAGES: case LoadAssetType.SWF:
					if (_load != null && _load.contentLoaderInfo != null) {
						_load.contentLoaderInfo.removeEventListener(Event.COMPLETE, completeHandler);
						_load.contentLoaderInfo.removeEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatusHandler);
						_load.contentLoaderInfo.removeEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
						_load.contentLoaderInfo.removeEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
						_load.contentLoaderInfo.removeEventListener(Event.OPEN, openHandler);
						_load.contentLoaderInfo.removeEventListener(ProgressEvent.PROGRESS, progressHandler);
					}
					break;
				case LoadAssetType.SOUND:
					if (_sound != null) {
					_sound.removeEventListener(Event.COMPLETE, completeHandler);
					_sound.removeEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatusHandler);
					_sound.removeEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
					_sound.removeEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
					_sound.removeEventListener(Event.OPEN, openHandler);
					_sound.removeEventListener(ProgressEvent.PROGRESS, progressHandler);
					}
					break;
			}
			
			
		}
		
		/**
		 * 释放加载变量
		 * @param	all
		 */
		public function destroy(all:Boolean = false):void {
			pause();
			
			killListeners();
			if (all) {
				
				if (_load) {
					_load.close();
					_load.unload();
					_load = null;
				}
				if (_sound) {
					_sound.close();
					_sound = null;
				}
			}
			
			if (_errorStrings != null) {
				_errorStrings.length = 0;
				_errorStrings = null;
			}
		}
		
		/**
		 * 是否加载成功
		 */
		public function get success():Boolean {
			return !_isFailure;
		}
		
		public function get errorStrings():Vector.<String> 
		{
			return _errorStrings;
		}

		public function get priority():uint
		{
			return _priority;
		}

		public function set priority(value:uint):void
		{
			_priority = value;
		}

		public function get url():String
		{
			return _url;
		}

		public function get opened():Boolean
		{
			return _isOpened;
		}

		public function get bytesLoader():int
		{
			return _bytesLoader;
		}

		public function get bytesTotal():int
		{
			return _bytesTotal;
		}

		public function get type():String
		{
			return _type;
		}

		public function get data():Object
		{
			return _data;
		}
		
		
	}

}