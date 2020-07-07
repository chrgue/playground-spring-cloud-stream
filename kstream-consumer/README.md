# Simple KStream Consumer

**0. Prerequisites**

Make sure you fulfilled all prerequisites from [here](../README.md).

**1. Start Consumer**

    ./mvnw clean spring-boot:run

**2. Produce sample data**

    kafkacat -b localhost:9092 -t raw_strings -P -l sample-data.txt
    
**3. Review results in the consumers logs**

**4. Run tests**

    ./mvnw clean verify
