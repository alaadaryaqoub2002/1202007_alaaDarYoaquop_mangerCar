<?php
if (isset($_GET['car'])) {
    $car = $_GET['car'];
    if (!empty($car)) {
        $server_name = "localhost";
        $username = "root";
        $password = "";
        $dbname = "mycar";

        // Create connection
        $conn = new mysqli($server_name, $username, $password, $dbname);
        // Check connection
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }

        // Prepare and execute SQL query
        $sql = "SELECT * FROM cars WHERE make = '" . $car . "'";
        $result = $conn->query($sql);

        // Check if any rows are returned
        if ($result->num_rows > 0) {
            $resultArray = array();
            // Fetch associative array
            while ($row = $result->fetch_assoc()) {
                $resultArray[] = $row;
            }
            // Output JSON
            echo json_encode($resultArray);
        } else {
            echo "0 results";
        }
        // Close connection
        $conn->close();
    } else {
        echo "Car parameter is empty";
    }
} else {
    echo "Car parameter is not provided. Please specify a car parameter.";
}
?>
