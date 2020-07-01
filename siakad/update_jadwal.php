<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'connect.php';

$key = $_POST['key'];

if ( $key == "update" ){

$id_mapel      = $_POST['id_mapel'];
$id_guru      = $_POST['id_guru'];
$nama_mapel      = $_POST['nama_mapel'];
$jurusan     = $_POST['jurusan'];
$kelas      = $_POST['kelas'];
$waktu_pelajaran    = $_POST['waktu_pelajaran'];
$hari    = $_POST['hari'];

    $query = "UPDATE mapel
				SET id_guru = '$id_guru',
					nama_mapel = '$nama_mapel',
					jurusan = '$jurusan',
					kelas = '$kelas',
					waktu_pelajaran = '$waktu_pelajaran',
					hari = '$hari'
				WHERE
					id_mapel = '$id_mapel'";

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
}

?>