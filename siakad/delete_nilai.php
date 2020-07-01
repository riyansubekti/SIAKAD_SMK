<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'connect.php';

$key = $_POST['key'];
$id_nilai      = $_POST['id_nilai'];

if ( $key == "delete" ){
	
	$query = "DELETE FROM nilai WHERE id_nilai='$id_nilai'";

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