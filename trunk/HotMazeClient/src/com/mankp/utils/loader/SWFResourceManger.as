package com.mankp.utils.loader
{
	import com.mankp.utils.loader.constants.LoadAssetType;
	
	import flash.utils.Dictionary;

	public class SWFResourceManger
	{
		private static var _assetsDir:Dictionary = new Dictionary();
		private var _swfname:String;
		
		public function SWFResourceManger(name:String)
		{
		}
        
		
		public static function addAsset(resourceManger:ResourceManger):void{
			
			var len:int = resourceManger.loaderAsset.assetsComplete.length;
			var lass:LoaderAsset;
			var matchSwf:Array;
			var name:String;
			while(len--){
				
				lass = resourceManger.loaderAsset.assetsComplete[len];
				matchSwf=lass.url.match(RegExp(/[A-Za-z0-9]+.swf$/));
			    name=String(matchSwf[0]).replace(RegExp(/.swf$/),"");
				_assetsDir[name]=lass;
			}
		}
		
		
		public static function getClass(name:String,exampleName:String):Class{
			var lass:LoaderAsset = _assetsDir[name] as LoaderAsset;
			if(lass==null && lass.load==null) return null;
			return lass.load.contentLoaderInfo.applicationDomain.getDefinition(exampleName) as Class;
		}
		
		
		
			
	}
}