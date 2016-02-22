<?php
ini_set('display_errors', 1);

define("HOST", "localhost");                                       // The host you want to connect to.
define("USER", "root");                                                          // The database username.
define("PASSWORD", "  ");                        // The database password.
define("DATABASE", "  ");          // The database name.

$mysqli = new mysqli("localhost", USER, PASSWORD, DATABASE);
if ($mysqli->connect_error) {
//    header("Location: ../error.php?err=Unable to connect to MySQL ".$mysqli->connect_error." host:".HOST);
	echo $mysqli->connect_error." host:".HOST;
    exit();
}

?>