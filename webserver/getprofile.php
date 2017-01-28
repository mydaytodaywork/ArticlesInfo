<?php
	include("connection.php");

	if(isset($_GET['username'])){
		$username=$_GET['username'];
		
		$query="SELECT `name`, `email`, `gender`, `dob`, `phoneno`, `profession`, `organization`, `date_joined` FROM `users_table` WHERE `username`='{$username}'";
		$r=mysqli_query($connection,$query);
		$result = array();
		//echo $query;
		while($res=mysqli_fetch_row($r)){
			array_push($result,array(
		 		"name"=>$res[0],
		 		"email"=>$res[1],
		 		"gender"=>$res[2],
		 		"dob"=>$res[3],
		 		"phone"=>$res[4],
		 		"profession"=>$res[5],
		 		"organization"=>$res[6],
		 		"joiningdate"=>$res[7],
		 	));
	 	}

		echo json_encode(array("details"=>$result));
	}

?>