<?php
class DB_Functions {
 
    private $conn;
 
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }
    function __destruct() {
         
    }

    public function storeUser($name, $email, $password) {
        $uuid = uniqid('', true);
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt
 
        $stmt = $this->conn->prepare("INSERT INTO users(unique_id, name, email, encrypted_password, salt, created_at) VALUES(?, ?, ?, ?, ?, NOW())");
        $stmt->bind_param("sssss", $uuid, $name, $email, $encrypted_password, $salt);
        $result = $stmt->execute();
        $stmt->close();
 
        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM users WHERE email = ?");
            $stmt->bind_param("s", $email);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            return $user;
        } else {
            return false;
        }
    }
 
    /**
     * Get user by email and password
     */
    public function getUserByEmailAndPassword($email, $password) {
 
       $stmt = $this->conn->prepare("SELECT * FROM t_user WHERE u_email = ? ");

		
        $stmt->bind_param("s", $email);
 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            $encrypted_password = $user['u_password'];
			$hash = strtolower($password);
            if ($encrypted_password == $hash) {
				
                return $user;
            }
			else{
				return NULL;
			}
		}
	}
 
    /**
     * Check user is existed or not
     */
    public function isUserExisted($email) {
        $stmt = $this->conn->prepare("SELECT email from users WHERE email = ?");
 
        $stmt->bind_param("s", $email);
 
        $stmt->execute();
 
        $stmt->store_result();
 
        if ($stmt->num_rows > 0) {
            // user existed 
            $stmt->close();
            return true;
        } else {
            // user not existed
            $stmt->close();
            return false;
        }
    }
 
    public function hashSSHA($password){
 
		$hash = sha1($password);
        return $hash;
    }
 
    public function checkhashSSHA($password) {
 
        $hash = sha1($password);
 
        return $hash;
    }
	
	public function krijoafat($afati, $dataefillimit, $dataembarimit){
		
		$stmt = $this->conn->prepare("INSERT INTO t_afatet_provimeve(afat_emri, afat_fillon, afat_mbaron) VALUES(?, ?, ?)");
        $stmt->bind_param("sss", $afati, $dataefillimit, $dataembarimit);
        $result = $stmt->execute();
        $stmt->close();
		if($result){
			$response["error"] = FALSE;
		}else{
			$response["error"] = TRUE;
			$response["error_msg"] = "Required parameters are missing!";
			echo json_encode($response);
		}
		return $response;
	}
	public function kerkolende($id, $departamenti){
		
		$stmt = $this->conn->prepare("SELECT * FROM	t_lenda WHERE l_id NOT IN (SELECT p_lenda_id FROM t_provimet WHERE p_user_id = ? ) AND	l_departamenti_id = ?");
        $stmt->bind_param("ss", $id, $departamenti);
 
        if ($stmt->execute()) {
			$user= array();
            $result = $stmt->get_result();
			while($row = $result->fetch_assoc()) {
				$user[] = $row;
				
			}
            $stmt->close();
			$res["error"] = false;
			$res ["lendet"] = $user;
			return $res;
		}else{
			return NULL;
		}	
	}
	public function kerkoafatAktiv(){
		$stmt = $this->conn->prepare("SELECT * FROM	t_afatet_provimeve WHERE DATE(NOW()) BETWEEN DATE(afat_fillon) AND DATE(afat_mbaron)");
        $stmt->execute();
	
		if($stmt->fetch() >= 1){			
			$response["error"] = FALSE;
			
		}else{
			$response["error"] = TRUE;
			$response["error_msg"] = "Nuk ka afat aktiv!";
			echo json_encode($response);
		}
		$stmt->close();
		return $response;
	}
	public function provimetParaqitura($lendaId, $userId){
		for($i=0;$i<count($lendaId);$i++){
		$stmt = $this->conn->prepare("INSERT INTO t_provimet(p_lenda_id, p_user_id) VALUES(?, ?)");
        $stmt->bind_param("ss", $lendaId[$i], $userId);
        $result = $stmt->execute();
        $stmt->close();
		}
		if($result){
			$response["error"] = FALSE;
		}else{
			$response["error"] = TRUE;
			$response["error_msg"] = "Provimet nuk jane paraqitur!";
			echo json_encode($response);
		}
		return $response;
	}
}
?>