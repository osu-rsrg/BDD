package components.booleanstructure;

import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.set.Set;
import components.set.Set2;

/**
 * {@code BooleanStructure} represented as a {@code Set} of {@code Assignments},
 * with implementations of primary methods
 *
 * @mathdefinitions <pre>
 * NO_EXTRANEOUS_VARIABLES (
 *   s: set of ASSIGNMENT, t: string of integer
 * ) : boolean is
 * for all a: ASSIGNMENT where ( a is in s )
 *   ( a is subset of entries(t) )
 *
 * NO_DUPLICATES_IN_VARS (
 *   t: string of integer
 * ) : boolean is
 * | t | = | entries(t) |
 * </pre>
 * @convention <pre>
 *             NO_EXTRANEOUS_VARIABLES($this.sat, $this.vars) and
 *             NO_DUPLICATES_IN_VARS($this.vars)
 * </pre>
 * @correspondence this = ($this.sat, $this.vars)
 */
public class BooleanStructureR extends BooleanStructureSecondary {

    /*
     * Private members
     */
    Set<Set<Integer>> sat;
    Sequence<Integer> vars;

    /*
     * Private Helper methods
     */

    /**
     * Reports whether {@code a} is in {@code sat} after filtering variables
     *
     * @param a
     *            the assignment
     * @param sat
     *            the set of satisfying assignments
     * @param vars
     *            the variables of the structure
     * @return true iff the filtered version of a is in sat
     * @requires NO_EXTRANEOUS_VARIABLES(sat, vars) and
     *           NO_DUPLICATES_IN_VARS(vars)
     * @ensures processAssignment = a intersection entries(vars) is in sat
     */
    protected static boolean processAssignment(Set<Set<Integer>> sat,
            Sequence<Integer> vars, Set<Integer> a) {

        // Filter out extraneous variables
        Set<Integer> v = filterVariables(a, vars);

        // assert: v = a intersect vars

        // Check whether the given assignment is in sat
        return sat.contains(v);
    }

    /**
     * Reports whether {@code x} is an entry of {@code vars}
     *
     * @param x
     *            the element to search for
     * @param vars
     *            the sequence to search in
     * @return true iff the given Integer is in the sequence
     * @ensures isInSequence = x is in entries(vars)
     */
    private static boolean isInSequence(int x, Sequence<Integer> vars) {
        boolean result = false;
        /**
         * @updates result, ~vars
         *
         * @maintains result iff x is in entries(~vars.seen)
         *
         * @decreases |~vars.unseen|
         */
        for (Integer elt : vars) {
            if (elt.equals(x)) {
                result = true;
            }
        }

        return result;
    }

    /**
     * Returns a set containing only those integers that are in {@code a} and
     * are entries of {@code vars}
     *
     * @param a
     *            the set containing elements
     * @param vars
     *            the sequence containing entries
     * @return a set containing only integers that are present in both the set
     *         and sequence passed in
     * @ensures filterVariables = a intersection entries(vars)
     */
    private static Set<Integer> filterVariables(Set<Integer> a,
            Sequence<Integer> vars) {
        Set<Integer> filteredAssignments = new Set2<Integer>();

        // Add only variables that are in the order to the filtered set
        /**
         * @updates filteredAssignments, ~a
         *
         * @maintains <pre>
         * filteredAssignments = entries(~a.seen) intersection entries(vars)
         * </pre>
         *
         * @decreases |~a.unseen|
         */
        for (Integer elt : a) {
            if (isInSequence(elt, vars)) {
                filteredAssignments.add(elt);
            }
        }

        return filteredAssignments;
    }

    /**
     * Creator of initial representation.
     *
     * @ensures <pre>
     *          $this.sat = { { } } and
     *          $this.vars = < >
     * </pre>
     */
    private void createNewRep() {
        this.sat = new Set2<Set<Integer>>();
        this.vars = new Sequence1L<Integer>();

        // Add a single, empty assignment to represent the True Expression
        Set<Integer> a = new Set2<Integer>();
        this.sat.add(a);
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public BooleanStructureR() {
        this.createNewRep();
    }

    /**
     * Constructor from {@code boolean}.
     *
     * @param b
     *            {@code boolean} to initialize from
     */
    public BooleanStructureR(boolean b) {
        this.createNewRep();

        if (!b) {
            // Remove the single, empty assignment to represent the False Expression
            this.sat.removeAny();
        }

    }

    /**
     * Constructor from {@code int}.
     *
     * @param i
     *            {@code int} to initialize from
     */
    public BooleanStructureR(int i) {

        this.createNewRep();

        Set<Integer> a = new Set2<Integer>();
        a.add(i);

        // Remove the single, empty assignment to represent the False Expression
        this.sat.removeAny();
        this.sat.add(a);

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
        assert source instanceof BooleanStructureR : ""
                + "Violation of: source is of dynamic"
                + " type BooleanExpression1";

        BooleanStructureR localSource = (BooleanStructureR) source;
        this.sat = localSource.sat;
        this.vars = localSource.vars;

        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public boolean evaluate(Set<Integer> a) {
        return processAssignment(this.sat, this.vars, a);
    }

    @Override
    public void apply(BinaryOperator o, BooleanStructure x,
            Sequence<Integer> newVars) {
        assert seqToSet(newVars).equals(union(seqToSet(this.vars),
                seqToSet(x.vars()))) : "Violation of: "
                        + "entries(newVars) = VARIABLES(this) union VARIABLES(x)";
        assert newVars.length() == seqToSet(newVars).size() : "Violation of: "
                + "| newVars | = | entries(newVars) |";
        assert IS_COMPATIBLE_ORDERING(this.vars, x.vars()) : "Violation of: "
                + "IS_COMPATIBLE_ORDERING(this.vars, x.vars)";
        assert IS_COMPATIBLE_ORDERING(this.vars, newVars) : "Violation of: "
                + "IS_COMPATIBLE_ORDERING(this.vars, newVars)";
        assert IS_COMPATIBLE_ORDERING(x.vars(), newVars) : "Violation of: "
                + "IS_COMPATIBLE_ORDERING(x.vars, newVars)";

        Set<Set<Integer>> newSat = new Set2<Set<Integer>>();

        PowerStringElements allAssignments = new PowerStringElements(newVars);
        /**
         * @updates newSat, ~allAssignments
         *
         * @maintains <pre>
         *        newSat is a subset of entries(~allAssignments.seen) and
         *        for all p: ASSIGNMENT where ( p is in entries(~allAssignments.seen) )
         *          ( p is in newSat iff
         *            ( ( if op = AND then (p intersection entries($this.vars) is in $this.sat) and EVALUATION(x, p) ) and
         *              ( if op = OR then (p intersection entries($this.vars) is in $this.sat) or EVALUATION(x, p) ) and
         *              ( if op = EQUIVALS then (p intersection entries($this.vars) is in $this.sat) iff EVALUATION(x, p) ) ) )
         * </pre>
         *
         * @decreases |~allAssignments.unseen|
         */
        for (Set<Integer> a : allAssignments) {

            boolean shouldBeAdded = processAssignment(this.sat, this.vars, a);
            switch (o) {
                case AND:
                    shouldBeAdded = shouldBeAdded && x.evaluate(a);
                    break;
                case OR:
                    shouldBeAdded = shouldBeAdded || x.evaluate(a);
                    break;
                case EQUIVALS:
                    shouldBeAdded = shouldBeAdded == x.evaluate(a);
                    break;
                default:
                    assert false : "Apply of unrecognized BinaryOperator: " + o;
            }

            // Add assignment to new sat if it meets conditions based on the
            // logical operator
            if (shouldBeAdded) {
                newSat.add(a);
            }
        }

        this.sat.transferFrom(newSat);

        // Swap the contents of the two orders
        Sequence<Integer> tempVars = new Sequence1L<Integer>();
        tempVars.transferFrom(newVars);
        newVars.transferFrom(this.vars);
        this.vars.transferFrom(tempVars);
    }

    @Override
    public void apply(UnaryOperator o) {

        if (o == UnaryOperator.NOT) {

            Set<Set<Integer>> newSat = new Set2<Set<Integer>>();

            // Loop through all possible assignments and add those that
            // weren't in the original sat
            PowerStringElements allAssignments = new PowerStringElements(
                    this.vars());
            /**
             * @updates newSat, ~allAssignments
             *
             * @maintains entries(~allAssignments.seen) \ $this.sat = newSat
             *
             * @decreases |~allAssignments.unseen|
             */
            for (Set<Integer> a : allAssignments) {

                // Add assignment to new sat if it isn't in the original one
                if (!(processAssignment(this.sat, this.vars, a))) {
                    newSat.add(a);
                }
            }

            this.sat.transferFrom(newSat);

        }

    }

    @Override
    public void restrict(Set<Integer> t, Set<Integer> f) {
        assert union(t, f).isSubset(seqToSet(this.vars)) : "Violation of: "
                + "t union f is subset of VARIABLES(this)";
        assert intersection(t, f).size() == 0 : "Violation of: "
                + "t intersection f = empty_set";

        Set<Set<Integer>> newSat = new Set2<Set<Integer>>();

        // Add new Assignment based on the variables being "restrained"
        /**
         * @updates $this.sat, newSat
         *
         * @maintains <pre>
         * ( newSat is subset of power(entries($this.vars) ) and
         * ( $this.sat is subset of #$this.sat ) and
         * for all p: ASSIGNMENT where ( p is subset of entries($this.vars) )
         *      (p in newSat iff
         *            ( ( p union t is in #$this.sat \ $this.sat ) and
         *              ( p intersection t = empty_set ) and
         *              ( p intersection f = empty_set ) ) )
         * </pre>
         *
         * @decreases |$this.sat|
         */
        while (this.sat.size() > 0) {
            Set<Integer> current = this.sat.removeAny();
            if (t.isSubset(current)) {
                current.remove(t);
                Set<Integer> falseIntersection = current.remove(f);
                if (falseIntersection.size() == 0) { //current intersect f = empty_set
                    if (!newSat.contains(current)) {
                        newSat.add(current);
                    }
                }
            }
        }

        // Reassign sat
        this.sat.transferFrom(newSat);

        // Remove the specified variables from the ordering
        /**
         * @updates $this.vars
         *
         * @maintains <pre>
         * IS_COMPATIBLE_ORDERING($this.vars, #$this.vars) and
         * ( entries($this.vars[0, i)) union
         *   ((entries($this.vars[i, |$this.vars|)) \ t) \ f ) =
         *            (entries(#$this.vars) \ t) \ f )
         * </pre>
         * @decreases |$this.vars| - i
         *
         */
        int index = 0;
        while (index < this.vars.length()) {
            if (t.contains(this.vars.entry(index))
                    || f.contains(this.vars.entry(index))) {
                this.vars.remove(index);
            } else {
                index++;
            }
        }

    }

    @Override
    public void reorder(Sequence<Integer> newVars) {
        assert seqToSet(this.vars).equals(seqToSet(newVars)) : "Violation of: "
                + "VARIABLES(this) = entries(newOrder)";

        Sequence<Integer> tempOrder = new Sequence1L<Integer>();
        tempOrder.transferFrom(newVars);
        newVars.transferFrom(this.vars);
        this.vars.transferFrom(tempOrder);
    }

    @Override
    public Sequence<Integer> vars() {
        return this.vars;
    }

    @Override
    public void setFromInt(int i) {
        this.sat.clear();
        this.vars.clear();

        Set<Integer> a = new Set2<Integer>();
        a.add(i);

        this.sat.add(a);

        this.vars.add(0, i);
    }

}
