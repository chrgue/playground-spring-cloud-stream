# playground for spring cloud stream

This project processes multiple domain objects (Filters & Categories) to maintain a so called _CategoryAggregate_.
It uses kafka partitions to allow a stateful processing. The aggregations will be saved in a mongo collection. 

The project consists of multiple functions:

* categorySource - generates one category
* filterSource - generates random filters
* categoryProcessor - transforms categories into aggregate
* filterProcessor - transforms filters into aggregate
* categorySink - merges aggregates & stores it in the mongo db

**Start environment:**

    docker-compose up -d
    
**Start the producer (sources only):**

    ./mvnw spring-boot:run -Dspring-boot.run.profiles=producer
    
**Start consumer (processors & sinks only):**

_Please note you can start this consumer multiple time._ 

    ./mvnw spring-boot:run -Dspring-boot.run.profiles=consumer
