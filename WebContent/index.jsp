<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page import="java.util.*" %>
<%@ page import="com.example.dorime.*" %>

<%
	String[] cities = {"Mumbai", "Singapore", "Philadelphia"};
	pageContext.setAttribute("myCities", cities);
	ArrayList<Course> cc = new ArrayList<>();
%>

<html>
<body>
<h1>Index Page</h1>
	<c:forEach var="tempCity" items="${myCities}" >
		${tempCity} <br/>
	</c:forEach>

</body>


<c:set var="stringToSplit" value="apple,banana,orange" />

<c:forEach items="${fn:split(stringToSplit, ',')}" var="fruit">
  ${fruit}<br>
</c:forEach>
</html>