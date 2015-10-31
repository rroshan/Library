-- MySQL dump 10.13  Distrib 5.6.15, for osx10.7 (x86_64)
--
-- Host: localhost    Database: library_db
-- ------------------------------------------------------
-- Server version	5.6.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping routines for database 'library_db'
--
/*!50003 DROP FUNCTION IF EXISTS `CalcRemainingBooks` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`roshan`@`localhost` FUNCTION `CALCREMAININGBOOKS`(p_book_id CHAR(10), p_branch_id INT) RETURNS int(11)
BEGIN
	DECLARE l_remaining_copies INT;
    DECLARE l_no_of_copies INT;
    DECLARE l_book_loans_copy INT;
    
    SET l_remaining_copies = 0;
    
     SELECT no_of_copies INTO l_no_of_copies FROM book_copies WHERE book_id = p_book_id and branch_id = p_branch_id;
    
    SELECT count(*) INTO l_book_loans_copy FROM book_loans WHERE book_id = p_book_id and branch_id = p_branch_id and date_in is null;
    
     SET l_remaining_copies = l_no_of_copies - l_book_loans_copy;
    
    return (l_remaining_copies);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `calculate_fines` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`roshan`@`localhost` PROCEDURE `calculate_fines`()
BEGIN
	DECLARE v_finished INTEGER DEFAULT 0;
    DECLARE v_loan_id INTEGER DEFAULT 0;
	DECLARE v_book_id char(10);
    DECLARE v_branch_id INTEGER DEFAULT 0;
    DECLARE v_card_no INTEGER DEFAULT 0;
    DECLARE v_date_out DATE;
    DECLARE v_due_date DATE;
    DECLARE v_date_in DATE;
    
    DECLARE v_days_overdue INTEGER DEFAULT 0;
    
	DEClARE loan_cursor CURSOR FOR SELECT * FROM book_loans;

    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = 1;
    
    OPEN loan_cursor;
    
    get_book_loan: LOOP
		FETCH loan_cursor INTO v_loan_id, v_book_id, v_branch_id, v_card_no, v_date_out, v_due_date, v_date_in;
        
        IF v_finished = 1 THEN 
			LEAVE get_book_loan;
		END IF;
        
        SELECT DATEDIFF(IFNULL(v_date_in, CURDATE()), v_due_date) INTO v_days_overdue;
        
        -- IF v_days_overdue > 0 for initial data
        IF v_days_overdue > 0 AND v_date_in IS NULL
        THEN
			INSERT INTO fines (loan_id, fine_amt, paid) VALUES (v_loan_id, (v_days_overdue * 0.25) , FALSE) ON DUPLICATE KEY UPDATE fine_amt=(v_days_overdue * 0.25);
        END IF;
    END LOOP get_book_loan;
    
    CLOSE loan_cursor;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-25 22:30:10
