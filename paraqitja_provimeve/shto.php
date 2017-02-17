<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<?php
	@session_start();
	if($_SESSION['user_logged_in'] == true && isSet($_SESSION['user_id'])){
		include "mysql.php";
		if($_SERVER['REQUEST_METHOD'] == "POST"){
			if(isSet($_POST['btn_submit'])){
				if(empty($_POST['password'])){
					goto password;
				}
				$email = strtolower($_POST['email']);
				$query = "SELECT * FROM t_user WHERE u_email = '$email'";
				$result = mysql_query($query);
				$number_of_rows = mysql_num_rows($result);
				if($number_of_rows == 0){
					
					header("Location: register1.php");
				}
				else{
					password:
					$new = true;
					$title = "username ose password nuk jane ne rregull!";
					$u_indeksi = $_POST['indeksi'];
					$_emri = $_POST['emri'];
					$_mbiemri = $_POST['mbiemri'];
					$email = "";
					//goto a;
				}
			}
		}
	}
?>
	<body>

		<div class="shto">
			<a href="register1.php" class="button1">Shto përdorues</a><br/>
			<br/>
			<a href="lenda.php" class="button2">Shto lëndë</a><br/>
			<br/>
			<a href="logout.php" class="button3">Logout</a>
			
		</div>
	</body>
