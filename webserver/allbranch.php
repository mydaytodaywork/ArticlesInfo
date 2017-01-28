<?php
	include("connection.php");
	
	$query="SELECT `branchid`,`branch`, `counter` FROM `branch_table` WHERE 1 order by counter desc";
	$r=mysqli_query($connection,$query);
	$result = array();

 	while($res=mysqli_fetch_row($r)){
		array_push($result,array(
			"branchid"=>$res[0],
	 		"branch"=>$res[1],
	 		"counter"=>$res[2],
	 	));
 	}
	echo json_encode(array("details"=>$result));
?>