
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.api.templates.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import com.avaje.ebean._
import play.mvc.Http.Context.Implicit._
import views.html._
/**/
object buildmonitor extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[List[BuildMonitorJob],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(jobs: List[BuildMonitorJob]):play.api.templates.Html = {
        _display_ {

Seq(format.raw/*1.31*/("""
    """),_display_(Seq(/*2.6*/for(job <- jobs) yield /*2.22*/{_display_(Seq(format.raw/*2.23*/("""
            <div class="job """),_display_(Seq(/*3.30*/job/*3.33*/.getColour())),format.raw/*3.45*/("""">
                <a class="title" href=""""),_display_(Seq(/*4.41*/job/*4.44*/.getUrl())),format.raw/*4.53*/("""" >"""),_display_(Seq(/*4.57*/job/*4.60*/.getName())),format.raw/*4.70*/("""</a>
                <p> Latest build is:<a href=""""),_display_(Seq(/*5.47*/job/*5.50*/.getLatestBuildUrl())),format.raw/*5.70*/("""">"""),_display_(Seq(/*5.73*/job/*5.76*/.getLatestBuildNumber())),format.raw/*5.99*/("""</a></p>
                """),_display_(Seq(/*6.18*/if(job.inProgress())/*6.38*/{_display_(Seq(format.raw/*6.39*/("""
                   <p>In Progress</p>
                """)))})),format.raw/*8.18*/("""
            </div>
    """)))})),format.raw/*10.6*/("""
"""))}
    }
    
    def render(jobs:List[BuildMonitorJob]) = apply(jobs)
    
    def f:((List[BuildMonitorJob]) => play.api.templates.Html) = (jobs) => apply(jobs)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Apr 25 15:41:24 BST 2012
                    SOURCE: /Users/kostasmamalis/development/buildmonitor/app/views/buildmonitor.scala.html
                    HASH: 981a57230b020e18b6e038269b66451ec7bde88e
                    MATRIX: 777->1|878->30|913->36|944->52|977->53|1037->83|1048->86|1081->98|1154->141|1165->144|1195->153|1229->157|1240->160|1271->170|1352->221|1363->224|1404->244|1437->247|1448->250|1492->273|1548->299|1576->319|1609->320|1696->376|1752->401
                    LINES: 27->1|30->1|31->2|31->2|31->2|32->3|32->3|32->3|33->4|33->4|33->4|33->4|33->4|33->4|34->5|34->5|34->5|34->5|34->5|34->5|35->6|35->6|35->6|37->8|39->10
                    -- GENERATED --
                */
            