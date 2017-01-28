<?php
	include("connection.php");
	
	$query="SELECT `postid`,`topic`,`shortinfo`,`subtime`,`author` FROM `posts` WHERE 1 order by postid desc limit 10";
	$r=mysqli_query($connection,$query);
	$result = array();

 	while($res=mysqli_fetch_row($r)){
		array_push($result,array(
	 		"postid"=>$res[0],
	 		"topic"=>$res[1],
	 		"shortinfo"=>$res[2],
	 		"subtime"=>$res[3],
	 		"author"=>$res[4],
	 	));
 	}
	echo json_encode(array("details"=>$result));
?>