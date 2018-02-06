package com.xmall.controller;

import com.xmall.common.TokenCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xies
 * @date 2018/2/6.
 */
@Controller
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value="get_cache.do")
    @ResponseBody
    public String getCache(String key){
        return TokenCache.getKey(key);
    }


    @RequestMapping(value="test.do")
    @ResponseBody
    public String test(String str){
        logger.info("test.info");
        return "testValue: "+str;
    }
}
