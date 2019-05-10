DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `populate_career_history`()
BEGIN
 
 DECLARE v_finished INTEGER DEFAULT 0;
 DECLARE v_person_id INTEGER;
 DECLARE v_state VARCHAR(2);
 DECLARE v_job_count INTEGER;
 DECLARE v_position_count INTEGER;
 DECLARE v_location_count INTEGER;
 DECLARE v_loop INTEGER default 0;
 DECLARE v_start_date DATE;
 DECLARE v_end_date DATE;
 -- declare cursor for people
 DEClARE people_cursor CURSOR FOR 
  SELECT id, state, FLOOR(RAND() * (3 + 1)) as career_opportunities FROM people ORDER BY id;
    
 -- declare NOT FOUND handler
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = 1;

  SELECT COUNT(*) INTO v_position_count FROM positions;
  SELECT COUNT(*) INTO v_location_count FROM company_locations;
 
  TRUNCATE TABLE career_history;
 commit;
 OPEN people_cursor;
 
 get_people: LOOP
 
 FETCH people_cursor INTO v_person_id, v_state, v_job_count;
 
 IF v_finished = 1 THEN 
 LEAVE get_people;
 END IF;
 
 -- TODO: Randomly insert career_history v_job_count times
 -- SELECT concat('v_person_id is ', v_person_id);
  SET v_loop := v_job_count;
  SET v_end_date := DATE(now());
 WHILE v_loop > 0 DO
	SET v_loop := v_loop - 1;
    SET v_start_date := DATE(DATE_SUB(v_end_date, INTERVAL FLOOR(RAND() * (5*365-365)+365) DAY));
	 INSERT INTO career_history(people_id, position_id, company_location_id, start_date, end_date)
	 VALUES(v_person_id, FLOOR(RAND() * (v_position_count - 1) + 1), FLOOR(RAND() * (v_location_count - 1) + 1), v_start_date, v_end_date);
     SET v_end_date := DATE(DATE_SUB(v_start_date, INTERVAL 1 DAY));
 END WHILE;
 
 
 END LOOP get_people;
 
 CLOSE people_cursor;
 
END$$
DELIMITER ;
