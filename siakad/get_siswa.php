<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'connect.php';

$username = $_GET['username'];
$role = $_GET['role'];

if ($role == null) {
	$role = "siswa";
}
if ($role == 'siswa') {
	$query = "SELECT *, login.password FROM siswa INNER JOIN login ON siswa.id_siswa = login.username WHERE siswa.id_siswa='$username'";
} else {
	$query = "SELECT *, login.password FROM guru INNER JOIN login ON guru.id_guru = login.username WHERE guru.id_guru='$username'";
}

$result = mysqli_query($conn, $query);
$response = array();

$server_name = $_SERVER['SERVER_ADDR'];

while( $row = mysqli_fetch_assoc($result) ){


	if ($role == 'siswa') {
				array_push($response, 
		array(
			'id_siswa'        =>$row['id_siswa'], 
			'username'      =>$row['username'], 
			'password'      =>$row['password'], 
			'nama'   =>$row['nama'],
			'kelas'     =>$row['kelas'],
			'alamat'    =>$row['alamat'],
			'jurusan'     =>$row['jurusan'],
			'tagihan'     =>$row['tagihan'],
			'foto'   =>"http://$server_name" . $row['foto']) 
		);
	} else {
				array_push($response, 
		array(
			'id_guru'        =>$row['id_guru'], 
			'username'      =>$row['username'], 
			'password'      =>$row['password'], 
			'nama'   =>$row['nama'],
			'alamat'    =>$row['alamat'],
			'foto'   =>"http://$server_name" . $row['foto'])
		);
	}

}

echo json_encode($response);

mysqli_close($conn);

?>