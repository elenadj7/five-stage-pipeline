# Five-stage pipeline processing simulator
The following instructions are supported: ADD, SUB, MUL, DIV, LOAD and STORE, with their standard meanings. ADD, SUB, LOAD and STORE require one cycle for execution (EX stage), while MUL and DIV require two cycles for execution (EX stage). LOAD and STORE require three cycles to access memory. It is assumed that the IF, ID, and WB stages always execute in one cycle. Each instruction has a maximum of three register operands (destination register and source register for unary operations, and destination register and two source registers for binary operations). A sequence of instructions is passed as input. The output is presented in a tabular form that shows, for each cycle, the stages in which the input instructions are located. It is possible to specify whether data forwarding between pipeline stages will occur or not. The output can display cases in which data forwarding occurs, with all relevant information (instruction sequence numbers and their execution stages during forwarding). The detection of hazards (WAR, RAW, and WAW dependencies) between instructions in the input sequence has also been implemented.
