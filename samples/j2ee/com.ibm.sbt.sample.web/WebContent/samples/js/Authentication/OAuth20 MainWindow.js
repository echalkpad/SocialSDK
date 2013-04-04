require(["sbt/Endpoint", "sbt/dom", "sbt/config"], function(Endpoint, dom, config) {
    var ep = Endpoint.find("connectionsOA2");
    ep.authenticate({
        forceAuthentication: true,
        success: function(response){
            dom.setText("content", "Successfully logged in");    
        }
    });
});