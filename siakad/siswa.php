<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'connect.php';

$query = "SELECT * FROM siswa ORDER BY id_siswa ASC ";
$result = mysqli_query($conn, $query);
$response = array();

$server_name = $_SERVER['SERVER_ADDR'];

while( $row = mysqli_fetch_assoc($result) ){

    array_push($response, 
    array(
        'id_siswa'        =>$row['id_siswa'], 
        'username'      =>$row['username'], 
        'nama'   =>$row['nama'],
        'kelas'     =>$row['kelas'],
        'alamat'    =>$row['alamat'],
        'jurusan'     =>$row['jurusan'],
        'tagihan'     =>$row['tagihan'],
        'foto'   =>"http://$server_name" . $row['foto']) 
    );
}

echo json_encode($response);

mysqli_close($conn);

?>