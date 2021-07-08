package components.booleanstructure;

import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.set.Set;
import components.set.Set2;

public abstract class BooleanStructureTestUtilities {

    /**
     * Creates a set which contains the integers from the varargs.
     *
     * @param numbers
     *            the list of integers to be put into a set
     * @return a set containing numbers passed in
     * @requires |numbers| = |entries(numbers)|
     * @ensures createSet = entries(numbers)
     */
    protected static Set<Integer> createSet(int... numbers) {
        Set<Integer> result = new Set2<>();
        for (int n : numbers) {
            result.add(n);
        }
        return result;
    }

    /**
     * Creates a sequence which contains the integers from the varargs.
     *
     * @param numbers
     *            the list of integers to be put into a set
     * @return a sequence containing numbers passed in
     * @ensures createSequence = numbers
     */
    protected static Sequence<Integer> createSequence(int... numbers) {
        Sequence<Integer> result = new Sequence1L<>();
        for (int n : numbers) {
            result.add(result.length(), n);
        }
        return result;
    }

    /**
     * Creates a set which contains all entries of a given sequence.
     *
     * @param x
     *            the sequence to be used to create the set
     * @return a set containing the entries of the sequence passed in
     * @requires |x| = |entries(x)|
     * @ensures seqToSet = entries(x)
     */
    protected static Set<Integer> seqToSet(Sequence<Integer> x) {
        Set<Integer> newSet = new Set2<Integer>();

        for (int elt : x) {
            newSet.add(elt);
        }

        return newSet;
    }

}
