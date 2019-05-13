	<?php
	$url="127.0.0.1";
	$database="bdorigem_php";
    $conn = mysqli_connect($url,$_POST['username'],$_POST['password'],$database);
	//$conn = mysqli_connect($url,"root","",$database);
	$user = $_POST['username'];
	$user = "root";
	$sql = "SELECT Email FROM utilizador WHERE Username ='". $user. "'";
	$result = mysqli_query($conn, $sql);
	$r1=mysqli_fetch_row($result);
	
	$sql2 = "SELECT * FROM cultura WHERE UtilizadorEmail ='". $r1[0]. "'";
	$result2 = mysqli_query($conn, $sql2);
	$response["culturas"] = array();
	if ($result2){
		if (mysqli_num_rows($result2)>0){
			while($r2=mysqli_fetch_assoc($result2)){
				$ad = array();
				$ad["IDCultura"] = $r2['IDCultura'];
				array_push($response["culturas"], $ad);
			}
		}	
	}
	
	
	$json = json_encode($response["culturas"]);
	echo $json;
	mysqli_close ($conn);