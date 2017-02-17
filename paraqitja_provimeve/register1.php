			<head>
			<link rel="stylesheet" type="text/css" href="css/style.css" />
			</head>
<?php
	@session_start();
	if($_SESSION['user_logged_in'] == true && isSet($_SESSION['user_id'])){
		include "mysql.php";
		if($_SERVER['REQUEST_METHOD'] == "POST"){
			if(isSet($_POST['register'])){
				if(empty($_POST['password'])){
					goto password;
				}
				$email = strtolower($_POST['email']);			
				
				$query = "SELECT * FROM t_user WHERE u_email = '$email'";
				$result = mysql_query($query);
				$number_of_rows = mysql_num_rows($result);
				if($number_of_rows == 0){
					$password = sha1($_POST['password']);
					$query_new = "INSERT INTO t_user(u_emri, u_mbiemri,u_datelindja,u_kampusi,u_departamenti, u_tipi,u_email,u_password, u_indeksi)
											VALUES ('$_POST[emri]', '$_POST[mbiemri]', '$_POST[datelindja]', '$_POST[kampusi]','$_POST[departamenti]', '$_POST[tipi]','$email', '$password', '$_POST[indeksi]')";
					$result_new = mysql_query($query_new);
					header("Location: shto.php");
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

		if(isSet($_GET["id"])){
			$query = "SELECT * FROM t_user WHERE u_id = $_GET[id]";
			$result = mysql_query($query);
			$number_of_rows = mysql_num_rows($result);
			if($number_of_rows == 1){
				$new = false;
				$title = "Edito userin";
				$row = mysql_fetch_array($result);
				$u_id = $row['u_id'];
				$u_indeksi = $row['u_indeksi'];
				$_emri = $row['u_emri'];
				$_mbiemri = $row['u_mbiemri'];
				$datelindja = $row['u_datelindja'];
				$kampusi = $row['u_kampusi'];
				$departamenti = $row['u_departamenti'];
				$tipi = $row['u_tipi'];
				$email = $row['u_email'];
				$password = $row['u_password'];
			}
			else{
				$new = true;
				$title = "Krijo user te ri";
				$u_indeksi = "";
				$_emri = "";
				$_mbiemri = "";
				$datelindja = "";
				$kampusi = "";
				$tipi = "";
				$email = "";
				$password = "";
			}
		}
		else{
			$new = true;
			$title = "Krijo user te ri";
			$u_indeksi = "";
			$_emri = "";
			$_mbiemri = "";
			$datelindja = "";
			$kampusi = "";
			$tipi = "";
			$email = "";
			$password = "";
		}
	
	}
	else{
		header("Location: login1.php");
	}
?>

<div id="register" >
<h1>
 Krijo përdorues
 <h1/>
	<form name="register" action="register1.php" method="POST" enctype="multipart/form-data">
					<table>
						<tr>
							<td colspan="2">
							</td>
						</tr>
						<tr>
							<td>
								Emri:
							</td>
							<td>
								<input type="text" name="emri"/>
							</td>
						</tr>
						<tr>
							<td>
								Mbiemri:
							</td>
							<td>
								<input type="text" name="mbiemri"/>
							</td>
						</tr>
						<tr>
							<td>
								Datëlindja:
							</td>
							<td>
								<input type="datetime" name="datelindja" value="<?php echo date('y-m-d');?>"/>
							</td>
						</tr>
						<tr>
							<td>
								Kampusi:
							</td>
							<td>
								<input type="text" name="kampusi"/>
							</td>
						</tr>
						<tr>
							<td>
								Departamenti:
							</td>
							<td>
								<select name="departamenti">
									<option value="">zgjedh</option>
									<option value="1">Shkenca Kompjuterike</option>
									<option value="2"> Shkenca Politike</option>
									<option value="3"> Biznes dhe Menaxhment</option>
									<option value="4"> Menaxhment</option>
									<option value="5"> Anglisht</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								Tipi:
							</td>
							<td>
								<select name="tipi">
									<option value="">zgjedh</option>
									<option value="1"> administrate</option>
									<option value="2"> student</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								Përdoruesi:
							</td>
							<td>
								<input type="text" name="email" value="" placeholder="email@universum-ks.org"/>
							</td>
						</tr>
						<tr>
							<td>
								Fjalëkalimi:
							</td>
							<td>
								<input type="password" name="password" placeholder="fjalëkalimi"/>
							</td>
						</tr>
						<tr>
							<td>
								Nr.Indeksi:
							</td>
							<td>
								<input type="text" name="indeksi"/>
							</td>
						</tr>
						
					</table>
					<div class="button">
					<input type="submit"  value="Regjistro" name="register" >
					</div>
				</form>
				</div>