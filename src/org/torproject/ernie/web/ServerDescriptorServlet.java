package org.torproject.ernie.web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.regex.*;

public class ServerDescriptorServlet extends HttpServlet {

  private Connection conn = null;

  public void init() {

    /* Try to load the database driver. */
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      /* Don't initialize conn and always reply to all requests with
       * "500 internal server error". */
      return;
    }

    /* Read JDBC URL from deployment descriptor. */
    String connectionURL = getServletContext().
        getInitParameter("jdbcUrl");

    /* Try to connect to database. */
    try {
      conn = DriverManager.getConnection(connectionURL);
    } catch (SQLException e) {
      conn = null;
    }
  }

  public void doGet(HttpServletRequest request,
      HttpServletResponse response) throws IOException,
      ServletException {

    /* Check if we have a database connection. */
    if (conn == null) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }

    /* Check desc-id parameter. */
    String descIdParameter = request.getParameter("desc-id");
    if (descIdParameter == null || descIdParameter.length() < 8 ||
        descIdParameter.length() > 40) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }
    String descId = descIdParameter.toLowerCase();
    Pattern descIdPattern = Pattern.compile("^[0-9a-f]+$");
    Matcher descIdMatcher = descIdPattern.matcher(descId);
    if (!descIdMatcher.matches()) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    /* Look up descriptor in the database. */
    String descriptor = null;
    byte[] rawDescriptor = null;
    try {
      Statement statement = conn.createStatement();
      String query = "SELECT descriptor, rawdesc FROM descriptor "
          + "WHERE descriptor LIKE '" + descId + "%'";
      ResultSet rs = statement.executeQuery(query);
      if (rs.next()) {
        descriptor = rs.getString(1);
        rawDescriptor = rs.getBytes(2);
      }
    } catch (SQLException e) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }

    /* Write response. */
    if (rawDescriptor == null) {
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    }
    try {
      response.setContentType("text/plain");
      response.setHeader("Content-Length", String.valueOf(
          rawDescriptor.length));
      response.setHeader("Content-Disposition", "inline; filename=\""
          + descriptor + "\"");
      BufferedOutputStream output = new BufferedOutputStream(
          response.getOutputStream());
      output.write(rawDescriptor);
    } finally {
      /* Nothing to do here. */
    }
  }
}

