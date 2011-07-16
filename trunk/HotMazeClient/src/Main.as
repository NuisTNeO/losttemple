package 
{
	import com.mankp.magicCard.net.decode.TestDecode;
	import com.mankp.socket.SocketFactory;
	import com.mankp.socket.decode.SocketDecode;
	import com.mankp.socket.server.SocketBase;
	import com.mankp.utils.loader.LoaderAsset;
	import com.mankp.utils.loader.ResourceManger;
	import com.mankp.utils.loader.SWFResourceManger;
	import com.mankp.utils.loader.constants.LoadAssetType;
	import com.mankp.utils.loader.events.AssetsLoaderProgressEvent;
	import com.mankp.utils.loader.events.LoaderAssetEvent;
	
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.events.ProgressEvent;
	import flash.net.Socket;
	import flash.system.System;
	import flash.text.TextField;
	import flash.utils.ByteArray;
	import flash.utils.Dictionary;
	import flash.utils.getTimer;
	
	import mx.resources.ResourceManager;
	/**
	 * ...
	 * @author LeonHe
	 */
	public class Main extends Sprite 
	{
		
		//[Embed(source = "assets/swf/UI.swf", symbol = "ButtonSkin")]
		public var ButtonSkin:Class;
		
		public function Main():void 
		{
			System.useCodePage=true;
			if (stage) init();
			else addEventListener(Event.ADDED_TO_STAGE, init);
		}
		
		public var socket:Socket;
		public var disPlayManger:ResourceManager;
		
		private function onComplete(e:LoaderAssetEvent):void{
			if(e.success){
				
				SWFResourceManger.addAsset(loadmanger);
				var btn:Class=SWFResourceManger.getClass("UI","ButtonSkin") as Class;
			    
				addChild(new btn() as Sprite );
				
				btn = SWFResourceManger.getClass("UI","BasicPanel") as Class;
				var sp:Sprite= new btn() as Sprite;
				sp.x = 100;
				addChild(sp);
			}else{
			   for(var i:int=0;i<e.failed.length;i++){
				   trace((e.failed[i] as LoaderAsset).errorStrings)
			   }
			}
			
		}
		
		private function onProgress(e:AssetsLoaderProgressEvent):void{
			trace((e.bytesLoaded/e.bytesTotal)*100);
		}
		
		
		private function onFinishConfigs(e:LoaderAssetEvent):void{
			var loader:LoaderAsset = e.complete[0] as LoaderAsset;
			var data:XML=loader.data as XML;
			trace(data.child("server").attribute("host"));
		}
		
		private var loadmanger:ResourceManger; 
		
		
		
		private function init(e:Event = null):void 
		{
			removeEventListener(Event.ADDED_TO_STAGE, init);
			
			/*loadmanger = ResourceManger.getManager("serverConfig");
			loadmanger.loaderAsset.loadAsset("../assets/configs/server.xml",LoadAssetType.XML);
			loadmanger.loaderCompletFx = onFinishConfigs; */
			
			//AssetQueuedLoader.enableCacheBreaker("0.1.0");
			
			loadmanger =ResourceManger.getManager("mankp");
			loadmanger.loaderAsset.loadAsset("assets/swf/basic.swf",LoadAssetType.SWF);
			loadmanger.loaderAsset.loadAsset("assets/swf/UI.swf",LoadAssetType.SWF);
			loadmanger.loaderCompletFx = onComplete;
			loadmanger.loaderAsset.addEventListener(AssetsLoaderProgressEvent.ASSET_PROGRESS,onProgress);
			
			
			
			//loadmanger.loaderAsset.addEventListener(LoaderAssetEvent.LOADER_ASSET_COMPLETE,onComplete);
			//loadmanger.loaderAsset.addEventListener(LoaderEvent.LOADER_FAILED,onLoaderFaile);
			
			
			
			/*var serverObj:ServerObject = new ServerObject();
			serverObj.setString("test","test");
			trace(serverObj);*/
			
			
			
			/*
			var screen:LoginScreen = new LoginScreen();
			addChild(screen);*/
			
			
//			var btn:Button = new Button();
//			btn.x = 100;
//			btn.y = 100;
//			btn.enabled = false;
//			//btn.setLable("Test");
//			addChild(btn);
//			
//			btn = new  Button();
//			btn.y = 150;
//			btn.addEventListener(MouseEvent.CLICK,onClick);
//			
//			btn.lable = "Xiao Fei";
//			btn.width = stage.width;
//		   addChild(btn);
//			
//			
//			socket = new Socket();
//			socket.addEventListener(Event.CONNECT,onConnect);
//			socket.addEventListener(ProgressEvent.SOCKET_DATA,onData);
//			
//			//socket.connect("116.232.46.194",8888);
//			
//			txt = new TextField();
//			txt.autoSize = TextFieldAutoSize.LEFT;
//			txt.height = 20;
//			txt.text="Text";
//			txt.border=true;
//			txt.type=TextFieldType.INPUT;
//			
//			addChild(txt);
//			
//			msg = new TextField();
//			msg.x = 300;
//			addChild(msg);
			
		   var socketSRV:SocketFactory = new SocketFactory();
		   var decode:SocketDecode = new SocketDecode();
		   decode.addDecode(TestDecode.TYPE,new TestDecode);
		   socketSRV.createSocketServer(SocketFactory.SOCKET_BASE);
		   socketSRV.connect("192.168.1.100",8889,decode);
		   socketSRV.send("test",{data:"你好潘亮"});
		   //socketSRV.close();
		  /* var v:Vector.<Function> = new Vector.<Function>();
		   v.push(onData);
		   //trace(this.hasOwnProperty("aonData"));
		   var data:Function = onData()
		   trace(v.indexOf(onData));*/
			
		}
		private var msg:TextField;
		private var txt:TextField;
		
		public function onData(e:ProgressEvent):void{
			while(socket.bytesAvailable){
				//socket.readObject();
				msg.appendText(socket.readMultiByte(socket.bytesAvailable,"UTF-8"));
			}
		}
		
		private function onClick(e:MouseEvent):void{
			var bytes:ByteArray = new ByteArray();
			bytes.writeUTFBytes(txt.text+"\n");
			//bytes.writeObject({a:1,b:2});
			//bytes.writeObject([1,2,3,4,5]);
			socket.writeBytes(bytes);
			socket.flush();
		}
		
		private function onConnect(e:Event):void{
			trace("连接中....");
		}
		
	}
	
}