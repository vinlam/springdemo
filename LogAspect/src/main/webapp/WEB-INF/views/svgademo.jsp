<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
		<title>红包</title>
		<link rel="stylesheet" type="text/css" href="/LogAspect/resources/svgademo/css/common/weui.min.css"/>
		<link rel="stylesheet" type="text/css" href="/LogAspect/resources/svgademo/fonts/iconfont.css">
		<style type="text/css">
			body,#demoCanvas{
				position:absolute;
				top:0;
				left:0;
				right:0;
				bottom:0;
			}
			body{
				background: #b92213;
			}
			#demoCanvas{
				top:2.3467rem;
				width:100%;
				height:25.65rem;
			}
			.panel{
				/*background-color: #fffae8;*/
				border-radius: 0.26667rem;
				margin:1.06667rem;
				position: fixed;
				bottom:0;
				padding:0.8rem;
			}
			.weui-navbar {
			    position: fixed;
			    padding: 0 0.42666rem;
			    height: 2.3467rem;
			    box-sizing: border-box;
			    -webkit-box-sizing: border-box;
			    display: flex;
			    display: -webkit-flex;
			    align-items: center;
			    -webkit-align-items: center;
			}
			.light-navbar {
			    color: #000000;
			    background-color: #ffffff;
			}
			.weui-navbar .left, .weui-navbar .right {
			    width: 2.6667rem;
			}
			.weui-navbar .right{
				text-align: right;
			}
			.light-navbar a .iconfont {
			    color: #101010;
			}
			.weui-navbar .center {
			    font-size: 0.8533rem;
			    flex: 1;
			    -webkit-flex: 1;
			    display: flex;
			    display: -webkit-flex;
			    align-items: center;
			    -webkit-align-items: center;
			    justify-content: center;
			    -webkit-justify-content: center;
			}
			.rule{
				background: #c74e42;margin-top: 0.53334rem;padding: 0.53334rem;box-sizing: border-box;font-size: 0.75rem;overflow: auto;height:7.1167rem;
			}
			.rule_il{
				background: url(/LogAspect/resources/svgademo/image/rule_i.png) -5% 100% no-repeat;
				display: inline-block;width: 2.8266rem;height: 0.8533rem;vertical-align: initial;
			}
			.rule_ir{
				background: url(/LogAspect/resources/svgademo/image/rule_i.png) 100% -5% no-repeat;
				display: inline-block;width: 2.8266rem;height: 0.8533rem;vertical-align:middle;
			}
		</style>
		<style type="text/css">
			@media screen and (max-width: 320px) {
				#demoCanvas{
					width: 100%;
					height:18.546667rem;
				}
			}
			@media screen and (max-width: 320px) and (min-height: 568px) {
				#demoCanvas{
					width: 100%;
					height:24.546667rem;
				}
			}
		</style>
	</head>
	<body>
		<div class="weui-navbar light-navbar">
			<div class="left">
				<a href="javascript:;"><i class="iconfont icon-back"></i></a>
			</div>
			<div class="center">抢积分红包</div>
			<div class="right">
				<a href="javascript:;"><i class="iconfont icon-more"></i></a>
			</div>
		</div>
		<div id="demoCanvas" data-flag="0">
		</div>
		<div class="content">
			<div class="panel panel_320">
				<div style="color:#f4dcd9">
					<div style="text-align: center;font-size: 1rem;">
						<i class="rule_il" style=""></i>
						活动规则
						<i class="rule_ir" style=""></i>
					</div>
					<div class="rule">
						<p>1、活动规则活动规则;</p>
						<p>2、活动规则活动规则活动规则活动规则活动规则活动规则;</p>
						<p>3、活动规则活动规则活动规则;</p>
						<p>4、活动规则活动规则活动规则活动规则活动规则;</p>
						<p>4、活动规则活动规则活动规则活动规则活动规则;</p>
						<p>4、活动规则活动规则活动规则活动规则活动规则;</p>
						<p>4、活动规则活动规则活动规则活动规则活动规则;</p>
						<p>4、活动规则活动规则活动规则活动规则活动规则;</p>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script src="/LogAspect/resources/svgademo/js/svga.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="/LogAspect/resources/svgademo/js/common/jquery-1.12.4.min.js" type="text/javascript" charset="utf-8"></script>
	<script>
	    // 三个步骤:
	    // 1.获取html的宽,
	    let htmlwidth=document.documentElement.clientWidth || document.body.clientWidth;//有些浏览器documentElement获取不到,那就使用后面的body
	    
	    // 2.htmlDom
	    let htmlDom=document.getElementsByTagName("html")[0]
	
	    //3.设置根元素样式
	    htmlDom.style.fontSize=htmlwidth/20+'px';//记住这个20是等份的意思,这样每一份是16px,即1rem=16px;
	    
	</script>
	<script type="text/javascript">
		var player = new SVGA.Player('#demoCanvas');
		var parser = new SVGA.Parser('#demoCanvas');
		parser.load('/LogAspect/resources/svgademo/svga/lhb.svga', function(videoItem) {
		    player.loops = 1;
		    player.clearsAfterStop = false;
		    console.log(videoItem)
		    player.setVideoItem(videoItem);
		    var tx_btn = {
		    	text: `立即打开`,
	            color: "#b92213",
	            family: "microsoft yahei",
	            size: "3.5rem"
		    }
		    player.setText(tx_btn,"tx_btn");
		    
		    //player.startAnimation();
		    player.onFrame(function (i) {
		    	if(i == 30){
		    		var tx_hb1 = {
				    	text: `恭喜您！获得`,
			            color: "#b92213",
			            family: "microsoft yahei",
			            size: "3.5rem"
				    }
				    player.setText(tx_hb1,"tx_hb1");
		    	}
		    	if(i == 35){
		    		var tx_hb2 = {
				    	text: `1000积分`,
			            color: "#b92213",
			            family: "microsoft yahei",
			            size: "3.5rem"
				    }
				    player.setText(tx_hb2,"tx_hb2");
				    player.setText({
				    	text: `立即使用`,
			            color: "#b92213",
			            family: "microsoft yahei",
			            size: "3.5rem"
				    },"tx_btn");
		    	}
		    	$("#demoCanvas").attr("data-flag","1");
		    });
		})
		$("#demoCanvas").off("click").on("click",function(){
			if($(this).attr("data-flag") == "0"){
				player.startAnimation();	
			}else{
				//立即使用
			}
		});
	</script>
</html>



