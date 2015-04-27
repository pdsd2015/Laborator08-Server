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
  <title>Google Cloud Messaging Registered Devices</title>
</head>
<body>
<?php
  if (isset($error)) {
    die($error);
  }
  
  $registration_id = $_POST['registration_id'];
  $username = $_POST['username'];
  $email = $_POST['email'];
  
  if(isset($registration_id) && isset($username) && isset($email)) {
    $query = "INSERT INTO registered_devices (registration_id, username, email) VALUES ('".$registration_id."', '".$username."', '".$email."')";
	mysqli_query($connection, $query);
	mysqli_close($connection);
  }
?>
</body>
</html>