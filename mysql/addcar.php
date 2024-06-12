<?php

header('Content-Type: application/json');

if ($_SERVER['REQUEST_METHOD'] == "POST") {
    // Validate and sanitize input data
    $car_type_id = isset($_POST['car_type_id']) ? intval($_POST['car_type_id']) : 0;
    $license_plate = isset($_POST['license_plate']) ? htmlspecialchars($_POST['license_plate']) : "";
    $make = isset($_POST['make']) ? htmlspecialchars($_POST['make']) : "";
    $model = isset($_POST['model']) ? htmlspecialchars($_POST['model']) : "";
    $year = isset($_POST['year']) ? intval($_POST['year']) : 0;
    $status = isset($_POST['status']) ? htmlspecialchars($_POST['status']) : "";

    // Database connection
    $server_name = "localhost";
    $username = "root";
    $password = "";
    $dbname = "mycar";
    $response = array();

    $conn = new mysqli($server_name, $username, $password, $dbname);
    if ($conn->connect_error) {
        $response['status'] = "error";
        $response['message'] = "Connection failed: " . $conn->connect_error;
    } else {
        // Prepare SQL query with placeholders
        $sql = "INSERT INTO `cars` (`car_type_id`, `license_plate`, `make`, `model`, `year`, `status`) VALUES (?, ?, ?, ?, ?, ?)";
        
        // Prepare and bind parameters
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("isssis", $car_type_id, $license_plate, $make, $model, $year, $status);
        
        // Execute the query
        if ($stmt->execute()) {
            $response['status'] = "success";
            $response['message'] = "Car added successfully";
        } else {
            $response['status'] = "error";
            $response['message'] = "Error adding car: " . $conn->error;
        }

        // Close statement
        $stmt->close();
        // Close connection
        $conn->close();
    }

    // Send JSON response
    echo json_encode($response);
}

?>
