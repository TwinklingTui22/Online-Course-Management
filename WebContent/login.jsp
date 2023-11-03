<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
    <head>
		 <title>Dorime - Login</title>
		 <link type="text/css" rel="stylesheet" href="css/login.css">
	</head>
	<body>
	    <header class="header">
	    DORIME
	    </header>
		<main>
		
			<div class="container">
				<div class="container-2">
					<div class="container-3">
						<img class="img"  src="images/img.jpg" alt="course image" >
									 
									 
					</div>
					
					
					<form class="form" action="login" method="post">
					
					<h1>Sign In</h1>
					<div class="input-field">
					<ion-icon class="icons" name="mail"></ion-icon>
					<input type="email" placeholder="Your Email" class="email" name="email">
					</div>
					
					<br><br><br>
					<div class="input-field">
					<ion-icon class="icons" name="key"></ion-icon>
					<input type="password" placeholder="Password" class="pass" name="password">
					
					</div>
					
					<br><br><br><br><br><br><br>
					<input type="submit" class="button" value="GO!">
					</form>					
				</div>
			</div>
		</main>
	    <script src="https://unpkg.com/ionicons@4.5.10-0/dist/ionicons.js"></script>
	</body>
</html>