# End 2 End example


This project processes multiple domain objects (Filters & Categories) to maintain a so called _CategoryAggregate_.
It uses kafka partitions to allow a stateful processing. The aggregations will be saved in a mongo collection. 

The project consists of multiple functions:

* categorySource - generates one category
* filterSource - generates random filters
* categoryProcessor - transforms categories into aggregate
* filterProcessor - transforms filters into aggregate
* categorySink - merges aggregates & stores it in the mongo db

**0. Prerequisites**

Make sure you fulfilled all prerequisites from [here](../README.md).



**1. Start consumer (processors & sinks only):**

_Please note you can start multiple instances._ 

    ./mvnw spring-boot:run -Dspring-boot.run.profiles=consumer
        
**2. Start producer**

    ./mvnw spring-boot:run -Dspring-boot.run.profiles=producer
    
**3. Run tests**

    ./mvnw clean verify