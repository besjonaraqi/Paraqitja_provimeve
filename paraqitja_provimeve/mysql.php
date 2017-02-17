<?php
	@session_start();
	$host = "localhost";
	$user = "root";
	$pass = "besjonaraqi";
	$db = "db_paraqitja_provimeve";
	
	mysql_connect($host,$user,$pass);
	@mysql_select_db($db) or die( "Unable to select database");
?>