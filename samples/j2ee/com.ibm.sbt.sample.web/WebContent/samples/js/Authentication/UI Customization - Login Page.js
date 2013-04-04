//if popup or mainWindow login UI is used, then loginPage property can be used to change default login form.
require(['sbt/Endpoint',"sbt/dom","sbt/config"], function(Endpoint,dom,config) {
	var ep = Endpoint.find("connections");
	config.Properties["loginUi"] = "popup";
	config.Properties["loginPage"] = "/sbt/authenticator/templates/QSlogin.html";
	if(ep.isAuthenticated){
		dom.setText("content","You are already logged in");
	}else{
		ep.authenticate({
			success: function(response){
				dom.setText("content","You successfully logged in");	
			}
		});
	}
});