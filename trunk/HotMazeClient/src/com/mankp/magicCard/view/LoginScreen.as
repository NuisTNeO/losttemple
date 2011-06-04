package com.mankp.magicCard.view 
{
	import flash.display.MovieClip;
	import flash.text.TextField;
	
	/**
	 * ...
	 * @author LeonHe
	 */
	[Embed(source = '../../../../assets/swf/basic.swf', symbol = 'LoginUI')]
	public class LoginScreen extends MovieClip 
	{
		public var username:TextField;
		public var password:TextField;
		
		public function LoginScreen() 
		{
			//username.text = "he";
			password.displayAsPassword = true;
		}
		
	}

}