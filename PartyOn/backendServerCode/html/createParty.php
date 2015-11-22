<?php

include_once 'db_connect.php';
include_once 'party.php';

$userId = '';
$startDate = ''; 
$active = '';

if (array_key_exists ('userId' , $_GET )) {
 $userId = $_GET['userId'];
} 

if (array_key_exists ('startDate' , $_GET )) {
 $startDate = $_GET['startDate'];
} 

if (array_key_exists ('active' , $_GET )) {
 $active = $_GET['active'];
} 

$new_party_id = create_party($mysqli, $userId, $startDate, $active);

if (null != $new_party) {
 echo 'created new party with id: '.$new_party_id;
} else {
 echo 'create party failed';
 echo $mysqli->error;
}


?>