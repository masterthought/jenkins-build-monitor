// @SOURCE:/Users/kostasmamalis/development/buildmonitor/conf/routes
// @HASH:2fb36c021d89cbf52fdb0218b680dff51c8220f5
// @DATE:Wed Apr 25 14:32:37 BST 2012

import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString


// @LINE:10
// @LINE:7
// @LINE:6
package controllers {

// @LINE:10
class ReverseAssets {
    


 
// @LINE:10
def at(file:String) = {
   Call("GET", "/assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                        

                      
    
}
                            

// @LINE:7
// @LINE:6
class ReverseBuildMonitor {
    


 
// @LINE:6
def index() = {
   Call("GET", "/")
}
                                                        
 
// @LINE:7
def buildmonitor() = {
   Call("GET", "/buildStatus")
}
                                                        

                      
    
}
                            
}
                    


// @LINE:10
// @LINE:7
// @LINE:6
package controllers.javascript {

// @LINE:10
class ReverseAssets {
    


 
// @LINE:10
def at = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"/assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                                                        

                      
    
}
                            

// @LINE:7
// @LINE:6
class ReverseBuildMonitor {
    


 
// @LINE:6
def index = JavascriptReverseRoute(
   "controllers.BuildMonitor.index",
   """
      function() {
      return _wA({method:"GET", url:"/"})
      }
   """
)
                                                        
 
// @LINE:7
def buildmonitor = JavascriptReverseRoute(
   "controllers.BuildMonitor.buildmonitor",
   """
      function() {
      return _wA({method:"GET", url:"/buildStatus"})
      }
   """
)
                                                        

                      
    
}
                            
}
                    


// @LINE:10
// @LINE:7
// @LINE:6
package controllers.ref {

// @LINE:10
class ReverseAssets {
    


 
// @LINE:10
def at(path:String, file:String) = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]))
)
                              

                      
    
}
                            

// @LINE:7
// @LINE:6
class ReverseBuildMonitor {
    


 
// @LINE:6
def index() = new play.api.mvc.HandlerRef(
   controllers.BuildMonitor.index(), HandlerDef(this, "controllers.BuildMonitor", "index", Seq())
)
                              
 
// @LINE:7
def buildmonitor() = new play.api.mvc.HandlerRef(
   controllers.BuildMonitor.buildmonitor(), HandlerDef(this, "controllers.BuildMonitor", "buildmonitor", Seq())
)
                              

                      
    
}
                            
}
                    
                