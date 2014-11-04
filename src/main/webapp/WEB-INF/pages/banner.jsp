<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div id="banner">
	<div id="banner-content">
	    <sec:authorize access="isAuthenticated()">
	    <div>
	        Hello <sec:authentication property="principal.username" />!
	        <a href="<s:url value="/user/all" />">Users</a>
	        <a href="<s:url value="/tweet/timeline" />">Timeline</a>
	        <a href="<s:url value="/j_spring_security_logout" />">Logout</a>
	    </div>
	    <div id="tweetArea">
	       <form method="POST" action="<s:url value="/tweet" />">
	           <textarea id="text" name="text" rows="3" cols="40"></textarea>
	           <input name="commit" type="submit" value="Post it!" />
	       </form>
	    </div>
	    </sec:authorize>
	    
	    <sec:authorize access="!isAuthenticated()">
	        Hello anonymous!
	        <a href="<s:url value="/spring_security_login" />">Sign In</a>
	        <a href="<s:url value="/user/new" />">Sign Up</a>
	    </sec:authorize>
	</div>
</div>