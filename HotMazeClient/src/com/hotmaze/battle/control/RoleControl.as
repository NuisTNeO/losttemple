package com.hotmaze.battle.control
{
	import com.pblabs.engine.PBE;
	import com.pblabs.engine.components.TickedComponent;
	import com.pblabs.engine.core.InputKey;
	import com.pblabs.engine.core.InputMap;
	import com.pblabs.engine.entity.PropertyReference;
	
	import flash.events.Event;
	import flash.geom.Point;
	
	
	public class RoleControl extends TickedComponent
	{
		
		public var velocityReference	:PropertyReference;
		public var positionReference	:PropertyReference;
		public var scaleReference		:PropertyReference;
		
		public var animationEventName 	:String;
		
		private var _inputMap:InputMap;
		
		private var _left:Number = 0;
		private var _right:Number = 0;
		private var _jump:Number = 0;
		private var _onGround:int = 0;
		
		private var _up:Number = 0;
		private var _down:Number = 0;
		
		private var _isIdle		:Boolean = true;
		private var _animation	:String = "idle";
		
		public function RoleControl()
		{
			//TODO: implement function
			super();
		}
		
		public function get activeAnimation():String
		{
			return _animation;
		}

		public function set activeAnimation(value:String):void
		{
			_animation = value;
		}

		public function get inputMap():InputMap
		{
			return _inputMap;
		}

		public function set inputMap(value:InputMap):void
		{
			_inputMap = value;
			if(_inputMap != null) 
			{
				_inputMap.mapActionToHandler("GoLeft", _OnLeft);
				_inputMap.mapActionToHandler("GoRight", _OnRight);
				_inputMap.mapActionToHandler("GoUp", _OnUp);
				_inputMap.mapActionToHandler("GoDown", _OnDown);
				
			}
		}
		
		override public function onTick(tickRate:Number):void
		{
			super.onTick(tickRate);
			
			var _position	:Point = owner.getProperty(positionReference);
			var _scale		:Point = owner.getProperty(scaleReference);
			
			
			if(PBE.isKeyDown(InputKey.UP))
			{	
				if(_isIdle)
				{
					_isIdle = false;
					_animation = "moveRight";
					owner.eventDispatcher.dispatchEvent(new Event(animationEventName));
				}
				_position.x += 6;
				_position.y -= 6;
				_scale.x = -1;
			}
				
			else if(PBE.isKeyDown(InputKey.DOWN))
			{	
				if(_isIdle)
				{
					_isIdle = false;
					_animation = "moveLeft";
					owner.eventDispatcher.dispatchEvent(new Event(animationEventName));
				}
				_position.x -= 6;
				_position.y += 6;
				_scale.x = 1;
			}
				
			else if(PBE.isKeyDown(InputKey.LEFT))
			{
				if(_isIdle)
				{
					_isIdle = false;
					_animation = "moveRight";
					owner.eventDispatcher.dispatchEvent(new Event(animationEventName));				
				}
				_position.x -= 6;
				_position.y -= 6;
				_scale.x = 1;
			}
				
			else if(PBE.isKeyDown(InputKey.RIGHT))
			{
				if(_isIdle)
				{
					_isIdle = false;
					_animation = "moveLeft";				
					owner.eventDispatcher.dispatchEvent(new Event(animationEventName));				
				}
				_position.x += 6;
				_position.y += 6;
				_scale.x = -1;
			}
				
			else if(!_isIdle)
			{
				_isIdle = true;
				_animation = "idle";
				owner.eventDispatcher.dispatchEvent(new Event(animationEventName));
			}
			
			if(_position.y > 285)
				_position.y = 285;
			
			if(_position.y < -285)
				_position.y = -285;
			
			if(_position.x > 385)
				_position.x = 385;
			
			if(_position.x < -385)
				_position.x = -385;	
			
			owner.setProperty(positionReference, _position);
			owner.setProperty(scaleReference, _scale);
		}

		private function _OnLeft(value:Number):void
		{
			_left = value;
		}
		
		private function _OnRight(value:Number):void
		{
			_right = value;
		}
		
		private function _OnUp(value:Number):void
		{
			_up = value;
		}
		
		private function _OnDown(value:Number):void
		{
			_down = value;
		}
		
		private function _OnJump(value:Number):void
		{
			trace("onJump ==== " + value);
			if (_onGround > 0)
				_jump = value;
		}
		
		
	}
}