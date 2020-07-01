<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'connect.php';

$id_siswa = $_GET["id_siswa"];

$query = "SELECT nilai.id_nilai, mapel.id_mapel, mapel.nama_mapel, siswa.id_siswa, siswa.nama, nilai.tugas, nilai.uts, nilai.uas, nilai.nilai, nilai.kkm, nilai.status, nilai.semester
FROM ((nilai INNER JOIN mapel ON nilai.id_mapel = mapel.id_mapel)
      INNER JOIN siswa ON nilai.id_siswa = siswa.id_siswa) WHERE siswa.id_siswa = '$id_siswa'";
$result = mysqli_query($conn, $query);
$response = array();
$nomer = 1;


$server_name = $_SERVER['SERVER_ADDR'];

while( $row = mysqli_fetch_assoc($result) ){

    array_push($response, 
    array(
		'nomer' => $nomer,
        'id_nilai'        =>$row['id_nilai'], 
        'id_mapel'        =>$row['id_mapel'], 
        'id_siswa'        =>$row['id_siswa'], 
        'nama_mapel'      =>$row['nama_mapel'], 
        'nama'   =>$row['nama'],
        'tugas'   =>$row['tugas'],
        'uts'   =>$row['uts'],
        'uas'   =>$row['uas'],
        'nilai'     =>$row['nilai'],
        'kkm'    =>$row['kkm'],
        'status'     =>$row['status'],
		'semester' =>$row['semester'])
    );
	$nomer++;
}

echo json_encode($response);

mysqli_close($conn);

?>