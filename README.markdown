DNP3 Slave Simulator
=============================

Runs a set of [DNP3](http://www.dnp.org/) slave devices which publish updates randomly at a certain rate. Designed to
be used for integration and performance testing of front-end applications.

Uses the [TotalGrid](http://www.totalgrid.org) [open-source DNP3](http://code.google.com/p/dnp3/) project. Built to
work with the [Reef](http://reef.totalgrid.org) automation system.


Usage
=============================

To run the simulator:

    java -jar dnp3-slave-simulator-0.0.1.jar <config file> <update rate>

The update rate represents updates per second per point.

To create a Reef configuration that will run against the simulation configuration file:

    java -jar dnp3-sim-reef-0.0.1.jar <sim config file> <reef output file>


Simulation
=============================

The simulator tries to achieve the configurable update rate per point across all the points in all of the slave devices.
Binary inputs are always toggled, while analog and counters are updated with random numbers. The DNP quality field will
always be "ONLINE".

Because the update generation is currently very simple, the effective floor on the rate of updates is one point per device
per 100ms. The effective ceiling is all points on all devices every 100ms.

Generation is also currently unbalanced; the simulator will attempt to generate the same number of points per device regardless
of disparities in their databases. Using differently-shaped databases is *not* currently recommended.

Setup/Configuration
=============================

XML Format
-------------------

An example XML configuration file:

    <simNode version="1.0" targetNamespace="slavesim.dnp3.reef.totalgrid.org" xmlns="slavesim.dnp3.reef.totalgrid.org">
        <slaveArray startPort="50000" count="3">
            <pointWidth status="5" analog="5" counter="5"/>
        </slaveArray>
    </simNode>

This will create three slave devices on ports 50000, 50001, and 50002, all with the databases that contain DNP3 binary
input, analog input, and counter types with indexes 0-4.

The `count` attribute on `<slaveArray>` can be increased to start an arbitrarily large number of devices.
The `<pointWidth>` element is required (only once) and can be used to create larger point databases.

If slaves must be split over multiple port ranges, the `<slaveArray>` element can be repeated.


Effective DNP Configuration
--------------------

### Link layer
- Slave address: 1
- Master address: 100
- Confirms: none
- Retries: none
- Timeout: 5000ms

### Application layer
- Max fragment size: 2048
- Response timeout: 5000ms
- Retries: none

### Slave
- Unsolicited pack timer: 50ms
- Default Binary (static): Object 1 var 2
- Default Binary (event): Object 2 var 1
- Default Analog (static): Object 30 var 1
- Default Analog (event): Object 32 var 1
- Default Counter (static): Object 20 var 1
- Default Counter (event): Object 22 var 1

Building
=============================

[Maven 3.0.x](http://maven.apache.org/)

Developed in IntelliJ IDEA 10.

