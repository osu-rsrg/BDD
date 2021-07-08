package components.booleanstructure;

import components.sequence.Sequence;
import components.set.Set;
import components.standard.Standard;

/**
 * Boolean Structure kernel component with primary methods. (Note: by
 * package-wide convention, all references are non-null.)
 *
 * @mathsubtypes <pre>
 *
 * ASSIGNMENT is finite set of integer
 *
 * BOOLEAN_STRUCTURE is (sat: finite set of ASSIGNMENT,
 * 						 vars: string of integer)
 *   exemplar exp
 *   constraints
 *     for all a: ASSIGNMENT where ( a in exp.sat )
 *       ( a is subset of entries(exp.vars) ) and
 *     | exp.vars | = | entries(exp.vars) |
 *
 * BINARY_OPERATOR is { AND, OR, EQUIVALS }
 *
 * UNARY_OPERATOR is { NOT, IDENTITY }
 * </pre>
 * @mathdefinitions <pre>
 * VARIABLES(
 *   e: BOOLEAN_STRUCTURE
 *  ): set of integer is
 * entries(e.vars)
 *
 * IS_TRUE(
 *   m: BOOLEAN_STRUCTURE
 *  ): boolean is
 * m.sat = { { } } and
 * m.vars = < >
 *
 * IS_FALSE(
 *   m: BOOLEAN_STRUCTURE
 *  ): boolean is
 * m.sat = { } and
 * m.vars = < >
 *
 * EVALUATION(
 *   m: BOOLEAN_STRUCTURE,
 *   a: ASSIGNMENT
 *  ): boolean is
 * a intersection VARIABLES(m) is in m.sat
 *
 * BINARY_APPLY(
 *   result: BOOLEAN_STRUCTURE,
 *   m: BOOLEAN_STRUCTURE,
 *   n: BOOLEAN_STRUCTURE,
 *   op: BINARY_OPERATOR
 * ): boolean is
 * VARIABLES(result) = VARIABLES(m) union VARIABLES(n) and
 * for all p: ASSIGNMENT where ( p is subset of entries(result.vars) )
 *   ( p is in result.sat iff
 *     ( ( if op = AND then EVALUATION(m, p) and EVALUATION(n, p) ) and
 *       ( if op = OR then EVALUATION(m, p) or EVALUATION(n, p) ) and
 *       ( if op = EQUIVALS then EVALUATION(m, p) iff EVALUATION(n, p) ) ) )
 *
 * UNARY_APPLY(
 *   result: BOOLEAN_STRUCTURE,
 *   m: BOOLEAN_STRUCTURE,
 *   op: UNARY_OPERATOR
 *  ): boolean is
 * result.vars = m.vars and
 * for all p: ASSIGNMENT where ( p is subset of entries(result.vars) )
 *   ( p is in result.sat iff
 *     ( ( if op = NOT then not EVALUATION(m, p) ) and
 *       ( if op = IDENTITY then EVALUATION(m, p) ) ) )
 *
 * RESTRICTION(
 *   result: BOOLEAN_STRUCTURE,
 *   m: BOOLEAN_STRUCTURE,
 *   t: set of integer,
 *   f: set of integer
 *  ): boolean is
 * VARIABLES(result) = VARIABLES(m) \ (t union f) and
 * IS_COMPATIBLE_ORDERING(result.vars, m.vars) and
 * for all p: ASSIGNMENT where ( p is subset of entries(result.vars) )
 *   ( p is in result.sat iff
 *     ( ( p union t is in m.sat ) and
 *       ( p intersection t = empty_set ) and
 *       ( f is subset of VARIABLES(m) \ (p union t) ) ) )
 *
 * IS_COMPATIBLE_ORDERING(
 *   x: string of integer,
 *   y: string of integer
 *  ): boolean is
 * for all i, j, u, v: integer
 *   where (0 <= i, j < | x | and
 *          0 <= u, v < | y | and
 *          x[i, i + 1) = y[u, u + 1) and x[j, j + 1) = y[v, v + 1) )
 *   (i < j iff u < v)
 *
 * </pre>
 *
 * @mathmodel type BooleanStructureKernel is modeled by BOOLEAN_STRUCTURE
 * @initially <pre>
 * ():
 *   ensures
 *     this = ( { { } }, < > )
 * (boolean b)
 *   ensures
 *     if b
 *          then this = ( { { } }, < > )
 *          else this = ( { }, < > )
 * (integer n)
 *   ensures
 *     this = ( { { n } }, <n> )
 * </pre>
 **/
public interface BooleanStructureKernel extends Standard<BooleanStructure> {

    /**
     * Evaluates {@code this} for the variable assignment {@code a}
     *
     * @param a
     *            the variables that are true
     * @return true iff a makes this evaluate to true
     * @ensures evaluate = EVALUATION(this, a)
     */
    boolean evaluate(Set<Integer> a);

    /**
     * Apply the binary operator {@code op} between two structures: {@code this}
     * and {@code other}, fixing the total order of the variables to
     * {@code newVars} in the result and updating {@code this} to the result of
     * the operation.
     *
     * @param op
     *            the binary operation to be applied between the two
     *            BooleanStructures
     * @param other
     *            the second BooleanStructure operand for the logical operation
     * @param newVars
     *            the desired order of variables in the resulting
     *            BooleanStructure
     * @updates this, newVars
     * @requires <pre>
     *      entries(newVars) = VARIABLES(this) union VARIABLES(other) and
     *      | newVars | = | entries(newVars) | and
     *      IS_COMPATIBLE_ORDERING(this.vars, other.vars) and
     *      IS_COMPATIBLE_ORDERING(this.vars, newVars) and
     *      IS_COMPATIBLE_ORDERING(other.vars, newVars)
     * </pre>
     * @ensures <pre>
     *      BINARY_APPLY(this, #this, other, op) and
     *      this.vars = #newVars and
     *      newVars = #this.vars
     * </pre>
     */
    void apply(BinaryOperator op, BooleanStructure other,
            Sequence<Integer> newVars);

    /**
     * Apply the unary operator {@code op} to {@code this} without changing the
     * total order of the variables of {@code this}.
     *
     * @param op
     *            the unary operation to be applied on this
     * @updates this
     * @ensures <pre>
     *      UNARY_APPLY(this, #this, op)
     * </pre>
     */
    void apply(UnaryOperator op);

    /**
     * Constrains the variables in {@code t} to true and {@code f} to false in
     * {@code this}.
     *
     * @param t
     *            variables to be set to true in this
     * @param f
     *            variables to be set to false in this
     * @updates this
     * @requires <pre>
     * 		t union f is subset of VARIABLES(this) and
     * 		t intersection f = empty_set
     * </pre>
     * @ensures <pre>
     *      RESTRICTION(this, #this, t, f)
     * </pre>
     */
    void restrict(Set<Integer> t, Set<Integer> f);

    /**
     * Changes the variable ordering of {@code this} to {@code newVars}.
     *
     * @param newVars
     *            the new variable order for the structure
     * @updates this, newVars
     * @requires <pre>
     * 		VARIABLES(this) = entries(newVars)
     *      | newVars | = | entries(newVars) |
     * </pre>
     * @ensures <pre>
     *      this.sat = #this.sat and
     *      this.vars = #newVars and
     *      newVars = #this.vars
     * </pre>
     */
    void reorder(Sequence<Integer> newVars);

    /**
     * Reports the order of the variables in {@code} this.
     *
     * @return the ordering of variables in the boolean structure
     * @aliases reference returned by {@code vars}
     * @ensures vars = this.vars
     */
    Sequence<Integer> vars();

    /**
     * Sets {@code this} to represent a single variable structure of variable
     * {@code i}.
     *
     * @replaces this
     * @ensures this = ( { { i } }, <i> )
     */
    void setFromInt(int i);
}
