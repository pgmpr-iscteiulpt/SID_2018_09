	<?php
	$url="127.0.0.1";
	$database="bdorigem_php";
    $conn = mysqli_connect($url,$_POST['username'],$_POST['password'],$database);
	$id = $_POST['idCultura'];

	$sql = "SELECT * FROM cultura WHERE IDCultura ='". $id[0]. "'";
	$result = mysqli_query($conn, $sql);
	$response["culturas"] = array();
	if ($result){
		if (mysqli_num_rows($result)>0){
			while($r=mysqli_fetch_assoc($result)){
				$ad = array();
				$ad["NomeCultura"] = $r['NomeCultura'];
				$ad["DescricaoCultura"] = $r['DescricaoCultura'];
				array_push($response["culturas"], $ad);
			}
		}	
	}
	
	
	$json = json_encode($response["culturas"]);
	echo $json;
	mysqli_close ($conn);