package mx.admino.interceptors;

import mx.admino.services.CondominioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Agrega los condominios de los cuales el usuario es administrador.
 */
@Component
public class CondominosHandlerInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(CondominosHandlerInterceptor.class);

    @Autowired
    CondominioService condominioSrv;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {

        if (request.getUserPrincipal() != null) {
            logger.info("postHandler: " + request.getUserPrincipal().getName());
            /// TODO. Falta obtener los  condominos (maximo 5) donde soy administrador y agregarlos al modelo.
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logger.info("afterCompletion");
    }
}
