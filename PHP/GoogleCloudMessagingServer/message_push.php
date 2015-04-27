<?php
  $connection = mysqli_connect("localhost", "******", "******", "******");
  if (!$connection) {
    $error="Connection to Database Failed";
  }
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <link rel="stylesheet" type="text/css" href="css/googlecloudmessaging.css" />
  <title>Google Cloud Messaging Registered Devices</title>
</head>
<body>
  <?php
    $registration_id = $_POST["registration_id"];
	$message = $_POST["message"];
  
    foreach ($_POST as $key => $value) {
	  if(strpos($key, "message_push") !== FALSE) {
		$position = strpos($key, "#") + 1;
		$id = substr($key, $position);
		$registration_id = $_POST["registration_id#".$id];
		$message = $_POST["message#".$id];
	  }
	}
	
	if (isset($registration_id) && isset($message)) {
	  $url = "https://android.googleapis.com/gcm/send";
	  $api_key = "AIzaSyBw9M1Dt2tnpFbWtc_l73jsvpJRFxoAuwE";
	  
	  $curl = curl_init();
	  
	  $headers = array(
	    "Authorization: key=".$api_key,
		"Content-Type: application/json"
	  );
	  
	  $body = array(
	    "registration_ids" => array($registration_id),
		"data" => array(
	      "message" => $message,
	    ),
	  );
	  
	  curl_setopt_array(
	    $curl,
		array(
		  CURLOPT_URL             => $url,
		  CURLOPT_RETURNTRANSFER  => true,
		  CURLOPT_POST            => true,
		  CURLOPT_HTTPHEADER      => $headers,
		  CURLOPT_POSTFIELDS      => json_encode($body),
		  CURLINFO_HEADER_OUT     => true
		)
	  );
	  
	  $http_response = curl_exec($curl);
	  
	  if (!$http_response) {
	    die ("Error: \"".curl_error($curl)."\" - Code: ".curl_errno($curl));
	  }
	  
	  curl_close($curl);
	}
  ?>
  <table style="border:none; width:auto; margin-left:auto; margin-right:auto;">
    <tr>
      <td><img style="margin-left:auto; margin-right:auto;" src="images/pdsd_logo.png" /></td>
      <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
      <td><h2 style="text-align:center; vertical-align:middle">Google Cloud Messaging Registered Devices</h2></td>
    </tr>
  </table>
  <br />
  <?php
    $query = "SELECT username, email, timestamp, id, registration_id FROM registered_devices";
	$result = mysqli_query($connection, $query);
	if(mysqli_num_rows($result) > 0) {
  ?>
  <form name="registered_devices" action="message_push.php" method="post">
    <?php
	  while($row = mysqli_fetch_assoc($result)) {
	?>
        <table style="background-color:#ffffff; border:none; width:auto; margin-left:auto; margin-right:auto;">
          <tr>
            <th>ATTRIBUTE</th>
            <th>VALUE</th>
          </tr>
          <tr style="background-color: #ebebeb">
            <td>Username</td>
            <td>
		      <?php
			    echo $row["username"];
			  ?>
            </td>
          </tr>
          <tr style="background-color: #ebebeb">
            <td>Email</td>
            <td>
		      <?php
			    echo $row["email"];
			  ?>
            </td>
          </tr>
          <tr style="background-color: #ebebeb">
            <td>Timestamp</td>
            <td>
		      <?php
			    echo $row["timestamp"];
			  ?>
            </td>
          </tr>
          <tr style="background-color: #ebebeb">
            <td>Message</td>
            <td>
		      <?php
			    echo "<textarea name=\"message#".$row["id"]."\" rows=\"5\" cols=\"25\"></textarea>";
			  ?>
            </td>
          </tr>
          <tr>
            <td style="text-align:center;" colspan="2">
              <?php
                echo "<input type=\"submit\" name=\"message_push#".$row["id"]."\" value=\"Push Message\" />";
			  ?>
            </td>
          </tr>
          <tr>
            <td style="text-align:center;" colspan="2">
              <?php
                echo "<input type=\"hidden\" name=\"registration_id#".$row["id"]."\" value=\"".$row["registration_id"]."\" />";
			  ?>
            </td>
          </tr>          
        </table>
        <br />
    <?php
	  }
	?>
  </form>
  <?php
	} else {
      echo "No device registered yet";
	}
  ?>
</body>
</html>