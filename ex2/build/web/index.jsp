<%-- 
    Document   : index
    Created on : Dec 23, 2013, 4:25:22 PM
    Author     : Moti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        	<h2>Add To Database:</h2>
	<form action="addServlet" method="POST">
		<p>
			<label for="addName">Name:</label>
			<input type="text" id="addName" name="addName"/>
		</p>
		<p>
			<label for="addID">ID:</label>
			<input type="text" id="addID" name="addID"/>
		</p>
		<p>
			<label for="addPrice">Price:</label>
			<input type="text" id="addPrice" name="addPrice"/>
		</p>
		<p>
			<label for="addDescription">Description:</label>
			<textarea name="addDescription" id="addDescription" cols="20" rows="3"></textarea>
		</p>
			<label for="addQuantity">Quantity:</label>			
			<input type="number" name="addQuantity" id="addQuantity" min="0" value="1"/>
		<p>
			<input type="submit" value="Add" class="button"/>
		</p>
	</form>

    </body>
</html>
