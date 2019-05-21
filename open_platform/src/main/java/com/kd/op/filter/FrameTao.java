package com.kd.op.filter;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrameTao implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        //必须
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        //实际设置
        response.setHeader("X-Frame-Options","SAMEORIGIN");//SAMEORIGIN：不允许被本域以外的页面嵌入
        response.setHeader("X-XSS-Protection","1; mode=block");//1; mode=block：启用XSS保护，并在检查到XSS攻击时，停止渲染页面
        response.setHeader("Content-Security-Policy","img-src 'self'");//这个响应头主要是用来定义页面可以加载哪些资源，减少XSS的发生
        response.setHeader("X-Content-Type-Options","nosniff");//互联网上的资源有各种类型，通常浏览器会根据响应头的Content-Type字段来分辨它们的类型。通过这个响应头可以禁用浏览器的类型猜测行为
//        //调用下一个过滤器（这是过滤器工作原理，不用动）
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
