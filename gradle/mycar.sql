-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 11, 2024 at 08:03 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mycar`
--

-- --------------------------------------------------------

--
-- Table structure for table `cars`
--

CREATE TABLE `cars` (
  `car_id` int(11) NOT NULL AUTO_INCREMENT,
  `car_type_id` int(11) DEFAULT NULL,
  `license_plate` varchar(20) DEFAULT NULL,
  `make` varchar(50) DEFAULT NULL,
  `model` varchar(50) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`car_id`),
  KEY `car_type_id` (`car_type_id`),
  CONSTRAINT `cars_ibfk_1` FOREIGN KEY (`car_type_id`) REFERENCES `cartypes` (`car_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cars`
--

INSERT INTO `cars` (`car_id`, `car_type_id`, `license_plate`, `make`, `model`, `year`, `status`) VALUES
(1, 1, 'ABC123', 'Toyota', 'Camry', 2020, 'available'),
(2, 2, 'XYZ789', 'Ford', 'Explorer', 2019, 'rented'),
(3, 3, 'LMN456', 'Chevrolet', 'Silverado', 2021, 'maintenance'),
(4, 4, 'JKL321', 'BMW', 'Z4', 2022, 'available');

-- --------------------------------------------------------

--
-- Table structure for table `cartypes`
--

CREATE TABLE `cartypes` (
  `car_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(50) DEFAULT NULL,
  `description` text DEFAULT NULL,
  PRIMARY KEY (`car_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cartypes`
--

INSERT INTO `cartypes` (`car_type_id`, `type_name`, `description`) VALUES
(1, 'Sedan', 'A small to medium-sized passenger car'),
(2, 'SUV', 'Sport Utility Vehicle'),
(3, 'Truck', 'A larger vehicle designed for transporting goods'),
(4, 'Convertible', 'A car with a roof that can be folded or removed');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `user_type` varchar(20) DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `driver_license` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`customer_id`, `first_name`, `last_name`, `gender`, `email`, `password`, `country`, `city`, `phone`, `user_type`, `profile_image`, `driver_license`) VALUES
(1, 'John', 'Doe', 'Male', 'john.doe@example.com', 'hashed_password1', 'USA', 'New York', '123-456-7890', 'regular', 'path/to/image1.jpg', 'D12345678'),
(2, 'Jane', 'Smith', 'Female', 'jane.smith@example.com', 'hashed_password2', 'USA', 'Los Angeles', '098-765-4321', 'regular', 'path/to/image2.jpg', 'E87654321'),
(3, 'Alice', 'Johnson', 'Female', 'alice.johnson@example.com', 'hashed_password3', 'Canada', 'Toronto', '111-222-3333', 'admin', 'path/to/image3.jpg', 'F23456789'),
(4, 'Bob', 'Brown', 'Male', 'bob.brown@example.com', 'hashed_password4', 'UK', 'London', '444-555-6666', 'regular', 'path/to/image4.jpg', 'G34567890');

-- --------------------------------------------------------

--
-- Table structure for table `rentalrates`
--

CREATE TABLE `rentalrates` (
  `rate_id` int(11) NOT NULL AUTO_INCREMENT,
  `car_type_id` int(11) DEFAULT NULL,
  `daily_rate` decimal(10,2) DEFAULT NULL,
  `weekly_rate` decimal(10,2) DEFAULT NULL,
  `monthly_rate` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`rate_id`),
  KEY `car_type_id` (`car_type_id`),
  CONSTRAINT `rentalrates_ibfk_1` FOREIGN KEY (`car_type_id`) REFERENCES `cartypes` (`car_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rentalrates`
--

INSERT INTO `rentalrates` (`rate_id`, `car_type_id`, `daily_rate`, `weekly_rate`, `monthly_rate`) VALUES
(1, 1, 50.00, 300.00, 1000.00),
(2, 2, 75.00, 450.00, 1500.00),
(3, 3, 100.00, 600.00, 2000.00),
(4, 4, 150.00, 900.00, 3000.00);

-- --------------------------------------------------------

--
-- Table structure for table `rentals`
--

CREATE TABLE `rentals` (
  `rental_id` int(11) NOT NULL AUTO_INCREMENT,
  `car_id` int(11) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `rental_date` datetime DEFAULT NULL,
  `return_date` datetime DEFAULT NULL,
  `actual_return_date` datetime DEFAULT NULL,
  `total_cost` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`rental_id`),
  KEY `car_id` (`car_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `rentals_ibfk_1` FOREIGN KEY (`car_id`) REFERENCES `cars` (`car_id`),
  CONSTRAINT `rentals_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rentals`
--

INSERT INTO `rentals` (`rental_id`, `car_id`, `customer_id`, `rental_date`, `return_date`, `actual_return_date`, `total_cost`) VALUES
(1, 1, 1, '2024-06-01 10:00:00', '2024-06-07 10:00:00', '2024-06-07 09:00:00', 300.00),
(2, 2, 2, '2024-06-02 12:00:00', '2024-06-09 12:00:00', NULL, 500.00),
(3, 3, 3, '2024-06-03 14:00:00', '2024-06-10 14:00:00', '2024-06-10 13:00:00', 700.00),
(4, 4, 4, '2024-06-04 16:00:00', '2024-06-11 16:00:00', NULL, 400.00);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cars`
--
ALTER TABLE `cars`
  MODIFY `car_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `cartypes`
--
ALTER TABLE `cartypes`
  MODIFY `car_type_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `rentalrates`
--
ALTER TABLE `rentalrates`
  MODIFY `rate_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `rentals`
--
ALTER TABLE `rentals`
  MODIFY `rental_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
