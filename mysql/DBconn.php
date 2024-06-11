
<?php
$host = 'localhost'; 
$dbname = 'mangercar'; 
$username = 'root'; 
$password = ''; 

try {
    $db = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);
   
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    echo "sesss";

} catch (PDOException $e) {
    echo "Connection failed: " . $e->getMessage();
    die();
}
?>
