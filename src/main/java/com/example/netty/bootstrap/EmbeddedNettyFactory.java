package com.example.netty.bootstrap;

import com.example.netty.core.NettyContext;
import io.netty.bootstrap.Bootstrap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.embedded.AbstractEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * https://leibnizhu.gitlab.io/2017/08/24/%E5%9F%BA%E4%BA%8ENetty%E7%9A%84Spring%20Boot%E5%86%85%E7%BD%AEServlet%E5%AE%B9%E5%99%A8%E7%9A%84%E5%AE%9E%E7%8E%B0%EF%BC%88%E4%B8%80%EF%BC%89/index.html
 * Spring Boot会查找EmbeddedServletContainerFactory接口的实现类(工厂类)，调用其getEmbeddedServletContainer()方法，来获取web应用的容器
 * 所以我们要实现这个接口，这里不直接实现，而是通过继承AbstractEmbeddedServletContainerFactory类来实现
 */
@Slf4j
public class EmbeddedNettyFactory extends AbstractEmbeddedServletContainerFactory implements ResourceLoaderAware{
    private static final String SERVER_INFO = "Netty@SpringBoot";
    private ResourceLoader resourceLoader;

    @Override
    public EmbeddedServletContainer getEmbeddedServletContainer(ServletContextInitializer... servletContextInitializers) {
        ClassLoader parentClassLoader = resourceLoader != null ? resourceLoader.getClassLoader() : ClassUtils.getDefaultClassLoader();
        //Netty启动环境相关信息
        Package nettyPackage = Bootstrap.class.getPackage();
        String title = nettyPackage.getImplementationTitle();
        String version = nettyPackage.getImplementationVersion();
        log.info("Running with " + title + " " + version);
        //是否支持默认Servlet
        if (isRegisterDefaultServlet()) {
            log.warn("This container does not support a default servlet");
        }
        //上下文
        NettyContext context = new NettyContext(getContextPath(), new URLClassLoader(new URL[]{}, parentClassLoader), SERVER_INFO);
        return null;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {

    }
}
