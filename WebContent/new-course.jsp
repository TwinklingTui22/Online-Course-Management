<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link type="text/css" rel="stylesheet" href="css/newCourse.css">
</head>
<body>
	<header class="header">
		<div class="website-name">DORIME</div> 	
		


	</header>
	<p class="title">Add New Course</p>
	<div class="container-card">
		<div class="container-void"></div>

		<img id="output" width="200" src="images/placeholder.png" class="container-img"/>
		<div class="container-form">
			<form action="create-course" method="post">
				<input type="file" accept="image/*" name="image-path" id="image" onchange="readImage(this)" class="image-input" required/>
				<label for="title">Title</label> 
				<input type="text" id="title" name="title" required> 
				<label for="details">Details</label>
				<textarea id="details" name="details" required></textarea>
				<div class="row">
					<div class="group">
						<label for="teacher">Course Teacher</label>
						 <select id="teacher" name="course-teacher-id">
						 	<c:set var="list" value="${TEACHERS_LIST}" />
						 	<c:forEach var="teacher" items="${list}" >
						 		<c:if test="${list[0] eq teacher}">
    									<option selected="selected" value="${teacher.userId}">${teacher.username}</option>
								</c:if>
								
									<c:if test="${list[0] ne teacher}">
    									<option value="${teacher.userId}">${teacher.username}</option>
								</c:if>
					 			
							</c:forEach>

							
						</select>
					</div>
					<div class="group">
						<label for="price" class="price">Price($)</label> 
						<input type="number" id="price" name="price" min="0" step="0.01" required>
					</div>

				</div>

				<input type="submit" value="Create">
			</form>


		</div>



	</div>
	<script type="module"
		src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule
		src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
		
	<script>
		function readImage(input){
			if(input.files && input.files[0]){
				var reader = new FileReader();
				reader.onload = function(e){
					document.getElementById("output").setAttribute('src', e.target.result);
					
				};
				reader.readAsDataURL(input.files[0]);
			}
		}
	</script>
</body>
</html>