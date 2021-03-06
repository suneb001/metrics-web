/* Copyright 2017--2018 The Tor Project
 * See LICENSE for licensing information */

package org.torproject.metrics.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServicesServlet extends AnyServlet {

  private static final long serialVersionUID = 516625494518844400L;

  @Override
  public void doGet(HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

    request.setAttribute("categories", this.categories);
    request.getRequestDispatcher("WEB-INF/services.jsp").forward(request,
        response);
  }
}

