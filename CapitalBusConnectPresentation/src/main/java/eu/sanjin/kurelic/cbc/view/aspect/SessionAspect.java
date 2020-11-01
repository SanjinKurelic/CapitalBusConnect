package eu.sanjin.kurelic.cbc.view.aspect;

import eu.sanjin.kurelic.cbc.business.services.CartService;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItems;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Aspect
@Component
public class SessionAspect {

  private final CartService service;

  public SessionAspect(CartService service) {
    this.service = service;
  }

  // Advices
  @Before("@annotation(readFromSession)")
  public void loadFromSession(ReadFromSession readFromSession) {
    var session = getSession();
    if (session.getAttribute(readFromSession.value()) == null) {
      session.setAttribute(readFromSession.value(), new CartItems());
    }
    service.loadCartItems((CartItems) session.getAttribute(readFromSession.value()));
  }

  @AfterReturning("@annotation(saveToSession)")
  public void saveToSession(SaveToSession saveToSession) {
    var session = getSession();
    session.setAttribute(saveToSession.value(), service.getCartItems());
  }

  // Utility
  private HttpSession getSession() {
    return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession(true);
  }
}
