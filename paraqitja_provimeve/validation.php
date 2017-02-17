<?php 
if(!isset($message)) {
if (!filter_var($_POST["email"], FILTER_VALIDATE_EMAIL)) {
$message = "Invalid UserEmail";
}
}
?>