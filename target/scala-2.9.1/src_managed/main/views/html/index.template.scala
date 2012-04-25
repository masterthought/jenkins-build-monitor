
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
object index extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[List[BuildMonitorJob],play.api.templates.Html] {

    /**/
    def apply/*1.2*/(alljobs : List[BuildMonitorJob]):play.api.templates.Html = {
        _display_ {

Seq(format.raw/*1.35*/("""
<html>
<head>
   <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.0/jquery.min.js"></script>
	<script> var auto_refresh = setInterval(function()"""),format.raw("""{"""),format.raw/*5.53*/("""$('#content').load('/buildStatus');"""),format.raw("""}"""),format.raw/*5.89*/(""", 5000);</script>
</head>
<body>
<style type="text/css">
	  body """),format.raw("""{"""),format.raw/*9.10*/("""
	    color: purple;
	    background-color: #2E2E2E """),format.raw("""}"""),format.raw/*11.33*/("""

	    .grey"""),format.raw("""{"""),format.raw/*13.12*/("""
			background-color:#D8D8D8;
			color:purple;
	    """),format.raw("""}"""),format.raw/*16.7*/("""
	    .yellow"""),format.raw("""{"""),format.raw/*17.14*/("""
			background-color:#F7D358;
			color:purple;
	    """),format.raw("""}"""),format.raw/*20.7*/("""
		.job """),format.raw("""{"""),format.raw/*21.9*/("""
			border-style:solid;
			border-width:8px;
			border-color:white;
			border-radius: 10px;
			font-size:20px;
			font-family:verdana;
			width:600px;
			padding:10px;
			margin:5px;
		   """),format.raw("""}"""),format.raw/*31.7*/("""

	  .red """),format.raw("""{"""),format.raw/*33.10*/("""
		border-radius: 5px;
		background-color:#FF4000;
		color:white;
		font-size:20px;
		font-family:verdana;
		width:600px;
		padding:10px;
		margin:5px;
	   """),format.raw("""}"""),format.raw/*42.6*/("""

      .red_anime """),format.raw("""{"""),format.raw/*44.19*/("""
          border-radius: 5px;
		background-color:#eee8aa;
		color:white;
		font-size:20px;
		font-family:verdana;
		width:600px;
		padding:10px;
		margin:5px;
      """),format.raw("""}"""),format.raw/*53.8*/("""
	.blue """),format.raw("""{"""),format.raw/*54.9*/("""
		border-radius: 5px;
		background-color:#58ACFA;
		color:white;
		font-size:20px;
		font-family:verdana;
		width:600px;
		padding:10px;
		margin:5px;
	   """),format.raw("""}"""),format.raw/*63.6*/("""

	 .title """),format.raw("""{"""),format.raw/*65.11*/("""
	     font-size: 40px;
	  """),format.raw("""}"""),format.raw/*67.5*/("""

	  .job a """),format.raw("""{"""),format.raw/*69.12*/("""
		color:black;
	"""),format.raw("""}"""),format.raw/*71.3*/("""
	 h1 """),format.raw("""{"""),format.raw/*72.7*/("""
		font-size: 40px;
		font-style: italic;
		font-family: verdana;
		color:white;
	"""),format.raw("""}"""),format.raw/*77.3*/("""
	  </style>
<div class="top">
   <h1>Build monitor</h1>
<!-- in the future we will put the configuration here! -->
</div>
<div id="content">
"""),_display_(Seq(/*84.2*/buildmonitor(jobs = alljobs))),format.raw/*84.30*/("""
</div>
<div id="footer">
</div>
</body>
</html>"""))}
    }
    
    def render(alljobs:List[BuildMonitorJob]) = apply(alljobs)
    
    def f:((List[BuildMonitorJob]) => play.api.templates.Html) = (alljobs) => apply(alljobs)
    
    def ref = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Apr 25 15:52:51 BST 2012
                    SOURCE: /Users/kostasmamalis/development/buildmonitor/app/views/index.scala.html
                    HASH: 3627ecc56543f80b52b70d81e3200ebff010e1ad
                    MATRIX: 770->1|875->34|1079->192|1161->228|1273->294|1373->347|1433->360|1532->413|1593->427|1692->480|1747->489|1982->678|2040->689|2243->846|2310->866|2523->1033|2578->1042|2781->1199|2840->1211|2914->1239|2974->1252|3038->1270|3091->1277|3220->1360|3393->1503|3443->1531
                    LINES: 27->1|30->1|34->5|34->5|38->9|40->11|42->13|45->16|46->17|49->20|50->21|60->31|62->33|71->42|73->44|82->53|83->54|92->63|94->65|96->67|98->69|100->71|101->72|106->77|113->84|113->84
                    -- GENERATED --
                */
            