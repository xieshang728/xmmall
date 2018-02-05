package com.xmall.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * @author xies
 * @date 2017/12/25
 */
public class PlatformContextListener extends ContextLoaderListener {
    private static Logger logger = LoggerFactory.getLogger(PlatformContextListener.class);
    private static ApplicationContext ctx = null;

    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        return super.initWebApplicationContext(servletContext);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.info("----------------------系统启动--------------------------");
        long start = System.currentTimeMillis();
        super.contextInitialized(event);
        ServletContext context = event.getServletContext();
        ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        long end = System.currentTimeMillis();
        long consume = end - start;
        long min = (consume / 1000) / 60;
        long sec = (consume / 1000) % 60;
        if (min > 0) {
            logger.info("-------------------启动系统花费了" + min + "分钟" + sec + "秒----------------");
        } else {
            logger.info("-------------------启动系统花费了" + sec + "秒-----------------------");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        logger.info("---------------------系统关闭---------------------------");
        super.contextDestroyed(event);
    }

    public static ApplicationContext getContext() {
        return ctx;
    }
}
