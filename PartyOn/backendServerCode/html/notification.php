<?php

class notificationObject
{
	public	$notificationId = 0;
	public	$FBID = 0;
	public 	$inviteId = 0;
	public	$type = '';
	public	$startDate = '';
	public	$data = '';
	public	$answer = 0;

	public function __construct($notificationId, $FBID, $inviteId, $type, $startDate, $data, $answer)
	{
		$this->notificationId = $notificationId;
		$this->FBID = $FBID;
		$this->inviteId = $inviteId;		
		$this->type = $type;
		$this->startDate = $startDate;
		$this->data = $data;
		$this->answer = $answer;	
	}
	
	 public function update_answer($mysqli, $answer, $data)
	 {
		 if ($stmt = $mysqli->prepare("UPDATE po_notification SET data=?, answer=? WHERE notificationId = ?"))
			 {
			$stmt->bind_param('sii', $data, $answer, $this->notificationId); 
		  if ($stmt->execute()) 
		  {
			return true;
		}
	}
	 echo $mysqli->error;      
	return false;
}
	 
	 function getJSON() {
	  $jd = [];
		
		  $jd["notificationId"] = $this->notificationId;
		  $jd["FBID"] = $this->FBID;
		  $jd["inviteId"] = $this->inviteId;
		  $jd["type"] = $this->type;
		  $jd["startDate"] = $this->startDate;
		  $jd["data"] = $this->data;
		  $jd["answer"] = $this->answer;
		
	  return json_encode($jd);
	 }
	
}

function get_notification($mysqli, $notificationId) {
	
	if ($stmt = $mysqli->prepare("SELECT notificationId, FBID, inviteId, type, startDate, data, answer FROM po_notification WHERE notificationId=?")) {
		$stmt->bind_param('i', $notificationId); 
		$stmt->execute();
		$stmt->bind_result($notificationId, $FBID, $inviteId, $type, $startDate, $data, $answer);

		if ($stmt->fetch()) {
			return new notificationObject($notificationId, $FBID, $inviteId, $type, $startDate, $data, $answer);
		}
	} else {
		// Could not create a prepared statement
		header("Location: ../error.php?err=Database error: cannot prepare statement");
		exit();
	}

	return null;
}


function create_notification($mysqli, $FBID, $inviteId, $type, $startDate, $data) {

	 if ($create_stmt = $mysqli->prepare("INSERT INTO po_notification (FBID, inviteId, type, startDate, data, answer) VALUES(?,?,?,?,?,0)")) 
		 {   
		  $create_stmt->bind_param('iisis', $FBID, $inviteId, $type, $startDate, $data);
		   
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

