package com;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.util.JsoupUtil;

public class xssjsouptest {
    public static void main(String[] args) {
    	Whitelist whitelist = new Whitelist();
    	whitelist.addTags("p");
    	String bodyHtml = "<div class='div' style='height:100px;'>div 标签的内容</div><p class='p' style='width:100px;'>p 标签的内容</p>";
		String res = Jsoup.clean(bodyHtml, whitelist);
		System.out.println(res);
		
		whitelist.addTags("div","h1");
		whitelist.addAttributes(":all","class","style","title");//
		String testHtml = "<h1 onclick='alert(1);'class='hh' style=''title='retr'>h1 内容 </h1><div class=''>div 内容 </div><p class='' style=''>p 内容 </p>";
		String result3 = Jsoup.clean(testHtml, whitelist);
		System.out.print(result3);// 输出:  <h1 style="title="">h1 内容 </h1><div>div 内容 </div><p class=''style="">p 内容 </p>
	
		String r = JsoupUtil.clean(testHtml);
		System.out.println(r);
    }
}
