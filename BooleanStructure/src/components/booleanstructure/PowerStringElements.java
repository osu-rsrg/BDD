package components.booleanstructure;

import java.util.Iterator;
import java.util.Random;

import components.sequence.Sequence;
import components.set.Set;
import components.set.Set2;

/**
 * Utility class to support iteration over all 2 ^ |vars| possible assignments.
 * The first assignment is all variables are true, while the last assignment is
 * all variables are false. The first half of the visited assignments have the
 * first variable in order being true, while the second half have this variable
 * being false.
 *
 * For example, for a boolean structure with 3 variables <1, 2, 3>, the order of
 * the visited assignments is: <pre>
 * {1, 2, 3}
 * {1, 2}
 * {1, 3}
 * {1}
 * {2, 3}
 * {2}
 * {3}
 * {}
 * </pre>
 *
 * This corresponds to the "ordinary" way in which a truth table is written.
 *
 ** @mathsubtypes <pre>
 *   STRING_OF_UNIQUE is string of integer
 *    exemplar s
 *    constraint |s| = |entries(s)|
 * </pre>
 *
 * @mathdefinitions <pre>
 * POWER_STRING(
 *   s: string of integer
 *  ): string of set of integer satisfies
 * if s = <>
 *   then POWER_STRING(s) = < {} >
 *   else POWER_STRING(s) = PREPEND(entries(s[0,1)), POWER_STRING(s[1, |s|))) *
 *                          POWER_STRING(s[1,|s|))
 *
 * PREPEND(
 *   prefix: set of integer,
 *   s: string of set of integer
 *  ): string of set of integer satisfies
 * |PREPEND(prefix, s)| = |s| and
 * for all i: integer where ( 0 <= i < |s| )
 *   ( PREPEND(prefix, s)[i, i+1) = < prefix union (>s[i, i+1)<) > )
 * </pre>
 *
 * @mathmodel type PowerStringElements is modeled by STRING_OF_UNIQUE
 *
 * @iterator ~this.seen * ~this.unseen = POWER_STRING(this)
 */
public class PowerStringElements implements Iterable<Set<Integer>> {

    private Sequence<Integer> vars;

    /**
     * Constructor from {@code Sequence<Integer>}.
     *
     * @param vars
     *            {@code Sequence<Integer>} to initialize from
     */
    PowerStringElements(Sequence<Integer> vars) {
        /*
         * Limit on the number of variables, based on row index type.
         */
        assert vars.length() <= Long.SIZE - 1 : "|vars| <= " + (Long.SIZE - 1); //|vars| <= 63

        this.vars = vars;
    }

    @Override
    public final Iterator<components.set.Set<java.lang.Integer>> iterator() {
        return new PowerStringIterator();
    }

    final class PowerStringIterator implements Iterator<Set<Integer>> {

        private long index = 0;

        /**
         * Reports whether there are any more subsets to iterate over
         *
         * @return true iff there are still more subsets to iterate over
         * @ensures hasNext = ( index < 2 ^ ( | vars | ) )
         */
        @Override
        public boolean hasNext() {
            return (this.index >> PowerStringElements.this.vars.length()) == 0;
        }

        /**
         * Set the assignment corresponding to the next "row" of the truth table
         * for this expression.
         *
         * @requires 0 <= index < 2 ^ |vars|
         * @ensures <pre>
         *   index = #index + 1 and
         *   trueAssignments is subset of entries(vars) and
         *   for all i, v : integer where (0 <= i < |vars| and <v> = vars[i, i+1))
         *     (v in trueAssignments iff (index / 2^i) mod 2 /= 0)
         * </pre>
         */
        @Override
        public Set<Integer> next() {
            Set<Integer> trueAssignments = new Set2<>();

            long row = this.index;
            for (int j = 0; j < PowerStringElements.this.vars.length(); j++) {
                int variable = PowerStringElements.this.vars
                        .entry(PowerStringElements.this.vars.length() - 1 - j);
                if ((row & 0x1) == 0) {
                    trueAssignments.add(variable);
                }
                row = (row >> 1);
            }

            this.index++;

            return trueAssignments;
        }

        /**
         * Set an assignment corresponding to the some "row" of the truth table
         * for this expression.
         *
         * @param trueAssignments
         *            the set of variables that are true in this row
         * @param rnd
         *            a Random object used to assign variables in a random
         *            fashion
         * @replaces trueAssignments
         * @ensures trueAssignments is subset of entries(order)
         */
        Set<Integer> randomNext(Random rnd) {
            Set<Integer> trueAssignments = new Set2<>();

            for (int j = 0; j < PowerStringElements.this.vars.length(); j++) {
                int variable = PowerStringElements.this.vars
                        .entry(PowerStringElements.this.vars.length() - 1 - j);
                if (rnd.nextBoolean()) {
                    trueAssignments.add(variable);
                }
            }
            return trueAssignments;
        }

    }

}
