<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'connect.php';

$key = $_POST['key'];
$id_siswa      = $_POST['id_siswa'];
$foto = $_POST['foto'];

if ( $key == "delete" ){
	
	$query = "DELETE siswa, login FROM siswa INNER JOIN login ON login.username = siswa.id_siswa WHERE siswa.id_siswa = '$id_siswa'";

        if ( mysqli_query($conn, $query) ){

            $iparr = explode ("/", $foto);
            $picture_split = $iparr[5];

            if ( unlink("picture/".$picture_split) ){

                $result["value"] = "1";
                $result["message"] = "Success!";

                echo json_encode($result);
                mysqli_close($conn);

            } else {
            
                $response["value"] = "0";
                $response["message"] = "Error to delete a image! ".mysqli_error($conn);
                echo json_encode($response);
    
                mysqli_close($conn);
            }

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