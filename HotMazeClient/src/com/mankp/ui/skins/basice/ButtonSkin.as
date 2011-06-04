package com.mankp.ui.skins.basice
{
	import com.mankp.ui.skins.Skin;
	
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.MovieClip;
	import flash.display.Sprite;
	import flash.text.TextField;
	
	public class ButtonSkin extends Skin
	{
		//[Embed(source = "../../../../../../assets/swf/UI.swf", symbol = "ButtonSkin")]
		public var Resource:Class;
		private var _rs:Sprite;
		private var _lable:String= "lable";
		private var _background:String = "bg";
		
		public function ButtonSkin()
		{
			super();
		}
		
		
		
		
		
		
		override public function painting():void
		{
			//_rs = new Resource() as Sprite;
		}
		
		
		
		
		override public function getView():Sprite
		{
			return _rs;
		}

		public function get lable():String
		{
			return _lable;
		}

		public function set lable(value:String):void
		{
			_lable = value;
		}

		public function get background():String
		{
			return _background;
		}

		public function set background(value:String):void
		{
			_background = value;
		}
		
		
		
		
		
	}
}