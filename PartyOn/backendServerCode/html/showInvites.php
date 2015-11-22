<?php

include_once 'db_connect.php';
include_once 'searchInvites.php';

$inviteId = '';
$FBID = '';
$active = '';

if (array_key_exists ('inviteId' , $_GET )) {
 $inviteId = $_GET['inviteId'];
} 

if (array_key_exists ('FBID' , $_GET )) {
 $FBID = $_GET['FBID'];
} 

if (array_key_exists ('active' , $_GET )) {
 $active = $_GET['active'];
} 

$invite = search_invites($mysqli, $inviteId, $FBID, $active);

if (null != $invite) {
 echo json_encode($invite);
} else {
 echo '{}';
}


?>