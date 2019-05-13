	<?php
	$url="127.0.0.1";
	$database="bdorigem_php";
    $conn = mysqli_connect($url,$_POST['username'],$_POST['password'],$database);
	$date = $_POST['date'];
	$id = $_POST['idCultura'];
	

	
	$sql = "SELECT Data, Hora, NomeVariavel, LimiteInferior, LimiteSuperior, ValorMedicao, Descricao FROM alertas WHERE IDCultura ='". $id. "' AND Data='". $date. "'";
	$result = mysqli_query($conn, $sql);
	$response["alertas"] = array();

	if ($result){
		if (mysqli_num_rows($result)>0){
			while($r=mysqli_fetch_assoc($result)){
				$ad = array();
				$dh = $r['Data'];
				$dh .= ' ';
				$dh .= $r['Hora'];
				$ad["DataHora"] = $dh;
				$ad["NomeVariavel"] = $r['NomeVariavel'];
				$ad["LimiteInferior"] = $r['LimiteInferior'];
				$ad["LimiteSuperior"] = $r['LimiteSuperior'];
				$ad["ValorMedicao"] = $r['ValorMedicao'];
				$ad["Descricao"] = $r['Descricao'];
				array_push($response["alertas"], $ad);
			}
		}	
	}
	
	
	$json = json_encode($response["alertas"]);
	echo $json;
	mysqli_close ($conn);
