<?php

include_once 'db_connect.php';
include_once 'notification.php';

$notificationId =  '';
$data = ''; 
$answer = '';


if (array_key_exists ('notificationId' , $_GET )) {
 $notificationId = $_GET['notificationId'];
} 

if (array_key_exists ('data' , $_GET )) {
 $data = $_GET['data'];
} 

if (array_key_exists ('answer' , $_GET )) {
 $answer = $_GET['answer'];
} 

$notification = get_notification($mysqli, $notificationId);


if (null != $notification) {
 echo $notification->update_answer($mysqli, $answer,$data);
} else {
 echo '{}';
}


?>