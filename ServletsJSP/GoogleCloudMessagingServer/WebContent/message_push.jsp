<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="ro.pub.cs.systems.pdsd.lab08.googlecloudmessaging.general.Constants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />
        <title><%= Constants.APPLICATION_NAME %></title>
        <link rel="stylesheet" type="text/css" href="css/googlecloudmessaging.css" />
    </head>
    <body>
        <sql:setDataSource var="connection" url="<%= Constants.DATABASE_CONNECTION %>" user="<%= Constants.DATABASE_USERNAME %>" password="<%= Constants.DATABASE_PASSWORD %>" />
        <sql:query dataSource="${connection}" var="registered_devices">
            SELECT username, email, timestamp, id, registration_id FROM registered_devices;
        </sql:query>
        <form name="registered_devices" action="MessagePushServlet" method="post">
	        <table style="border:none; width:auto; margin-left:auto; margin-right:auto;">
	    		<tr>
	      			<td><img style="margin-left:auto; margin-right:auto;" src="images/pdsd_logo.png" /></td>
	      			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
	      			<td><h2 style="text-align:center; vertical-align:middle"><%= Constants.APPLICATION_NAME %></h2></td>
	    		</tr>
	  		</table>
	  		<br/>
	  		<c:if test="${registered_devices.rowCount > 0}" >
		  		<c:forEach var="registered_device" items="${registered_devices.rows}">
	            	<table style="background-color:#ffffff; border:none; width:auto; margin-left:auto; margin-right:auto;">
	          			<tr>
	            			<th>ATTRIBUTE</th>
	            			<th>VALUE</th>
	          			</tr>
	          			<tr style="background-color: #ebebeb">
				            <td>Username</td>
				            <td>${registered_device["username"]}</td>
				        </tr>
				        <tr style="background-color: #ebebeb">
				            <td>Email</td>
				            <td>${registered_device["email"]}</td>
				        </tr>
				        <tr style="background-color: #ebebeb">
				            <td>Timestamp</td>
				            <td>${registered_device["timestamp"]}</td>
				        </tr>
				        <tr style="background-color: #ebebeb">
				            <td>Message</td>
				            <td>
							    <textarea name="message#${registered_device['id']}" rows="5" cols="25"></textarea>
				            </td>
				        </tr>
				        <tr>
				            <td style="text-align:center;" colspan="2">
				                <input type="submit" name="message_push#$row['id']" value="Push Message" />
				            </td>
				        </tr>
				        <tr>
				            <td style="text-align:center;" colspan="2">
				                <input type="hidden" name="registration_id#$row['id']" value="$row['registration_id']" />
				            </td>
				        </tr>          
	          		</table>                      
			    </c:forEach>
		    </c:if>
        </form>
    </body>
</html>  