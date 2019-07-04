package com.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.util.JsonUtil;


@Controller
public class ImgController {
	private static Logger logger = LoggerFactory.getLogger(ImgController.class);
    @RequestMapping({"authCode"})
    public void getAuthCode(HttpServletRequest request, HttpServletResponse response,HttpSession session)
            throws IOException {
        int width = 63;
        int height = 37;
        Random random = new Random();
        //设置response头信息
        //禁止缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        //生成缓冲区image类
        BufferedImage image = new BufferedImage(width, height, 1);
        //产生image类的Graphics用于绘制操作
        Graphics g = image.getGraphics();
        //Graphics类的样式
        g.setColor(this.getRandColor(200, 250));
        g.setFont(new Font("Times New Roman",0,28));
        g.fillRect(0, 0, width, height);
        //绘制干扰线
        for(int i=0;i<40;i++){
            g.setColor(this.getRandColor(130, 200));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }

        //绘制字符
        String strCode = "";
        for(int i=0;i<4;i++){
            String rand = String.valueOf(random.nextInt(10));
            strCode = strCode + rand;
            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
            g.drawString(rand, 13*i+6, 28);
        }
        //将字符保存到session中用于前端的验证
        session.setAttribute("strCode", strCode);
        
        g.dispose();

        ImageIO.write(image, "JPEG", response.getOutputStream());
        response.getOutputStream().flush();
    }
    
    //创建颜色
    Color getRandColor(int fc,int bc){
        Random random = new Random();
        if(fc>255)
            fc = 255;
        if(bc>255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r,g,b);
    }
    /**
     * 进行验证用户的验证码是否正确
     * @param value 用户输入的验证码
     * @param request HttpServletRequest对象
     * @return 一个String类型的字符串。格式为：<br/>
     *                     {"res",boolean},<br/>
     *                 如果为{"res",true}，表示验证成功<br/>
     *                 如果为{"res",false}，表示验证失败
     */
    @RequestMapping("validate")
    @ResponseBody
    public String validate(String value,HttpServletRequest request){
        String valueCode=(String)request.getSession().getAttribute("strCode");
        Map<String, Boolean> map=new HashMap<String, Boolean>();
        if(valueCode!=null){
            if(valueCode.equals(value)){
                map.put("res", true);
                return JsonUtil.beanToJson(map);
            }
        }
        map.put("res", false);
        return JsonUtil.beanToJson(map);
    }
    
    @Autowired
    private Producer captchaProducer = null;

/**
     * 生成验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/yzmImg")
    public void yzmImg(HttpServletRequest request,HttpServletResponse response) throws IOException{
    	logger.info("-----生成验证码-----");
        HttpSession session = request.getSession();
        String preCode = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        logger.info("-----生成验证码-----前一个验证码："+preCode);
        System.out.println("-----生成验证码-----前一个验证码："+preCode);
        
        //生成验证码
        response.setDateHeader("Expires", 0);
         // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = captchaProducer.createText();
        System.err.println(capText);
        // store the text in the session
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        session.setAttribute(Constants.KAPTCHA_SESSION_DATE, new Date());
        // 存放到缓存服务器上，并把key记录到cookie
        //CodeUtils.creatCode(capText, response, request);
        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }

    }
}

