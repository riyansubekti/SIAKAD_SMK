<?php 

require_once 'connect.php';

$key       = $_POST['key'];
$password      = $_POST['password'];
$role     = $_POST['role'];
$id_siswa      = $_POST['id_siswa'];
$nama    = $_POST['nama'];
$kelas    = $_POST['kelas'];
$alamat    = $_POST['alamat'];
$jurusan    = $_POST['jurusan'];
$tagihan    = $_POST['tagihan'];
$foto    = $_POST['foto'];

if ( $key == "insert" ){
    $query = "INSERT INTO login (username,password,role)
    VALUES ('$id_siswa','$password','$role')";
	$query1 = "INSERT INTO siswa (id_siswa,username,nama,kelas,alamat,jurusan,tagihan,foto)
    VALUES ('$id_siswa','$id_siswa','$nama','$kelas','$alamat','$jurusan','$tagihan','$foto')";

        if ( mysqli_query($conn, $query) && mysqli_query($conn, $query1) ){

            if ($foto == null) {
				
				$finalPath = "/siakad/1.jpeg"; 
                $result["value"] = "1";
                $result["message"] = "Success";
    
                echo json_encode($result);
                mysqli_close($conn);

            } else {
				
                $path = "picture/$id_siswa.jpeg";
                $finalPath = "/siakad/".$path;

                $insert_picture = "UPDATE siswa SET foto='$finalPath' WHERE id_siswa='$id_siswa' ";
            
                if (mysqli_query($conn, $insert_picture)) {
            
                    if ( file_put_contents( $path, base64_decode($foto) ) ) {
                        
                        $result["value"] = "1";
                        $result["message"] = "Success!";
            
                        echo json_encode($result);
                        mysqli_close($conn);
            
                    } else {
                        
                        $response["value"] = "0";
                        $response["message"] = "Error! ".mysqli_error($conn);
                        echo json_encode($response);

                        mysqli_close($conn);
                    }

                }
			}
        } 
        else {
            $response["value"] = "0";
            $response["message"] = "Error! ".mysqli_error($conn);
            echo json_encode($response);

            mysqli_close($conn);
        }
}

?>