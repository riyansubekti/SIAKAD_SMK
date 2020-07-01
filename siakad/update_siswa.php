<?php 

header("Content-Type: application/json; charset=UTF-8");

require_once 'connect.php';

$key = $_POST['key'];

if ( $key == "update" ){

    $id_siswa         = $_POST['id_siswa'];
    $password    = $_POST['password'];
    $nama      = $_POST['nama'];
    $kelas     = $_POST['kelas'];
    $alamat      = $_POST['alamat'];
    $jurusan    = $_POST['jurusan'];
    $tagihan    = $_POST['tagihan'];
    $foto    = $_POST['foto'];

    $query = "UPDATE login, siswa
				SET login.password = '$password',
					siswa.nama = '$nama',
					siswa.kelas = '$kelas',
					siswa.alamat = '$alamat',
					siswa.jurusan = '$jurusan',
					siswa.tagihan = '$tagihan'
				WHERE
					login.username = '$id_siswa'
					AND siswa.id_siswa = '$id_siswa'";

        if ( mysqli_query($conn, $query) ){

            if ($foto == null) {

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