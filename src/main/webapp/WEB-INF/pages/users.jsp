<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<html>
    <head>
       <link rel="stylesheet" type="text/css" href="<s:url value="/resources/css/main.css" /> ">
    </head>
    <body>
        <jsp:include page="banner.jsp" />
        <div id="main">
            <jsp:include page="userList.jsp" />
        </div>
    </body>
</html>
        