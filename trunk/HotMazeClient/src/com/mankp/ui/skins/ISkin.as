package com.mankp.ui.skins
{
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.Sprite;

	public interface ISkin
	{
		function initilaze():void;
		
		function painting():void;
		
		function getView():Sprite;
		
		function setSize(w:Number,h:Number):void;
	}
}