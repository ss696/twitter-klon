<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
       <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css" /> ">
    </head>
    <body>
        <jsp:include page="banner.jsp" />
        <div id="main">
            <div id="user_info">
	            Username: ${user.nickname}<br />
	            First name: ${user.firstname }<br />
	            Last name: ${user.lastname }<br />
	            Email: ${user.email }<br />
	            Update by email: ${user.updateByEmail ? 'Yes' : 'No' }<br />
	            <jsp:include page="following_manage_form.jsp">
                     <jsp:param name="nickname" value="${user.nickname}"/>
                     <jsp:param name="isFollowing" value="${user.isFollowing}"/>
                 </jsp:include><br />
            </div>
            <jsp:include page="tweetList.jsp" />
        </div>
    </body>
</html>