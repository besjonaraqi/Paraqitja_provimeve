<?php
	@session_start();
	if(($_SERVER['REQUEST_METHOD'] == "POST") && isSet($_POST['btn_submit'])){
		$email = strtolower($_POST['email']);
		$password = $_POST['password'];
		include "mysql.php";
		$query = "SELECT * FROM t_user WHERE u_email = '$email'";
		$result = mysql_query($query);
		$number_of_rows = mysql_num_rows($result);
	
		if($number_of_rows == 1){
			
			$row = mysql_fetch_array($result);
			$password = sha1($password);
			$query_pass = "SELECT * FROM t_user WHERE u_email = '$email' AND u_password = '$password'";
			$result_pass = mysql_query($query_pass);
			$number_of_rows_pass = mysql_num_rows($result_pass);
			if($number_of_rows_pass == 1){
				$_SESSION['user_logged_in'] = true;
				$_SESSION['user_id'] = $row['u_id'];
				$_SESSION['user_fullname'] = $row['u_emri'] . " " . $row['u_mbiemri'];
				header("Location: shto.php");
			}
			else{
				header("Location: login1?error=login");
			}
		}
		else{
			header("Location: login1.php?error=login");
		}
	}
	else{
		header("Location: login1.php?error=requestmethod");
	}
?>