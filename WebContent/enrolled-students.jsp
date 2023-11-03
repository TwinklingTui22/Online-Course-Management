
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<title>Online Course Management System</title>
<link type="text/css" rel="stylesheet" href="css/enrolledStudents.css">
</head>
<body>
	<header class="header">
		<div class="website-name">DORIME</div>
		<div class="nav">
			<div class="butto">
				<a href="logout" class="logout">Log Out</a>
			</div>
			<ion-icon class="icons" name="person"></ion-icon>
			<h2>${USERNAME}</h2>
		</div>

	</header>
	<main>
		<section>
			<div class="course-title">${TITLE}</div>

		</section>
		<hr>
		<div class="void-2"></div>
		<section>
			<div class="student-list">
				<div class="people">
					<div class="list-title">Enrolled Students</div>

					<div class="container-4">
						<c:forEach var="tempStudent" items="${ENROLLED_STUDENTS}">
							<div class="border">
								<div class="student-info">
									<div class="user-name2">${tempStudent.username}</div>
									<div class="user-email">${tempStudent.email}</div>
								
								</div>
							</div>



							<div class="void-clean"></div>
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