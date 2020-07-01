<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'connect.php';

$role = $_GET['role'];

$query = "SELECT * FROM pengumuman WHERE penerima='$role' ";

$result = mysqli_query($conn, $query);
$response = array();

$server_name = $_SERVER['SERVER_ADDR'];

while( $row = mysqli_fetch_assoc($result) ){

    array_push($response, 
    array(
        'id_pengumuman'		=> $row['id_pengumuman'], 
        'judul'		=> $row['judul'], 
        'deskripsi'		=> $row['deskripsi'], 
        'penerima'	=> $row['penerima'])
    );
}

echo json_encode($response);

mysqli_close($conn);

?>