package
{
	
	import com.hotmaze.battle.control.RoleControl;
	import com.hotmaze.battle.scene.Wall;
	import com.hotmaze.resource.*;
	import com.pblabs.animation.*;
	import com.pblabs.box2D.*;
	import com.pblabs.engine.PBE;
	import com.pblabs.engine.core.*;
	import com.pblabs.engine.entity.*;
	import com.pblabs.rendering2D.*;
	import com.pblabs.rendering2D.spritesheet.*;
	import com.pblabs.rendering2D.ui.*;
	import com.pblabs.screens.*;
	
	import flash.display.*;
	import flash.events.Event;
	import flash.geom.Point;
	
	[SWF(width="700",height="500")]
	public class HotMazeClient extends Sprite
	{
		public function HotMazeClient()
		{
			// Register our types.
			PBE.registerType(com.pblabs.rendering2D.DisplayObjectScene);
			PBE.registerType(com.pblabs.rendering2D.SpriteSheetRenderer);
			PBE.registerType(com.pblabs.rendering2D.SimpleSpatialComponent);
			PBE.registerType(com.pblabs.rendering2D.BasicSpatialManager2D);
			PBE.registerType(com.pblabs.rendering2D.spritesheet.CellCountDivider);
			PBE.registerType(com.pblabs.rendering2D.spritesheet.SpriteSheetComponent);
			PBE.registerType(com.pblabs.rendering2D.ui.SceneView);
			PBE.registerType(com.pblabs.animation.AnimatorComponent);
			
			// Initialize game.
			PBE.startup(this);
			
			PBE.addResources(new GameResources());
			
//			LevelManager.instance.addFileReference(1, "../assets/Levels/level.pbelevel");
//			LevelManager.instance.addGroupReference(1, "Everything");
//			LevelManager.instance.addGroupReference(1, "Level1");
			
			// Make the game scale properly.
			stage.scaleMode = StageScaleMode.SHOW_ALL; 
			
			// Pause/resume based on focus.
			stage.addEventListener(Event.DEACTIVATE, function():void{ PBE.processManager.timeScale = 0; });
			stage.addEventListener(Event.ACTIVATE, function():void{ PBE.processManager.timeScale = 1; });
			
			
			createScene();
			
			createBackground();
			
			createHero();
			
			createWall();
			
		}
		
		private function createScene():void {
			
			var sceneView:SceneView = new SceneView();
			sceneView.height = 600;
			sceneView.width = 800;
			
			sceneView.name = "HotMaze Battle Scene";
			
			PBE.initializeScene(sceneView);
		}
		
		private function createBackground():void {
			
			// Allocate an entity for our background sprite
			var bg:IEntity = PBE.allocateEntity();
			
			// Add our spatial component to the background entity ...
			createSpatial( bg, 
				// with location of 0,0...
				new Point(0, 0)
			);
			
			// Create a simple render component to display our object
			
			// Just like the hero, this also uses a SpriteRenderComponent
			var render:SpriteRenderer = new SpriteRenderer();
			
			// Tell the Render component to use one of the images embedded by our ResourceLinker
			render.fileName = "../assets/Images/bg.jpg";
			
			// Set our background to render below the hero.
			render.layerIndex = 1;
			
			// Add the renderer to the scene.
			render.scene = PBE.scene;
			
			// Point the render component to this entity's Spatial component for position information
			render.positionProperty = new PropertyReference("@Spatial.position");
			
			// Add our render component to the BG entity with the name "Render"
			bg.addComponent( render, "Render" );
			
			// Register the entity with PBE under the name "BG"
			bg.initialize("BG");         
		}
		
		// This is a shortcut function to help simplify the creation of spatial components
		private function createSpatial( ent:IEntity, pos:Point, size:Point = null ):void
		{
			// Create our spatial component
			var spatial:SimpleSpatialComponent = new SimpleSpatialComponent();
			
			// Do a named lookup to register our background with the scene spatial database
			spatial.spatialManager = PBE.spatialManager;
			
			// Set our background position in space
			spatial.position = pos;
			
			if (size != null) 
				spatial.size = size;
			
			ent.addComponent(spatial, "Spatial");
		}
		
		private function createHero():void
		{
			var _hero	:IEntity = allocateEntity();
			
			
			// Add dimensions to our hero
			var _spatial	:Box2DSpatialComponent = new Box2DSpatialComponent();
			_spatial.size = new Point(50, 50);
			_spatial.position = new Point(0, 0);
			_spatial.canRotate = false;
			_spatial.canSleep = false;
			_spatial.collidesWithTypes = new ObjectType("Wall");
			var colShape:CollisionShape = new CollisionShape();
			var array:Array = new Array();
			
			var collisionShapes:CircleCollisionShape = new CircleCollisionShape();
			collisionShapes.offset = new Point(0, 0.5);
			collisionShapes.radius = 0.5;
			array.push(collisionShapes);
			_spatial.collisionShapes = array;
			
			_spatial.collisionType = new ObjectType(new Array("hero", "Renderable"));
			
//			_spatial.spatialManager = PBE.spatialManager;
			
			_hero.addComponent(_spatial, "Spatial");
			
			
			// Render our hero
			var _renderer		:SpriteSheetRenderer = new SpriteSheetRenderer();
			_renderer.positionProperty = new PropertyReference("@Spatial.position");
			_renderer.spriteSheet = _idleSpriteSheet;
			_renderer.spriteIndex = 0;
			_renderer.layerIndex = 10;
			_renderer.scene = PBE.scene;
			
			_hero.addComponent(_renderer, "Renderer");
			
			// Add animation controller and animations
			var _animationController:AnimationController = new AnimationController();
			_animationController.spriteSheetReference = new PropertyReference("@Renderer.spriteSheet"); 
			_animationController.currentFrameReference = new PropertyReference("@Renderer.spriteIndex");
			_animationController.defaultAnimation = "idle";
			_animationController.changeAnimationEvent = "ufoUpdatedEvent";
			_animationController.currentAnimationReference = new PropertyReference("@Controller.activeAnimation");
			
			// Create Idle Animation
			var _idleSpriteSheet:SpriteSheetComponent= new SpriteSheetComponent();
			_idleSpriteSheet.imageFilename = "../assets/Images/Role/moveup.png";
			
			var _idleDivider:CellCountDivider = new CellCountDivider();
			_idleDivider.xCount = 4;
			_idleDivider.yCount = 2;
			_idleSpriteSheet.divider = _idleDivider;
			//			
			var _idleAnimation:AnimationControllerInfo = new AnimationControllerInfo();
			_idleAnimation.frameRate = 3;
			_idleAnimation.loop = true;
			_idleAnimation.spriteSheet = _idleSpriteSheet;
			_animationController.animations["idle"] = _idleAnimation;
			
			// Create Move Left Animation
			var _moveLeftSpriteSheet:SpriteSheetComponent = new SpriteSheetComponent();
			_moveLeftSpriteSheet.imageFilename = "../assets/Images/Role/moveleftdown.png";
			
			var _moveLeftDivider:CellCountDivider = new CellCountDivider();
			_moveLeftDivider.xCount = 4;
			_moveLeftDivider.yCount = 2;
			_moveLeftSpriteSheet.divider = _moveLeftDivider;
			
			var _moveLeftAnimation:AnimationControllerInfo = new AnimationControllerInfo();
			_moveLeftAnimation.frameRate = 6;
			_moveLeftAnimation.loop = true;
			_moveLeftAnimation.spriteSheet = _moveLeftSpriteSheet;
			_animationController.animations["moveLeft"] = _moveLeftAnimation;
			
			// Create Move Right Animation
			var _moveRightSpriteSheet:SpriteSheetComponent = new SpriteSheetComponent();
			_moveRightSpriteSheet.imageFilename = "../assets/Images/Role/moveleftup.png";
			
			var _moveRightDivider:CellCountDivider = new CellCountDivider();
			_moveRightDivider.xCount = 4;
			_moveRightDivider.yCount = 2;
			_moveRightSpriteSheet.divider = _moveRightDivider;
			
			var _moveRightAnimation:AnimationControllerInfo = new AnimationControllerInfo();
			_moveRightAnimation.frameRate = 6;
			_moveRightAnimation.loop = true;
			_moveRightAnimation.spriteSheet = _moveRightSpriteSheet;
			_animationController.animations["moveRight"] = _moveRightAnimation;
			
			_hero.addComponent(_animationController, "Animator");
			
			//增加控制器
			var _input	:RoleControl = new RoleControl();
			_input.positionReference = new PropertyReference("@Spatial.position");
			_input.scaleReference = new PropertyReference("@Renderer.scale");
			_input.animationEventName = "ufoUpdatedEvent";
			
			_hero.addComponent(_input, "Controller");
			
			
			_hero.initialize("hero");
		}
		
		
		private function createWall():void {
			
			var _wall:IEntity = allocateEntity();
			var wall:Wall = new Wall(_wall, 100,100);
		}
		
		private function loadXml():void {
			
			var loadXml:LoadXML = new LoadXML();
			
			trace("success!");
		}
	}
}