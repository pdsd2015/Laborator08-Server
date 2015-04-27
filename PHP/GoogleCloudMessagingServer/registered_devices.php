<?php
  $connection = mysqli_connect("localhost", "******", "******", "******");
  if (!$connection) {
    $error="Connection to Database Failed";
  }
  $query = "SELECT id, registration_id, username, email, timestamp FROM registered_devices";
  $result = mysqli_query($connection, $query);
  $registered_devices = array();
  if(mysqli_num_rows($result) > 0) {
	while($row = mysqli_fetch_assoc($result)) {
      $registered_device = array(
        "id" => $row["id"],
		"registration_id" => $row["registration_id"],
		"username" => $row["username"],
		"email" => $row["email"],
		"timestamp" => $row["timestamp"]
      );
      $registered_devices[] = $registered_device;
	}
  }
  echo json_encode($registered_devices);
?>