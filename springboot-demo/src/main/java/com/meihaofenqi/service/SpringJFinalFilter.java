package com.meihaofenqi.service;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class SpringJFinalFilter implements Filter {

    private Filter jfinalFilter;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        jfinalFilter = createJFinalFilter("com.jfinal.core.JFinalFilter");
        jfinalFilter.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        jfinalFilter.doFilter(request, response, chain);
    }

    @Override
    public void destroy() {
        jfinalFilter.destroy();
    }

    private Filter createJFinalFilter(String filterClass) {
        Object temp = null;
        try {
            temp = Class.forName(filterClass).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Can not create instance of class: " + filterClass, e);
        }

        if (temp instanceof Filter) {
            return (Filter) temp;
        } else {
            throw new RuntimeException("Can not create instance of class: " + filterClass + ".");
        }
    }
}
