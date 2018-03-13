package com.mmall.controller.common.interceptor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisSharededPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * create by YuWen
 */
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");
        //首先拿到请求对应Controller中的方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //解析handlerMethod(方法名,类型)
        String methodName = handlerMethod.getMethod().getName();
        String className = handlerMethod.getBean().getClass().getSimpleName();

        //解析参数,具体参数的key 和 value
        StringBuffer requestParamBuffer = new StringBuffer();
        Map paramMap = request.getParameterMap();
        for (Iterator iterator = paramMap.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry next = (Map.Entry) iterator.next();
            String mapKey = (String) next.getKey();
            String mapValue = StringUtils.EMPTY;

            //request这个参数的map返回的value是一个String 数组
            Object obj = next.getValue();
            if (obj instanceof String[]) {
                String[] strs = (String[]) obj;
                mapValue = Arrays.toString(strs);
            }
            requestParamBuffer.append(mapKey).append("=").append(mapValue).append(",");
        }

        log.info("权限拦截器能拦截到请求，className:{},methodName:{},param:{}",
                className, methodName, requestParamBuffer);
        User user = null;
        String loginToken = CookieUtil.readLoginCookie(request);
        if (StringUtils.isNotEmpty(loginToken)) {
            user = JsonUtil.stringToObj(RedisSharededPoolUtil.get(loginToken), User.class);
        }
        if (user == null || (user.getRole().intValue() != Const.Role.ROLE_ADMIN)) {
            //即返回false 即不允许调用Controller中的方法
            response.reset();     //对response进行重置,否则报异常 getWriter() has already been called for this response.
            response.setCharacterEncoding("UTF-8");     //此处设置编码,否则乱码
            response.setContentType("application/json;charset=UTF-8");      //此处要设置返回值类型,因为全部是json接口

            PrintWriter out = response.getWriter();

            //上传由于富文本控件的特殊要求，要特殊处理返回值，并判断是否登录以及是否有管理员权限
            if (user == null) {
                if (StringUtils.equals(className, "ProductManageController")
                        && StringUtils.equals(methodName, "richtextImgUpload")) {
                    Map map = Maps.newHashMap();
                    map.put("success", false);
                    map.put("msg", "用户未登录，请登录管理员");
                    out.print(JsonUtil.objToString(map));
                } else
                    out.print(JsonUtil.objToString(ServerResponse.createByErrorMessage("拦截器拦截，用户未登录")));
            } else {
                if (StringUtils.equals(className, "ProductManageController")
                        && StringUtils.equals(methodName, "richtextImgUpload")) {
                    Map map = Maps.newHashMap();
                    map.put("success", false);
                    map.put("msg", "无权限操作");
                    out.print(JsonUtil.objToString(map));
                } else
                    out.print(JsonUtil.objToString(ServerResponse.createByErrorMessage("拦截器拦截，用户无权限操作")));
            }
            out.flush();    //将out流中的数据进行一个清空
            out.close();    //关闭

            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
        //可以通过modelAndView参数来改变显示的视图,或者修改方法视图的方法
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion");
    }
}
