			<head>
			<link rel="stylesheet" type="text/css" href="css/style.css" />
			</head>
<?php
	@session_start();
	if($_SESSION['user_logged_in'] == true && isSet($_SESSION['user_id'])){
		include "mysql.php";
		if($_SERVER['REQUEST_METHOD'] == "POST"){
			if(isSet($_POST['register'])){
				$query = "SELECT * FROM t_lenda WHERE l_emri = '$_POST[lenda]'";
				$result = mysql_query($query);
				$number_of_rows = mysql_num_rows($result);
				if($number_of_rows == 0){
					$query_new = "INSERT INTO t_lenda(l_emri,l_semestri_id,l_departamenti_id,l_kredi)
									VALUES ('$_POST[lenda]', '$_POST[semestri]', '$_POST[departamenti]', '$_POST[kredi]')";
					$result_new = mysql_query($query_new);

					header("Location: shto.php");
				}
				else{
					header("Location: aa.html");
				}
			}
		}
?>

<div id="register" >
<h1>
 Krijo lëndë të re
 <h1/>
	<form name="register_lenda" action="lenda.php" method="POST" enctype="multipart/form-data">
					<table>
						<tr>
							<td colspan="2">
							</td>
						</tr>
						<tr>
							<td>
								Lënda:
							</td>
							<td>
								<input type="text" name="lenda"/>
							</td>
						</tr>
						<tr>
							<td>
								Semestri:
							</td>
							<td>
								<select name="semestri">
									<option value="1"> I</option>
									<option value="2"> II</option>
									<option value="3"> III</option>
									<option value="4"> IV</option>
									<option value="5"> V</option>
									<option value="6"> VI</option>
								</select>
							</td>
						</tr>	
						<tr>
							<td>
								Departamenti:
							</td>
							<td>
								<select name="departamenti">
									<option value="">zgjedh</option>
									<option value="1"> Shkenca Kompjuterike</option>
									<option value="2"> Shkenca Politike</option>
									<option value="3"> Biznes dhe Menaxhment</option>
									<option value="4"> Gjuhe Angleze</option>
								</select>
							</td>
						</tr>							
						<tr>
							<td>
								Kredi:
							</td>
							<td>
								<input type="text" name="kredi"/>
							</td>
						</tr>
						
					</table>
					<div class="button">
					<input type="submit"  value="Regjistro" name="register" >
					</div>
				</form>
				</div>
<?php
	}
	else{
		header("Location: login1.php");
	}
?>
