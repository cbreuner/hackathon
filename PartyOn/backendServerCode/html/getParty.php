<?php

include_once 'db_connect.php';
include_once 'party.php';

$partyId = '';


if (array_key_exists ('partyId' , $_GET )) {
 $partyId = $_GET['partyId'];
} 



$party = get_party($mysqli, $partyId);

if (null != $party) {
 echo $party->getJSON();
} else {
 echo '{}';
}


?>