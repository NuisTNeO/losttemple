package
{
	import com.mankp.magicCard.core.MagicCardCore;
	import com.mankp.socket.SocketFactory;
	import com.mankp.socket.decode.SocketDecode;
	import com.mankp.utils.loader.LoaderAsset;
	import com.mankp.utils.loader.ResourceManger;
	import com.mankp.utils.loader.constants.LoadAssetType;
	import com.mankp.utils.loader.events.LoaderAssetEvent;
	
	import flash.display.Sprite;
	import flash.events.Event;
	
	import mx.resources.ResourceManager;

	[SWF(width="1024",height="800")]
	public class MagicCard extends Sprite
	{
		private var _loadManage:ResourceManger;
		public function MagicCard()
		{
			if (stage) init();
			else addEventListener(Event.ADDED_TO_STAGE, init);
		}
		
		private function init(e:Event = null):void 
		{
			removeEventListener(Event.ADDED_TO_STAGE, init);
			
			/*载入配置文件*/
			_loadManage = ResourceManger.getManager("configs");
			_loadManage.loaderAsset.loadAsset("assets/xml/configs.xml",LoadAssetType.XML);
		    _loadManage.loaderCompletFx = loadConfigFinish; 
		}
		/**
		 * 载入配置文件完成 
		 * @param e
		 * 
		 */		
		private function loadConfigFinish(e:LoaderAssetEvent):void{
			
			if(e.success){
				var serverxml:LoaderAsset = e.complete[0] as LoaderAsset;
				var xmlconfig:XML=serverxml.data as XML;
			/*	var len:int = xmlconfig..appendChild("resources").appendChild("resource").length();
				
				var resource:* =  xmlconfig..appendChild("resources");
				_loadManage = ResourceManger.getManager("resources");
				while(len--){
					trace(resource[len])
					//_loadManage.loaderAsset.loadAsset(
				}*/
				
				connectSocket(xmlconfig);
				
			}else{
			  	
			   trace("载入配置文件失败..");
			}
		}
		
		
		public function connectSocket(xml:XML):void{
			var sockserver:SocketFactory = new SocketFactory();
			var decode:SocketDecode = new SocketDecode();
		
			sockserver.createSocketServer(SocketFactory.SOCKET_BASE,xml..@host,xml..@port,decode);
			MagicCardCore.socket = sockserver;
			sockserver.send("test","asdasddas");
		}
		
		
		
		
	}
}