# Cloud_project_DNA2022
# Simple-distributed-store-system

NAME : **<span style="color:blue">MOUNIR OUREKOUCH</span>**

CLASS : DNA 2022



## Description
This project implements a distributed store system, also known as a Distributed File System, using the actor model in Akka. The system provides a key-value store with basic operations such as store, lookup, and delete. 

## Implementation
The project is divided into three parts:

Day 1: A simple implementation of the system based on an in-memory Key-Value HashTable.

Day 2: An improved implementation using durable actors which implement the key-value abstraction with an append-only file.

Day 3: Implementation of caching actors to improve overall performance of the system.

## Usage
To use the system, follow these steps:

1- Clone the repository.

2- Run the sbt command by typing sbt in the terminal and then type update command

3- Before running any scala file you should comment all other scala files in "example" folder, and uncomment only the file you want to run.

4-Use the available methods to store, lookup, or delete keys and their associated values.

## Testing
To test the system, we implemented a randomized client program that makes a large number of requests to the storage service. The client program populates the storage with values for keys from 1-500 and picks an operation at random from store, delete, and lookup.

## Conclusion
The project demonstrates the power of the actor model in implementing distributed systems. The use of durable actors and caching actors can significantly improve the performance of the system.
