package components.booleanstructure;

/**
 * {@code BooleanStructureKernel} enhanced with secondary methods.
 */
import components.set.Set;

/**
 * Layered implementations of secondary methods for {@code BooleanStructure}
 *
 * @mathdefinitions <pre>
 * SATISFIABLE(
 *   m: BOOLEAN_STRUCTURE
 *  ): boolean is
 *  | m.sat | > 0
 *
 * VALID(
 *   m: BOOLEAN_STRUCTURE
 *  ): boolean is
 *  | m.sat | = 2^(| m.vars |)
 *
 * EXPANSION(
 *   result: BOOLEAN_STRUCTURE,
 *   m: BOOLEAN_STRUCTURE,
 *   v: set of integer
 *  ): boolean is
 * VARIABLES(result) = VARIABLES(m) union v and
 * IS_COMPATIBLE_ORDERING(result.vars, m.vars) and
 * for all p: ASSIGNMENT where ( p is subset of entries(result.vars) )
 *   ( p is in result.sat iff EVALUATION(m, p) )
 *
 * EQUIVALENT(
 *   m: BOOLEAN_STRUCTURE,
 *   n: BOOLEAN_STRUCTURE,
 *  ): boolean is
 * for all p: ASSIGNMENT where ( p is subset of (entries(m.vars) union entries(n.vars)) )
 *   ( EVALUATION(m, p) iff EVALUATION(n, p) )
 *
 * </pre>
 */

public interface BooleanStructure extends BooleanStructureKernel {

    /**
     * Copies {@code other} to {@code this}.
     *
     * @param other
     *            {@code BooleanStructrue} to copy from
     * @replaces this
     * @ensures this = other
     */
    void copyFrom(BooleanStructure other);

    /**
     * Perform the logical negation operation on {@code this}.
     *
     * @updates this
     * @ensures <pre>
     *      UNARY_APPLY(this, #this, NOT)
     * </pre>
     */
    void negate();

    /**
     * Perform the logical conjunction operation between {@code this} and
     * {@code other} without specifying a total order of variables and updating
     * {@code this} to the result of the operation.
     *
     * @param other
     *            the {@code BooleanStructure} the conjunction operation is
     *            being performed with
     * @updates this
     * @requires IS_COMPATIBLE_ORDERING(this.vars, other.vars)
     * @ensures <pre>
     *      BINARY_APPLY(this, #this, other, AND) and
     *      IS_COMPATIBLE_ORDERING(#this.vars, this.vars) and
     *      IS_COMPATIBLE_ORDERING(other.vars, this.vars)
     * </pre>
     */
    void conj(BooleanStructure other);

    /**
     * Perform the logical disjunction operation between {@code this} and
     * {@code other} without specifying a total order of variables and updating
     * {@code this} to the result of the operation.
     *
     * @param other
     *            the {@code BooleanStructure} the disjunction operation is
     *            being performed with
     * @updates this
     * @requires IS_COMPATIBLE_ORDERING(this.vars, other.vars)
     * @ensures <pre>
     *      BINARY_APPLY(this, #this, other, OR) and
     *      IS_COMPATIBLE_ORDERING(#this.vars, this.vars) and
     *      IS_COMPATIBLE_ORDERING(other.vars, this.vars)
     * </pre>
     */
    void disj(BooleanStructure other);

    /**
     * Add the variables in {@code newVars} to {@code this}.
     *
     * @param newVars
     *            variables to be added to structure
     * @updates this
     * @requires newVars intersection this.vars = empty_set
     * @ensures <pre>
     *      EXPANSION(this, #this, newVars)
     *  </pre>
     */
    void expand(Set<Integer> newVars);

    /**
     * Return a string corresponding to the truth table for {@code this}. The
     * header is the order sequence, followed by the body of the truth table.
     * The first row is all true, while the last row is all false. The top half
     * of the truth table is for the first variable being false, while the
     * bottom half is for this variable being true.
     *
     * For example, for a truth table with 3 variables, the truth table might
     * be: <pre>
     * <18,5,23>
     * T T T | T
     * T T F | T
     * T F T | F
     * T F F | F
     * F T T | T
     * F T F | F
     * F F T | T
     * F F F | F
     * </pre>
     *
     * @requires |this.vars| < 64
     *
     * @return the string corresponding to the truth table
     */
    String toStringTT();

    /**
     * Reports whether {@code this} is satisfiable; that is, if there is an
     * assignment for which {@code this} evaluates to true
     *
     * @return true iff there exists a set of variable assignments that makes
     *         the structure true
     * @ensures isSat = SATISFIABLE(this)
     */
    boolean isSat();

    /**
     * Reports whether {@code this} is a tautology; that is, if {@code this}
     * always evaluates to true.
     *
     * @return true iff for all sets of variable assignments, {@code this} is
     *         true
     * @ensures isValid = VALID(this)
     */
    boolean isValid();

    /**
     * Reports whether {@code this} is logically equivalent to {@code other}.
     *
     * @param other
     *            the BooleanStructure being compared to this for equivalence
     * @return true iff {@code this} is logically equivalent to {@code other}
     * @ensures <pre>
     *      isEquivalent = EQUIVALENT(this, other)
     * </pre>
     */
    boolean isEquivalent(BooleanStructure other);

    /**
     * Reports the number of variables of {@code this}.
     *
     * @return the number of variables of {@code this}
     * @ensures numVariables = | this.vars |
     */
    int numVariables();

    /**
     * Reports whether the structure is the True structure.
     *
     * @return true iff {@code this} is the True structure
     * @ensures isTrueStructure = IS_TRUE(this)
     */
    boolean isTrueStructure();

    /**
     * Reports whether the structure is the False structure.
     *
     * @return true iff {@code this} is the False structure
     * @ensures isFalseStructure = IS_FALSE(this)
     */
    boolean isFalseStructure();

    /**
     * Sets the structure to represent the SyntaxTree {@code st}
     *
     * @replaces this
     * @ensures [this represents the propositional formula expressed in st]
     */
    void setFromTree(SyntaxTree st);

    /**
     * Returns an assignment which makes {@code this} evaluate to true
     *
     * @return a satisfying assignment for {@code this}
     * @requires SATISFIABLE(this)
     * @ensures satAssignment is in this.sat
     */
    Set<Integer> satAssignment();

}
