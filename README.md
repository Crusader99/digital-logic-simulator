# Digital Logic Simulator (DiLoSim)

With this software you will be able to build your own circuits and test them. You are allowed to use this easy-to-use tool for educational reasons.

![](https://provider.ddnss.de/img/Uc77gsfH?raw=1 "Graphical user interface")

## Features

* Action history, which can undo changes
* Save and load files from file system
* Copy multiple items to the clipboard and paste them elsewhere
* Magnet function (Dock elements together)
* Zoom function

**Included gates:**
* NOT gate
* AND/NAND gate
* OR/NOR/XOR/XNOR gate

**Asynchronous flip-flops (without clock signal):**
* RS-Flipflop
* !R!S-Flipflop
* SL-Flipflop
* EL-Flipflop

**Synchronous edge-triggered flip-flops:**
* T flip-flop
* D flip-flop
* JK flip-flop
* JK flip-flop with set & reset

**Other:**
* Switch
* Lamp / LED
* 7 segment display
* BCD decoder

## Download

The binaries are available there:
[Download latest release](https://sourceforge.net/projects/digital-logic-simulator/)

## Run on Windows:

* Install [Java](https://java.com/en/download/)
* Download the binaries from [here](https://sourceforge.net/projects/digital-logic-simulator/)
* Open the jar-file with the java runtime

## Run on Ubuntu/Debian:

Install Java:
```
sudo apt-get update
sudo apt-get install default-jre
```

Download the binaries from [here](https://sourceforge.net/projects/digital-logic-simulator/)

Run jar-file:
```
sudo chmod 777 file.jar
java -jar file.jar
```

## Limitations

* Currently **only German language** supported.
* This software requires **Java 8 or above** to work. Tested with OpenJDK & Oracle Java.

=> If you find any bugs you can create an issue.

## UML

The graphics have been simplified for better understanding.

![](https://provider.ddnss.de/img/zhmKjUj9?raw=1 "UML-1 DE/GER")

![](https://provider.ddnss.de/img/Y3TtEZ15?raw=1 "UML-2 DE/GER")

## Dependencies

The following external libraries were used to create this software:
* [lombok](https://projectlombok.org/)

## Author

The software was written by me from scratch. Graphical designs by Fabian Mößner. Some parts (libraries) of the software are created by other persons or organizations.

## License

This software is licensed under the [MIT license](https://opensource.org/licenses/MIT).
The font "Liberation Mono" which is used to print the gui has its own license: [View font license](https://www.fontsquirrel.com/license/liberation-mono)

