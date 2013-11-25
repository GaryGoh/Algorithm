README
======

Distributed Systems Assignment
------------------------------

### What?

This project contains the skeleton of an application which simulates a
distributed system and can be used to complete the assignments for CS4616.

### How?

The assignment has been set up to run using `make` and `ant` - running either
command should build the project and run the resulting simulator program. The
'Run' button will then run the simulation, or you can use 'Step' to execute the
node program steps one at a time. Notice that the simulator isn't truly faithful
to the distributed network idea in that instructions are run on each node
sequentially as opposed to in parallel. In spite of this, the simulator is quite
accurate overall.

The first thing to notice is that each program terminates immediately - the
functionality of each node is what needs to be implemented for this assignment.

### Changes to Make

The program which will run on each node can be added to the `NodeProgram.main()`
method, which exists in `src/NodeProgram.java`. The implementation of the
`NodeProgram.getText()` should be updated to show whether a node is a leader or
not. The `NodeMessage` class should also be updated to carry more information
from node to node. This should be enough to implement the LCR algorithm; to
implement the HS algorithm you will also have to update the `Main.construct()`
method so that the call to `makeNodeRing()` creates a bidirectional ring, by
replacing the `EdgeDirection.UNIDIRECTIONAL` parameter with
`EdgeDirection.BIDIRECTIONAL`.
