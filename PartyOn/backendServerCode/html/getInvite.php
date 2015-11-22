<?php

include_once 'db_connect.php';
include_once 'invite.php';

$inviteId = '';

if (array_key_exists ('inviteId' , $_GET )) {
 $inviteId = $_GET['inviteId'];
} 


$invite = get_invite($mysqli, $inviteId);

if (null != $invite) {
 echo $invite->getJSON();
} else {
 echo '{}';
}


?>