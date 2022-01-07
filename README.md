A web application to store and manage code snippets.
Technology:
SpringBoot, Hibernate, H2 db, Thymeleaf, highlight.js

Offers REST endpoints so that it can be integrated with other projects.


POST /api/code/new take JSON with three fields: Code, time, views and create new record in database. If fields time and views has positive value it's set limit on the number of views and/or time, and after its expiration, the code snippet is deleted from the database. And return JSON with unique snippets ID. 

GET /api/code/id where id it's unique snippets UUID, return JSON with code snippets.

GET /api/code/latest return a JSON array with 10 most recently uploaded code snippets sorted from the newest to the oldest.



GET /code/new return HTML form for creation new snippets.

POST /code/new take data from form with three fields: Code, time, views and create new record in database. If fields time and views has positive value it's set limit on the number of views and/or time, and after its expiration, the code snippet is deleted from the database.

GET /code/id  where id it's unique snippets UUID, return HTML page with code snippets.

GET /code/latest return HTML that contains 10 most recently uploaded code snippets.
