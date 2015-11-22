<?php

session_start();

require_once 'autoload.php';
use Facebook\FacebookSession;
use Facebook\FacebookRedirectLoginHelper;
use Facebook\FacebookRequest;
use Facebook\FacebookResponse;
use Facebook\FacebookSDKException;
use Facebook\FacebookRequestException;
use Facebook\FacebookAuthorizationException;
use Facebook\GraphObject;
use Facebook\Entities\AccessToken;
use Facebook\HttpClients\FacebookCurlHttpClient;
use Facebook\HttpClients\FacebookHttpable;
// init app with app id and secret
FacebookSession::setDefaultApplication( '1633219956938901','e4e21f5ad5c4ca41c703199a32e35d93' );
// login helper with redirect_uri
    $helper = new FacebookRedirectLoginHelper('http://107.170.231.224/fbconfig.php' );
try {
  $session = $helper->getSessionFromRedirect();
} catch( FacebookRequestException $ex ) {
  // When Facebook returns an error
} catch( Exception $ex ) {
  // When validation fails or other local issues
}       
// see if we have a session
if ( isset( $session ) ) {
  // graph api request for user data

	//  get response
  $request = new FacebookRequest( $session, 'GET', '/me?locale=en_US&fields=first_name,last_name,email,name' );
  $response = $request->execute();
  // get response
  $graphObject = $response->getGraphObject();
     	$fbid = $graphObject->getProperty('id');             
 	    $fbfullname = $graphObject->getProperty('name'); //Full only
	    $femail = $graphObject->getProperty('email');   
		$firstName = $graphObject->getProperty('first_name');
		$lastName = $graphObject->getProperty('last_name');

		/* ---- Session Variables -----*/
	    $_SESSION['FBID'] = $fbid;           
        $_SESSION['FULLNAME'] = $fbfullname;
	    $_SESSION['EMAIL'] =  $femail;
		$_SESSION['FIRSTNAME'] =  $firstName;
		$_SESSION['LASTNAME'] =  $lastName;

		
		
  $request = new FacebookRequest($session, 'GET', '/me/friends?access_token=' . $session->getToken() . '&fields=name,id,picture' );
	 $response = $request->execute();
	
	 	$graphObject = $response->getGraphObject();
	/*			 var_dump($graphObject);
			exit();		*/
	 	$friends = $graphObject->getProperty('data')->asArray();	
		$_SESSION['FRIENDS'] =  $friends;
    /* ---- header location after session ----*/
  header("Location: index.php");
} else {
  $loginUrl = $helper->getLoginUrl(array());
 header("Location: ".$loginUrl);
}

?>
