<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<ul class="tweet-list">
     <c:forEach var="tweet" items="${tweets}" varStatus="loopStatus">
         <li class="rounded-corners ${loopStatus.index % 2 == 0 ? 'odd' : 'even'}">
            <div class="clearfix full-size-wide">
                <s:url var="userProfileLink" value="/user/profile/${tweet.nickname}" />
                
                <a href="${userProfileLink}">
	               <img class="small-userpic" src="<s:url value="/resources/userpics/${tweet.userPic}" />"/>
	            </a>
	            
	            <div class="tweet">
		            <div class="tweet-message"><span class="username"><a href="${userProfileLink}">${tweet.nickname}</a>: </span>${tweet.text}</div>
	                <small><span>Date: <joda:format value="${tweet.tweetDate}" pattern="dd-MMM-yyyy" /></span></small>
	            </div>
            </div>
         </li>
     </c:forEach>
 </ul>