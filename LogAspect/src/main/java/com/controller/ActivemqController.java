package com.controller;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.service.activemq.producer.QueueProducerService;
import com.service.activemq.producer.TopicProducerService;

/**
 * @version V1.0
 * @ClassName ActivemqController
 * @Description Activemq Controller
 **/
@Controller
@RequestMapping(value="/views")
public class ActivemqController {

    private Logger logger = LoggerFactory.getLogger(ActivemqController.class);


    @Autowired
    private QueueProducerService queueProducerService;

    @Autowired
    private TopicProducerService topicProducerService;

    @Resource
    @Qualifier("queueDestination")
    private Destination queueDestination;

    @Resource
    @Qualifier("topicDestination")
    private Destination topicDestination;

    @RequestMapping("")
    public ModelAndView activemq(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("mqindex");
        return mv;
    }


    /**
     * 去发消息页面
     * @return
     */
    @RequestMapping(value="/producer",method= RequestMethod.GET)
    public ModelAndView producer(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("name", "producer");
        mv.setViewName("activemqProducer");
        return mv;
    }

    /**
     * 发送消息
     * @param message
     * @return
     */
    @RequestMapping(value="/onsend",method=RequestMethod.POST)
    public ModelAndView producer(@RequestParam("message") String message,@RequestParam("sendflag") String sendflag) {
        logger.info("------------send to jms------------");
        ModelAndView mv = new ModelAndView();
        //1 主题  2 队列
        if("1".equals(sendflag)){
            logger.info("topic生产者产生消息：" + message);
            topicProducerService.sendMessage(topicDestination, "topic生产者产生消息：" + message);
        }else {
            logger.info("队列生产者产生消息：" + message);
            queueProducerService.sendMessage(queueDestination, "队列生产者产生消息："  + message);
        }
        mv.setViewName("mqindex");
        return mv;
    }
}
