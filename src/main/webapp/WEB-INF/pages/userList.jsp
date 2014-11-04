<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<ul class="user-list">
    <c:forEach var="user" items="${userList}" varStatus="loopStatus">
        <li class="rounded-corners ${loopStatus.index % 2 == 0 ? 'odd' : 'even'}">
            <div class="clearfix full-size-wide">
                <s:url var="userProfileLink" value="/user/profile/${user.nickname}" />
                
                <a href="${userProfileLink}">
                   <img class="small-userpic" src="<s:url value="/resources/userpics/${user.userPic}" />"/>
                </a>
                
                <div class="user-info">
                    <div class="username">
                        <a href="${userProfileLink}">${user.nickname}</a>: (${user.firstname} ${user.lastname}) 
                     </div>
                     <div>
                        <jsp:include page="following_manage_form.jsp">
                            <jsp:param name="nickname" value="${user.nickname}"/>
                            <jsp:param name="isFollowing" value="${user.isFollowing}"/>
                        </jsp:include>
                     </div>
                </div>
            </div>
        </li>
    </c:forEach>
</ul>