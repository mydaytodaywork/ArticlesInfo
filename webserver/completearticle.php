<?php
	include("connection.php");

	if(isset($_GET['id'])){
		$pid=$_GET['id'];
		
		$query="UPDATE `posts` SET visits=visits+1 WHERE postid=$pid";
		$result=mysqli_query($connection,$query);

		$query="SELECT `branch`, `kind`, `topic`, `longinfo`, `author`, `links`, `subtime`, `code_data` FROM posts p, branch_table b, kind_table k 
		WHERE postid=$pid and p.branchid=b.branchid and p.kindid=k.kindid";
		$r=mysqli_query($connection,$query);
		$result = array();

		while($res=mysqli_fetch_row($r)){
			array_push($result,array(
		 		"branch"=>$res[0],
		 		"kind"=>$res[1],
		 		"topic"=>$res[2],
		 		"longinfo"=>$res[3],
		 		"author"=>$res[4],
		 		"links"=>$res[5],
		 		"subtime"=>$res[6],
		 		"code_data"=>$res[7],
		 	));
	 	}

		echo json_encode(array("details"=>$result));
	}

?>