<?php
session_start(); 

function storeId(){
	 echo $_SESSION['FBID']; 
	 echo '<br>';
	 echo $_SESSION['FRIENDS'];
	 //  var_dump($_SESSION['FRIENDS']);
	 echo '<br>';
	 echo $_SESSION['FIRSTNAME'];
	 echo '<br>';
	 echo $_SESSION['LASTNAME'];
	 echo '<br>';
	 echo $_SESSION['FULLNAME'];
}

function showFriends() {
	$friends=$_SESSION['FRIENDS'];
	foreach($friends as $friend) {
		echo $friend->name;
		echo '<br>';
		echo $friend->id;
		$picture=$friend->picture;
//		var_dump($friend);
		echo '<br>';
		$pictureURL =  $picture->data->url;
		echo '<img src=' . $pictureURL . '></img>';
		echo '<br><br>';
//			exit();		
	}
}



?>
<!doctype html>
<html xmlns:fb="http://www.facebook.com/2008/fbml">
  <head>
    <title>Login with Facebook</title>
	
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/style.css" rel="stylesheet">
	
	<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
 </head>
  <body>
  <?php include 'inc/nav.php';?>
  <?php if ($_SESSION['FBID']): ?>      <!--  After user login  -->
<div class="container">
<div class="hero-unit">
  <h1>Hello <?php echo $_SESSION['USERNAME']; ?></h1>
  </div>
<div class="span4">
 <ul class="nav nav-list">
<li class="nav-header">Image</li>
	<li><img src="https://graph.facebook.com/<?php echo $_SESSION['FBID']; ?>/picture"></li>
<li class="nav-header">Facebook ID</li>
<li><?php echo  $_SESSION['FBID']; ?></li>
<li class="nav-header">Facebook fullname</li>
<li><?php echo $_SESSION['FULLNAME']; ?></li>

<br>First Name<li><?php echo $_SESSION['FIRSTNAME']; ?></li>
<br><br>
<li class="nav-header">Facebook Email</li>
<li><?php echo $_SESSION['EMAIL']; ?></li> 
<div><br><br><br><a href="logout.php">Logout</a></div>
<br><br>
view store ID
<?php storeId(); ?>
<br><br>
<h1>Your Friends!!! </h1><br><br>
<?php showFriends(); ?>
<br><br>
</ul></div></div>
    <?php else: ?>     <!-- Before login --> 
<div class="container">
<h1>Login with Facebook</h1>
           Not Connected
<div>
      <a href="fbconfig.php">Login with Facebook</a></div>
      </div>
    <?php endif ?>
  </body>
</html>
