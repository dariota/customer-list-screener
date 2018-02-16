# Customer List Screener

## Dependencies

You'll need maven to test/run/build the project, and Java as well - JDK and JRE 8.

## Usage

To just run the tests (one will take about 10 seconds depending on your machine - it generates about 50MB of data to ensure a production class isn't slurping data, but the rest are fast).

`mvn test``

To package the project into an executable jar (which also runs the tests)

`mvn package`

This will build the project into a shaded jar (all dependencies packaged with it) into `target/customer-list-screener-0.0.1.jar`

You can run the jar and pass in the name of the customer list to read from as the first argument - if you leave it out it will be assumed to be `customers.json` in the current directory.

Assuming you're in the project root and the customers.json file is as well

`java -jar target/customer-list-screener-0.0.1.jar customers.json`

This will produce a list of the ID and name of the customers eligible, sorted first by ID then name.
