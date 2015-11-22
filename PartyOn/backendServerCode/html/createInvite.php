<?php
session_start();

include_once 'db_connect.php';
include_once 'invite.php';

$FBID =  $_SESSION['FBID'];
$initFBID =  $FBID;
$event = ''; 
$startDate = ''; 
$data = ''; 
$active = '';


if (array_key_exists ('event' , $_GET )) {
 $event = $_GET['event'];
} 

if (array_key_exists ('initFBID' , $_GET )) {
 $initFBID = $_GET['initFBID'];
} 

if (array_key_exists ('startDate' , $_GET )) {
 $startDate = $_GET['startDate'];
} 

if (array_key_exists ('data' , $_GET )) {
 $data = $_GET['data'];
} 

if (array_key_exists ('active' , $_GET )) {
 $active = $_GET['active'];
} 

$new_invite_id = create_invite($mysqli, $FBID, $initFBID, $event, $startDate, $data, $active);

if (null != $new_invite_id) {
 echo $new_invite_id;
} else {
 echo 'create invite failed';
 echo $mysqli->error;
}


?>