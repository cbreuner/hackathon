<?php

class inviteObject
{
	public	$inviteId = 0;
	public	$FBID = '';
	public	$active = '';
	
	public function __construct($inviteId, $FBID, $active)
	{
		$this->inviteId = $inviteId;
		$this->FBID = $FBID;
		$this->active = $active;		
	}
	
	 function getJSON() {
	  $jd = [];
		
		  $jd["inviteId"] = $this->inviteId;
		  $jd["FBID"] = $this->FBID;
		  $jd["active"] = $this->active;		
		  
	  return json_encode($jd);
	 }
	
}

// Not Invited 0
// Unread = 1
// Read = 2
// Accepted = 3
// Rejected = 4

function search_invites($mysqli, $inviteId, $FBID, $active) {
	
	if ($stmt = $mysqli->prepare("SELECT inviteId, FBID, active FROM po_invite WHERE FBID=?")) {
		$stmt->bind_param('s', $FBID); 
		$stmt->execute();
		$stmt->bind_result($inviteId, $FBID, $active);

		if ($stmt->fetch()) {
			return new inviteObject($inviteId, $FBID, $active);
		}
	} else {
		// Could not create a prepared statement
		header("Location: ../error.php?err=Database error: cannot prepare statement");
		exit();
	}

	return null;
}


