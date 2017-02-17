<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

	$lendet = $db->kerkolende($_POST['id'], $_POST['departamenti']);
	echo json_encode($lendet);
	//$lendet = '{"error":false,"lendet":['.$lendet.'}';
	//echo json_encode($lendet);
	/*if ($lendet != false){	
			$response["lendet"]["l_id"] = $item["l_id"];
			$response["lendet"]["l_emri"] = $item["l_emri"];
			$response["lendet"]["l_semestri_id"] = $item["l_semestri_id"];
			$response["lendet"]["l_departamenti_id"] = $item["l_departamenti_id"];
			$response["lendet"]["l_kredi"] = $item["l_kredi"];
			//echo json_encode($response);
	}
	else {
        $response["error"] = TRUE;
        $response["error_msg"] = "Nuk ka lende";
        echo json_encode($response);
    }*/
?>