**Execution Instructions**
- To generate the reports run the main method in BuildReportGenerator.
- Application uses the project_info.csv file in resources folder for the source data.
- Project is built with Java 8. Only additional libraries used are JUnit, Mockito, and lombok.
- Gradle is used as the build tool.

**Design Decisions**
- I have used Strategy pattern for the implementation of file reader. This is to make it possible 
to extend the application to support other data sources as well, such as XML files, databases etc.
- File reading is segregated from data validation, collection and report genration logic.
- Java nio.Files.lines was used for the file reading since it supports loading data lazily, which
will help it in case of reading larger files.
- Pagination support is also added to the file reading to help generating reports from portions of data.
- Visitor pattern is used to implement printing logic to support making a selection of reports to be generated and make 
it extendable to support different report formats such as pdf reports etc.
- Builder pattern is used to keep objects immutable.
- For the purpose of the project I haven't used any dependency injection framework, else I would prefer 
to use Spring as the DI framework.

**Implementation**
- Implementation has been done for all four reports stated in the coding challenge.
- As suggested in the initial discussion report is generated in the application console.
- A screenshot of the reports generated is attached to the resources folder.

**Test Coverage**
- Here are the test coverage statistics,
Classes: 94.7%
Methods: 93.3%
Lines: 90.3%

- A screenshot of test coverage report is attached to the resources folder.
- Application mainly contains unit tests, additionally some integration tests are added as well for file reading.
