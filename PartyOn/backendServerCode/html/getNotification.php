<?php

include_once 'db_connect.php';
include_once 'notification.php';

$notificationId = '';


if (array_key_exists ('notificationId' , $_GET )) {
 $notificationId = $_GET['notificationId'];
} 



$notification = get_notification($mysqli, $notificationId);


if (null != $notification) {
 echo $notification->getJSON();
} else {
 echo '{}';
}


?>