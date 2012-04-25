// @SOURCE:/Users/kostasmamalis/development/buildmonitor/conf/routes
// @HASH:2fb36c021d89cbf52fdb0218b680dff51c8220f5
// @DATE:Wed Apr 25 14:32:37 BST 2012

import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString

object Routes extends Router.Routes {


// @LINE:6
val controllers_BuildMonitor_index0 = Route("GET", PathPattern(List(StaticPart("/"))))
                    

// @LINE:7
val controllers_BuildMonitor_buildmonitor1 = Route("GET", PathPattern(List(StaticPart("/buildStatus"))))
                    

// @LINE:10
val controllers_Assets_at2 = Route("GET", PathPattern(List(StaticPart("/assets/"),DynamicPart("file", """.+"""))))
                    
def documentation = List(("""GET""","""/""","""controllers.BuildMonitor.index()"""),("""GET""","""/buildStatus""","""controllers.BuildMonitor.buildmonitor()"""),("""GET""","""/assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)"""))
             
    
def routes:PartialFunction[RequestHeader,Handler] = {        

// @LINE:6
case controllers_BuildMonitor_index0(params) => {
   call { 
        invokeHandler(_root_.controllers.BuildMonitor.index(), HandlerDef(this, "controllers.BuildMonitor", "index", Nil))
   }
}
                    

// @LINE:7
case controllers_BuildMonitor_buildmonitor1(params) => {
   call { 
        invokeHandler(_root_.controllers.BuildMonitor.buildmonitor(), HandlerDef(this, "controllers.BuildMonitor", "buildmonitor", Nil))
   }
}
                    

// @LINE:10
case controllers_Assets_at2(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(_root_.controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String])))
   }
}
                    
}
    
}
                