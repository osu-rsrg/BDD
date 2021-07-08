# The Binary Decision Diagram (BDD) Software Component

This Java software component consists of

1. an abstraction of a boolean formula
   (split between two interfaces: `BooleanStructure` and `BooleanStructureKernel`),
   including formal behavioral specifications,
2. a reference implementation of this abstraction, `BooleanStructure1`, based on
   a simple (but inefficient) data structure, and
3. a second implementation of this abstraction, `BooleanStructure2`, based on
   the Binary Decision Diagram data structure.

The design and implementation of this component is described in Saad Asim's
Master's thesis.
A formal proof of correctness is given in Laine Rumreich's Master's thesis.

1. Asim, Saad.
   "[The Binary Decision Diagram: Abstraction and Implementation](http://rave.ohiolink.edu/etdc/view?acc_num=osu152414624378423)."
   Master's Thesis. Ohio State University, 2018.
2. Rumreich, Laine.
   "The Binary Decision Diagram: Formal Verification of a Reference Implementation."
   Master's Thesis. Ohio State University, 2021.

This BDD component uses a library of data structures
from Ohio State University's Software I/II courses.
To compile and use this BDD component, you will need the `components.jar` library,
which is available from the Software I/II course sequence
[homepage](http://web.cse.ohio-state.edu/software/web/index.html).
