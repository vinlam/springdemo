(function($, undefined) {
	$.req = $.req || {};
	var serverUrl = "http://mall.ccb.com/ecp/api/";
	
	var hostname="http://mall.ccb.com/";
	localStorage.setItem('hostname',hostname);
	var tokenRegex = document.cookie.match(/XSRF-TOKEN=([^;]+)/);
	var csrf_token = tokenRegex && tokenRegex.length >1 ? tokenRegex[1] : null;
	
	var isDemo = false;
	//避免超时重复发送接口
	var timecontroller = false;
	$.extend($.req, {
		timeOut : 20000,
		dataType : "json",
		urlFlag : true,
		apiSend : function(method, action, params, successCallback,errorCallback, wait,async) {
			//避免超时重复发送接口
			//if(timecontroller){
			//	return;
			//}
			//console.log($.req.urlFlag);
			//console.log(window.location.href);
			var contentType = "application/json";
			//var async = async;
			
			if (wait === undefined) {
				wait = true;
			}
			if (async === undefined) {
				async = true;
			}

			if (method === undefined) {
				method = 'post';
			}

			if (wait) {
				//$.showLoading();
			}
			params = params || {};

			var param = {
			//	"EMP_SID": "",
			//	"channel": "1201",
			//	"responseFormat": "JSON"
			};
			if(typeof(params) == "object"){
				params = $.extend(params, param);
				if(params.formData || method.toLowerCase() === "get"){
					contentType = "application/x-www-form-urlencoded; charset=UTF-8";
				}else{
					params = JSON.stringify(params);
				} 
			}
			var  gologin= localStorage.getItem('gologin');
			localStorage.removeItem('gologin'); 
			
//			if ($.req.urlFlag) {
//				requestUrl = serverUrl;
//			} else {
//				requestUrl = locationUrl;
//			}
			requestUrl = serverUrl;
			if(action.match(/http:\/\/|https:\/\//)){
				url = action;
			}else{
				url = requestUrl + action;
			}
			var randtimestamp = new Date().getTime();//Math.random()*1000000;
			if(url.indexOf('?') > 0){
				url =url +"&rts="+randtimestamp;
			}else{
				url =url +"?rts="+randtimestamp;
			}
			//console.log('请求连接' + url + ' 请求参数' + JSON.stringify(params));
			$.ajax(url, {
				headers : {
					Accept : "application/json",
					'X-XSRF-TOKEN': csrf_token
				},
				contentType : contentType,
				data : params,
				dataType : $.req.dataType, //服务器返回json格式数据
				type : method, //HTTP请求类型
				xhrFields : {
					withCredentials : true
				},
				async:async,
				crossDomain: true,
				timeout : $.req.timeOut, //超时时间设置为20秒；
				success : function(data) {
					//服务器返回响应，根据响应结果，分析是否登录成功；
					//console.log('返回信息' + JSON.stringify(data));
					if (wait) {
						//$.hideLoading();
					}

					if (typeof (successCallback) == 'function') {
						successCallback(data);
					} else {
						alert('缺少回调函数', 'text');
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//异常处理；
					//console.log("ajaxerror: " + JSON.stringify(XMLHttpRequest) + " textStatus: " + XMLHttpRequest.statusText);
					if (wait) {
						//$.hideLoading();
					}
					
					if(!gologin){
						if (typeof (checkGoToLogin) == 'function') {
							checkGoToLogin(XMLHttpRequest);
						}
					}
					
					
					if (typeof (errorCallback) == 'function') {
						//XMLHttpRequest.em="网络请求超时,请稍后再试";
						errorCallback(XMLHttpRequest);
						console.log("err callback");
					} else {
						alert('网络请求超时,请稍后再试', "text");
						return;
					}
				}
			});
		}
	})
})(jQuery)






