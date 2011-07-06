package com.hotmaze.resource
{
	import com.pblabs.engine.resource.ResourceBundle;
	
	public class GameResources extends ResourceBundle
	{
		public function GameResources()
		{
			super();
		}
		//PBE
		[Embed(source='../assets/pbeThings/common.pbe', mimeType='application/octet-stream')]
		public var commonpbe:Class;
		
		[Embed(source='../assets/pbeThings/spriteSheets.pbe', mimeType='application/octet-stream')]
		public var spriteSheetspbe:Class;
		
		[Embed(source='../assets/pbeThings/templates.pbe', mimeType='application/octet-stream')]
		public var templatespbe:Class;
		
		//MAP
		[Embed(source='../assets/Images/terrain.png')]
		public var terrain:Class;
		
		[Embed(source="../assets/Images/bg.jpg")]
		public var resBg:Class;
		
		[Embed(source="../assets/Images/Wall/wall1.png")]
		public var wall1:Class;
		
		//ROLE
		[Embed(source="../assets/Images/fanship.png")]
		public var resShip:Class;
		
		[Embed(source='../assets/Images/role.png')]
		public var role:Class;
		
		[Embed(source='../assets/Images/Role/movedown.png')]
		public var movedown:Class;
		
		[Embed(source='../assets/Images/Role/moveleftdown.png')]
		public var moveleftdown:Class;
		
		[Embed(source='../assets/Images/Role/moveleftup.png')]
		public var moveleftup:Class;
		
		
		
	}
}