# KStream Processor

**0. Prerequisites**

Make sure you fulfilled all prerequisites from [here](../README.md).

**1. Start Processor**

    ./mvnw clean spring-boot:run

**2. Listen to processed topic**

    kafkacat -b localhost:9092 -t processed_strings -P -l sample-data.txt
    
**3. Produce sample data**

    kafkacat -b localhost:9092 -t raw_strings -P -l sample-data.txt
    
**4. Review results in the consumers logs**

**5. Run tests**

    ./mvnw clean verify
