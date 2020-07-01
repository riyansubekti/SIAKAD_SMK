<?php 

require_once 'connect.php';

$key = $_POST['key'];
$id_guru     = $_POST['id_guru'];
$nama_mapel      = $_POST['nama_mapel'];
$jurusan    = $_POST['jurusan'];
$kelas    = $_POST['kelas'];
$waktu_pelajaran    = $_POST['waktu_pelajaran'];
$hari    = $_POST['hari'];

if ( $key == "insert" ){
    $query = "INSERT INTO mapel (id_guru,nama_mapel,jurusan,kelas,waktu_pelajaran,hari)
    VALUES ('$id_guru','$nama_mapel','$jurusan','$kelas','$waktu_pelajaran','$hari')";

        if ( mysqli_query($conn, $query)){
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
}

?>