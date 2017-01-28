<?php
	include("connection.php");
	$username="Kamal";
	$message="Kamal";
	$subject="xyz";
	
	if($_SERVER['REQUEST_METHOD']=='POST'){
		$username=$_POST['username'];
		$message=$_POST['message'];
		$subject=$_POST['subject'];
	}

	
	$query="SELECT `email`,`profession`,`organization` FROM `users_table` WHERE username='{$username}'";
	$result=mysqli_query($connection,$query);
	$row=mysqli_fetch_row($result);
	$email=$row[0];
	$profession=$row[1];
	$institute=$row[2];

	$t=time();
	$ctime=date("Y-m-d h:i:s",$t);

	$query="INSERT INTO `contact_us_table`(`name`, `email`, `institute`, `subject`, `message`, `received_time`,`marking`) VALUES ('{$username}','{$email}','{$institute}','{$subject}','{$message}','{$ctime}',0)";
	$result=mysqli_query($connection,$query);

	echo "0";
?>