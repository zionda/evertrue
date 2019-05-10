# evertrue
Evertrue coding challenge

DZion 2019/05/10 08:46
----------------------
Initial check-in. MySQL objects (5 tables and population procedure), parsed data files, loader programs and POJO classes to be used with Gson to create JSON output. TODO: Create class to interpret incoming URI (/com/dzion/evertrue/[career_info/{people_id}|comanyretention|mosttenured]) to call the appropriate method of the controller. Going to need to write and embed SQL in each procedure to populate POJOs. Should create URI (/com/dzion/evertrue/populate) to execute populate_career_history stored procedure. Loader programs should accept input for fully qualified filename and path.