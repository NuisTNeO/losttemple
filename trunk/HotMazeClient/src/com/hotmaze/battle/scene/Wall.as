package com.hotmaze.battle.scene
{
	import com.pblabs.box2D.*;
	import com.pblabs.engine.PBE;
	import com.pblabs.engine.core.ObjectType;
	import com.pblabs.engine.entity.IEntity;
	import com.pblabs.engine.entity.PropertyReference;
	import com.pblabs.rendering2D.SpriteSheetRenderer;
	import com.pblabs.rendering2D.spritesheet.SpriteSheetComponent;
	
	import flash.geom.Point;

	public class Wall
	{
		private static var num:int = 0;
		
		private var entity:IEntity = null;
		
		public function Wall(_entity:IEntity, x:int=0, y:int=0)
		{
			entity = _entity;
			init(x, y);
			num++;
			trace("wall num = " + num);
		}
		
		private function init(x:int=0, y:int=0):void {
			
			createSpatial(x, y);
			
			createRender();
			
			entity.initialize("Wall");
		}
		
		private function createSpatial(x:int=0, y:int=0):void {
		
			var _spatial	:Box2DSpatialComponent = new Box2DSpatialComponent();
			
			_spatial.canMove = false;
			_spatial.canRotate = false;
			_spatial.canSleep = false;
			_spatial.collidesWithTypes = new ObjectType("hero");
			
			var array:Array = new Array();
			array.push(new Point(-1,-1));
			array.push(new Point(1,-1));
			array.push(new Point(1,1));
			array.push(new Point(-1,1));
			var collisionShapes:PolygonCollisionShape = new PolygonCollisionShape();
			collisionShapes.vertices = array;
			
			_spatial.collisionShapes = new Array(collisionShapes);
			
			_spatial.collisionType = new ObjectType(new Array("Wall", "Renderable"));
			
			//_spatial.spatialManager = PBE.spatialManager;
			
			_spatial.size = new Point(50, 50);
			_spatial.position = new Point(x, y);
			
			entity.addComponent(_spatial, "Spatial");
		}
		
		private function createRender():void {
		
			var spriteSheet:SpriteSheetComponent = new SpriteSheetComponent();
			spriteSheet.imageFilename = "../assets/Images/Wall/wall1.png";
		
			var _renderer		:SpriteSheetRenderer = new SpriteSheetRenderer();
			_renderer.positionProperty = new PropertyReference("@Spatial.position");
			_renderer.rotationProperty = new PropertyReference("@Spatial.rotation");
			_renderer.sizeProperty = new PropertyReference("@Spatial.size");
			_renderer.spriteSheet = spriteSheet;
			_renderer.layerIndex = 5;
			_renderer.scene = PBE.scene;
			
			entity.addComponent(_renderer, "Renderer");
		}
		
		
	}
}