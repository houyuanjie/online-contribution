<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hello</title>
</head>
<body>

<h1>Hello [[<%= request.getRemoteUser() %>]]!</h1>
<form action="<c:url value="/logout"/>" method="post">
    <input type="submit" value="Sign Out"/>
</form>

</body>
</html>
