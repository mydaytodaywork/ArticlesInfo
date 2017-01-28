<?php
	include("connection.php");
	
	$query="SELECT `kindid`, `kind`, `counter` FROM `kind_table` order by counter desc";
	$r=mysqli_query($connection,$query);
	$result = array();

 	while($res=mysqli_fetch_row($r)){
		array_push($result,array(
			"id"=>$res[0],
	 		"category"=>$res[1],
	 		"counter"=>$res[2],
	 	));
 	}
	echo json_encode(array("details"=>$result));
?>