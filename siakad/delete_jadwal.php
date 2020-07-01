<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'connect.php';

$key = $_POST['key'];
$id_mapel      = $_POST['id_mapel'];

if ( $key == "delete" ){
	
	$query = "DELETE FROM mapel WHERE id_mapel='$id_mapel'";

        if ( mysqli_query($conn, $query) ){
			$result["value"] = "1";
			$result["message"] = "Success!";
			echo json_encode($result);
			mysqli_close($conn);
        } 
        else {
            $response["value"] = "0";
            $response["message"] = "Error! ".mysqli_error($conn);
            echo json_encode($response);
            mysqli_close($conn);
        }

} else {
    $response["value"] = "0";
    $response["message"] = "Error! ".mysqli_error($conn);
    echo json_encode($response);
    mysqli_close($conn);
}

?>