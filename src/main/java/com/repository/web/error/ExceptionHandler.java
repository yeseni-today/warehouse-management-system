package com.repository.web.error;

import com.google.common.base.Throwables;
import com.repository.base.BaseController;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import static com.repository.common.Constants.HTML_ERROR_GENERAL;

/**
 * General error handler for the application.
 */
@ControllerAdvice
class ExceptionHandler extends BaseController {

    /**
     * Handle exceptions thrown by handlers. 非404
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ModelAndView exception(Exception exception, WebRequest request) {
        ModelAndView modelAndView = new ModelAndView(HTML_ERROR_GENERAL);
        Throwable rootCause = Throwables.getRootCause(exception);
        modelAndView.addObject("errorMessage", rootCause);
        logger.error(rootCause.toString(), exception);
        return modelAndView;
    }
}