<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'connect.php';

$key = $_POST['key'];

if ( $key == "update" ){

$id_nilai      = $_POST['id_nilai'];
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

    $query = "UPDATE nilai
				SET id_mapel = '$id_mapel',
					id_siswa = '$id_siswa',
					tugas = '$tugas',
					uts = '$uts',
					uas = '$uas',
					nilai = '$nilai',
					kkm = '$kkm',
					status = '$status',
					semester = '$semester'
				WHERE
					id_nilai = '$id_nilai'";

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