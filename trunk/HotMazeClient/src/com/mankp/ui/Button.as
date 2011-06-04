package com.mankp.ui 
{
	import com.mankp.ui.skins.basice.ButtonSkin;
	
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.MovieClip;
	import flash.display.SimpleButton;
	import flash.display.Sprite;
	import flash.text.TextField;

	/**
	 * 按钮类，继承SimpleButton
	 * @author leon
	 * 
	 */	
	public class Button extends SimpleButton
	{
		
		
		private var _skin:DisplayObjectContainer;
		private var _upskin:Sprite;
		private var _overskin:Sprite;
		private var _downskin:Sprite;
		private var _enablemc:Sprite;
		private var _lable:String="lable";
		private var _background:String = "bg";
		
		public function Button(skin:DisplayObjectContainer=null) 
		{
			if(skin==null){
				var skincls:ButtonSkin = new ButtonSkin();
			    skin = skincls.getView();
			}
			_skin = skin;
			this.bindComponent();
		}
		
		public function bindComponent():void
		{
			upState = getStateSkin("up");
			overState = getStateSkin("over");
			downState = getStateSkin("down");
			hitTestState = getStateSkin("over");
			_enablemc = getStateSkin("enablemc");
		}
		
		public function getStateSkin(state:String):MovieClip{
			var mc:MovieClip = _skin[state] as MovieClip;
			mc.x = mc.y = 0;
			return mc;
		}
		
		
		
		/**
		 * 设置按钮文字 
		 * @param value
		 * 
		 */		
		public function set lable(value:String):void {
			chooseChild(_lable,"text",value);
	   }
		
		
		override public function set enabled(value:Boolean):void
		{
			if(!value){
				upState=_enablemc;
				
			}else{
				upState=getStateSkin("up");
			}
			super.enabled = value;
		}
		
		override public function set width(value:Number):void
		{
			
			chooseChild(_lable,"width",value);
			chooseChild(_background,"width",value);
			super.width = value;
		}
		
		override public function set height(value:Number):void
		{
			chooseChild(_lable,"height",value);
			chooseChild(_background,"height",value);
			super.height = value;
		}
		
		/**
		 * 选择皮肤元件上子元件并设置子元件传入的属性值 
		 * @param lab 子元件名称
		 * @param params 属性名
		 * @param value  属性值
		 * 
		 */		
		private function chooseChild(lab:String,params:String,value:*):void{
			upState[lab][params] = value;
			overState[lab][params] = value;
			downState[lab][params] = value;
		}
		
		public function get skin():ButtonSkin{
			return _skin as ButtonSkin;
		}
	    
		
	}

}