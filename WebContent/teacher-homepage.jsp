<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!DOCTYPE html>
<html>
<head>
<title>Dorime - Homepage</title>
<link type="text/css" rel="stylesheet" href="css/Homepage.css">
</head>
<body>

	<header class="header">
		<div class="website-name">DORIME</div>
		<div class="nav">
			<div class="button">
				<a href="logout" class="logout">Log Out</a>
			</div>

			<ion-icon class="icons" name="person"></ion-icon>
			<h2>${USERNAME}</h2>
		</div>

	</header>
	<main>





		<section class="my-courses">
			<div>

				<div class="container">
					<h1 class="title">My Teachings</h1>
					<div class="container-2 grid">
					<c:forEach var="course" items="${ASSIGNED_COURSES}">
					
					<c:url var="viewStudentsLink" value="enrolled-students">
						<c:param name="course_id" value="${course.course_id}" />
						<c:param name="title" value="${course.title}" />
					</c:url>
					
					<div class="course-item">
								<div class="img">
									<img class="course-img" src="images/${course.image_path}" alt="course image">
								</div>
								<div class="course-title">${course.title}</div>
<%-- 								<div class="teacher-name">${course.course_teacher_name}</div> --%>
								<div class="course-description">
									<c:set var="details" value="${course.details}"/>
									<div class="container-3">
										<ul>
											<c:forEach var="item" items="${fn:split(details, '.')}">
												<li>${item}</li>
											</c:forEach>
										</ul>
									</div>
								</div>		
								<button class="card-btn">$${course.price}</button>
							
							</div>
					</c:forEach>
					
					</div>
				</div>
			</div>

		</section>


		<div class="void"></div>
		<footer class="footer">&copy; 2023 Dorime ,Inc </footer>

	</main>

	<script src="https://unpkg.com/ionicons@4.5.10-0/dist/ionicons.js"></script>
</body>
</html>