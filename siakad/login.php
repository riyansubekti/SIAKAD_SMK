<?php 
require_once 'connect.php';

$username = $_GET["username"];
$password = $_GET["password"];

$sql = "SELECT username,role FROM login WHERE username='$username' AND password='$password'";

$result = mysqli_query($conn,$sql);

if (!mysqli_num_rows($result)>0) {
	$status = "failed";
	echo json_encode(array("response"=>$status));
} else {
	$row = mysqli_fetch_assoc($result);
	$username = $row['username'];
	$role = $row['role'];
	$status = "ok";
	
	echo json_encode(array("response"=>$status,"role"=>$role,"username"=>$username));
}

mysqli_close($conn);
?>