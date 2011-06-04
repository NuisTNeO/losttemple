package com.mankp.utils
{
	import flash.display.Bitmap;
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.MovieClip;
	import flash.display.Shape;

	public class DisplayUtils
	{
		public function DisplayUtils()
		{
		}
		
		
		/**
		 * 销毁显示容器中的显示对象，并释放内存空间 
		 * @param display <coder>DisplayObject</code> to destory
		 * 
		 */		
		public static function removeAllChilder(display:DisplayObject):void{
		    
			reverseWalkDisplayObject(display,function(display:DisplayObject):void{
			
				if(display){
					if(display is MovieClip){
						MovieClip(display).stop();
					}
					if(display is Bitmap && Bitmap(display).bitmapData){
						Bitmap(display).bitmapData.dispose();
					}
					
					if(display is Shape && Shape(display).graphics){
						Shape(display).graphics.clear();
					}
					if(display.parent){
						display.parent.removeChild(display);
					}
				}
			});
		}
		
		public static function reverseWalkDisplayObject(disCon:DisplayObject,caller:Function):void{
			caller(disCon);
			if(disCon is DisplayObjectContainer){
				var len:int = DisplayObjectContainer(disCon).numChildren;
				var child:DisplayObject;
				while(len--){
					child = DisplayObjectContainer(disCon).getChildAt(len);
					reverseWalkDisplayObject(child,caller);
				}
			}
			
		}
		
		
		
	}
}