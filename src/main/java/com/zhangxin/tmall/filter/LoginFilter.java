package com.zhangxin.tmall.filter;


import com.zhangxin.tmall.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();//获取要访问的路径

        //不过滤css、js、jpg、png、gif、bootstrap的字体
        if(uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".jpg") || uri.endsWith(".png") || uri.endsWith("gif") || uri.contains("glyphicon")) {
            filterChain.doFilter(request,response);
            return;
        }

        if(uri.contains("admin") || uri.endsWith("showProducts.do") || uri.endsWith("login.do") || uri.endsWith("logout.do") || uri.endsWith("showProduct.do") || uri.endsWith("register.do") || uri.endsWith("showProductByName.do") || uri.endsWith("login.jsp") || uri.endsWith("register.jsp") || uri.endsWith("search.jsp") || uri.endsWith("classificationPage.jsp") || uri.endsWith("productPage.jsp") || uri.endsWith("login.jsp") || uri.endsWith("index.jsp") || uri.endsWith("SSMTmall/") || uri.endsWith("successRegister.jsp") || uri.endsWith("moTaiLogin.do") || uri.endsWith("findPassword.jsp") || uri.endsWith("findPassword.do")){
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null) {
            response.sendRedirect("/SSMTmall/jsp/anotherPage/login.jsp");
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
