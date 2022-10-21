⁸# How to run

## Prerequisites

Install docker ```brew install -cask docker```

Install docker compose ```brew install docker-compose```

Environment setup - Kafka, Zookeeper ```docker-compose up```


## Creating Topics for WIMT and CONSUMER LOBs
```  
docker ps -a 

docker exec -it <<first 3 characters of container name>> sh

Creating WIMT_TOPIC topic:
/opt/kafka/bin/kafka-topics.sh --create --topic WIMT-TOPIC --zookeeper customeractivityconsumer-zookeeper-1:2181 --partitions 1 --replication-factor 1

Creating CONSUMER_TOPIC topic:
/opt/kafka/bin/kafka-topics.sh --create --topic CONSUMER-TOPIC --zookeeper customeractivityconsumer-zookeeper-1:2181 --partitions 1 --replication-factor 1
```

## Verifying created topics
```
/opt/kafka/bin/kafka-topics.sh --list --zookeeper customeractivityconsumer-zookeeper-1:2181
```
## Publishing messages on WIMT_TOPIC
```
/opt/kafka/bin/kafka-console-producer.sh --topic WIMT-TOPIC --bootstrap-server localhost:9092
{"xaid":"1234","modifiedAt":"01-01-2020", "action": "AttributeUpdate", "createdAt": "01-01-2019", "includeNetworth": "Y"}

translation.properties for translating LOB message format to CustomerActivity message format

RequestedBy=$.xaid
CreatedDate=$.modifiedAt
SRType=$.action
LOBName=WIMT


Message consumed and recorded in the system as:
{
  "LOBName": "WIMT",
  "SRType": "AttributeUpdate",
  "SRSubType": "",
  "SRStatus": "",
  "CreatedBy": "",
  "CreatedDate": "01-01-2020",
  "EndDate": "01-01-2020",
  "RequestedBy": "1234",
  "RequestDescription": "",
  "ResolutionDescription": "",
  "ActivitySource": "WIMT-TOPIC",
  "Payload": "{"createdAt":"01-01-2019","modifiedAt":"01-01-2020","action":"AttributeUpdate","includeNetworth":"Y","xaid":"1234"}"
}
```
## Publishing messages on CONSUMER_TOPIC
```
/opt/kafka/bin/kafka-console-producer.sh --topic CONSUMER-TOPIC --bootstrap-server localhost:9092
{"ecn":"1234", "type": "notification", "eventInfo": {"updatedAt":"01-01-2020", "createdAt": "01-01-2019"}, "description":"quarterly report"}

translation.properties for translating LOB message format to CustomerActivity message format

RequestedBy=$.ecn
CreatedDate=$.eventInfo.updatedAt
SRType=$.type
LOBName=CONSUMER


Message consumed and recorded in the system as:
{
  "LOBName": "CONSUMER",
  "SRType": "notification",
  "SRSubType": "",
  "SRStatus": "",
  "CreatedBy": "",
  "CreatedDate": "01-01-2020",
  "EndDate": "01-01-2020",
  "RequestedBy": "1234",
  "RequestDescription": "",
  "ResolutionDescription": "",
  "ActivitySource": "CONSUMER-TOPIC",
  "Payload": "{"ecn":"1234","eventInfo":{"updatedAt":"01-01-2020","createdAt":"01-01-2019"},"description":"quarterly report","type":"notification"}"
}
```

## API Details

http://localhost:8080/customerActivities

```
[{"srsubType":"","createdBy":"","createdDate":"01-01-2020","requestedBy":"1234","requestDescription":"","resolutionDescription":"","activitySource":"WIMT-TOPIC","lobname":"WIMT","srtype":"AttributeUpdate","endDate":"01-01-2020","payload":"","Id":"a631a717-251c-4e65-9542-37c8cd757df8","LOBName":"WIMT","SRType":"AttributeUpdate","SRSubType":"","CreatedBy":"","CreatedDate":"01-01-2020","EndDate":"01-01-2020","RequestedBy":"1234","RequestDescription":"","ResolutionDescription":"","ActivitySource":"WIMT-TOPIC","Payload":""}]
```

## App screenshot
![image](https://github.com/Naresh-doniparti/CustomerActivityConsumer/blob/b4a035763e484f280f1896ff3553e674d08822db/IMG-20221021-WA0010.jpg)

## References
json-path :  https://www.baeldung.com/guide-to-jayway-jsonpath
