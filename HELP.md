# How to run

## Prerequisites

Install docker ```brew install -cask docker```

Install docker compose ```brew install docker-compose```

Environment setup - Kafka, Zookeeper ```docker-compose up```


## Creating Topics for service1 and service2
```  
docker ps -a 

docker exec -it <<first 3 characters of container name>> sh

Service1 topic:
/opt/kafka/bin/kafka-topics.sh --create --topic service1 --zookeeper customeractivityconsumer-zookeeper-1:2181 --partitions 1 --replication-factor 1

Service2 topic:
/opt/kafka/bin/kafka-topics.sh --create --topic service2 --zookeeper customeractivityconsumer-zookeeper-1:2181 --partitions 1 --replication-factor 1
```

## Verifying created topics
```
/opt/kafka/bin/kafka-topics.sh --list --zookeeper customeractivityconsumer-zookeeper-1:2181
```
## Publishing messages -service1
```
/opt/kafka/bin/kafka-console-producer.sh --topic service1 --bootstrap-server localhost:9092
{"xaid":"1234","modifiedAt":"01-01-2020", "action": "AttributeUpdate", "createdAt": "01-01-2019", "includeNetworth": "Y"}
```
## Publishing messages -service2
```
/opt/kafka/bin/kafka-console-producer.sh --topic service2 --bootstrap-server localhost:9092
{"ecn":"1234","updatedAt":"01-01-2020", "type": "notification", "createdAt": "01-01-2019", "description":"quarterly report"}
```