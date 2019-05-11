# evertrue
Evertrue coding challenge

DZion 2019/05/10 08:46
----------------------
Initial check-in. MySQL objects (5 tables and population procedure), parsed data files, loader programs and POJO classes to be used with Gson to create JSON output. TODO: Create class to interpret incoming URI (/com/dzion/evertrue/[career_info/{people_id}|comanyretention|mosttenured]) to call the appropriate method of the controller. Going to need to write and embed SQL in each procedure to populate POJOs. Should create URI (/com/dzion/evertrue/populate) to execute populate_career_history stored procedure. Loader programs should accept input for fully qualified filename and path.

DZion 2019/05/10 18:08
----------------------
Currently to install the manual steps are as follows: 1) Create an evertrue schema in a MySQL database instance. 2) Create the 5 tables and stored procedure by executing the scripts in the MySQL folder. 3) Populate the tables using the data files located in the data folder with the corresponding java programs in the src folder; CSVReader.java (people table), CompaniesReader.java (companies table), CompanyLocationReader.java (company_locations table) and PositionsReader.java (positions table). Paths are all hard-coded (for now) and you'll need mysql-connector-java-8.0.16.jar to connect to MySQL. I loaded all the files using Eclipse IDE. 4) Populate the career history table using the stored procedure by "CALL populate_career_history". If I have more time I'll expose that as web service with an appropriate URI. 5) At this point you can build the URIController class. That needs both jar files in the lib directory; mysql-connector-java-8.0.16.jar and gson-2.2.2.jar. The GSON utility is a nice and easy way to convert prepopulated POJOs to JSON. The appropriate classes use @SerializedName to output the correct JSON headers.

I ran the following URIs as parameters to URIController against my local database to produce the following JSON output:
/com/dzion/evertrue/careerinfo/5 -> careerinfo.json
/com/dzion/evertrue/companyrentention -> companyrentention.json
/com/dzion/evertrue/mosttenured -> data\mosttenured.json

Keep in mind the populate_career_history procedure is random, so results will vary. Since the statistical population was large I included average retention to 2 digits to more obviously display the sorting. I've included a TODO list in my last round of commit comments. Checking out for the evening, have a great weekend.

DZion 2019/05/11 17:46
----------------------
Unit tested and determined most tenured selection criteria wasn't correct. I pulled the updated query into a helper method to make it much more readable. I also converted the statement to a prepared statement, much easier and flexible to modify the query. Average company retention is the average of all location end_date minus start_date. People qualify as most tenured if an one stint at any company locations is greater than the company's average. A person can be selected multiple times using these rules. Would it make sense to have an array within the most tenured people displaying all end_date/start_date pairs that are greater than that company's average?
