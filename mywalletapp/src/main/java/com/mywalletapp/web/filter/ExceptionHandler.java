package com.mywalletapp.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.mywalletapp.logger.Logger;
import com.mywalletapp.logger.LoggerFactoryUtil;
import com.mywalletapp.web.exception.UiException;
import com.mywalletapp.web.exception.WrappedServletException;

public class ExceptionHandler extends OncePerRequestFilter {

  private final static Logger LOG = LoggerFactoryUtil.getLogger(com.mywalletapp.web.filter.ExceptionHandler.class);

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (ServletException se) {
      Throwable ex = getException(se);
      LOG.error(ex.getMessage(), ex);
      redirect(request, response, ex);
    } catch (Throwable t) {
      LOG.error(t.getMessage(), t);
      redirect(request, response, t);
    }
  }

  private void redirect(HttpServletRequest request, HttpServletResponse response, Throwable ex) throws ServletException, IOException {
    request.setAttribute("exception", ex);
    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/error.jsp");
    dispatcher.forward(request, response);
  }

  private Throwable getException(ServletException se) {
    Throwable rootCause = se.getRootCause();
    if (UiException.class.equals(rootCause.getClass())) {
      return rootCause;
    } else {
      return new WrappedServletException("General internal error", se);
    }
  }
}
