package am.smartCode.jdbc.filter;

import am.smartCode.jdbc.util.constants.Path;
import am.smartCode.jdbc.util.constants.Strings;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println(Strings.FILTER_INITIALIZED);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String email =(String) httpRequest.getSession().getAttribute(Strings.EMAIL);
        if (email == null){
            httpRequest.setAttribute(Strings.MESSAGE,Strings.PLEASE_LOGIN);
            httpResponse.setStatus(401);
            httpRequest.getRequestDispatcher(Path.REGISTER).forward(httpRequest,httpResponse);
        }else {
            filterChain.doFilter(httpRequest,httpResponse);
        }
    }

    @Override
    public void destroy() {
        System.out.println(Strings.FILTER_DESTROYED);
    }
}
