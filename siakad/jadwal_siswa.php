<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'connect.php';

$kelas = $_GET["kelas"];

$query = "SELECT mapel.nama_mapel, mapel.waktu_pelajaran, mapel.hari, guru.nama 
FROM mapel INNER JOIN guru ON mapel.id_mapel = guru.id_guru WHERE mapel.kelas = '$kelas'";
$result = mysqli_query($conn, $query);
$response = array();
$nomer = 1;


$server_name = $_SERVER['SERVER_ADDR'];

while( $row = mysqli_fetch_assoc($result) ){

    array_push($response, 
    array(
		'nomer' 			=> $nomer,
        'nama_mapel'		=> $row['nama_mapel'], 
        'waktu_pelajaran'	=> $row['waktu_pelajaran'], 
        'hari' 				=> $row['hari'],
        'nama' 				=> $row['nama'])
    );
	$nomer++;
}

echo json_encode($response);

mysqli_close($conn);

?>