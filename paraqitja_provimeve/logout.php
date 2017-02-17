<?php
	@session_start();
	unset($_SESSION['user_logged_in']);
	unset($_SESSION['user_id']);
	session_destroy();
	header("Location: login1.php");
?>