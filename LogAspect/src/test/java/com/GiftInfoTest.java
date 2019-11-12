package com;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.component.GiftInfoContext;
import com.entity.GiftInfo;
import com.util.JsonMapper;

/**
 * 礼品信息调用
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
//@ContextConfiguration("classpath:applicationContext.xml")
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class GiftInfoTest {

    @Resource
    private GiftInfoContext giftInfoContext;
    @Test
    public void TestGift() {
    	System.out.println("--------");
    	GiftInfo info = getGiftInfo(1,1);
    	System.out.println(JsonMapper.toJsonString(info));
    }
    
    public GiftInfo getGiftInfo(int subjectId, int activityId) {
        GiftInfo giftInfo = giftInfoContext.getGiftInfo(subjectId, activityId);
        Assert.assertNotNull(giftInfo);
        return giftInfo;
    }
}