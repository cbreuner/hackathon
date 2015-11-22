<?php

include_once 'db_connect.php';
include_once 'notification.php';

$FBID =  '';
$inviteId = '';
$type = ''; 
$startDate = ''; 
$data = ''; 

if (array_key_exists ('FBID' , $_GET )) {
 $FBID = $_GET['FBID'];
} 

if (array_key_exists ('inviteId' , $_GET )) {
 $inviteId = $_GET['inviteId'];
} 

if (array_key_exists ('type' , $_GET )) {
 $type = $_GET['type'];
} 

if (array_key_exists ('startDate' , $_GET )) {
 $startDate = $_GET['startDate'];
} 

if (array_key_exists ('data' , $_GET )) {
 $data = $_GET['data'];
} 

$new_notification_id = create_notification($mysqli, $FBID, $inviteId, $type, $startDate, $data);

if (null != $new_notification_id) {
 echo $new_notification_id;
} else {
 echo 'create notification failed';
 echo $mysqli->error;
}


?>