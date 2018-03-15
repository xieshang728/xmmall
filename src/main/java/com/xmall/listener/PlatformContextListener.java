package com.xmall.listener;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PlatformContextListener extends ContextLoaderListener {
    private static ApplicationContext ctx = null;

    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        return super.initWebApplicationContext(servletContext);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        log.info("----------------------系统启动--------------------------");
        long start = System.currentTimeMillis();
        super.contextInitialized(event);
        ServletContext context = event.getServletContext();
        ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        long end = System.currentTimeMillis();
        long consume = end - start;
        long min = (consume / 1000) / 60;
        long sec = (consume / 1000) % 60;
        if (min > 0) {
            log.info("-------------------启动系统花费了" + min + "分钟" + sec + "秒----------------");
        } else {
            log.info("-------------------启动系统花费了" + sec + "秒-----------------------");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        log.info("---------------------系统关闭---------------------------");
        super.contextDestroyed(event);
    }

    public static ApplicationContext getContext() {
        return ctx;
    }
}
