package components.booleanstructure;

import java.util.Iterator;
import java.util.Random;
import java.util.StringJoiner;

import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.set.Set;
import components.set.Set2;
import components.set.Set4;

/**
 * Layered implementations of secondary methods for {@code BooleanStructure}.
 */
public abstract class BooleanStructureSecondary implements BooleanStructure {

    /*
     * Private Helper Methods
     */

    /**
     * Creates a set which contains all elements of {@code x}
     *
     * @param x
     *            the sequence to be used to create the set
     * @return a set containing the elements of x
     * @requires |x| = |entries(x)|
     * @ensures seqToSet = entries(x)
     */
    protected static Set<Integer> seqToSet(Sequence<Integer> x) {
        Set<Integer> newSet = new Set4<Integer>();

        /**
         * @updates newSet, ~x
         *
         * @maintains newSet = entries(~x.seen)
         *
         * @decreases |~x.unseen|
         */
        for (int elt : x) {
            newSet.add(elt);
        }

        return newSet;
    }

    /**
     * Creates the union of {@code x} and {@code y}
     *
     * @param x
     *            the first set
     * @param y
     *            the second set
     * @return a set representing the union of x and y
     * @ensures union = x union y
     */
    protected static Set<Integer> union(Set<Integer> x, Set<Integer> y) {
        Set<Integer> union = new Set4<Integer>();

        /**
         * @updates union, ~x
         *
         * @maintains union = entries(~x.seen)
         *
         * @decreases |~x.unseen|
         */
        for (int elt : x) {
            union.add(elt);
        }

        /**
         * @updates union, ~y
         *
         * @maintains union = #union union entries(~y.seen)
         *
         * @decreases |~y.unseen|
         */
        for (int elt : y) {
            if (!union.contains(elt)) {
                union.add(elt);
            }
        }

        return union;
    }

    /**
     * Creates the intersection of {@code x} and {@code y}
     *
     * @param x
     *            the first set
     * @param y
     *            the second set
     * @return a set representing the intersection of x and y
     * @ensures intersect = x intersection y
     */
    protected static Set<Integer> intersection(Set<Integer> x, Set<Integer> y) {
        Set<Integer> intersection = new Set4<Integer>();

        /**
         * @updates intersection, ~x
         *
         * @maintains intersection = y intersection entries(~x.seen)
         *
         * @decreases |~x.unseen|
         */
        for (int elt : x) {
            if (y.contains(elt)) {
                intersection.add(elt);
            }

        }

        return intersection;
    }

    /**
     * Report the index of an element in the subsequence of a sequence given a
     * start index, returning -1 if the element is not present.
     *
     * @param x
     *            the element to be checked
     * @param order
     *            the sequence being searched
     * @param start
     *            the starting index of the sequence for the search
     * @return the index of x in order[start, |order|)
     *
     * @ensures <pre>
     *      if x is in entries(order[start, |order|))
     *              then order[findIndex, findIndex + 1) = <x>
     *              else findIndex = -1
     * </pre>
     */
    protected static int findIndex(Integer x, Sequence<Integer> order,
            int start) {
        int index = -1;

        /**
         * @updates index, i
         *
         * @maintains <pre>
         *      i >= start and
         *      if x is in entries(order[start, i))
         *              then order[index, index + 1) = <x>
         *              else findIndex = -1
         * </pre>
         *
         * @decreases |order| - i
         */
        for (int i = start; i < order.length(); i++) {
            if (order.entry(i) == x) {
                index = i;
            }
        }

        return index;
    }

    /**
     * Given 2 orders, generate a new order which represents the combination of
     * the two orders
     *
     * @param order1
     *            the first order
     * @param order2
     *            the second order
     * @requires IS_COMPATIBLE_ORDERING(order1, order2)
     * @return a new order compatible with both order1 and order2
     * @ensures <pre>
     *      entries(newOrder) = VARIABLES(order1) union VARIABLES(order2) and
     *      | newOrder | = | entries(newOrder) | and
     *      IS_COMPATIBLE_ORDERING(newOrder, order1) and
     *      IS_COMPATIBLE_ORDERING(newOrder, order2)
     * </pre>
     */
    private static Sequence<Integer> newOrder(Sequence<Integer> order1,
            Sequence<Integer> order2) {

        Sequence<Integer> newOrder = new Sequence1L<Integer>();

        // Index to keep track up to which index elements of the 2nd order have
        // been added
        int secondIndex = 0;
        /**
         * @updates i, newOrder, secondIndex
         *
         * @maintains <pre>
         *      entries(newOrder) = VARIABLES(order1[0, i)) union VARIABLES(order2[0, secondIndex)) and
         *      | newOrder | = | entries(newOrder) | and
         *      IS_COMPATIBLE_ORDERING(newOrder, order1) and
         *      IS_COMPATIBLE_ORDERING(newOrder, order2)
         * </pre>
         *
         * @decreases |order1| - i
         */
        for (int i = 0; i < order1.length(); i++) {
            int current = order1.entry(i);

            // Only add current if it hasn't been added already (through 2nd
            // order)
            if (findIndex(current, newOrder, 0) == -1) {
                if (secondIndex < order2.length()) {
                    // The index of current in the second order
                    int cutoff = findIndex(current, order2, secondIndex);

                    // Add all elements of the second order up to current
                    /**
                     * @updates newOrder, secondIndex
                     *
                     * @maintains newOrder = order2[#secondIndex, secondIndex)
                     *
                     * @decreases cutoff - secondIndex
                     */
                    while (secondIndex <= cutoff) {
                        newOrder.add(newOrder.length(),
                                order2.entry(secondIndex));
                        secondIndex++;
                    }

                    // Add current if it wasn't in the second order
                    if (cutoff == -1) {
                        newOrder.add(newOrder.length(), current);
                    }

                }
                // All elements of second index have already been added
                else {
                    newOrder.add(newOrder.length(), current);
                }
            }

        }

        // Add remaining elements of second order
        /**
         * @updates newOrder, secondIndex
         *
         * @maintains entries(newOrder) = entries(#newOrder) union
         *            VARIABLES(order2[#secondIndex, secondIndex))
         *
         * @decreases |order2| - secondIndex
         */
        while (secondIndex < order2.length()) {
            newOrder.add(newOrder.length(), order2.entry(secondIndex));
            secondIndex++;
        }

        return newOrder;

    }

    /**
     * Reports whether two variable orderings are compatible
     *
     * @param x
     *            the first ordering
     * @param y
     *            the second ordering
     * @return true iff x and y have a compatible ordering
     * @ensures IS_COMPATIBLE_ORDERING(x, y)
     */
    protected static boolean IS_COMPATIBLE_ORDERING(Sequence<Integer> x,
            Sequence<Integer> y) {
        boolean result = true;
        for (int i = 0; i < x.length(); i++) {
            int xi = x.entry(i);
            for (int j = 0; j < x.length(); j++) {
                int xj = x.entry(j);
                for (int u = 0; u < y.length(); u++) {
                    int yu = y.entry(u);
                    for (int v = 0; v < y.length(); v++) {
                        int yv = y.entry(v);
                        if (xi == yu && xj == yv) {
                            result = result && ((i < j) == (u < v));
                        }
                    }
                }
            }
        }
        return result;
    }

    // Private method to create a BooleanExpression1 by parsing a SyntaxTree
    private static BooleanStructure createFromTree(BooleanStructure b,
            SyntaxTree st) {
        BooleanStructure newExp;

        // Split into variable case and operator cases
        if (st.right() == null) {
            if (st.label().equals("T")) {
                newExp = b.newInstance();
            } else if (st.label().equals("F")) {
                newExp = b.newInstance();
                newExp.negate();
            } else {
                newExp = b.newInstance();
                newExp.setFromInt(Integer.parseInt(st.label()));
            }
        } else if (st.label().equals("not")) {
            newExp = createFromTree(b, st.right());

            newExp.negate();
        } else {
            newExp = createFromTree(b, st.left());
            BooleanStructure secondExp = createFromTree(b, st.right());

            if (st.label().equals("and")) {
                newExp.conj(secondExp);
            } else {
                newExp.disj(secondExp);
            }
        }

        return newExp;
    }

    /*
     * Common methods (from Object) -------------------------------------------
     */

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("( "); // start BOOLEAN_EXPRESSION

        Sequence<Integer> thisOrder = this.vars();

        /*
         * add BOOLEAN_EXPRESSION's sat
         */
        StringJoiner s = new StringJoiner(",", "{", "}");

        // Loop through all possible assignments for truth values to check
        // if the assignment evaluates to true
        PowerStringElements allAssignments = new PowerStringElements(
                this.vars());
        for (Set<Integer> a : allAssignments) {
            if (this.evaluate(a)) {
                s.add(a.toString());
            }
        }
        result.append(s.toString());

        result.append(", ");

        /*
         * add BOOLEAN_EXPRESSION's vars
         */
        result.append(thisOrder.toString());

        result.append(" )"); // end BOOLEAN_EXPRESSION_MODEL

        return result.toString();
    }

    /**
     * Calculate the integer of the log base 2 of a number.
     *
     * @param x
     *            the number whose log is calculated
     * @return the floor of the log (base 2) of the number
     * @requires x > 0
     * @ensures 2 ^ (logBase2) <= x < 2 ^ (logBase2 + 1)
     */
    private static int logBase2(int x) {
        assert x > 0 : "x > 0";
        return 31 - Integer.numberOfLeadingZeros(x);
    }

    @Override
    public int hashCode() {
        /*
         * Number of rows to include in calculation. assert sampleSize > 0
         */
        final int sampleSize = 60;
        /*
         * Parameters for hashing.
         */
        final int a = 37;
        final int b = 17;

        int result = 0;

        Sequence<Integer> thisOrder = this.vars();

        for (Integer elt : thisOrder) {
            result = a * result + b * elt.hashCode();
        }

        int fingerprint = 0;
        boolean sampleEveryRow = (thisOrder.length() <= logBase2(sampleSize));
        Random rnd = new Random(result);
        PowerStringElements allAssignments = new PowerStringElements(
                this.vars());
        Iterator<Set<Integer>> it = allAssignments.iterator();
        int i = 0;
        while ((sampleEveryRow && it.hasNext())
                || (!sampleEveryRow && i < sampleSize)) {
            fingerprint = Integer.rotateLeft(fingerprint, 1);

            Set<Integer> t;
            if (sampleEveryRow) {
                t = it.next();
            } else {
                t = ((PowerStringElements.PowerStringIterator) it)
                        .randomNext(rnd);
                i++;
            }

            /*
             * Flip the least significant bit depending on whether this row is
             * true.
             */
            if (this.evaluate(t)) {
                fingerprint = fingerprint ^ 0x1;
            }

        }
        result = result ^ Integer.hashCode(fingerprint);

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BooleanStructure)) {
            return false;
        }

        // Cast obj as a BooleanStructure to call Kernel methods
        BooleanStructure b = (BooleanStructure) obj;

        // Extract the ordering of the two expressions
        Sequence<Integer> thisOrder = this.vars();
        Sequence<Integer> bOrder = b.vars();

        // Check that the ordering of the two expressions is the same
        if (thisOrder.equals(bOrder)) {

            PowerStringElements allAssignments = new PowerStringElements(
                    this.vars());
            for (Set<Integer> a : allAssignments) {
                if (this.evaluate(a) != b.evaluate(a)) {
                    return false;
                }
            }

            return true;

        } else {
            return false;
        }

    }

    /*
     * Other non-kernel methods -----------------------------------------------
     */

    @Override
    public void copyFrom(BooleanStructure x) {
        // Generate a false structure with the same vars as x
        BooleanStructure newExp = this.newInstance();
        newExp.negate();

        Set<Integer> expansion = seqToSet(x.vars());
        newExp.expand(expansion);

        // reorder variables in new structure to match x's order
        Sequence<Integer> order = x.vars();
        Sequence<Integer> newOrder = new Sequence1L<Integer>();
        /**
         * @updates newOrder, ~order
         *
         * @maintains newOrder = ~order.seen
         *
         * @decreases |~order.unseen|
         */
        for (int v : order) {
            newOrder.add(newOrder.length(), v);
        }

        newExp.reorder(newOrder);

        // Combine the false structure and x.sat to get a copy of x.sat
        newExp.disj(x);

        this.transferFrom(newExp);
    }

    @Override
    public void negate() {
        this.apply(UnaryOperator.NOT);
    }

    @Override
    public void conj(BooleanStructure x) {
        assert IS_COMPATIBLE_ORDERING(this.vars(), x.vars()) : "Violation of: "
                + "IS_COMPATIBLE_ORDERING(this.vars, x.vars)";

        Sequence<Integer> newOrder = newOrder(this.vars(), x.vars());

        this.apply(BinaryOperator.AND, x, newOrder);
    }

    @Override
    public void disj(BooleanStructure x) {
        assert IS_COMPATIBLE_ORDERING(this.vars(), x.vars()) : "Violation of: "
                + "IS_COMPATIBLE_ORDERING(this.vars, x.vars)";

        Sequence<Integer> newOrder = newOrder(this.vars(), x.vars());

        this.apply(BinaryOperator.OR, x, newOrder);
    }

    @Override
    public void expand(Set<Integer> newVars) {
        assert intersection(seqToSet(this.vars()), newVars).equals(newVars
                .newInstance()) : "Violation of: newVars intersection this.vars = empty_set";

        // Create the false structure
        BooleanStructure falseStruct = this.newInstance();
        falseStruct.negate();

        // Iterate over every new variable
        /**
         * @updates this, ~newVars
         *
         * @maintains <pre>
         *      for all p: ASSIGNMENT where ( p is subset of entries(this.vars) )
         *          ( p is in this.sat iff EVALUATION(#this, p) ) and
         *      entries(this.vars) = entries(this.vars) union entries(~newVars.seen) and
         *      IS_COMPATIBLE_ORDERING(this.vars, #this.vars)
         * </pre>
         *
         * @decreases |~newVars.unseen|
         */
        for (int x : newVars) {
            // Create a single variable structure based on the integer x
            BooleanStructure xStruct = this.newInstance();
            xStruct.setFromInt(x);

            // x and False
            xStruct.conj(falseStruct);

            // this or (x and False)
            this.disj(xStruct);
        }
    }

    @Override
    public String toStringTT() {
        StringBuilder result = new StringBuilder();
        Sequence<Integer> thisOrder = this.vars();

        result.append(thisOrder);

        int numRows = (int) Math.pow(2, thisOrder.length());

        // Loop through all possible assignments for truth values to check
        // if the two expressions are logically equivalent
        Set<Integer> trueAssignments = new Set2<Integer>();
        /**
         * @decreases |numRows| - i
         */
        for (int i = 0; i < numRows; i++) {
            result.append(System.lineSeparator());

            trueAssignments.clear();

            long variableMask = 1 << thisOrder.length() - 1;
            /**
             * @updates trueAssignments, j
             *
             * @maintains <pre>
             * trueAssignments is subset of entries(thisOrder[|thisOrder| - j, |thisOrder|))
             * </pre>
             *
             * @decreases |thisOrder| - j
             */
            for (int j = 0; j < thisOrder.length(); j++) {
                int variable = thisOrder.entry(thisOrder.length() - 1 - j);
                if ((i & variableMask) == 0) {
                    result.append("T ");
                    trueAssignments.add(variable);
                } else {
                    result.append("F ");
                }
                variableMask = (variableMask >> 1);
            }

            if (this.evaluate(trueAssignments)) {
                result.append("| T");
            } else {
                result.append("| F");
            }
        }

        return result.toString();
    }

    @Override
    public boolean isSat() {
        boolean found = false;

        // Create an iterator over all possible assignments of truth values
        PowerStringElements allAssignments = new PowerStringElements(
                this.vars());

        // Attempt to find a value for which the structure evaluates to true
        Iterator<Set<Integer>> it = allAssignments.iterator();
        /**
         * @updates found, it
         *
         * @maintains <pre>
         *      found iff not(entries(it.seen) intersection this.sat = empty_set)
         * </pre>
         * @decreases |it.unseen|
         */
        while (it.hasNext() && !found) {
            Set<Integer> t = it.next();
            found = this.evaluate(t);
        }

        return found;
    }

    @Override
    public boolean isValid() {
        // Loop through all possible assignments for truth values to check
        // if the expression always evaluates to true

        boolean allTrue = true;

        PowerStringElements allAssignments = new PowerStringElements(
                this.vars());
        Iterator<Set<Integer>> it = allAssignments.iterator();
        /**
         * @updates allTrue, it
         *
         * @maintains <pre>
         *      allTrue iff (entries(it.seen) intersection this.sat = entries(it.seen))
         * </pre>
         * @decreases |it.unseen|
         */
        while (it.hasNext() && allTrue) {
            Set<Integer> t = it.next();
            allTrue = this.evaluate(t);
        }

        return allTrue;
    }

    @Override
    public boolean isEquivalent(BooleanStructure x) {
        boolean result = true;

        // Create a new sequence to represent the union of the two variables
        Sequence<Integer> combinedVars = new Sequence1L<Integer>();
        Set<Integer> addedSet = new Set4<Integer>();

        /**
         * @updates addedSet, combinedVars
         *
         * @maintains <pre>
         *      addedSet = entries(~this.vars.seen) and
         *      combinedVars = ~this.vars.seen
         * </pre>
         *
         * @decreases |~this.vars.unseen|
         */
        for (int i : this.vars()) {
            addedSet.add(i);
            combinedVars.add(combinedVars.length(), i);
        }

        /**
         * @updates combinedVars
         *
         * @maintains <pre>
         *      entries(combinedVars) = entries(#combinedVars) union entries(~x.vars.seen)
         * </pre>
         *
         * @decreases |~x.vars.unseen|
         */
        for (int i : x.vars()) {
            if (!addedSet.contains(i)) {
                combinedVars.add(combinedVars.length(), i);
            }
        }

        // Loop through all possible assignments for truth values to check
        // if the two expressions are logically equivalent
        PowerStringElements allAssignments = new PowerStringElements(
                combinedVars);
        Iterator<Set<Integer>> it = allAssignments.iterator();
        /**
         * @updates result, it
         *
         * @maintains <pre>
         *  result iff
         *      ( for all p: ASSIGNMENT where ( p is in entries(it.seen) )
         *            ( (p intersection entries(this.vars) is in this.sat) iff
         *              (p intersection entries(x.vars) is in x.sat) ) )
         * </pre>
         * @decreases |it.unseen|
         */
        while (it.hasNext() && result) {
            Set<Integer> t = it.next();
            result = (this.evaluate(t) == x.evaluate(t));
        }

        return result;
    }

    @Override
    public int numVariables() {
        return this.vars().length();
    }

    @Override
    public boolean isTrueStructure() {
        boolean isTrueStructure;
        if (this.vars().length() > 0) {
            isTrueStructure = false;
        } else {
            // The structure is the literal True if the empty set evaluates to true
            Set<Integer> emptySet = new Set4<Integer>();
            isTrueStructure = this.evaluate(emptySet);
        }

        return isTrueStructure;
    }

    @Override
    public boolean isFalseStructure() {
        boolean isFalseStructure;
        if (this.vars().length() > 0) {
            isFalseStructure = false;
        } else {
            // The structure is the literal False if the empty set evaluates to false
            Set<Integer> emptySet = new Set4<Integer>();
            isFalseStructure = !this.evaluate(emptySet);
        }

        return isFalseStructure;
    }

    @Override
    public void setFromTree(SyntaxTree st) {
        BooleanStructure newExp = createFromTree(this, st);

        this.transferFrom(newExp);
    }

    @Override
    public Set<Integer> satAssignment() {
        // Loop through all possible assignments for truth values to find a
        // satisfying assignment

        boolean found = false;
        Set<Integer> a = null;
        PowerStringElements allAssignments = new PowerStringElements(
                this.vars());
        Iterator<Set<Integer>> it = allAssignments.iterator();
        /**
         * @updates found, it, a
         *
         * @maintains <pre>
         *      found implies (a is in this.sat) and
         *      not(found) implies entries(it.seen) intersection this.sat = empty_set
         * </pre>
         * @decreases |it.unseen|
         */
        while (it.hasNext() && !found) {
            a = it.next();
            found = this.evaluate(a);
        }

        // Return a copy of the assignment to avoid aliasing
        Set<Integer> aCopy = a.newInstance();
        /**
         * @updates aCopy, a
         *
         * @maintains aCopy = entries(~a.seen)
         *
         * @decreases |~a.unseen|
         */
        for (int el : a) {
            aCopy.add(el);
        }

        return aCopy;

    }

}
