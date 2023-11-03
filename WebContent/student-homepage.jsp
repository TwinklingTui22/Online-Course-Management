<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<title>Dorime - Homepage</title>
<link type="text/css" rel="stylesheet" href="css/Homepage.css">

<script src="https://common.olemiss.edu/_js/sweet-alert/sweet-alert.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://common.olemiss.edu/_js/sweet-alert/sweet-alert.css">
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
					<h1 class="title">My Courses</h1>
					<div class="container-2 grid">
						<c:forEach var="course" items="${ENROLLED_COURSES}">
							<div class="course-item">
								<div class="img">
							<img class="course-img" src="images/${course.image_path}" alt="course image">
						</div>
						<div class="course-title">${course.title}</div>
						<div class="teacher-name">${course.course_teacher_name}</div>
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
								<div class="card-btn">
									<a href="#" class="bttn">Go to course</a>
								</div>
							</div>
						</c:forEach>

					</div>
				</div>
			</div>

		</section>

		<section class="my-courses">
			<div>
				<h1 class="title">All Courses</h1>
				<div class="container-all">
					<div class="container-2 grid">
						<c:forEach var="course" items="${AVAILABLE_COURSES}">
							<div class="course-item">
								<div class="img">
							<img class="course-img" src="images/${course.image_path}" alt="course image">
						</div>
						<div class="course-title">${course.title}</div>
						<div class="teacher-name">${course.course_teacher_name}</div>
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
								
								<form action="homepage" method="post" id="addReg">
									<input type="hidden" name=course_id value=${course.course_id}>
									<input type="submit" class="card-btn" value="$${course.price}" >
								</form>
								

							</div>
						</c:forEach>



					</div>
				</div>
			</div>

		</section>

		<div class="void"></div>
		<footer class="footer">&copy; 2023 Dorime ,Inc </footer>

	</main>
	
	<script>
	
	 var form = document.getElementById("addReg");
	 
	 if(form!=null){
		 
		 form.addEventListener("submit", (event) => {
			  event.preventDefault(); 
			  
			  swal({
				  title: "Buy Course!",
				  text: "Are you sure you want to buy this course?",
				  type: "info",
				  showCancelButton: true,
				  confirmButtonColor: "#77b8a0",
				  confirmButtonText: "Yes, Buy it!",
				  cancelButtonText: "No, cancel pls!",
				  closeOnConfirm: false,
				  closeOnCancel: false
				},
				function(isConfirm){
				  if (isConfirm) {
				    swal({
				    	title:"Congratulations!", 
				    	text:"You bought this course", 
				    	type:"success",
				    }, function(){
				    	form.submit();
				    });
				  } else {
					  swal({
					    title:"Cancelled!", 
					    text:"It's Okay :)", 
					    type:"error",
					  });
				 }
				  
				});
		 });
		 
		 
	 }

	  
	</script>

	<script src="https://unpkg.com/ionicons@4.5.10-0/dist/ionicons.js"></script>
</body>
</html>