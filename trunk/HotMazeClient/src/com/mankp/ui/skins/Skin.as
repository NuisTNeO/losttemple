package com.mankp.ui.skins
{
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.Sprite;
	
	public class Skin extends Sprite implements ISkin
	{
		private var _data:*;
		public function Skin()
		{
			this.initilaze();
		}
		
		public function initilaze():void
		{
		   painting();
		}
		
		public function painting():void
		{
			
		}
		
		public function getView():Sprite
		{
			return null;
		}
		
		public function setSize(w:Number, h:Number):void
		{
	         
		}
		
		public function get data():*{
			return _data;
		}

		public function set data(value:*):void
		{
			_data = value;
		}
		
		
	}
}