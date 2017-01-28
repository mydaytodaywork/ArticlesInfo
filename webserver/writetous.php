<?php
	include("connection.php");
	$username="Kamal";
	$article="Kamal";
	$title="xyz";
	$category="Kamal";
	$branch="xyz";
	$links="xyz";

	if($_SERVER['REQUEST_METHOD']=='POST'){
		$username=$_POST['username'];
		$title=$_POST['title'];
		$branch=$_POST['branch'];
		$category=$_POST['category'];
		$article=$_POST['article'];
		$links=$_POST['links'];
	}

	
	$t=time();
	$ctime=date("Y-m-d h:i:s",$t);

	$query="INSERT INTO `requests`(`parainfo`, `author`, `title`, `subtime`, `kind`, `branch`, `links`) VALUES ('{$article}','{$username}','{$title}','{$ctime}',
	'{$category}','{$branch}','{$links}')";
	$result=mysqli_query($connection,$query);

	echo "0";
?>