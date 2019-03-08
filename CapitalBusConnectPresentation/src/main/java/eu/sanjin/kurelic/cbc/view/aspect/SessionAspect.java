package eu.sanjin.kurelic.cbc.view.aspect;

import eu.sanjin.kurelic.cbc.business.services.CartService;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItems;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Aspect
@Component
public class SessionAspect {
    private final CartService service;

    @Autowired
    public SessionAspect(@Qualifier("cartServiceImpl") CartService service) {
        this.service = service;
    }

    // Advices
    @Before("@annotation(readFromSession)")
    public void loadFromSession(JoinPoint joinPoint, ReadFromSession readFromSession) {
        var session = getSession();
        if(session.getAttribute(readFromSession.sessionKey()) == null) {
            session.setAttribute(readFromSession.sessionKey(), new CartItems());
        }
        service.loadCartItems((CartItems) session.getAttribute(readFromSession.sessionKey()));
    }

    @AfterReturning("@annotation(saveToSession)")
    public void saveToSession(JoinPoint joinPoint, SaveToSession saveToSession) {
        var session = getSession();
        session.setAttribute(saveToSession.sessionKey(), service.getCartItems());
    }

    // Helpers
    private HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession(true);
    }
}
