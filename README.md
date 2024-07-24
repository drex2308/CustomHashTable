# MyHashTable Implementation

## Overview

This repository contains my implementation of the `MyHashTable` class. The primary goal of this project was to gain a deeper understanding of how a hash table works by implementing it from scratch and to explore what makes a good hash function.

## Description

The `MyHashTable` class is a custom implementation of a hash table that uses an array as its underlying data structure and linear probing for collision resolution. This project aims to achieve efficient search operations by maintaining the load factor and rehashing when necessary.

## Features

- **Singly Linked List**: The list is a linked structure of nodes, where each node's data type is `String`.
- **Hash Table Properties**:
  - **Linear Probing**: Uses linear probing as its collision resolution mechanism.
  - **Default Capacity and Load Factor**: The default capacity is 10 and the default load factor is 0.5.
  - **DataItem Class**: Each element of the array is a `DataItem` with a `String` data value and an `int` frequency.
- **Constructors**:
  - `MyHashTable()`: Initializes the hash table with default capacity.
  - `MyHashTable(int initialCapacity)`: Initializes the hash table with a specified initial capacity.
- **Hash Function**: Implements a hash function based on Horner's method for efficient computation.
- **Rehashing**: Rehashes the table when the load factor exceeds 0.5, doubling the size and ensuring the new size is a prime number.
- **Efficient Operations**:
  - **Size and Collisions**: Calculates size and number of collisions in O(1) time complexity.
  - **Deletion**: Marks spaces as deleted without decreasing the frequency, updating the size accordingly.

## Methods

- **Interface Methods** (from `MyHTInterface`):
  - `void insert(String value)`: Inserts a new word into the hash table, updating its frequency if it already exists.
  - `boolean contains(String key)`: Checks if the hash table contains the specified word.
  - `boolean delete(String key)`: Deletes the specified word from the hash table, marking the space as deleted.
  - `int size()`: Returns the number of elements in the hash table.
  - `int numOfCollisions()`: Returns the number of collisions that have occurred.
  - `int hashValue(String key)`: Returns the hash value of the specified word.
  - `void display()`: Displays all words in the hash table with their frequencies.

## Implementation Details

To deepen my understanding of data structures, I implemented this project with specific constraints and rules:

- **Linear Probing**: Utilizes linear probing for collision resolution to ensure fast search operations.
- **Prime Number Rehashing**: Ensures the new table size is a prime number during rehashing to improve hash distribution.
- **Efficient Display**: The `display()` method is optimized to print all values in the correct format, showing empty and deleted spaces distinctly.
- **No Loops or Imports**: The implementation strictly avoids recursion and does not use any imports or the Collections framework.

## Usage

To use the `MyHashTable` class, create an instance and call its methods, or run the provided driver program in the repository.

## Learnings

- **Hash Table Mechanics**: I gained a deeper understanding of how hash tables work, including collision resolution and rehashing.
- **Hash Function Design**: I learned to design an efficient hash function using Horner's method.
- **Data Structure Manipulation**: I developed skills in manipulating data structures to maintain efficiency and correctness.
- **Problem-Solving**: I addressed edge cases and ensured the robustness of the implementation through extensive testing.

## Conclusion

This project was a valuable experience in understanding the intricacies of implementing a hash table from scratch. The `MyHashTable` class provides an efficient solution for fast search operations, highlighting the importance of a well-designed hash function and effective collision resolution.
