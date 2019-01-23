package com.example.netty.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.Servlet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 保存，计算URL-pattern与请求路径的匹配关系
 */
@Slf4j
public class RequestUrlPatternMapper {
    private UrlPatternContext urlPatternContext;
    private String contextPath;

    private class UrlPatternContext {
        MappedServlet defaultServlet = null; //默认Servlet
        Map<String, MappedServlet> exactServlets = new HashMap<>(); //精确匹配
        List<MappedServlet> wildcardServlets = new LinkedList<>(); //路径匹配
        Map<String, MappedServlet> extensionServlets = new HashMap<>(); //扩展名匹配
    }

    private class MappedServlet extends MapElement<Servlet> {
        @Override
        public String toString() {
            return pattern;
        }

        String servletName;

        MappedServlet(String name, Servlet servlet, String servletName) {
            super(name, servlet);
            this.servletName = servletName;
        }
    }

    private class MapElement<T> {
        final String pattern;
        final T object;

        MapElement(String pattern, T object) {
            this.pattern = pattern;
            this.object = object;
        }
    }
}
