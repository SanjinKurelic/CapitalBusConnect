package eu.sanjin.kurelic.cbc.view.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private static final String NOT_FOUND_PAGE = "error/not_found";
    private static final String BAD_REQUEST_PAGE = "error/bad_request";

    private final static Logger LOG = Logger.getLogger(ExceptionControllerAdvice.class.getName());

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFoundError() {
        return new ModelAndView(NOT_FOUND_PAGE);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView otherError(Throwable throwable) {
        var viewModel = new ModelAndView(BAD_REQUEST_PAGE);
        // log error for revision - we could store it to file or database
        LOG.log(Level.WARNING, throwable.getMessage(), throwable);
        return viewModel;
    }

}
