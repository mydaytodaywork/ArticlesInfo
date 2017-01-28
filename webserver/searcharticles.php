<?php
	include("connection.php");
	
	if(isset($_GET['keyword'])){
		$keyword=$_GET['keyword'];
		$query="SELECT `postid`,`topic`,`shortinfo`,`subtime`,`author` FROM `posts` where keywords like('%$keyword%') or topic like ('%$keyword%')";
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
	}

	else if(isset($_GET['user'])){
		$author=$_GET['user'];
		$query="SELECT `postid`,`topic`,`shortinfo`,`subtime`,`author` FROM `posts` where author='{$author}'";
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
	}
?>