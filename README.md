# HARMs
Graphical User Interface for HARMs

## Description
-	This is a GUI tool for HARMs written based on Java SWT. The tool support graph-based attack models in HARMs. It also provides security analysis using HARMs Engine.

![Main](./img/main.png)
 
## Features
-	Add nodes
-	Add arcs
-	Move nodes or arcs
-	Delete nodes or arcs
-	Undo and redo functions
-	Clear all
-	Save and load data
-	Analayze data

## How to use
- Draw Attack Graph
- Set an Attacker and Target
- Set all vulnerabilities on Arc
- Run analysis or metrics on Tools menu

## Usage Examples
- Analysis<br />
![Analysis](./img/analysis.png)
- Metrics<br />
![Metrics](./img/metrics.png)

## Installation
### Windows, Linux and MACOS
1. Install HARMs Engine at https://github.com/whistlebee/harmat<br />
2. Download and Run the program from command line
```java
git clone https://github.com/Naehyung/HARMs
cd HARMs
mvn package
cd target
java -jar HARMs-0.0.1.jar
```
