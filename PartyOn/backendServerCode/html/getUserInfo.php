<?php

ini_set('display_errors', 1);

define('Facebook_SDK_V4_SRC_DIR','/var/www/html/');
require_once("autoload.php");

require_once('Facebook/FacebookSession.php');
require_once('Facebook/FacebookRedirectLoginHelper.php');
require_once('Facebook/FacebookRequest.php');
require_once('Facebook/FacebookResponse.php');
require_once('Facebook/FacebookSDKException.php');
require_once('Facebook/FacebookRequestException.php');
require_once('Facebook/FacebookAuthorizationException.php');
require_once('Facebook/GraphObject.php');

use Facebook\FacebookSession;
use Facebook\FacebookRequest;
use Facebook\GraphUser;
use Facebook\FacebookRequestException;
use Facebook\FacebookRedirectLoginHelper;
FacebookSession::setDefaultApplication('1633219956938901', 'e4e21f5ad5c4ca41c703199a32e35d93');



/* GET USER ID, NAME AND PROFILE PICTURE FROM Facebook */
$request = new FacebookRequest(
  $session,
  
  'GET',
  '/me',
  array(
    'fields' => 'id,name,picture'
  )
);


$response = $request->execute();
$graphObject = $response->getGraphObject();
/* handle the result */
?>