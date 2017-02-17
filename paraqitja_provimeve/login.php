<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
$response = array("error" => FALSE);
 
if (isset($_POST['email']) && isset($_POST['password'])) {
 
    $email = $_POST['email'];
    $password = $_POST['password'];
 
    $user = $db->getUserByEmailAndPassword($email, $password);
	
	
    if ($user != false) {
        $response["error"] = FALSE;
        $response["uid"] = $user["u_id"];
        $response["user"]["u_emri"] = $user["u_emri"];
        $response["user"]["u_email"] = $user["u_email"];
		$response["user"]["u_mbiemri"] = $user["u_mbiemri"];
		$response["user"]["u_kampusi"] = $user["u_kampusi"];
		$response["user"]["u_departamenti"] = $user["u_departamenti"];
		$response["user"]["u_indeksi"] = $user["u_indeksi"];
		$response["user"]["u_tipi"] = $user["u_tipi"];
		$response["user"]["u_datelindja"] = $user["u_datelindja"];
		$response["user"]["u_password"] = $user["u_password"];
        $response["user"]["u_createdAt"] = $user["u_createdAt"];
        $response["user"]["u_updatedAt"] = $user["u_updatedAt"];
        echo json_encode($response);
    } else {
        $response["error"] = TRUE;
        $response["error_msg"] = "Email apo password është gabim. Ju lutem provoni edhe një herë!";
        echo json_encode($response);
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Parametrat e nevojshëm të email apo password mungojnë!";
    echo json_encode($response);
}
?>