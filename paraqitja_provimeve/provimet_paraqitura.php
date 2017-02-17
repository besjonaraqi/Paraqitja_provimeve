<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

	$response = $db->provimetParaqitura($_POST['lendaId'], $_POST['userId']);
	echo json_encode($response);		
?>