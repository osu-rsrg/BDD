package components.booleanstructure;

import java.util.Iterator;

import components.map.Map;
import components.map.Map4;
import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.set.Set;
import components.set.Set4;

/**
 * {@code BooleanStructure} represented as a Binary Decision Diagram, with
 * implementations of primary methods.
 *
 * @mathdefinitions <pre>
 * POWERSET(
 *   e: set of integer
 * ): set of set of integer is
 * { s: set of integer where (s is subset of e) (s) }
 *
 * INTERVAL(
 *   n: integer
 * ) string of integer satisfies
 * | INTERVAL(n) | = n and
 * for all i: integer where (0 <= i < n) (INTERVAL(n)[i] = i)
 *
 * ROOT_DEPTH(
 *   bdd: string of string of NODE
 * ): integer satisfies
 * if (there exists d: integer where (0 < d < | bdd |)
 *     ( (| bdd[d] | > 0) and (for all e: integer where (| bdd[e] | > 0) (d >= e))) )
 *    then ROOT_DEPTH(bdd) = d
 * else
 *   ROOT_DEPTH(bdd) = 0
 *
 * BDD_CONTAINS_NODE(
 *   bdd: string of string of NODE,
 *   n : NODE): boolean is
 * there exists i: integer (n is element of entries(bdd[i]))
 *
 * BDD_CONTAINS_COORD(
 *   bdd: string of string of NODE,
 *   c: COORD
 *   root: COORD): boolean is
 * if root.depth = 0 then root = c
 * else
 *   ( c.depth = 0 and 0 <= c.index < 2 ) or
 *   ( 1 <= c.depth < | bdd | and 0 <= c.index < | bdd[c.depth] | )
 *
 * EVALUATE_BDD(
 *   bdd: string of string of NODE,
 *   root: COORD,
 *   vars: string of integer,
 *   trueFirst: boolean,
 *   a: ASSIGNMENT): boolean satisfies
 * ( if root.depth = 0 then trueFirst iff ( root.index = 0) ) and
 * ( if vars[| vars | - root.depth] is in a then
 *     EVALUATE_BDD(bdd, bdd[root.depth][root.index].hi, vars, a) ) and
 * ( if vars[| vars | - root.depth] is not in a then
 *     EVALUATE_BDD(bdd, bdd[root.depth][root.index].lo, vars, a) )
 *
 * OBDD_CONVENTIONS(
 *   bdd: string of string of NODE,
 *   root: COORD,
 *   vars: string of integer
 * ): boolean is
 * ( root.index = 0 ) and
 *
 * ( | bdd[root.depth] | = 1 iff root.depth > 0 ) and
 *
 * ( | vars | = | entries(vars) | ) and
 *
 * ( | bdd | = | vars | + 1 ) and
 *
 * ( bdd[0] = <> ) and
 *
 * ( for all i: integer where (root.depth < i < | bdd |) (bdd[i] = <>) ) and
 *
 * ( for all d, i: integer where (BDD_CONTAINS_COORD(bdd, (d, i), root) and d > 0)
 *     ( BDD_CONTAINS_COORD(bdd, bdd[d][i].hi, root))  and
 *     ( BDD_CONTAINS_COORD(bdd, bdd[d][i].lo, root))  and
 *     ( bdd[d][i].hi.depth < d and bdd[d][i].lo.depth < d) ) and
 *
 * ( for all d, i: integer where (BDD_CONTAINS_COORD(bdd, (d, i), root))
 *     ( there exists path: string of COORD
 *       ( path[0] = root and
 *         path[|path| - 1] = (d, i) and
 *         for all j where (0 <= j < |path| - 1)
 *           (bdd[path[j].depth][path[j].index].hi = path[j + 1] or
 *            bdd[path[j].depth][path[j].index].lo = path[j + 1])
 *       )
 *     )
 *  )
 *
 * ROBDD_CONVENTIONS(
 *   bdd: string of string of NODE,
 *   root: COORD,
 *   vars: string of integer
 * ): boolean is
 * ( OBDD_CONVENTIONS(bdd, root, vars) and
 *
 * ( for all n: NODE where (BDD_CONTAINS_NODE(bdd, n))
 *   ( n.hi != n.lo ) ) and
 *
 * ( for all d : integer where (0 <= d <= root.depth)
 *	 ( for all i, j: integer where (0 <= i < |bdd[d]| and 0 <= j < |bdd[d]|)
 *       ( if bdd[d][i].hi = bdd[d][j].hi and bdd[d][i].lo = bdd[d][j].lo then i = j )
 *   ) )
 * </pre>
 *
 * @convention <pre>
 * ROBDD_CONVENTIONS($this.bdd, $this.root, $this.vars)
 * </pre>
 *
 * @correspondence <pre>
 * this =
 *   ( { a: ASSIGNMENT where
 *       ( a is in POWERSET(entries($this.vars)) and
 *         EVALUATE_BDD($this.bdd, $this.root, $this.vars, $this.trueFirst, a) )
 *     ( a ) },
 *     $this.vars )
 * </pre>
 */

public class BooleanStructure2 extends BooleanStructureSecondary {

    /**
     * Coord helper class
     *
     * @mathmodel type COORD is modeled by (depth: integer, index: integer)
     *
     * @correspondence this = ($this.depth, $this.index)
     */
    private static class Coord {

        // Row of element
        public int depth;

        // Column of element
        public int index;

        public Coord(int depth, int index) {
            this.depth = depth;
            this.index = index;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Coord)) {
                return false;
            }

            Coord a = (Coord) obj;

            return this.depth == a.depth && this.index == a.index;
        }

        @Override
        public int hashCode() {
            int result = 2;
            result = 37 * result + this.depth;
            result = 37 * result + this.index;

            return result;
        }

        @Override
        public String toString() {
            return "(" + this.depth + ", " + this.index + ")";
        }
    }

    /**
     * Node helper class
     *
     * @mathmodel type NODE is modeled by (hi: COORD, lo: COORD)
     *
     * @correspondence this = ($this.hi, $this.lo)
     */
    private static class Node {

        // Location of hi child
        public Coord hi;

        // Location of lo child
        public Coord lo;

        public Node(Coord hi, Coord lo) {
            this.hi = hi;
            this.lo = lo;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Node)) {
                return false;
            }

            Node a = (Node) obj;
            return this.hi.equals(a.hi) && this.lo.equals(a.lo);

        }

        @Override
        public int hashCode() {
            int result = 2;
            result = 37 * result + this.hi.depth + this.hi.index;
            result = 37 * result + this.lo.depth + this.lo.index;

            return result;
        }

        @Override
        public String toString() {
            return "[" + this.hi.toString() + ", " + this.lo.toString() + "]";
        }
    }

    /**
     * IDPair helper class (used only for reduction algorithm implementation)
     *
     * @mathmodel type IDPAIR is modeled by (hiID: integer, loID: integer)
     *
     * @correspondence this = ($this.hiID, $this.loID)
     */
    private static class IDPair {
        // ID of lo child
        int loID;
        // ID of hi child
        int hiID;

        public IDPair(int loID, int hiID) {
            this.loID = loID;
            this.hiID = hiID;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof IDPair)) {
                return false;
            }

            IDPair i = (IDPair) obj;

            return this.loID == i.loID && this.hiID == i.hiID;
        }

        @Override
        public int hashCode() {
            int result = 2;
            result = 37 * result + this.hiID;
            result = 37 * result + this.loID;

            return result;
        }

    }

    /*
     * Private members
     */
    private Sequence<Sequence<Node>> bdd;
    private Sequence<Integer> vars;
    private Coord root;
    private boolean trueFirst;

    /*
     * Private helper methods
     */

    /**
     * Reports whether {@code c} is the location of a leaf node
     *
     * @param c
     *            the coord to check
     * @return true iff c corresponds to a leaf node
     * @ensures isLeaf = (c.depth = 0)
     */
    private static boolean isLeaf(Coord c) {
        return c.depth == 0;
    }

    /**
     * Reports whether {@code c} is the location of the true literal or false
     * literal
     *
     * @param c
     *            the coord to check
     * @param trueFirst
     *            tells whether the true literal is currently at index 0
     * @return true iff c corresponds to the True literal
     * @requires c.depth = 0
     * @ensures leafValue = ((c.index = 0 and trueFirst) or (c.index = 1 and not
     *          trueFirst))
     */
    private static boolean leafValue(Coord c, boolean trueFirst) {
        assert c.depth == 0 : "Violation of c.depth = 0";

        boolean leafValue = trueFirst;
        if (c.index == 1) {
            leafValue = !leafValue;
        }

        return leafValue;
    }

    /**
     * Returns the variable label corresponding to a depth
     *
     * @param vars
     *            the ordering
     * @param depth
     *            the depth to get the label for
     * @return the variable label at depth
     * @requires 0 < depth < |vars|
     * @ensures getLabel = vars[|vars| - depth]
     */
    private static int getLabel(Sequence<Integer> vars, int depth) {
        return vars.entry(vars.length() - depth);
    }

    /**
     * Returns the node at the location {@code c} in {@code bdd}
     *
     * @param bdd
     *            the Sequence containing all nodes of the bdd
     * @param c
     *            the coordinate of the desired node
     * @return the node at position c in bdd
     * @requires <pre>
     *      0 < c.depth < |bdd| and
     *      0 <= index < |bdd[i]|
     * </pre>
     * @ensures getNode = bdd[c.depth][c.index]
     */
    private static Node getNode(Sequence<Sequence<Node>> bdd, Coord c) {
        return bdd.entry(c.depth).entry(c.index);
    }

    /**
     * Finds the new position of the root in {@code bdd} after a restrict
     * operation
     *
     * @param bdd
     *            the bdd structure to be restricted
     * @param root
     *            the location of the first node to consider
     * @param vars
     *            the current variable ordering of the bdd
     * @param t
     *            a set containing the variables to be constrained to true
     * @param f
     *            a set containing the variables to be constrainted to false
     * @return the new root of bdd following a restrict operation with variables
     *         in t constrained to true and variables in f constrained to false
     * @requires <pre>
     *      |bdd| = |vars| + 1 and
     * 		0 <= child.depth < |bdd| and
     * 		0 <= child.index < |bdd[child.depth]| and
     * 		t union f is subset of entries(vars) and
     * 		t intersection f = empty_set
     * </pre>
     * @ensures <pre>
     * 		findUnconstrainedRoot = root or
     * 		there exists path: String of COORD
     * 		  ( path[0] = root and
     * 		    path[|path - 1|] = findUnconstrainedRoot and
     * 		    for all j where (0 <= j < |path| - 1)
     * 		      ( (bdd[path[j].depth][path[j].index].hi = path[j + 1] and vars[|vars| - path[j].depth] is in t) or
     * 				(bdd[path[j].depth][path[j].index].lo = path[j + 1] and vars[|vars| - path[j].depth] is in f) ) )
     * </pre>
     */
    private static Coord findUnconstrainedRoot(Sequence<Sequence<Node>> bdd,
            Coord root, Sequence<Integer> vars, Set<Integer> t,
            Set<Integer> f) {
        Coord returnChild = new Coord(root.depth, root.index);

        while (!isLeaf(returnChild)
                && (t.contains(getLabel(vars, returnChild.depth))
                        || f.contains(getLabel(vars, returnChild.depth)))) {
            Node rootNode = bdd.entry(returnChild.depth)
                    .entry(returnChild.index);
            if (t.contains(getLabel(vars, returnChild.depth))) {
                returnChild.depth = rootNode.hi.depth;
                returnChild.index = rootNode.hi.index;
            } else {
                returnChild.depth = rootNode.lo.depth;
                returnChild.index = rootNode.lo.index;
            }
        }

        return returnChild;
    }

    /**
     * Updates {@code reduced} to be the maximally compact version of
     * {@code oldBDD} if necessary
     *
     * @param oldBDD
     *            the unreduced BDD
     * @param reduced
     *            the reduced BDD
     * @param oldTrueFirst
     *            the value of trueFirst prior to the reduction
     * @param root
     *            the root of the bdd
     * @replaces reduced
     * @updates root
     * @return the value of trueFirst for the reduced bdd
     * @requires <pre>
     *   |#reduced| = |oldBDD|
     *   OBDD_CONVENTIONS(oldBDD, (#root, 0), INTERVAL(|oldBDD| - 1))
     * </pre>
     * @ensures <pre>
     *   root = (ROOT_DEPTH(reduced), 0) and
     *	 if root == (0, 1) then reduce = false and
     *   if root != (0, 1) then reduce = #reduce and
     *   RODBDD_CONVENTIONS(reduced, root, INTERVAL(|reduced| - 1)) and
     *	 for all a: ASSIGNMENT where (a is in POWERSET(entries(INTERVAL(|reduced| - 1))))
     *     ( EVALUATE_BDD(reduced, root, INTERVAL(|reduced| - 1), reduce, a) =
     *       EVALUATE_BDD(oldBDD, #root, INTERVAL(|reduced| - 1), oldTrueFirst, a) )
     * </pre>
     */
    private static boolean reduce(Sequence<Sequence<Node>> oldBDD,
            Sequence<Sequence<Node>> reduced, boolean oldTrueFirst,
            Coord root) {
        boolean trueRoot = true;
        boolean isFalseLiteral = (oldTrueFirst && root.depth == 0
                && root.index == 1)
                || (!oldTrueFirst && root.depth == 0 && root.index == 0);
        int currentID = 2;

        // ID of node based on position in undreduced BDD
        int[][] idArr = new int[oldBDD.length()][];
        for (int i = 0; i < idArr.length; i++) {
            idArr[i] = new int[oldBDD.entry(i).length()];
        }

        // Set IDs for terminals
        idArr[0] = new int[2];
        idArr[0][0] = 0;
        idArr[0][1] = 1;

        // Position of node in reduced BDD based on ID. Terminal nodes added
        // immediately
        Map<Integer, Coord> reducedLocMap = new Map4<Integer, Coord>();
        reducedLocMap.add(0, new Coord(0, 0));
        reducedLocMap.add(1, new Coord(0, 1));

        // Iterate over all variables of BDD going bottom up
        for (int j = 1; j < oldBDD.length(); j++) {
            // Map to check for duplicate subBDDs among nodes with same
            // variables
            Map<IDPair, Integer> currVarMap = new Map4<IDPair, Integer>();

            // Iterate over each node with the same variable
            for (int k = 0; k < oldBDD.entry(j).length(); k++) {
                Node current = oldBDD.entry(j).entry(k);

                int loID = idArr[current.lo.depth][current.lo.index];
                int hiID = idArr[current.hi.depth][current.hi.index];

                IDPair childPair = new IDPair(loID, hiID);

                // Check if ID of lo and hi child are the same
                if (loID == hiID) {
                    idArr[j][k] = loID;
                    if (loID == 1) {
                        trueRoot = false;
                    }
                } else if (currVarMap.hasKey(childPair)) {
                    idArr[j][k] = currVarMap.value(childPair);
                } else {
                    // Update IDs and maps
                    idArr[j][k] = currentID;
                    currVarMap.add(childPair, currentID);
                    reducedLocMap.add(currentID,
                            new Coord(j, reduced.entry(j).length()));
                    currentID++;

                    // Add new Node to reduced BDD
                    Coord reducedLoLoc = reducedLocMap.value(loID);
                    Coord reducedHiLoc = reducedLocMap.value(hiID);
                    Node newNode = new Node(reducedHiLoc, reducedLoLoc);
                    reduced.entry(j).add(reduced.entry(j).length(), newNode);
                }
            }
        }

        // Assign new root based on last ID assigned
        root.depth = reducedLocMap.value(currentID - 1).depth;
        root.index = reducedLocMap.value(currentID - 1).index;

        // Account for Reduced BDD being the literal true
        if (currentID == 2 && trueRoot && !isFalseLiteral) {
            root.depth = reducedLocMap.value(0).depth;
            root.index = reducedLocMap.value(0).index;
        }

        // Update trueFirst and the root if we've reduced down to the False literal
        boolean newTrueFirst = oldTrueFirst;

        if (root.depth == 0 && root.index == 1) {
            root.depth = 0;
            root.index = 0;
            newTrueFirst = false;
        }
        return newTrueFirst;

    }

    /**
     * Produces the OBDD resulting from applying a logical operator between two
     * bdds
     *
     * @param thisBDD
     *            the bdd structure apply is being called on
     * @param thisRoot
     *            the root of the bdd apply is being called on
     * @param xBDD
     *            the other bdd to apply the logical operator on
     * @param xRoot
     *            the root of the other bdd
     * @param op
     *            the binary operator being applied between the two bdds
     * @param thisVars
     *            the ordering of the bdd apply is being called on
     * @param xVars
     *            the ordering of the other bdd
     * @param newVars
     *            the ordering of the resulting bdd
     * @param thisTrueFirst
     *            tells whether the true literal is currently at index 0 for the
     *            bdd structure apply is being called on
     * @param xTrueFirst
     *            tells whether the true literal is currently at index 0 for the
     *            other bdd
     * @param newBDD
     *            the resulting bdd structure
     * @updates newBDD
     * @return the root of the resulting bdd
     * @requires <pre>
     *   ROBDD_CONVENTIONS(thisBDD, thisRoot, thisVars) and
     *   ROBDD_CONVENTIONS(xBDD, xRoot, xVars) and
     *   IS_COMPATIBLE_ORDERING(thisVars, xVars) and
     *   entries(newVars) = entries(thisVars) union entries(xVars) and
     *   | newVars | = | entries(newVars) | and
     *   | newBDD | = | newVars | + 1
     * </pre>
     * @ensures <pre>
     *   combineBDD = (ROOT_DEPTH(newBDD), 0) and
     *   OBDD_CONVENTIONS(newBDD, combineBDD, newVars) and
     *   ( ( if op = AND then for all a: ASSIGNMENT where (a is in POWERSET(entries(newVars)))
     *                          ( EVALUATE_BDD(newBDD, combineBDD, newVars, true, a) =
     *                              EVALUATE_BDD(thisBDD, thisRoot, thisVars, thisTrueFirst, a) and
     *                              EVALUATE_BDD(xBDD, xRoot, xVars, xTrueFirst, a)
     *                          )
     *     ) and
     *     ( if op = OR then for all a: ASSIGNMENT where (a is in POWERSET(entries(newVars)))
     *                          ( EVALUATE_BDD(newBDD, combineBDD, newVars, true, a) =
     *                              EVALUATE_BDD(thisBDD, thisRoot, thisVars, thisTrueFirst, a) or
     *                              EVALUATE_BDD(xBDD, xRoot, xVars, xTrueFirst, a)
     *                          )
     *     ) and
     *     ( if op = EQUIVALS then for all a: ASSIGNMENT where (a is in POWERSET(entries(newVars)))
     *                          ( EVALUATE_BDD(newBDD, combineBDD, newVars, true, a) =
     *                              ( EVALUATE_BDD(thisBDD, thisRoot, thisVars, thisTrueFirst, a) =
     *                                EVALUATE_BDD(xBDD, xRoot, xVars, xTrueFirst, a) )
     *                          )
     *     )
     *   )
     * </pre>
     */
    private static Coord combineBDD(Sequence<Sequence<Node>> thisBDD,
            Coord thisRoot, Sequence<Sequence<Node>> xBDD, Coord xRoot,
            BinaryOperator op, Sequence<Integer> thisVars,
            Sequence<Integer> xVars, Sequence<Integer> newVars,
            boolean thisTrueFirst, boolean xTrueFirst,
            Sequence<Sequence<Node>> newBDD) {

        // Depth to insert the Node at
        int insertionDepth;

        // Coord to insert at and return
        Coord insertionCoord;

        // Both nodes are leaves
        if (isLeaf(thisRoot) && isLeaf(xRoot)) {

            boolean opResult = leafValue(thisRoot, thisTrueFirst);
            switch (op) {
                case AND:
                    opResult = opResult && leafValue(xRoot, xTrueFirst);
                    break;
                case OR:
                    opResult = opResult || leafValue(xRoot, xTrueFirst);
                    break;
                case EQUIVALS:
                    opResult = opResult == leafValue(xRoot, xTrueFirst);
                    break;
                default:
                    assert false : "Apply of unrecognized BinaryOpertor: " + op;
            }

            if (opResult) {
                insertionCoord = new Coord(0, 0);
            } else {
                insertionCoord = new Coord(0, 1);
            }
        } else {
            // Node to be added to BDD
            Node newNode;

            Coord hiCoord;
            Coord loCoord;

            if (!isLeaf(thisRoot) && !isLeaf(xRoot) && (getLabel(thisVars,
                    thisRoot.depth) == getLabel(xVars, xRoot.depth))) {

                Node thisNode = getNode(thisBDD, thisRoot);
                Node xNode = getNode(xBDD, xRoot);

                // Recursively add and get reference to Hi and Lo nodes
                hiCoord = combineBDD(thisBDD, thisNode.hi, xBDD, xNode.hi, op,
                        thisVars, xVars, newVars, thisTrueFirst, xTrueFirst,
                        newBDD);
                loCoord = combineBDD(thisBDD, thisNode.lo, xBDD, xNode.lo, op,
                        thisVars, xVars, newVars, thisTrueFirst, xTrueFirst,
                        newBDD);

                // Set the insertion depth
                insertionDepth = newVars.length() - findIndex(
                        getLabel(thisVars, thisRoot.depth), newVars, 0);
            } else if (!isLeaf(thisRoot) && (isLeaf(xRoot) || findIndex(
                    getLabel(thisVars, thisRoot.depth), newVars,
                    0) < findIndex(getLabel(xVars, xRoot.depth), newVars, 0))) {
                // The node from this has a higher precedence than the node from
                // x
                Node thisNode = getNode(thisBDD, thisRoot);

                // Recursively add and get reference to Hi and Lo nodes
                hiCoord = combineBDD(thisBDD, thisNode.hi, xBDD, xRoot, op,
                        thisVars, xVars, newVars, thisTrueFirst, xTrueFirst,
                        newBDD);
                loCoord = combineBDD(thisBDD, thisNode.lo, xBDD, xRoot, op,
                        thisVars, xVars, newVars, thisTrueFirst, xTrueFirst,
                        newBDD);

                // Set the insertion depth
                insertionDepth = newVars.length() - findIndex(
                        getLabel(thisVars, thisRoot.depth), newVars, 0);
            } else {
                // The node from x has a higher precedence than the node from
                // this
                Node xNode = getNode(xBDD, xRoot);

                // Recursively add and get reference to Hi and Lo nodes
                hiCoord = combineBDD(thisBDD, thisRoot, xBDD, xNode.hi, op,
                        thisVars, xVars, newVars, thisTrueFirst, xTrueFirst,
                        newBDD);
                loCoord = combineBDD(thisBDD, thisRoot, xBDD, xNode.lo, op,
                        thisVars, xVars, newVars, thisTrueFirst, xTrueFirst,
                        newBDD);

                // Create the new node
                newNode = new Node(hiCoord, loCoord);

                // Set the insertion depth
                insertionDepth = newVars.length()
                        - findIndex(getLabel(xVars, xRoot.depth), newVars, 0);
            }
            // Create the new node
            newNode = new Node(hiCoord, loCoord);

            // Insert position and coordinate return value
            insertionCoord = new Coord(insertionDepth,
                    newBDD.entry(insertionDepth).length());
            newBDD.entry(insertionCoord.depth).add(insertionCoord.index,
                    newNode);
        }

        return insertionCoord;
    }

    /**
     * Evaluate a BooleanStructure2 BDD for a given assignment
     *
     * @param bdd
     *            the bdd structure
     * @param vars
     *            the variable ordering
     * @param root
     *            the location of the root in the bdd structure
     * @param trueFirst
     *            tells whether the true literal is currently at index 0 for the
     *            bdd structure
     * @param t
     *            the assignment to evaluate
     * @return true iff the BDD evaluates to true for the given assignment
     * @requires ROBDD_CONVENTIONS(bdd, vars, root)
     * @ensures evaluateBDD = EVALUATE_BDD(bdd, vars, root, trueFirst, t)
     */
    private static boolean evaluateBDD(Sequence<Sequence<Node>> bdd,
            Sequence<Integer> vars, Coord root, boolean trueFirst,
            Set<Integer> t) {
        Coord currentCoord = root;
        while (!isLeaf(currentCoord)) {
            Node currentNode = getNode(bdd, currentCoord);
            if (t.contains(getLabel(vars, currentCoord.depth))) {
                currentCoord = currentNode.hi;
            } else {
                currentCoord = currentNode.lo;
            }
        }

        return leafValue(currentCoord, trueFirst);
    }

    // Creator of initial representation
    private void createNewRep() {
        // Set up BDD as a sequence of sequences with a true node
        this.bdd = new Sequence1L<Sequence<Node>>();
        this.bdd.add(0, new Sequence1L<Node>());

        // Instantiate vars
        this.vars = new Sequence1L<Integer>();

        // Instantiate the root
        this.root = new Coord(0, 0);

        // The true label appears first
        this.trueFirst = true;
    }

    /*
     * Constructors
     */

    /**
     * No-argument constructor.
     */
    public BooleanStructure2() {
        this.createNewRep();
    }

    /**
     * Constructor from {@code boolean}.
     *
     * @param b
     *            {@code boolean} to initialize from
     */
    public BooleanStructure2(boolean b) {
        this.createNewRep();

        this.trueFirst = b;
    }

    /**
     * Constructor from {@code int}.
     *
     * @param i
     *            {@code int} to initialize from
     */
    public BooleanStructure2(int i) {
        this.createNewRep();

        // Create a new row for the variable node at position 1 in the BDD
        // sequence
        Sequence<Node> varRow = new Sequence1L<Node>();
        this.bdd.add(1, varRow);

        // Add the variable node to newly created row
        Coord hi = new Coord(0, 0);
        Coord lo = new Coord(0, 1);

        Node varNode = new Node(hi, lo);

        // Add to BDD and set as new root
        this.bdd.entry(1).add(0, varNode);
        this.root = new Coord(1, 0);

        // Add variable to order
        this.vars.add(0, i);
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @Override
    public void clear() {
        this.createNewRep();
    }

    @Override
    public final BooleanStructure newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public void transferFrom(BooleanStructure source) {
        assert source != null : "Violation of:" + " source is not null";
        assert source != this : "Violation of:" + " source is not this";
        assert source instanceof BooleanStructure2 : ""
                + "Violation of: source is of dynamic"
                + " type BooleanExpression2";

        BooleanStructure2 localSource = (BooleanStructure2) source;
        this.bdd = localSource.bdd;
        this.vars = localSource.vars;
        this.root = localSource.root;
        this.trueFirst = localSource.trueFirst;

        localSource.createNewRep();
    }

    @Override
    public void copyFrom(BooleanStructure source) {
        assert source != null : "Violation of:" + " source is not null";
        assert source != this : "Violation of:" + " source is not this";
        if (!(source instanceof BooleanStructure2)) {
            super.copyFrom(source);
        } else {
            // Create copy of BDD structure
            BooleanStructure2 localSource = (BooleanStructure2) source;
            Sequence<Sequence<Node>> copyBDD = new Sequence1L<Sequence<Node>>();
            for (int i = 0; i < localSource.bdd.length(); i++) {
                copyBDD.add(i, new Sequence1L<Node>());
                for (int j = 0; j < localSource.bdd.entry(i).length(); j++) {
                    Node current = localSource.bdd.entry(i).entry(j);
                    Coord hi = new Coord(current.hi.depth, current.hi.index);
                    Coord lo = new Coord(current.lo.depth, current.lo.index);

                    Node copyNode = new Node(hi, lo);
                    copyBDD.entry(i).add(j, copyNode);
                }
            }

            // Create copy of variable ordering
            Sequence<Integer> copyVars = new Sequence1L<Integer>();
            for (int i = 0; i < localSource.vars.length(); i++) {
                copyVars.add(i, localSource.vars.entry(i));
            }

            // Create copy of root Coord
            Coord copyRoot = new Coord(localSource.root.depth,
                    localSource.root.index);

            // Set values for BDD
            this.bdd = copyBDD;
            this.vars = copyVars;
            this.root = copyRoot;
            this.trueFirst = localSource.trueFirst;
        }
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public boolean evaluate(Set<Integer> t) {
        return evaluateBDD(this.bdd, this.vars, this.root, this.trueFirst, t);
    }

    @Override
    public void apply(BinaryOperator o, BooleanStructure x,
            Sequence<Integer> newVars) {
        assert x instanceof BooleanStructure2 : ""
                + "Violation of: x is a BooleanExpression2";
        assert seqToSet(newVars).equals(union(seqToSet(this.vars),
                seqToSet(x.vars()))) : "Violation of: "
                        + "elements(newVars) = VARIABLES(this) union VARIABLES(x)";
        assert newVars.length() == seqToSet(newVars).size() : "Violation of: "
                + "| newVars | = | elements(newVars) |";
        assert IS_COMPATIBLE_ORDERING(this.vars, x.vars()) : "Violation of: "
                + "IS_COMPATIBLE_ORDERING(this.vars, x.vars)";
        assert IS_COMPATIBLE_ORDERING(this.vars, newVars) : "Violation of: "
                + "IS_COMPATIBLE_ORDERING(this.vars, newVars)";
        assert IS_COMPATIBLE_ORDERING(x.vars(), newVars) : "Violation of: "
                + "IS_COMPATIBLE_ORDERING(x.vars, newVars)";

        // Cast x as a BE2 to access its private members BooleanExpression2
        BooleanStructure2 localX = (BooleanStructure2) x;

        // Instantiate |vars| rows for the new BDD
        Sequence<Sequence<Node>> newBDD = this.bdd.newInstance();
        Sequence<Sequence<Node>> reducedBDD = this.bdd.newInstance();
        for (int i = 0; i < newVars.length() + 1; i++) {
            newBDD.add(i, new Sequence1L<Node>());
            reducedBDD.add(i, new Sequence1L<Node>());
        }

        this.root = combineBDD(this.bdd, this.root, localX.bdd, localX.root, o,
                this.vars, localX.vars, newVars, this.trueFirst,
                localX.trueFirst, newBDD);

        // Reduce the BDD
        this.trueFirst = reduce(newBDD, reducedBDD, true, this.root);
        this.bdd = reducedBDD;

        // Swap the vars
        Sequence<Integer> tempVars = newVars.newInstance();
        tempVars.transferFrom(this.vars);
        this.vars.transferFrom(newVars);
        newVars.transferFrom(tempVars);

    }

    @Override
    public void apply(UnaryOperator o) {
        if (o == UnaryOperator.NOT) {
            this.trueFirst = !this.trueFirst;
        }
    }

    @Override
    public void restrict(Set<Integer> t, Set<Integer> f) {
        assert union(t, f).isSubset(seqToSet(this.vars)) : "Violation of: "
                + "t union f is subset of VARIABLES(this)";
        assert intersection(t, f).size() == 0 : "Violation of: "
                + "t intersection f = empty_set";
        // Create structures to keep track of reachability and new indices for
        // nodes
        boolean[][] reachArr = new boolean[this.bdd.length()][];
        Coord[][] coordArr = new Coord[this.bdd.length()][];
        for (int i = 1; i < this.bdd.length(); i++) {
            reachArr[i] = new boolean[this.bdd.entry(i).length()];
            coordArr[i] = new Coord[this.bdd.entry(i).length()];
        }

        // Special cases for T/F row
        reachArr[0] = new boolean[2];
        coordArr[0] = new Coord[2];
        reachArr[0][0] = true;
        reachArr[0][1] = true;
        coordArr[0][0] = new Coord(0, 0);
        coordArr[0][1] = new Coord(0, 1);

        // In case root and descendants are restricted, find where the root of
        // the restricted BDD will be (original structure)
        Coord restrRoot = findUnconstrainedRoot(this.bdd, this.root, this.vars,
                t, f);

        // Mark root and its children as reachable
        if (!isLeaf(restrRoot)) {
            reachArr[restrRoot.depth][restrRoot.index] = true;

            Node rootNode = this.bdd.entry(restrRoot.depth)
                    .entry(restrRoot.index);

            reachArr[rootNode.hi.depth][rootNode.hi.index] = true;
            reachArr[rootNode.lo.depth][rootNode.lo.index] = true;
        }

        // Mark the rest of the nodes if they're reachable
        for (int i = restrRoot.depth - 1; i > 0; i--) {
            if (!f.contains(getLabel(this.vars, i))) {
                for (int j = 0; j < reachArr[i].length; j++) {
                    if (reachArr[i][j]) {
                        Node current = this.bdd.entry(i).entry(j);
                        reachArr[current.hi.depth][current.hi.index] = true;
                    }

                }
            }
            if (!t.contains(getLabel(this.vars, i))) {
                for (int j = 0; j < reachArr[i].length; j++) {
                    if (reachArr[i][j]) {
                        Node current = this.bdd.entry(i).entry(j);
                        reachArr[current.lo.depth][current.lo.index] = true;
                    }
                }
            }
        }

        // Instantiate BDDs for restriction and reduction
        int numRemVars = this.vars.length() - t.size() - f.size();
        Sequence<Sequence<Node>> restrBDD = this.bdd.newInstance();
        Sequence<Sequence<Node>> reducedBDD = this.bdd.newInstance();
        for (int i = 0; i < numRemVars + 1; i++) {
            restrBDD.add(i, new Sequence1L<Node>());
            reducedBDD.add(i, new Sequence1L<Node>());
        }

        // Create new ordering
        Sequence<Integer> newVars = this.vars.newInstance();
        for (int i = 0; i < this.vars.length(); i++) {
            if (!t.contains(this.vars.entry(i))
                    && !f.contains(this.vars.entry(i))) {
                newVars.add(newVars.length(), this.vars.entry(i));
            }
        }

        // Depth we're adding at in restricted structure
        int restrDepth = 1;

        // Location of root once structure has been restricted
        int updatedRestrRootDepth = restrRoot.depth;
        int updatedRestrRootIndex = restrRoot.index;

        for (int i = 1; i <= restrRoot.depth; i++) {
            if (!t.contains(getLabel(this.vars, i))
                    && !f.contains(getLabel(this.vars, i))) {
                // Index of node in restricted structure
                int restrIndex = 0;
                for (int j = 0; j < reachArr[i].length; j++) {
                    // Check if it's a node that'll be in the restricted
                    // structure (is reachable after restrict)
                    if (reachArr[i][j]) {
                        Node currentNode = this.bdd.entry(i).entry(j);

                        // Find coord of new hi child of current node in
                        // restricted structure
                        Coord hi = coordArr[currentNode.hi.depth][currentNode.hi.index];

                        // Find coord of new lo child of current node in
                        // restricted structure
                        Coord lo = coordArr[currentNode.lo.depth][currentNode.lo.index];

                        // Add node to the restricted structure
                        Node newNode = new Node(hi, lo);
                        restrBDD.entry(restrDepth).add(restrIndex, newNode);

                        // Last reachable element is root
                        updatedRestrRootDepth = restrDepth;
                        updatedRestrRootIndex = restrIndex;

                        // Set the index that the node was placed at in the
                        // restricted structure
                        coordArr[i][j] = new Coord(restrDepth, restrIndex);
                        restrIndex++;
                    }
                }
                restrDepth++;
            } else {
                if (t.contains(getLabel(this.vars, i))) {
                    for (int j = 0; j < reachArr[i].length; j++) {
                        Node currentNode = this.bdd.entry(i).entry(j);
                        coordArr[i][j] = coordArr[currentNode.hi.depth][currentNode.hi.index];
                    }
                } else {
                    for (int j = 0; j < reachArr[i].length; j++) {
                        Node currentNode = this.bdd.entry(i).entry(j);
                        coordArr[i][j] = coordArr[currentNode.lo.depth][currentNode.lo.index];
                    }
                }
            }
        }

        this.root = new Coord(updatedRestrRootDepth, updatedRestrRootIndex);

        // Reduce the BDD
        this.trueFirst = reduce(restrBDD, reducedBDD, this.trueFirst,
                this.root);
        this.bdd = reducedBDD;

        // Transfer vars
        this.vars.transferFrom(newVars);
    }

    @Override
    public void reorder(Sequence<Integer> newVars) {
        assert seqToSet(this.vars).equals(seqToSet(
                newVars)) : "Violation of: VARIABLES(this) = elements(newVars)";

        if (newVars.length() > 1) {
            int depth = 0;
            int numNodes = 1;

            // Instantiate rows for new BDDs
            Sequence<Sequence<Node>> reordered = this.bdd.newInstance();
            Sequence<Sequence<Node>> reduced = this.bdd.newInstance();
            for (int i = 0; i < newVars.length() + 1; i++) {
                reordered.add(i, new Sequence1L<Node>());
                reduced.add(i, new Sequence1L<Node>());
            }

            // Populate all but the last nonterminal node
            for (int i = reordered.length() - 1; i > 1; i--) {
                // Put 2^depth nodes in current row
                for (int j = 0; j < numNodes; j++) {
                    // Position of hi is one level above, at index 2j. Lo is at
                    // same level at index 2j + 1
                    Node newNode = new Node(new Coord(i - 1, 2 * j),
                            new Coord(i - 1, 2 * j + 1));
                    reordered.entry(i).add(reordered.entry(i).length(),
                            newNode);
                }

                depth++;
                numNodes = (int) Math.pow(2.0, depth);
            }

            // Set hi/lo coords based on evaluation in original BDD
            PowerStringElements allAssignments = new PowerStringElements(
                    newVars);
            Iterator<Set<Integer>> assignIt = allAssignments.iterator();
            for (int j = 0; j < numNodes; j++) {
                Coord hi;
                Coord lo;

                if (evaluateBDD(this.bdd, this.vars, this.root, this.trueFirst,
                        assignIt.next())) {
                    hi = new Coord(0, 0);
                } else {
                    hi = new Coord(0, 1);
                }

                if (evaluateBDD(this.bdd, this.vars, this.root, this.trueFirst,
                        assignIt.next())) {
                    lo = new Coord(0, 0);
                } else {
                    lo = new Coord(0, 1);
                }

                reordered.entry(1).add(reordered.entry(1).length(),
                        new Node(hi, lo));
            }

            // Reduce the BDD
            this.trueFirst = reduce(reordered, reduced, this.trueFirst,
                    this.root);
            this.bdd = reduced;

            // Swap the vars
            Sequence<Integer> tempVars = newVars.newInstance();
            tempVars.transferFrom(this.vars);
            this.vars.transferFrom(newVars);
            newVars.transferFrom(tempVars);
        }
    }

    @Override
    public Sequence<Integer> vars() {
        return this.vars;
    }

    @Override
    public void setFromInt(int i) {
        this.createNewRep();

        // Create a new row for the variable node at position 1 in the BDD
        // sequence
        Sequence<Node> varRow = new Sequence1L<Node>();
        this.bdd.add(1, varRow);

        // Add the variable node to newly created row
        Coord hi = new Coord(0, 0);
        Coord lo = new Coord(0, 1);

        Node varNode = new Node(hi, lo);

        // Add to BDD and set as new root
        this.bdd.entry(1).add(0, varNode);
        this.root = new Coord(1, 0);

        // Add variable to order
        this.vars.add(0, i);
    }

    /*
     * Overridden secondary methods
     * ---------------------------------------------
     */

    @Override
    public void expand(Set<Integer> newVars) {
        assert intersection(seqToSet(this.vars), newVars).equals(newVars
                .newInstance()) : "Violation of: newVariables intersection this.vars = empty_set";
        for (int var : newVars) {
            /*
             * Adding to the front to preserve ordering. Also, since we're
             * adding rows to the end of the BDD, the newly added variables will
             * have the highest precedence in the ordering
             */

            this.vars.add(0, var);
            this.bdd.add(this.bdd.length(), new Sequence1L<Node>());
        }
    }

    @Override
    public boolean isSat() {
        return !((this.trueFirst && this.root.depth == 0
                && this.root.index == 1)
                || (!this.trueFirst && this.root.depth == 0
                        && this.root.index == 0));
    }

    @Override
    public boolean isValid() {
        return (this.trueFirst && this.root.depth == 0 && this.root.index == 0)
                || (!this.trueFirst && this.root.depth == 0
                        && this.root.index == 1);
    }

    @Override
    public boolean isTrueStructure() {
        return isLeaf(this.root) && leafValue(this.root, this.trueFirst);
    }

    @Override
    public boolean isFalseStructure() {
        return isLeaf(this.root) && !leafValue(this.root, this.trueFirst);
    }

    @Override
    public Set<Integer> satAssignment() {
        // Construct assignment starting from True terminal
        Coord child;
        if (this.trueFirst) {
            child = new Coord(0, 0);
        } else {
            child = new Coord(0, 1);
        }

        // Loop until root has been reached
        int currentDepth = 1;
        Set<Integer> a = new Set4<Integer>();
        while (child.depth != this.root.depth) {

            // Find the parent of the current child
            boolean parentFound = false;
            while (!parentFound) {
                // If child was a hi child, add it to our assignment, otherwise
                // leave it out
                for (int i = 0; i < this.bdd.entry(currentDepth)
                        .length(); i++) {
                    if (this.bdd.entry(currentDepth).entry(i).hi
                            .equals(child)) {
                        parentFound = true;
                        child = new Coord(currentDepth, i);
                        a.add(getLabel(this.vars, currentDepth));
                    } else if (this.bdd.entry(currentDepth).entry(i).lo
                            .equals(child)) {
                        parentFound = true;
                        child = new Coord(currentDepth, i);
                    }
                }
                currentDepth++;
            }
        }

        return a;
    }

}
