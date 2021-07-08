package components.booleanstructure;

import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.set.Set;
import components.set.Set2;

/**
 * @see BooleanStructureR
 */
public class BooleanStructure1 extends BooleanStructureR {

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public BooleanStructure1() {
        super();
    }

    /**
     * Constructor from {@code boolean}.
     *
     * @param b
     *            {@code boolean} to initialize from
     */
    public BooleanStructure1(boolean b) {
        super(b);

    }

    /**
     * Constructor from {@code int}.
     *
     * @param i
     *            {@code int} to initialize from
     */
    public BooleanStructure1(int i) {
        super(i);
    }

    /*
     * Overridden secondary methods
     * ---------------------------------------------
     */

    @Override
    public void expand(Set<Integer> newVars) {
        assert intersection(newVars, seqToSet(this.vars))
                .equals(new Set2<Integer>()) : "Violation of: "
                        + "newVariables intersection this.vars = empty_set";

        Sequence<Integer> newOrder = new Sequence1L<Integer>();
        // Add all original variables to the ordering
        /**
         * @updates newOrder, ~this.vars
         *
         * @maintains newOrder = ~this.vars.seen
         *
         * @decreases |~this.vars.unseen|
         */
        for (int elt : this.vars) {
            newOrder.add(newOrder.length(), elt);
        }
        /**
         * @updates newOrder, ~newVariables
         *
         * @maintains newOrder = #newOrder * ~newVariables.seen
         *
         * @decreases |~this.vars.unseen|
         */
        // Add all new variables to the ordering
        for (int elt : newVars) {
            newOrder.add(newOrder.length(), elt);
        }

        Set<Set<Integer>> newSat = new Set2<Set<Integer>>();

        // Loop through all possible assignments and add those that evaluate to
        // true for the original expression
        PowerStringElements allAssignments = new PowerStringElements(newOrder);
        /**
         * @updates newSat, ~allAssignments
         *
         * @maintains <pre>
         * newSat is a subset of entries(~allAssignments.seen) and
         * for all p: ASSIGNMENT where ( p is in entries(~allAssignments.seen) )
         *   ( p is in newSat iff
         *     ( p intersection entries($this.vars) is in $this.sat ) )
         * </pre>
         * @decreases |~allAssignments.unseen|
         */
        for (Set<Integer> a : allAssignments) {

            // Add assignment to new sat if it was true in the original
            if (processAssignment(this.sat, this.vars, a)) {
                newSat.add(a);
            }
        }

        this.sat.transferFrom(newSat);
        this.vars.transferFrom(newOrder);
    }

    @Override
    public boolean isTrueStructure() {
        return (this.vars.length() == 0 && this.sat.size() == 1);
    }

    @Override
    public boolean isFalseStructure() {
        return (this.vars.length() == 0 && this.sat.size() == 0);
    }

    @Override
    public Set<Integer> satAssignment() {
        Set<Integer> a = this.sat.removeAny();
        Set<Integer> aCopy = a.newInstance();
        for (Integer el : a) {
            aCopy.add(el);
        }
        this.sat.add(a);
        return aCopy;
    }

}
