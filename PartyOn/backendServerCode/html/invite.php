<?php

class inviteObject
{
	public	$inviteId = 0;
	public	$FBID = '';
	public	$initFBID = '';	
	public	$event = '';
	public	$startDate = '';
	public	$data = '';
	public	$active = '';

	public function __construct($inviteId, $FBID, $initFBID, $event, $startDate, $data, $active)
	{
		$this->inviteId = $inviteId;
		$this->FBID = $FBID;
		$this->initFBID = $initFBID;		
		$this->event = $event;
		$this->startDate = $startDate;
		$this->data = $data;
		$this->active = $active;	
	}
	
	 function getJSON() {
	  $jd = [];
		
		  $jd["inviteId"] = $this->inviteId;
		  $jd["FBID"] = $this->FBID;
		  $jd["initFBID"] = $this->initFBID;		  
		  $jd["event"] = $this->event;
		  $jd["startDate"] = $this->startDate;
		  $jd["data"] = $this->data;
		  $jd["active"] = $this->active;
		
	  return json_encode($jd);
	 }
	
}

function get_invite($mysqli, $inviteId) {
	
	if ($stmt = $mysqli->prepare("SELECT inviteId, FBID, initFBID, event, startDate, data, active FROM po_invite WHERE inviteId=?")) {
		$stmt->bind_param('i', $inviteId); 
		$stmt->execute();
		$stmt->bind_result($inviteId, $FBID, $initFBID, $event, $startDate, $data, $active);

		if ($stmt->fetch()) {
			return new inviteObject($inviteId, $FBID, $initFBID, $event, $startDate, $data, $active);
		}
	} else {
		// Could not create a prepared statement
		header("Location: ../error.php?err=Database error: cannot prepare statement");
		exit();
	}

	return null;
}


function create_invite($mysqli, $FBID, $initFBID, $event, $startDate, $data, $active) {

	 if ($create_stmt = $mysqli->prepare("INSERT INTO po_invite (FBID, initFBID, event, startDate, data, active) VALUES(?,?,?,?,?,?)")) 
		 {   
		  $create_stmt->bind_param('issisi', $FBID, $initFBID, $event, $startDate, $data, $active);
		   
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

