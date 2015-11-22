<?php

class partyObject
{
	public	$partyId = 0;
	public	$userId = '';
	public	$startDate = '';
	public	$active = '';

	public function __construct($partyId, $userId, $startDate, $active)
	{
		$this->partyId = $partyId;
		$this->userId = $userId;
		$this->startDate = $startDate;
		$this->active = $active;	
	}
	
	 function getJSON() {
	  $jd = [];
		
		  $jd["partyId"] = $this->partyId;
		  $jd["userId"] = $this->userId;
		  $jd["startDate"] = $this->startDate;
		  $jd["active"] = $this->active;
		
	  return json_encode($jd);
	 }
	
}

function get_party($mysqli, $partyId) {
	
	if ($stmt = $mysqli->prepare("SELECT partyId, userId, startDate, active FROM po_party WHERE partyId=?")) {
		$stmt->bind_param('i', $partyId); 
		$stmt->execute();
		$stmt->bind_result($partyId, $userId, $startDate, $active);

		if ($stmt->fetch()) {
			return new partyObject($partyId, $userId, $startDate, $active);
		}
	} else {
		// Could not create a prepared statement
		header("Location: ../error.php?err=Database error: cannot prepare statement");
		exit();
	}

	return null;
}


function create_party($mysqli, $userId, $startDate, $active) {

	 if ($create_stmt = $mysqli->prepare("INSERT INTO po_party (userId, startDate, active) VALUES(?,?,?)")) 
		 {   
		  $create_stmt->bind_param('sii', $userId, $startDate, $active);
		   
			  // Execute the prepared query.
			  if (!$create_stmt->execute()) {
			   die("\ncreate Error: " . $mysqli->error);
				 }
			  //echo "record $name created successfully";
			  return $mysqli->insert_id;
		 } else {
		  die("\ncsError: " . $mysqli->error);
	 }
}

