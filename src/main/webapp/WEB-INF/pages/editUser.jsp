<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
       <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css" /> ">
    </head>
    <body>
        <jsp:include page="banner.jsp" />
        <div id="main">
            <div id="user_edit_form">
	            <h2>Creating user account</h2>
	            <sf:form method="POST" modelAttribute="user" enctype="multipart/form-data">
	                <fieldset>
	                    <table cellspacing="0">
	                        <tr>
	                            <th><label for="user_nickname">Nickname:</label></th>
	                            <td><sf:input path="nickname" size="20" id="user_nickname" /></td>
	                            <td>
	                               <div class="errors">
	                                   <sf:errors path="nickname" />
	                               </div>
	                            </td>
	                        </tr>
	                        <tr>
	                            <th><label for="user_firstname">Firstname:</label></th>
	                            <td><sf:input path="firstname" size="20" id="user_firstname" /></td>
	                        </tr>
	                        <tr>
	                            <th><label for="user_lastname">Lastname:</label></th>
	                            <td><sf:input path="lastname" size="20" id="user_lastname" /></td>
	                        </tr>
	                        <tr>
	                            <th><label for="user_password">Password:</label></th>
	                            <td><sf:password path="password" size="20" showPassword="true" id="user_password" /></td>
	                        </tr>
	                        <tr>
	                            <th><label for="user_email">Email:</label></th>
	                            <td><sf:input path="email" size="20" id="user_email" /></td>
	                        </tr>
	                        <tr>
	                            <th></th>
	                            <td>
	                                <sf:checkbox path="updateByEmail" id="user_send_mail_updates" />
	                                <label for="user_send_mail_updates">Send me mail updates!</label>
	                            </td>
	                        </tr>
	                        <tr>
	                           <th><label for="image">Profile image:</label></th>
	                           <td><input name="image" type="file" /></td>
	                        </tr>
	                        <tr><th></th></tr>
	                        <tr>
	                            <td><input name="commit" type="submit" value="I accept. Create my account." /></td>
	                        </tr>                        
	                        <tr>
	                        <th></th>
	                        <td>
		                        <div class="errors">
		                           <sf:errors path="*" />
		                        </div>
	                        </td>
	                        </tr>
	                    </table>
	                </fieldset>
	            </sf:form>          
            </div>  
        </div>
    </body>
</html>