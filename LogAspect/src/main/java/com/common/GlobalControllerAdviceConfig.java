package com.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdviceConfig {
//	@ModelAttribute(value = "msg")
//  public String message() {
//      return "欢迎访问 hangge.com";
//  }
//
//  @ModelAttribute(value = "info")
//  public Map<String, String> userinfo() {
//      HashMap<String, String> map = new HashMap<>();
//      map.put("name", "hangge");
//      map.put("age", "100");
//      return map;
//  }

	//当然 @ModelAttribute 也可以不写 value 参数，直接在方法中对全局 Model 设置 key 和 value。下面代码的效果同上面是一样的
	@ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("msg", "欢迎访问 hangge.com");
 
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "hangge");
        map.put("age", "100");
        model.addAttribute("info", map);
    }
	


}
