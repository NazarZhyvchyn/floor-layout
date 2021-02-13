# Floor layout planner
A CRUD application that helps with floor layout. Allows storing legal rooms with validation and allows to recall
previously added rooms. Any recalled room are displayed on an html canvas.
# How to run

Clone this project into some folder on your computer. Open the project in your IDE. Run the main method in FloorLayoutProjectApplication class. When the intialization is complete, go to http://localhost:8080 in your browser.
<a name="purpose"></a>

<a name="tehnologies-used"></a>
# Technologies used
* Java 8
* Spring Boot 
* Thymeleaf
* H2 Database 
* JUnit
<a name="validation"></a>
# How to make a validation:
Make a POST request to /valdiateRoom with following request body (e.g.):
```json
{
    "room": [
        {
            "x": "5",
            "y": "5"
        },
        {
            "x": "5",
            "y": "6"
        },
        {
            "x": "6",
            "y": "6"
        },
        {
            "x": "6",
            "y": "5"
        }
    ]
}




