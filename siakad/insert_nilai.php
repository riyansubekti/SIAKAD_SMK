<?php 

require_once 'connect.php';

$key = $_POST['key'];
$id_mapel      = $_POST['id_mapel'];
$id_siswa     = $_POST['id_siswa'];
$tugas      = $_POST['tugas'];
$uts    = $_POST['uts'];
$uas    = $_POST['uas'];
$kkm    = $_POST['kkm'];
$semester    = $_POST['semester'];

$array = array($tugas, $uts, $uas);
$nilai = array_sum($array) / count($array);

if ($nilai < $kkm) {
	$status = "Tidak Lulus";
}else{
	$status = "Lulus";
}

if ( $key == "insert" ){
    $query = "INSERT INTO nilai (id_mapel,id_siswa,tugas,uts,uas,nilai,kkm,status,semester)
    VALUES ('$id_mapel','$id_siswa','$tugas','$uts','$uas','$nilai','$kkm','$status','$semester')";

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