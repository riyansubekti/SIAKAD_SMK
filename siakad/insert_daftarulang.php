<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'connect.php';

$id_siswa = $_POST['id_siswa'];
$kelas      = $_POST['kelas'];
$tagihan      = $_POST['tagihan'];


    $query = "UPDATE siswa
				SET kelas = '$kelas',
					tagihan = '$tagihan'
				WHERE
					id_siswa = '$id_siswa'";

        if ( mysqli_query($conn, $query) ){
			$result["value"] = "1";
			$result["message"] = "Success";    
			echo json_encode($result);
			mysqli_close($conn);
        } else {
            $response["value"] = "0";
            $response["message"] = "Error! ".mysqli_error($conn);
            echo json_encode($response);
            mysqli_close($conn);
        }

?>