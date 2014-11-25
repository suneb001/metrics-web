<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
  <title>Tor Metrics: Number of bytes spent on answering directory requests</title>
  <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1">
  <link href="/css/stylesheet-ltr.css" type="text/css" rel="stylesheet">
  <link href="/images/favicon.ico" type="image/x-icon" rel="shortcut icon">
</head>
<body>
  <div class="center">
    <%@ include file="banner.jsp"%>
    <div class="main-column">

<h3>Tor Metrics: Number of bytes spent on answering directory requests</h3>
<br>
<p>The following graph shows the portion of
<a href="about.html#bandwidth-history">consumed bandwidth</a> that
<a href="about.html#directory-authority">directory authorities</a> and
<a href="about.html#directory-mirror">mirrors</a> have spent on answering
directory requests.
Not all directories report these statistics, so the graph shows an
estimation of total consumed bandwidth as it would be observed if all
directories reported these statistics.</p>
<img src="dirbytes.png${dirbytes_url}"
     width="576" height="360" alt="Dir bytes graph">
<form action="dirbytes.html">
  <div class="formrow">
    <input type="hidden" name="graph" value="dirbytes">
    <p>
    <label>Start date (yyyy-mm-dd):</label>
      <input type="text" name="start" size="10"
             value="<c:choose><c:when test="${fn:length(dirbytes_start) == 0}">${default_start_date}</c:when><c:otherwise>${dirbytes_start[0]}</c:otherwise></c:choose>">
    <label>End date (yyyy-mm-dd):</label>
      <input type="text" name="end" size="10"
             value="<c:choose><c:when test="${fn:length(dirbytes_end) == 0}">${default_end_date}</c:when><c:otherwise>${dirbytes_end[0]}</c:otherwise></c:choose>">
    </p><p>
    <input class="submit" type="submit" value="Update graph">
    </p>
  </div>
</form>
<p>Download graph as
<a href="dirbytes.pdf${dirbytes_url}">PDF</a> or
<a href="dirbytes.svg${dirbytes_url}">SVG</a>.</p>
<p><a href="stats/bandwidth.csv">CSV</a> file containing all data.</p>
<br>

    </div>
  </div>
  <div class="bottom" id="bottom">
    <%@ include file="footer.jsp"%>
  </div>
</body>
</html>