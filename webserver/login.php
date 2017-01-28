<?php
	include("connection.php");
	$username="xyz";
	$password="xyz";
	
	if($_SERVER['REQUEST_METHOD']=='POST'){
		$username=$_POST['username'];
		$password=$_POST['password'];
	}

	$query="SELECT password FROM `users_table` WHERE username='{$username}'";
	$result=mysqli_query($connection,$query);
	
	$exists=2;
	
	if(mysqli_num_rows($result)==0){
		$exists=0;		//username doesnt exist.
	}else{
		$row=mysqli_fetch_row($result);
		$pass=$row[0];
		if($pass!=$password){
			$exists=1;
		}
	}

	echo $exists;

?>