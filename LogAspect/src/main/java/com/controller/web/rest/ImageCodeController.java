package com.controller.web.rest;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.util.RandomValidateCodeUtil;
import com.util.ImageCode;

@RestController
@RequestMapping(value="api/")
public class ImageCodeController {
	public static final Logger logger = LoggerFactory.getLogger(ImageCodeController.class);
	
	@RequestMapping(value="/getCode",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public String getImageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        
        OutputStream os = response.getOutputStream();
        
        //返回验证码和图片的map
        Map<String,Object> map = ImageCode.createCode(86, 37);
        String simpleCaptcha = "simpleCaptcha";
        //request.getSession().setAttribute(simpleCaptcha, map.get("strEnsure").toString().toLowerCase());
        //request.getSession().setAttribute("codeTime",new Date().getTime());
        try {
            ImageIO.write((BufferedImage) map.get("image"), "jpg", os);
        } catch (IOException e) {
            return "";
        }   finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
		return null;
	}

	@RequestMapping(value="/getCodeByte",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public byte[] getByte() throws IOException{
        Map<String,Object> map = ImageCode.createCode(86, 37);
        String randomStr = (String) map.get("code");
        logger.info("randomStr  result: "+randomStr);
        //redisService.delete("USER:IMG:" + macCode);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
			ImageIO.write((BufferedImage) map.get("image"),"jpg",baos);
			return (byte[]) baos.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("获取验证码失败>>>>   ", e);
		} finally {
			if(baos != null) {
				baos.close();
				baos = null;
			}
		}
        //redisService.save("USER:IMG:" + macCode, randomStr);  // 存储到redis中，后续用于作验证
		return null;
        
    }
	
	
	/**
     * 生成验证码
     */
    @RequestMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            logger.error("获取验证码失败>>>>   ", e);
        }
    }

    /**
     * 校验验证码
     */
    @RequestMapping(value = "/checkVerify", method = RequestMethod.POST,headers = "Accept=application/json")
    public boolean checkVerify(@RequestParam String verifyInput, HttpSession session) {
        try{
            //从session中获取随机数
            String inputStr = verifyInput;
            String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
            if (random == null) {
                return false;
            }
            if (random.equals(inputStr)) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            logger.error("验证码校验失败", e);
            return false;
        }
    }
}
