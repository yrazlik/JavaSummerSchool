<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.example.jdbc.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<%
	List<Student> students = (List<Student>) request.getAttribute("STUDENT_LIST");

%>
<body>
	<div id="wrapper">
		<div id="header">
			<h1>Students</h1>
		</div>
	</div>
	
	<div id="container">
	
		<input type="submit" value="Add Student" onclick="window.location.href='add-student-form.jsp'; return false;" class="add-student-button" />
		<table>
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
			</tr>
			<% if(students != null) {
				for(Student student : students) { %>
					<tr>
						<td><%= student.getFirstName() %></td>
						<td><%= student.getLastName() %></td>
						<td><%= student.getEmail() %></td>
						<td><a href="StudentControllerServlet?command=SHOW_UPDATE_FORM&id=<%=student.getId()%>&firstName=<%=student.getFirstName()%>&lastName=<%=student.getLastName()%>&email=<%=student.getEmail()%>">Update</a></td>
					</tr>	
			<% }} %>
		</table>
	</div>

</body>
</html>