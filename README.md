# Apache Kafka Tutorial & Sample Projects

## Overview
Kafka was first developed at LinkedIn and later became an open sourced Apcache project in 2011.
Apache Kafka is publish-subsribe based fault tolerant messaging system. It is fast, scalable and distributed by design.

## Prerequisites
you must have a godd understanding of Java, distributed messaging system and Linux Environment

## Messaging System
- in big data, there a two main challenges. The first challenge is how to collect large volume of data and the second challenge is to analyze the collected data. To overcome those challenges, you must need a messaging system.
- A messaging system is reponsible for tranfering data from one application to another one, so application can focus on daza, but not worry about how to share it.

## Pulish-Subscribe messaging system
- messages are persisted in a topic
- consumers (subscriber) can subscribe one or more topic and consume all the messages in that topic

## What is Kafka
- Apache Kafka is a distributed publish-subsribe messaging system
- robust queue that can handle a high valume of data and suitable for both offline and online messages
- Kafka message a persisted on the disk and replicated within the cluster to prevent data loss
- built on top of Zookeeper synchronization service

## Use Cases of Kafka
- metrcis
- log aggregation
- stream processing

## Kafka Architecture
![Architecture](Kafka_architecture.png)

## Cloning the repository

Please use the following command:

```bash
git clone https://github.com/minhducngo85/Kafka.git
```