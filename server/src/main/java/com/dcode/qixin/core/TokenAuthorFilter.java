package com.dcode.qixin.core;

import com.dcode.qixin.service.UserService;
import com.dcode.qixin.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@WebFilter(urlPatterns = { "/*" }, filterName = "tokenAuthorFilter")
public class TokenAuthorFilter implements Filter {

    private static Logger logger = LoggerFactory
            .getLogger(TokenAuthorFilter.class);

    @Autowired
    protected UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse rep = (HttpServletResponse) servletResponse;

        //设置允许跨域的配置
        // 这里填写你允许进行跨域的主机ip（正式上线时可以动态配置具体允许的域名和IP）
        rep.setHeader("Access-Control-Allow-Origin", "*");
        // 允许的访问方法
        rep.setHeader("Access-Control-Allow-Methods","POST, GET, PUT, OPTIONS, DELETE, PATCH");
        // Access-Control-Max-Age 用于 CORS 相关配置的缓存
        rep.setHeader("Access-Control-Max-Age", "3600");
        rep.setHeader("Access-Control-Allow-Headers","token,Origin, X-Requested-With, Content-Type, Accept");

        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("application/json; charset=utf-8");
        String token = req.getHeader("token");//header方式
        Result resultInfo = new Result();

        boolean isFilter = false;

        if(StringUtils.isEmpty(token)) {
            token = "1";
        }

        String method = ((HttpServletRequest) servletRequest).getMethod();
        if (method.equals("OPTIONS")) {
            rep.setStatus(HttpServletResponse.SC_OK);
        }else{
            if (null == token || token.isEmpty()) {
                resultInfo.setCode(ResultCode.UNAUTHORIZED);
                resultInfo.setMessage("用户授权认证没有通过!客户端请求参数中无token信息");
            } else {
                if (null != userService.getUserByToken(token)
                        || !((HttpServletRequest) servletRequest).getRequestURI().contains("/api/")  //非api不需要token验证
                        || ((HttpServletRequest) servletRequest).getRequestURI().equals("/api/user/login")) {
                    resultInfo.setCode(ResultCode.SUCCESS);
                    resultInfo.setMessage("用户授权认证通过!");
                    isFilter = true;
                } else {
                    resultInfo.setCode(ResultCode.UNAUTHORIZED);
                    resultInfo.setMessage("用户授权认证没有通过!客户端请求参数token信息无效");
                }
            }
            if (resultInfo.getCode() == ResultCode.UNAUTHORIZED.code()) {// 验证失败
                PrintWriter writer = null;
                OutputStreamWriter osw = null;
                try {
                    osw = new OutputStreamWriter(servletResponse.getOutputStream(),
                            "UTF-8");
                    writer = new PrintWriter(osw, true);
                    String jsonStr = JSON.toJSONString(resultInfo);
                    writer.write(jsonStr);
                    writer.flush();
                    writer.close();
                    osw.close();
                } catch (UnsupportedEncodingException e) {
                    logger.error("过滤器返回信息失败:" + e.getMessage(), e);
                } catch (IOException e) {
                    logger.error("过滤器返回信息失败:" + e.getMessage(), e);
                } finally {
                    if (null != writer) {
                        writer.close();
                    }
                    if (null != osw) {
                        osw.close();
                    }
                }
                return;
            }

            if (isFilter) {
                logger.info("token filter过滤ok!");
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
