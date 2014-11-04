<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<c:choose>
    <c:when test="${param.isFollowing}">
        <form method="POST" action="<s:url value="/user/unfollow" />">
           <input type="hidden" name="userToUnfollow" value="${param.nickname}" />
           <input name="commit" type="submit" value="Unfollow" />
        </form>
    </c:when>
    <c:otherwise>
        <form method="POST" action="<s:url value="/user/follow" />" >
           <input type="hidden" name="userToFollow" value="${param.nickname}" />
           <input name="commit" type="submit" value="Follow" />
        </form>
    </c:otherwise>
</c:choose>