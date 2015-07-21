<%@page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body id="wrapper" class="boundary login">
	<header id="header">
		<a href="http://www.stormpath.com" target="_blank"><div class="sprite logo"></div></a>
	</header>
	<div id="content" class="loginbody">
		<section id="marquee">
			<div class="contentpanel details">
				<!-- When a form does not have an action value, the browser will submit the form request to the same URL.  -->
				<form id="command" action="" method="post">
					<div>
						<label for="email">Email</label>
						<input id="email" name="username" tabindex="1" class="field" type="text" value="" />
					</div>
					<div>
						<label for="password"> Password <a href="/forgotLogin">Forgot Password?</a></label>
						<input id="password" name="password" tabindex="2" class="field" type="password" value="" />
					</div>

					<div id="buttons">
						<div class="login">
							<a href="#" id="loginbutton" class="actionbutton" tabindex="4">Log In</a>
						</div>
					</div>
				</form>
			</div>
			<p class="register-cta">Don't have an account? <a href="/register">Sign Up for Stormpath</a></p>
		</section>
	</div>
</body>
</html>