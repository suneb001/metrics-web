<jsp:useBean id="template" class="org.torproject.ernie.web.TemplateController" scope="request" />
<jsp:setProperty name="template" property="template" value="graphs_packages"/>
<jsp:setProperty name="template" property="title" value="Tor Metrics Portal: Packages"/>
<%@ include file="/WEB-INF/templates/main.tpl.jsp" %>