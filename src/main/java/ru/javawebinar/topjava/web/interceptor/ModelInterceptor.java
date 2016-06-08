package ru.javawebinar.topjava.web.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ru.javawebinar.topjava.LoggedUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This interceptor adds the user to the model of every requests managed
 * Нужно для того чтобы в хэдаре отобразить имя пользователя, для этого
 * мы извлекаем объект класса UserTo и кладем его в модель (если его там еще нет)
 * В хэдаре достаем его из модели следующим образом:
 * <a class="btn btn-info" role="button" href="profile">${userTo.name} profile</a>
 */
public class ModelInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && !modelAndView.isEmpty() && modelAndView.getModelMap().get("user") == null) {
            LoggedUser loggedUser = LoggedUser.safeGet();
            if (loggedUser != null) {
                modelAndView.getModelMap().addAttribute("userTo", loggedUser.getUserTo());
            }
        }
    }
}
