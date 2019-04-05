<?php

$start = microtime(true);

$url="127.0.0.1";
$sourceDatabase="bdorigem_php";
$destinationDatabase="bddestino_php";
$usernameLeitura="LeituraMigracao";
$passwordLeitura="passleitura";
$usernameEscrita="EscritaMigracao";
$passwordEscrita="passescrita";

#Connection to the Source Database
$sourceConn = mysqli_connect($url, $usernameLeitura, $passwordLeitura, $sourceDatabase);
if (!$sourceConn){
	die ("Connection Failled: ".$sourceConn->connect_error);
}

#Connection to the Destination Database
$destinationConn = mysqli_connect($url, $usernameEscrita, $passwordEscrita, $destinationDatabase);
if (!$destinationConn){
	die ("Connection Failled: ".$destinationConn->connect_error);
}

#####################

$sqlTimestamp = "SELECT NOW()";
$result = mysqli_query($destinationConn, $sqlTimestamp);
$r=mysqli_fetch_row($result);

#####################

$sqlLastUpdate = "SELECT DataHoraAtualizacao FROM sistema";
$result1 = mysqli_query($destinationConn, $sqlLastUpdate);
if ($result1 == NULL) {
	$r1 = array(0);
}else{
	$r1=mysqli_fetch_row($result1);
}


#####################
ini_set('memory_limit', '-1');
ini_set('max_execution_time', 4000);

$sqlLogsNotExported = "SELECT * FROM logs WHERE DataHora < '$r1[0]' ";
$result2 = mysqli_query($sourceConn, $sqlLogsNotExported);
$logsToExport = array();

if ($result2) { 
	if (mysqli_num_rows($result2)>0){ 
		while($r2=mysqli_fetch_assoc($result2)){ 
			array_push($logsToExport, $r2); 
		} 
	}
}
########################

if(is_array($logsToExport)){  
           foreach($logsToExport as $row => $value)  
           {  
                $v1 = mysqli_real_escape_string($sourceConn, $value['LogID']);  
                $v2 = mysqli_real_escape_string($sourceConn, $value['User']);  
				$v3 = mysqli_real_escape_string($sourceConn, $value['Operacao']); 
				$v4 = mysqli_real_escape_string($sourceConn, $value['DataHora']); 
				$v5 = mysqli_real_escape_string($sourceConn, $value['Tabela']); 
				$v6 = mysqli_real_escape_string($sourceConn, $value['Campo']); 
				$v7 = mysqli_real_escape_string($sourceConn, $value['Atualizacao']); 
                $sql = "INSERT INTO `logs` VALUES ('$v1', '$v2', '$v3', str_to_date('$v4', '%Y-%m-%d %H:%i:%s'), '$v5', '$v6', '$v7')";  
                $result3= mysqli_query($destinationConn, $sql);  
           }  
} 

######################

$sqlDeleteTimestamp = "DELETE FROM sistema";
$result4 = mysqli_query($destinationConn, $sqlDeleteTimestamp);

$sqlUpdateTimestamp = "INSERT INTO `sistema` VALUES ('$r[0]')";
$result5 = mysqli_query($destinationConn, $sqlUpdateTimestamp); 

mysqli_close($sourceConn);
mysqli_close($destinationConn);

$time_elapsed_secs = microtime(true) - $start;
echo $time_elapsed_secs;

?>
