<?php
	include("connection.php");
	$username="Kamal";
	$password="Kamal";
	$profession="xyz";
	$occupation="xyz";
	$email="xyz";
	$gender="Male";
	$phone="9090538616";
	$dob="01/08/1997";
	$name="xyz";
	if($_SERVER['REQUEST_METHOD']=='POST'){
		$username=$_POST['username'];
		$name=$_POST['name'];
		$password=$_POST['password'];
		$profession=$_POST['profession'];
		$occupation=$_POST['organization'];
		$email=$_POST['email'];
		$gender=$_POST['gender'];
		$phone=$_POST['phone'];
		$dob=$_POST['dob'];
	}

	//counter before insertion
	$count_query="select count(*) from users_table";
	$result=mysqli_query($connection,$count_query);
	$roww=mysqli_fetch_row($result);
	$count1=$roww[0];

	$t=time();
	$ctime=date("Y-m-d h:i:s",$t);

	$query="INSERT INTO `users_table`(`username`, `name`, `email`, `password`, `gender`, `dob`, `phoneno`, `profession`, `organization`, `date_joined`) VALUES ('{$username}','{$name}','{$email}','{$password}','{$gender}','{$dob}','{$phone}','{$profession}','{$occupation}','{$ctime}');";
	echo $query;
	$result=mysqli_query($connection,$query);


	//counter after insertion
	$count_query="select count(*) from users_table";
	$result=mysqli_query($connection,$count_query);
	$roww=mysqli_fetch_row($result);
	$count2=$roww[0];

	if($count1==$count2)
		echo "0";
	else 
		echo "1";
?>