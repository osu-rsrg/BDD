package components.booleanstructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.set.Set;
import components.set.Set2;

public abstract class BooleanStructureReferenceTest
        extends BooleanStructureTestUtilities {

    /**
     * <pre>
     * IS_COMPATIBLE_ORDERING(
     *   x: string of integer, y: string of integer
     *  ): boolean is
     * for all i, j, u, v: integer
     *   where (0 <= i, j < | x | and
     *          0 <= u, v < | y | and
     *          x[i, i + 1) = y[u, u + 1) and x[j, j + 1) = y[v, v + 1) )
     *   (i < j iff u < v)
     * </pre>
     */
    private static boolean IS_COMPATIBLE_ORDERING(Sequence<Integer> x,
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

    /**
     * Creates a copy of a sequence
     *
     * @param seq
     *            the sequence to copy
     * @return a copy of the sequence
     * @ensures seqCopy = s
     *
     */
    private static Sequence<Integer> seqCopy(Sequence<Integer> seq) {
        Sequence<Integer> copy = new Sequence1L<Integer>();

        for (int i = 0; i < seq.length(); i++) {
            copy.add(i, seq.entry(i));
        }

        return copy;
    }

    private static boolean setEquivalent(Sequence<Integer> seq,
            Set<Integer> s) {
        for (int i = 0; i < seq.length(); i++) {
            if (!s.contains(seq.entry(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Invokes the appropriate BooleanStructure constructor for the
     * implementation under test and returns the result.
     *
     * @return the new BooleanStructure
     * @ensures constructorTest = ( ( { ( { }, { } ) }, { } ), <> )
     */
    protected abstract BooleanStructure constructorTest();

    /**
     * Invokes the appropriate BooleanStructure constructor for the
     * implementation under test and returns the result.
     *
     * @param b
     *            boolean to initialize from
     * @return the new BooleanStructure
     * @ensures constructorTest = ( ( { ( { }, { } ) }, { } ), <> ) if b
     *          constructorTest = ( ( { }, { } ), <> ) otherwise
     */
    protected abstract BooleanStructure constructorTest(boolean b);

    /**
     * Invokes the appropriate BooleanStructure constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            int to initialize from
     * @return the new BooleanStructure
     * @requires i >= 0
     * @ensures constructorTest = ( ( { ( { i }, { } ) }, { i } ), <i> )
     */
    protected abstract BooleanStructure constructorTest(int i);

    /**
     * Invokes the appropriate BooleanStructure constructor for the
     * implementation under test and returns the result.
     *
     * @param t
     *            SyntaxTree to initialize from
     * @return the new BooleanStructure
     * @requires TODO
     * @ensures TODO
     */
    protected abstract BooleanStructure constructorTest(SyntaxTree t);

    /*
     * Constructor Tests
     */

    /*
     * Empty Constructor - Always evaluates to true
     */
    @Test
    public final void testEmptyConstructor() {
        BooleanStructure exp = this.constructorTest();

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();

        assertEquals(0, exp.numVariables());
        assertEquals(expectedVars, exp.vars());
        assertTrue(exp.evaluate(createSet()));

    }

    /*
     * True Constructor - Always evaluates to true
     */
    @Test
    public final void testBooleanConstructorTrue() {
        BooleanStructure exp = this.constructorTest();

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();

        assertEquals(0, exp.numVariables());
        assertEquals(expectedVars, exp.vars());
        assertTrue(exp.evaluate(createSet()));
    }

    /*
     * False Constructor - Always evaluates to false
     */
    @Test
    public final void testBooleanConstructorFalse() {
        BooleanStructure exp = this.constructorTest(false);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();

        assertEquals(0, exp.numVariables());
        assertEquals(expectedVars, exp.vars());
        assertFalse(exp.evaluate(createSet()));
    }

    /*
     * Integer constructor - Should evaluate to true and false when passed
     * assignments of true and false, respectively
     */
    @Test
    public final void testIntegerConstructor() {
        BooleanStructure exp = this.constructorTest(0);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);

        assertEquals(expectedVars, exp.vars());
        assertEquals(1, exp.numVariables());
        assertTrue(exp.evaluate(createSet(0)));
        assertFalse(exp.evaluate(createSet()));
    }

    /*
     * SyntaxTree constructor
     */

    /*
     * True Expression
     */
    @Test
    public final void testTreeConstructorTrueExpression() {
        SyntaxTree t = new SyntaxTree("T");
        BooleanStructure exp = this.constructorTest(t);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();

        assertEquals(0, exp.numVariables());
        assertEquals(expectedVars, exp.vars());
        assertTrue(exp.evaluate(createSet()));
    }

    /*
     * False Expression
     */
    @Test
    public final void testTreeConstructorFalseExpression() {
        SyntaxTree t = new SyntaxTree("F");
        BooleanStructure exp = this.constructorTest(t);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();

        assertEquals(0, exp.numVariables());
        assertEquals(expectedVars, exp.vars());
        assertFalse(exp.evaluate(createSet()));
    }

    /*
     * Single Variable Expression
     */
    @Test
    public final void testTreeConstructorSingleVariableExpression() {
        SyntaxTree t = new SyntaxTree("0");
        BooleanStructure exp = this.constructorTest(t);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);

        assertEquals(expectedVars, exp.vars());
        assertEquals(1, exp.numVariables());
        assertTrue(exp.evaluate(createSet(0)));
        assertFalse(exp.evaluate(createSet()));
    }

    /*
     * Single variable expression with a unary operator - not( 0 )
     */
    @Test
    public final void testTreeConstructorSingleVariableExpressionNot() {
        SyntaxTree t = new SyntaxTree("0 not");
        BooleanStructure exp = this.constructorTest(t);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);

        assertEquals(expectedVars, exp.vars());
        assertEquals(1, exp.numVariables());
        assertFalse(exp.evaluate(createSet(0)));
        assertTrue(exp.evaluate(createSet()));
    }

    /*
     * Single Variable Expression with a T - ( 0 and T )
     */
    @Test
    public final void testTreeConstructorSingleVariableAndTrue() {
        SyntaxTree t = new SyntaxTree("0 T and");

        BooleanStructure exp = this.constructorTest(t);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);

        assertEquals(1, exp.numVariables());
        assertEquals(expectedVars, exp.vars());

        assertTrue(exp.evaluate(createSet(0)));
        assertFalse(exp.evaluate(createSet()));

    }

    /*
     * Two variable expression (AND) - ( 0 and 1 )
     */
    @Test
    public final void testTreeConstructorTwoVariableExpressionAnd() {
        SyntaxTree t = new SyntaxTree("0 1 and");
        BooleanStructure exp1 = this.constructorTest(t);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);
        expectedVars.add(1, 1);

        assertEquals(2, exp1.numVariables());
        assertEquals(expectedVars, exp1.vars());

        // ( { 0, 1 }, { } )
        assertTrue(exp1.evaluate(createSet(0, 1)));
        // ( { 0 }, { 1 } )
        assertFalse(exp1.evaluate(createSet(0)));
        // ( { 1 }, { 0 } )
        assertFalse(exp1.evaluate(createSet(1)));
        // ( { }, { 0, 1 } )
        assertFalse(exp1.evaluate(createSet()));

    }

    /*
     * Two variable expression - ( 0 or 1 )
     */
    @Test
    public final void testTreeConstructorTwoVariableExpressionOr() {
        SyntaxTree t = new SyntaxTree("0 1 or");
        BooleanStructure exp = this.constructorTest(t);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);
        expectedVars.add(1, 1);

        assertEquals(2, exp.numVariables());
        assertEquals(expectedVars, exp.vars());

        // ( { 0, 1 }, { } )
        assertTrue(exp.evaluate(createSet(0, 1)));
        // ( { 0 }, { 1 } )
        assertTrue(exp.evaluate(createSet(0)));
        // ( { 1 }, { 0 } )
        assertTrue(exp.evaluate(createSet(1)));
        // ( { }, { 0, 1 } )
        assertFalse(exp.evaluate(createSet()));
    }

    /*
     * Two variable expression with a negation - not( ( 0 or 1 ) )
     */
    @Test
    public final void testTreeConstructorTwoVariableExpressionOrNot() {
        SyntaxTree t = new SyntaxTree("0 1 or not");
        BooleanStructure exp = this.constructorTest(t);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);
        expectedVars.add(1, 1);

        assertEquals(2, exp.numVariables());
        assertEquals(expectedVars, exp.vars());

        // ( { 0, 1 }, { } )
        assertFalse(exp.evaluate(createSet(0, 1)));
        // ( { 0 }, { 1 } )
        assertFalse(exp.evaluate(createSet(0)));
        // ( { 1 }, { 0 } )
        assertFalse(exp.evaluate(createSet(1)));
        // ( { }, { 0, 1 } )
        assertTrue(exp.evaluate(createSet()));
    }

    /*
     * Three variable expressions with and - ( ( 0 and 1 ) and 2 )
     */
    @Test
    public final void testTreeConstructorThreeVariableExpressionAnds() {
        SyntaxTree t = new SyntaxTree("0 1 and 2 and");
        BooleanStructure exp = this.constructorTest(t);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);
        expectedVars.add(1, 1);
        expectedVars.add(2, 2);

        assertEquals(3, exp.numVariables());
        assertEquals(expectedVars, exp.vars());

        // ( { 0, 1, 2 }, { } )
        assertTrue(exp.evaluate(createSet(0, 1, 2)));
        // ( { 0, 1 }, { 2 } )
        assertFalse(exp.evaluate(createSet(0, 1)));
        // ( { 0, 2 }, { 1 } )
        assertFalse(exp.evaluate(createSet(0, 2)));
        // ( { 0 }, { 1, 2 } )
        assertFalse(exp.evaluate(createSet(0)));
        // ( { 1, 2 }, { 0 } )
        assertFalse(exp.evaluate(createSet(1, 2)));
        // ( { 1 }, { 0, 2 } )
        assertFalse(exp.evaluate(createSet(1)));
        // ( { 2 }, { 0, 1 } )
        assertFalse(exp.evaluate(createSet(2)));
        // ( { }, { 0, 1, 2 } )
        assertFalse(exp.evaluate(createSet()));

    }

    /*
     * Three variable expression with and, negation - not( ( ( 0 and 1 ) and 2 )
     * )
     */
    @Test
    public final void testTreeConstructorThreeVariableExpressionAndsNot() {
        SyntaxTree t = new SyntaxTree("0 1 and 2 and not");
        BooleanStructure exp = this.constructorTest(t);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);
        expectedVars.add(1, 1);
        expectedVars.add(2, 2);

        assertEquals(3, exp.numVariables());
        assertEquals(expectedVars, exp.vars());

        // ( { 0, 1, 2 }, { } )
        assertFalse(exp.evaluate(createSet(0, 1, 2)));
        // ( { 0, 1 }, { 2 } )
        assertTrue(exp.evaluate(createSet(0, 1)));
        // ( { 0, 2 }, { 1 } )
        assertTrue(exp.evaluate(createSet(0, 2)));
        // ( { 0 }, { 1, 2 } )
        assertTrue(exp.evaluate(createSet(0)));
        // ( { 1, 2 }, { 0 } )
        assertTrue(exp.evaluate(createSet(1, 2)));
        // ( { 1 }, { 0, 2 } )
        assertTrue(exp.evaluate(createSet(1)));
        // ( { 2 }, { 0, 1 } )
        assertTrue(exp.evaluate(createSet(2)));
        // ( { }, { 0, 1, 2 } )
        assertTrue(exp.evaluate(createSet()));

    }

    /*
     * Four variable expression with ands/ors - ( ( 0 and 1 ) and ( 2 or 3 ) )
     */
    @Test
    public final void testTreeConstructorFourVariableExpressionAndsOrs() {
        SyntaxTree t = new SyntaxTree("0 1 and 2 3 or and");
        BooleanStructure exp = this.constructorTest(t);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);
        expectedVars.add(1, 1);
        expectedVars.add(2, 2);
        expectedVars.add(3, 3);

        assertEquals(4, exp.numVariables());
        assertEquals(expectedVars, exp.vars());

        // ( ( 0 and 1 ) and ( 2 or 3 ) )

        // ( { 0, 1, 2, 3 }, { } )
        assertTrue(exp.evaluate(createSet(0, 1, 2, 3)));
        // ( { 0, 1, 2 }, { 3 } )
        assertTrue(exp.evaluate(createSet(0, 1, 2)));
        // ( { 0, 1, 3 }, { 2 } )
        assertTrue(exp.evaluate(createSet(0, 1, 3)));
        // ( { 0, 1 }, { 2, 3 } )
        assertFalse(exp.evaluate(createSet(0, 1)));
        // ( { 0, 2, 3 }, { 1 } )
        assertFalse(exp.evaluate(createSet(0, 2, 3)));
        // ( { 0, 2 }, { 1, 3 } )
        assertFalse(exp.evaluate(createSet(0, 2)));
        // ( { 0, 3 }, { 1, 2 } )
        assertFalse(exp.evaluate(createSet(0, 3)));
        // ( { 0 }, { 1, 2, 3 } )
        assertFalse(exp.evaluate(createSet(0)));
        // ( { 1, 2, 3 }, { 0 } )
        assertFalse(exp.evaluate(createSet(1, 2, 3)));
        // ( { 1, 2 }, { 0, 3 } )
        assertFalse(exp.evaluate(createSet(1, 2)));
        // ( { 1, 3 }, { 0, 2 } )
        assertFalse(exp.evaluate(createSet(1, 3)));
        // ( { 1 }, { 0, 2, 3 } )
        assertFalse(exp.evaluate(createSet(1)));
        // ( { 2, 3 }, { 0, 1 } )
        assertFalse(exp.evaluate(createSet(2, 3)));
        // ( { 2 }, { 0, 1, 3 } )
        assertFalse(exp.evaluate(createSet(2)));
        // ( { 3 }, { 0, 1, 2 } )
        assertFalse(exp.evaluate(createSet(3)));
        // ( { }, { 0, 1, 2, 3 } )
        assertFalse(exp.evaluate(createSet()));

    }

    /*
     * Kernel Methods
     */

    /*
     * Evaluate Tests
     */

    /*
     * Evaluation of the "empty" constructor based on multiple assignments of
     * varying sizes
     */
    @Test
    public final void testEvaluateEmptyExpression() {
        BooleanStructure exp = this.constructorTest();

        // ( { }, { } )
        assertTrue(exp.evaluate(createSet()));
        // ( { 0 }, { } )
        assertTrue(exp.evaluate(createSet(0)));
        // ( { }, { 0 } )
        assertTrue(exp.evaluate(createSet()));
        // ( { 0 }, { 1, 2 } )
        assertTrue(exp.evaluate(createSet(0)));
        // ( { 1, 2 }, { 0 } )
        assertTrue(exp.evaluate(createSet(1, 2)));
    }

    /*
     * Evaluation of the "true" expression based on multiple assignments of
     * varying sizes
     */
    @Test
    public final void testEvaluateTrueExpression() {
        BooleanStructure exp = this.constructorTest(true);

        // ( { }, { } )
        assertTrue(exp.evaluate(createSet()));
        // ( { 0 }, { } )
        assertTrue(exp.evaluate(createSet(0)));
        // ( { }, { 0 } )
        assertTrue(exp.evaluate(createSet()));
        // ( { 0 }, { 1, 2 } )
        assertTrue(exp.evaluate(createSet(0)));
        // ( { 1, 2 }, { 0 } )
        assertTrue(exp.evaluate(createSet(1, 2)));
    }

    /*
     * Evaluation of the "false" expression based on multiple assignments of
     * varying sizes
     */
    @Test
    public final void testEvaluateFalseExpression() {
        BooleanStructure exp = this.constructorTest(false);

        // ( { }, { } )
        assertFalse(exp.evaluate(createSet()));
        // ( { 0 }, { } )
        assertFalse(exp.evaluate(createSet(0)));
        // ( { }, { 0 } )
        assertFalse(exp.evaluate(createSet()));
        // ( { 0 }, { 1, 2 } )
        assertFalse(exp.evaluate(createSet(0)));
        // ( { 1, 2 }, { 0 } )
        assertFalse(exp.evaluate(createSet(1, 2)));
    }

    /*
     * Evaluation of a single variable expression based on an assignment
     * containing the variable in the expression
     */
    @Test
    public final void testEvaulateOneVariableExpressionOneAssignment() {
        BooleanStructure exp = this.constructorTest(1);

        // ( { 1 }, { } )
        assertTrue(exp.evaluate(createSet(1)));
        // ( { }, { 1 } )
        assertFalse(exp.evaluate(createSet()));
    }

    /*
     * Evaluation of a single variable expression based on multiple assignments
     * of varying sizes, each containing the variable in the expression as well
     * as other variables not part of the expression
     */
    @Test
    public final void testEvaluateOneVariableExpressionMultipleAssignments() {
        BooleanStructure exp = this.constructorTest(1);

        // ( { 1, 2 }, { } )
        assertTrue(exp.evaluate(createSet(1, 2)));
        // ( { }, { 1, 2 } )
        assertFalse(exp.evaluate(createSet()));
        // ( { 2, 18 }, { 1 } )
        assertFalse(exp.evaluate(createSet(2, 18)));
        // ( { 2, 300 }, { 1, 18 }
        assertFalse(exp.evaluate(createSet(2, 300)));
        // ( { 0, 1, 2 }, { 18, 300 } )
        assertTrue(exp.evaluate(createSet(0, 1, 2)));
    }

    /*
     * Evaluation of a two variable expression based on multiple assignments
     */
    @Test
    public final void testEvaulateTwoVariableExpressionMultipleAssignmentsAnd() {
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorTest(3);

        Sequence<Integer> newVars = new Sequence1L<Integer>();
        newVars.add(0, 1);
        newVars.add(1, 3);

        exp1.apply(BinaryOperator.AND, exp2, newVars);

        // ( { 1, 3 }, { } )
        assertTrue(exp1.evaluate(createSet(1, 3)));
        // ( { 1, 3 }, { 2, 18 } )
        assertTrue(exp1.evaluate(createSet(1, 3)));
        // ( { 1 }, { 3 } )
        assertFalse(exp1.evaluate(createSet(1)));
        // ( { 3 }, { 1 } )
        assertFalse(exp1.evaluate(createSet(3)));
        // ( { 3, 18 }, { 1, 300 } )
        assertFalse(exp1.evaluate(createSet(3, 18)));
    }

    /*
     * Evaluation of a two variable expression based on multiple assignments
     */
    @Test
    public final void testEvaulateTwoVariableExpressionMultipleAssignmentsOr() {
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorTest(3);

        Sequence<Integer> newVars = new Sequence1L<Integer>();
        newVars.add(0, 1);
        newVars.add(1, 3);

        exp1.apply(BinaryOperator.OR, exp2, newVars);

        // ( { 1, 3 }, { } )
        assertTrue(exp1.evaluate(createSet(1, 3)));
        // ( { 1, 3 }, { 2, 18 } )
        assertTrue(exp1.evaluate(createSet(1, 3)));
        // ( { 1 }, { 3 } )
        assertTrue(exp1.evaluate(createSet(1)));
        // ( { 3 }, { 1 } )
        assertTrue(exp1.evaluate(createSet(3)));
        // ( { 3, 18 }, { 1, 300 } )
        assertTrue(exp1.evaluate(createSet(3, 18)));
        // ( { 2, 18 }, { 1, 3 } )
        assertFalse(exp1.evaluate(createSet(2, 18)));
    }

    /*
     * Unary Apply Tests
     */

    /*
     * Perform the identity operation on an empty expression. Same empty
     * ordering. Always evaluates to true
     */
    @Test
    public final void testUnaryApplyIdentityEmptyExpression() {
        BooleanStructure exp = this.constructorTest();

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();

        exp.apply(UnaryOperator.IDENTITY);

        assertEquals(0, exp.numVariables());
        assertEquals(expectedVars, exp.vars());
        assertTrue(exp.evaluate(createSet()));

    }

    /*
     * Perform the not operation on an empty expression. Same empty ordering.
     * Always evaluates to false
     */
    @Test
    public final void testUnaryApplyNotEmptyExpression() {
        BooleanStructure exp = this.constructorTest();

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();

        exp.apply(UnaryOperator.NOT);

        assertEquals(0, exp.numVariables());
        assertEquals(expectedVars, exp.vars());
        assertFalse(exp.evaluate(createSet()));
        assertTrue(exp.isFalseStructure());

    }

    /*
     * Perform the identity operation on the true expression. Same empty
     * ordering. Always evaluates to true
     */
    @Test
    public final void testUnaryApplyIdentityTrueExpression() {
        BooleanStructure exp = this.constructorTest(true);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();

        exp.apply(UnaryOperator.IDENTITY);

        assertEquals(0, exp.numVariables());
        assertEquals(expectedVars, exp.vars());
        assertTrue(exp.evaluate(createSet()));
        assertTrue(exp.isTrueStructure());
    }

    /*
     * Perform the not operation on the true expression. Same empty ordering.
     * Always evaluates to false
     */
    @Test
    public final void testUnaryApplyNotTrueExpression() {
        BooleanStructure exp = this.constructorTest(true);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();

        exp.apply(UnaryOperator.NOT);

        assertEquals(0, exp.numVariables());
        assertEquals(expectedVars, exp.vars());
        assertFalse(exp.evaluate(createSet()));
        assertTrue(exp.isFalseStructure());
    }

    /*
     * Perform the identity operation on the false expression. Same empty
     * ordering. Always evaluates to false
     */
    @Test
    public final void testUnaryApplyIdentityFalseExpression() {
        BooleanStructure exp = this.constructorTest(false);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();

        exp.apply(UnaryOperator.IDENTITY);

        assertEquals(0, exp.numVariables());
        assertEquals(expectedVars, exp.vars());
        assertFalse(exp.evaluate(createSet()));
        assertTrue(exp.isFalseStructure());

    }

    /*
     * Perform the not operation on the false expression. Same empty ordering.
     * Always evaluates to true
     */
    @Test
    public final void testUnaryApplyNotFalseExpression() {
        BooleanStructure exp = this.constructorTest(false);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();

        exp.apply(UnaryOperator.NOT);

        assertEquals(0, exp.numVariables());
        assertEquals(expectedVars, exp.vars());
        assertTrue(exp.evaluate(createSet()));
        assertTrue(exp.isTrueStructure());
    }

    /*
     * Perform the identity operation on a single variable expression. Same,
     * single variable ordering. Evaluates to the same values as before the
     * apply
     */
    @Test
    public final void testUnaryApplyIdentitySingleVariableExpression() {
        BooleanStructure exp = this.constructorTest(300);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 300);

        exp.apply(UnaryOperator.IDENTITY);

        assertEquals(1, exp.numVariables());
        assertEquals(expectedVars, exp.vars());
        assertTrue(exp.evaluate(createSet(300)));
        assertFalse(exp.evaluate(createSet()));
    }

    /*
     * Perform the not operation on a single variable expression. Same, single
     * variable ordering. Evaluates to the negation of the expression before the
     * apply
     */
    @Test
    public final void testUnaryApplyNotSingleVariableExpression() {
        BooleanStructure exp = this.constructorTest(18);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 18);

        exp.apply(UnaryOperator.NOT);

        assertEquals(1, exp.numVariables());
        assertEquals(expectedVars, exp.vars());
        assertFalse(exp.evaluate(createSet(18)));
        assertTrue(exp.evaluate(createSet()));
    }

    /*
     * Perform the identity operation on two variable expressions
     */
    @Test
    public final void testUnaryApplyIdentityTwoVariableExpressions() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);

        BooleanStructure exp3 = this.constructorTest(0);
        BooleanStructure exp4 = this.constructorTest(1);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 0);
        newVars1.add(1, 1);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 0);
        newVars2.add(1, 1);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);
        expectedVars.add(1, 1);

        exp1.apply(BinaryOperator.AND, exp2, newVars1);
        exp3.apply(BinaryOperator.OR, exp4, newVars2);

        exp1.apply(UnaryOperator.IDENTITY);
        exp3.apply(UnaryOperator.IDENTITY);

        assertEquals(2, exp1.numVariables());
        assertEquals(expectedVars, exp1.vars());
        assertTrue(exp1.evaluate(createSet(0, 1)));
        assertFalse(exp1.evaluate(createSet(1)));
        assertFalse(exp1.evaluate(createSet(0)));
        assertFalse(exp1.evaluate(createSet()));

        assertEquals(2, exp3.numVariables());
        assertEquals(expectedVars, exp3.vars());
        assertTrue(exp3.evaluate(createSet(0, 1)));
        assertTrue(exp3.evaluate(createSet(1)));
        assertTrue(exp3.evaluate(createSet(0)));
        assertFalse(exp3.evaluate(createSet()));
    }

    /*
     * Perform the not operation on two variable expressions
     */
    @Test
    public final void testUnaryApplyNotTwoVariableExpressions() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);

        BooleanStructure exp3 = this.constructorTest(0);
        BooleanStructure exp4 = this.constructorTest(1);

        Sequence<Integer> newVars1A = new Sequence1L<Integer>();
        newVars1A.add(0, 0);
        newVars1A.add(1, 1);

        Sequence<Integer> newVars1B = new Sequence1L<Integer>();
        newVars1B.add(0, 0);
        newVars1B.add(1, 1);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);
        expectedVars.add(1, 1);

        exp1.apply(BinaryOperator.AND, exp2, newVars1A);
        exp3.apply(BinaryOperator.OR, exp4, newVars1B);

        exp1.apply(UnaryOperator.NOT);
        exp3.apply(UnaryOperator.NOT);

        assertEquals(2, exp1.numVariables());
        assertEquals(expectedVars, exp1.vars());
        assertFalse(exp1.evaluate(createSet(0, 1)));
        assertTrue(exp1.evaluate(createSet(1)));
        assertTrue(exp1.evaluate(createSet(0)));
        assertTrue(exp1.evaluate(createSet()));

        assertEquals(2, exp3.numVariables());
        assertEquals(expectedVars, exp3.vars());
        assertFalse(exp3.evaluate(createSet(0, 1)));
        assertFalse(exp3.evaluate(createSet(1)));
        assertFalse(exp3.evaluate(createSet(0)));
        assertTrue(exp3.evaluate(createSet()));
    }

    /*
     * Binary Apply Tests
     */

    /*
     * Perform the and operation on two true expressions
     */
    @Test
    public final void testBinaryApplyAndTwoTrues() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorTest(true);

        Sequence<Integer> newVars = new Sequence1L<Integer>();

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();

        exp1.apply(BinaryOperator.AND, exp2, newVars);
        exp2.apply(BinaryOperator.AND, exp1, newVars);

        assertEquals(0, exp1.numVariables());
        assertEquals(expectedVars, exp1.vars());
        assertTrue(exp1.evaluate(createSet()));
        assertTrue(exp1.isTrueStructure());

        assertEquals(0, exp2.numVariables());
        assertEquals(expectedVars, exp2.vars());
        assertTrue(exp2.evaluate(createSet()));
        assertTrue(exp2.isTrueStructure());
    }

    /*
     * Perform the and operation on two false expressions
     */
    @Test
    public final void testBinaryApplyAndTwoFalses() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorTest(false);

        Sequence<Integer> newVars = new Sequence1L<Integer>();

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();

        exp1.apply(BinaryOperator.AND, exp2, newVars);
        exp2.apply(BinaryOperator.AND, exp1, newVars);

        assertEquals(0, exp1.numVariables());
        assertEquals(expectedVars, exp1.vars());
        assertFalse(exp1.evaluate(createSet()));
        assertTrue(exp1.isFalseStructure());

        assertEquals(0, exp2.numVariables());
        assertEquals(expectedVars, exp2.vars());
        assertFalse(exp2.evaluate(createSet()));
        assertTrue(exp2.isFalseStructure());
    }

    /*
     * Perform the and operation on a true expression and a false expression
     */
    @Test
    public final void testBinaryApplyAndTrueFalse() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorTest(false);

        Sequence<Integer> newVars = new Sequence1L<Integer>();

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();

        exp1.apply(BinaryOperator.AND, exp2, newVars);
        exp2.apply(BinaryOperator.AND, exp1, newVars);

        assertEquals(0, exp1.numVariables());
        assertEquals(expectedVars, exp1.vars());
        assertFalse(exp1.evaluate(createSet()));
        assertTrue(exp1.isFalseStructure());

        assertEquals(0, exp2.numVariables());
        assertEquals(expectedVars, exp2.vars());
        assertFalse(exp2.evaluate(createSet()));
        assertTrue(exp2.isFalseStructure());
    }

    /*
     * Perform the or operation on two true expressions
     */
    @Test
    public final void testBinaryApplyOrTwoTrues() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorTest(true);

        Sequence<Integer> newVars = new Sequence1L<Integer>();

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();

        exp1.apply(BinaryOperator.OR, exp2, newVars);
        exp2.apply(BinaryOperator.OR, exp1, newVars);

        assertEquals(0, exp1.numVariables());
        assertEquals(expectedVars, exp1.vars());
        assertTrue(exp1.evaluate(createSet()));
        assertTrue(exp1.isTrueStructure());

        assertEquals(0, exp2.numVariables());
        assertEquals(expectedVars, exp2.vars());
        assertTrue(exp2.evaluate(createSet()));
        assertTrue(exp2.isTrueStructure());
    }

    /*
     * Perform the or operation on two false expressions
     */
    @Test
    public final void testBinaryApplyOrTwoFalses() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorTest(false);

        Sequence<Integer> newVars = new Sequence1L<Integer>();

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();

        exp1.apply(BinaryOperator.OR, exp2, newVars);
        exp2.apply(BinaryOperator.OR, exp1, newVars);

        assertEquals(0, exp1.numVariables());
        assertEquals(expectedVars, exp1.vars());
        assertFalse(exp1.evaluate(createSet()));
        assertTrue(exp1.isFalseStructure());

        assertEquals(0, exp2.numVariables());
        assertEquals(expectedVars, exp2.vars());
        assertFalse(exp2.evaluate(createSet()));
        assertTrue(exp2.isFalseStructure());
    }

    /*
     * Perform the or operation on a true expression and false expression
     */
    @Test
    public final void testBinaryApplyOrTrueFalse() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorTest(false);

        Sequence<Integer> newVars = new Sequence1L<Integer>();

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();

        exp1.apply(BinaryOperator.OR, exp2, newVars);
        exp2.apply(BinaryOperator.OR, exp1, newVars);

        assertEquals(0, exp1.numVariables());
        assertEquals(expectedVars, exp1.vars());
        assertTrue(exp1.evaluate(createSet()));
        assertTrue(exp1.isTrueStructure());

        assertEquals(0, exp2.numVariables());
        assertEquals(expectedVars, exp2.vars());
        assertTrue(exp2.evaluate(createSet()));
        assertTrue(exp2.isTrueStructure());
    }

    /*
     * Perform the or operation on a true expression and a single variable
     * expression
     */
    @Test
    public final void testBinaryApplyOrTrueSingleVariable() {
        BooleanStructure varExp = this.constructorTest(0);
        BooleanStructure trueExp = this.constructorTest(true);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 0);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 0);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);

        varExp.apply(BinaryOperator.OR, trueExp, newVars1);
        trueExp.apply(BinaryOperator.OR, varExp, newVars2);

        assertEquals(1, varExp.numVariables());
        assertEquals(expectedVars, varExp.vars());

        assertEquals(1, trueExp.numVariables());
        assertEquals(expectedVars, trueExp.vars());

        assertTrue(varExp.evaluate(createSet(0)));
        assertTrue(varExp.evaluate(createSet()));

        assertTrue(trueExp.evaluate(createSet(0)));
        assertTrue(trueExp.evaluate(createSet()));

    }

    /*
     * Perform the or operation on a false expression and a single variable
     * expression
     */
    @Test
    public final void testBinaryApplyOrFalseSingleVariable() {
        BooleanStructure varExp = this.constructorTest(0);
        BooleanStructure falseExp = this.constructorTest(false);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 0);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 0);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);

        varExp.apply(BinaryOperator.OR, falseExp, newVars1);
        falseExp.apply(BinaryOperator.OR, varExp, newVars2);

        assertEquals(1, varExp.numVariables());
        assertEquals(expectedVars, varExp.vars());

        assertEquals(1, falseExp.numVariables());
        assertEquals(expectedVars, falseExp.vars());

        assertTrue(varExp.evaluate(createSet(0)));
        assertFalse(varExp.evaluate(createSet()));

        assertTrue(falseExp.evaluate(createSet(0)));
        assertFalse(falseExp.evaluate(createSet()));
    }

    /*
     * Perform the and operation on a true expression and a single variable
     * expression
     */
    @Test
    public final void testBinaryApplyAndTrueSingleVariable() {
        BooleanStructure varExp = this.constructorTest(0);
        BooleanStructure trueExp = this.constructorTest(true);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 0);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 0);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);

        varExp.apply(BinaryOperator.AND, trueExp, newVars1);
        trueExp.apply(BinaryOperator.AND, varExp, newVars2);

        assertEquals(1, varExp.numVariables());
        assertEquals(expectedVars, varExp.vars());

        assertEquals(1, trueExp.numVariables());
        assertEquals(expectedVars, trueExp.vars());

        assertTrue(varExp.evaluate(createSet(0)));
        assertFalse(varExp.evaluate(createSet()));

        assertTrue(trueExp.evaluate(createSet(0)));
        assertFalse(trueExp.evaluate(createSet()));
    }

    /*
     * Perform the and operation on a false expression and a single variable
     * expression
     */
    @Test
    public final void testBinaryApplyAndFalseSingleVariable() {
        BooleanStructure varExp = this.constructorTest(0);
        BooleanStructure falseExp = this.constructorTest(false);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 0);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 0);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);

        varExp.apply(BinaryOperator.AND, falseExp, newVars1);
        falseExp.apply(BinaryOperator.AND, varExp, newVars2);

        assertEquals(1, varExp.numVariables());
        assertEquals(expectedVars, varExp.vars());

        assertEquals(1, falseExp.numVariables());
        assertEquals(expectedVars, falseExp.vars());

        assertFalse(varExp.evaluate(createSet(0)));
        assertFalse(varExp.evaluate(createSet()));

        assertFalse(falseExp.evaluate(createSet(0)));
        assertFalse(falseExp.evaluate(createSet()));
    }

    /*
     * Perform the and operation on two single variable expressions
     */
    @Test
    public final void testBinaryApplyAnd2SingleVariables() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);

        Sequence<Integer> newVars = new Sequence1L<Integer>();
        newVars.add(0, 0);
        newVars.add(1, 1);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);
        expectedVars.add(1, 1);

        exp1.apply(BinaryOperator.AND, exp2, newVars);

        assertEquals(2, exp1.numVariables());
        assertEquals(expectedVars, exp1.vars());

        // ( { 0, 1 }, { } )
        assertTrue(exp1.evaluate(createSet(0, 1)));
        // ( { 0 }, { 1 } )
        assertFalse(exp1.evaluate(createSet(0)));
        // ( { 1 }, { 0 } )
        assertFalse(exp1.evaluate(createSet(1)));
        // ( { }, { 0, 1 } )
        assertFalse(exp1.evaluate(createSet()));
    }

    /*
     * Perform the or operation on two single variable expressions
     */
    @Test
    public final void testBinaryApplyOr2SingleVariables() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);

        Sequence<Integer> newVars = new Sequence1L<Integer>();
        newVars.add(0, 0);
        newVars.add(1, 1);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);
        expectedVars.add(1, 1);

        exp1.apply(BinaryOperator.OR, exp2, newVars);

        assertEquals(2, exp1.numVariables());
        assertEquals(expectedVars, exp1.vars());

        // ( { 0, 1 }, { } )
        assertTrue(exp1.evaluate(createSet(0, 1)));
        // ( { 0 }, { 1 } )
        assertTrue(exp1.evaluate(createSet(0)));
        // ( { 1 }, { 0 } )
        assertTrue(exp1.evaluate(createSet(1)));
        // ( { }, { 0, 1 } )
        assertFalse(exp1.evaluate(createSet()));
    }

    /*
     * Perform the or operation between a single variable and 2 variable
     * expression
     */
    @Test
    public final void testBinaryApplyOrTwoVariableSingleVariable() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(2);

        BooleanStructure exp4 = this.constructorTest(0);
        BooleanStructure exp5 = this.constructorTest(1);
        BooleanStructure exp6 = this.constructorTest(2);

        Sequence<Integer> newVars1A = new Sequence1L<Integer>();
        newVars1A.add(0, 0);
        newVars1A.add(1, 1);

        Sequence<Integer> newVars1B = new Sequence1L<Integer>();
        newVars1B.add(0, 0);
        newVars1B.add(1, 1);

        Sequence<Integer> newVars2A = new Sequence1L<Integer>();
        newVars2A.add(0, 0);
        newVars2A.add(1, 1);
        newVars2A.add(1, 2);

        Sequence<Integer> newVars2B = new Sequence1L<Integer>();
        newVars2B.add(0, 0);
        newVars2B.add(1, 1);
        newVars2B.add(1, 2);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);
        expectedVars.add(1, 1);
        expectedVars.add(1, 2);

        // (x0 V x1) V x2
        exp1.apply(BinaryOperator.OR, exp2, newVars1A);
        exp1.apply(BinaryOperator.OR, exp3, newVars2A);

        // (x0 ^ x1) V x2
        exp4.apply(BinaryOperator.AND, exp5, newVars1B);
        exp4.apply(BinaryOperator.OR, exp6, newVars2B);

        assertEquals(3, exp1.numVariables());
        assertEquals(expectedVars, exp1.vars());

        // ( { 0, 1, 2 }, { } )
        assertTrue(exp1.evaluate(createSet(0, 1, 2)));
        // ( { 0, 1 }, { 2 } )
        assertTrue(exp1.evaluate(createSet(0, 1)));
        // ( { 0, 2 }, { 1 } )
        assertTrue(exp1.evaluate(createSet(0, 2)));
        // ( { 0 }, { 1, 2 } )
        assertTrue(exp1.evaluate(createSet(0)));
        // ( { 1, 2 }, { 0 } )
        assertTrue(exp1.evaluate(createSet(1, 2)));
        // ( { 1 }, { 0, 2 } )
        assertTrue(exp1.evaluate(createSet(1)));
        // ( { 2 }, { 0, 1 } )
        assertTrue(exp1.evaluate(createSet(2)));
        // ( { }, { 0, 1, 2 } )
        assertFalse(exp1.evaluate(createSet()));

        // ( { 0, 1, 2 }, { } )
        assertTrue(exp4.evaluate(createSet(0, 1, 2)));
        // ( { 0, 1 }, { 2 } )
        assertTrue(exp4.evaluate(createSet(0, 1)));
        // ( { 0, 2 }, { 1 } )
        assertTrue(exp4.evaluate(createSet(0, 2)));
        // ( { 0 }, { 1, 2 } )
        assertFalse(exp4.evaluate(createSet(0)));
        // ( { 1, 2 }, { 0 } )
        assertTrue(exp4.evaluate(createSet(1, 2)));
        // ( { 1 }, { 0, 2 } )
        assertFalse(exp4.evaluate(createSet(1)));
        // ( { 2 }, { 0, 1 } )
        assertTrue(exp4.evaluate(createSet(2)));
        // ( { }, { 0, 1, 2 } )
        assertFalse(exp4.evaluate(createSet()));
    }

    /*
     * Perform the and operation between a single variable and 2 variable
     * expression
     */
    @Test
    public final void testBinaryApplyAndTwoVariableSingleVariable() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(2);

        BooleanStructure exp4 = this.constructorTest(0);
        BooleanStructure exp5 = this.constructorTest(1);
        BooleanStructure exp6 = this.constructorTest(2);

        Sequence<Integer> newVars1A = new Sequence1L<Integer>();
        newVars1A.add(0, 0);
        newVars1A.add(1, 1);

        Sequence<Integer> newVars1B = new Sequence1L<Integer>();
        newVars1B.add(0, 0);
        newVars1B.add(1, 1);

        Sequence<Integer> newVars2A = new Sequence1L<Integer>();
        newVars2A.add(0, 0);
        newVars2A.add(1, 1);
        newVars2A.add(1, 2);

        Sequence<Integer> newVars2B = new Sequence1L<Integer>();
        newVars2B.add(0, 0);
        newVars2B.add(1, 1);
        newVars2B.add(1, 2);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);
        expectedVars.add(1, 1);
        expectedVars.add(1, 2);

        // (x0 V x1) ^ x2
        exp1.apply(BinaryOperator.OR, exp2, newVars1A);
        exp1.apply(BinaryOperator.AND, exp3, newVars2A);

        // (x0 ^ x1) ^ x2
        exp4.apply(BinaryOperator.AND, exp5, newVars1B);
        exp4.apply(BinaryOperator.AND, exp6, newVars2B);

        assertEquals(3, exp1.numVariables());
        assertEquals(expectedVars, exp1.vars());

        // ( { 0, 1, 2 }, { } )
        assertTrue(exp1.evaluate(createSet(0, 1, 2)));
        // ( { 0, 1 }, { 2 } )
        assertFalse(exp1.evaluate(createSet(0, 1)));
        // ( { 0, 2 }, { 1 } )
        assertTrue(exp1.evaluate(createSet(0, 2)));
        // ( { 0 }, { 1, 2 } )
        assertFalse(exp1.evaluate(createSet(0)));
        // ( { 1, 2 }, { 0 } )
        assertTrue(exp1.evaluate(createSet(1, 2)));
        // ( { 1 }, { 0, 2 } )
        assertFalse(exp1.evaluate(createSet(1)));
        // ( { 2 }, { 0, 1 } )
        assertFalse(exp1.evaluate(createSet(2)));
        // ( { }, { 0, 1, 2 } )
        assertFalse(exp1.evaluate(createSet()));

        // ( { 0, 1, 2 }, { } )
        assertTrue(exp4.evaluate(createSet(0, 1, 2)));
        // ( { 0, 1 }, { 2 } )
        assertFalse(exp4.evaluate(createSet(0, 1)));
        // ( { 0, 2 }, { 1 } )
        assertFalse(exp4.evaluate(createSet(0, 2)));
        // ( { 0 }, { 1, 2 } )
        assertFalse(exp4.evaluate(createSet(0)));
        // ( { 1, 2 }, { 0 } )
        assertFalse(exp4.evaluate(createSet(1, 2)));
        // ( { 1 }, { 0, 2 } )
        assertFalse(exp4.evaluate(createSet(1)));
        // ( { 2 }, { 0, 1 } )
        assertFalse(exp4.evaluate(createSet(2)));
        // ( { }, { 0, 1, 2 } )
        assertFalse(exp4.evaluate(createSet()));
    }

    /*
     * Perform a binary apply on expressions with common variables (3 literals,
     * 2 variables)
     */
    @Test
    public final void testBinaryApply3Literals2VariablesAndOr() {
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorTest(2);
        BooleanStructure exp3 = this.constructorTest(2);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 1);
        newVars1.add(1, 2);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 1);
        newVars2.add(1, 2);

        // (x1 ^ x2) v x2
        exp1.apply(BinaryOperator.AND, exp2, newVars1);
        exp1.apply(BinaryOperator.OR, exp3, newVars2);

        // ( { 1, 2 }, { } )
        assertTrue(exp1.evaluate(createSet(1, 2)));
        // ( { 1 }, { 2 } )
        assertFalse(exp1.evaluate(createSet(1)));
        // ( { 2 }, { 1 } )
        assertTrue(exp1.evaluate(createSet(2)));
        // ( { }, { 1, 2 } )
        assertFalse(exp1.evaluate(createSet()));
    }

    /*
     * Perform a binary apply on expressions with common variables (3 literals,
     * 2 variables)
     */
    @Test
    public final void testBinaryApply3Literals2VariablesAndAnd() {
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorTest(2);
        BooleanStructure exp3 = this.constructorTest(2);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 1);
        newVars1.add(1, 2);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 1);
        newVars2.add(1, 2);

        // (x1 ^ x2) ^ x2
        exp1.apply(BinaryOperator.AND, exp2, newVars1);
        exp1.apply(BinaryOperator.AND, exp3, newVars2);

        // ( { 1, 2 }, { } )
        assertTrue(exp1.evaluate(createSet(1, 2)));
        // ( { 1 }, { 2 } )
        assertFalse(exp1.evaluate(createSet(1)));
        // ( { 2 }, { 1 } )
        assertFalse(exp1.evaluate(createSet(2)));
        // ( { }, { 1, 2 } )
        assertFalse(exp1.evaluate(createSet()));
    }

    /*
     * Perform a binary apply on expressions with common variables (3 literals,
     * 2 variables)
     */
    @Test
    public final void testBinaryApply3Literals2VariablesOrAnd() {
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorTest(2);
        BooleanStructure exp3 = this.constructorTest(2);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 1);
        newVars1.add(1, 2);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 1);
        newVars2.add(1, 2);

        // (x1 ^ x2) v x2
        exp1.apply(BinaryOperator.OR, exp2, newVars1);
        exp1.apply(BinaryOperator.AND, exp3, newVars2);

        // ( { 1, 2 }, { } )
        assertTrue(exp1.evaluate(createSet(1, 2)));
        // ( { 1 }, { 2 } )
        assertFalse(exp1.evaluate(createSet(1)));
        // ( { 2 }, { 1 } )
        assertTrue(exp1.evaluate(createSet(2)));
        // ( { }, { 1, 2 } )
        assertFalse(exp1.evaluate(createSet()));
    }

    /*
     * Perform a binary apply on expressions with the same variables (4
     * Literals, 3 variables)
     */
    @Test
    public final void testBinaryApply4Literals3Variables1() {
        SyntaxTree t1 = new SyntaxTree("1 2 and");
        SyntaxTree t2 = new SyntaxTree("3 1 and");
        BooleanStructure exp1 = this.constructorTest(t1);
        BooleanStructure exp2 = this.constructorTest(t2);

        Sequence<Integer> newVars = new Sequence1L<Integer>();
        newVars.add(0, 3);
        newVars.add(1, 1);
        newVars.add(2, 2);

        exp1.apply(BinaryOperator.OR, exp2, newVars);

        // ( { 1, 2, 3 }, { } )
        assertTrue(exp1.evaluate(createSet(1, 2, 3)));
        // ( { 1, 2 }, { 3 } )
        assertTrue(exp1.evaluate(createSet(1, 2)));
        // ( { 1, 3 }, { 2 } )
        assertTrue(exp1.evaluate(createSet(1, 3)));
        // ( { 1 }, { 2, 3 } )
        assertFalse(exp1.evaluate(createSet(1)));
        // ( { 2, 3 }, { 1 } )
        assertFalse(exp1.evaluate(createSet(2, 3)));
        // ( { 2 }, { 1, 3 } )
        assertFalse(exp1.evaluate(createSet(2)));
        // ( { 1 }, { 2, 3 } )
        assertFalse(exp1.evaluate(createSet(3)));
        // ( { }, { 1, 2, 3 } )
        assertFalse(exp1.evaluate(createSet()));
    }

    /*
     * Perform a binary apply on expressions with the same variables (4
     * Literals, 3 variables)
     */
    @Test
    public final void testBinaryApply4Literals3Variables2() {
        SyntaxTree t1 = new SyntaxTree("1 2 or");
        SyntaxTree t2 = new SyntaxTree("3 1 or");
        BooleanStructure exp1 = this.constructorTest(t1);
        BooleanStructure exp2 = this.constructorTest(t2);

        Sequence<Integer> newVars = new Sequence1L<Integer>();
        newVars.add(0, 3);
        newVars.add(1, 1);
        newVars.add(2, 2);

        exp1.apply(BinaryOperator.AND, exp2, newVars);

        // ( { 1, 2, 3 }, { } )
        assertTrue(exp1.evaluate(createSet(1, 2, 3)));
        // ( { 1, 2 }, { 3 } )
        assertTrue(exp1.evaluate(createSet(1, 2)));
        // ( { 1, 3 }, { 2 } )
        assertTrue(exp1.evaluate(createSet(1, 3)));
        // ( { 1 }, { 2, 3 } )
        assertTrue(exp1.evaluate(createSet(1)));
        // ( { 2, 3 }, { 1 } )
        assertTrue(exp1.evaluate(createSet(2, 3)));
        // ( { 2 }, { 1, 3 } )
        assertFalse(exp1.evaluate(createSet(2)));
        // ( { 1 }, { 2, 3 } )
        assertFalse(exp1.evaluate(createSet(3)));
        // ( { }, { 1, 2, 3 } )
        assertFalse(exp1.evaluate(createSet()));
    }

    /*
     * Perform a binary apply on expressions with the same variables (4
     * Literals, 2 variables)
     */
    @Test
    public final void testBinaryApply4Literals2Variables() {
        SyntaxTree t1 = new SyntaxTree("1 2 or");
        SyntaxTree t2 = new SyntaxTree("1 2 and");
        BooleanStructure exp1 = this.constructorTest(t1);
        BooleanStructure exp2 = this.constructorTest(t2);

        Sequence<Integer> newVars = new Sequence1L<Integer>();
        newVars.add(0, 1);
        newVars.add(1, 2);

        exp1.apply(BinaryOperator.OR, exp2, newVars);

        // ( { 1, 2 }, { } )
        assertTrue(exp1.evaluate(createSet(1, 2)));
        // ( { 1 }, { 2 } )
        assertTrue(exp1.evaluate(createSet(1)));
        // ( { 2 }, { 1 } )
        assertTrue(exp1.evaluate(createSet(2)));
        // ( { }, { 1, 2 } )
        assertFalse(exp1.evaluate(createSet()));
    }

    /*
     * Expand Tests
     */

    /*
     * Expand the True expression by 1 variable
     */
    @Test
    public final void testExpandTrueExpressionBy1() {
        BooleanStructure exp = this.constructorTest();

        Set<Integer> expansion = new Set2<Integer>();
        expansion.add(1);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 1);

        exp.expand(expansion);

        assertEquals(1, exp.numVariables());
        assertEquals(expectedVars, exp.vars());

        assertTrue(exp.evaluate(createSet(1)));
        assertTrue(exp.evaluate(createSet()));
        assertTrue(exp.isSat());
        assertTrue(exp.isValid());
    }

    /*
     * Expand the False expression by 1 variable
     */
    @Test
    public final void testExpandFalseExpressionBy1() {
        BooleanStructure exp = this.constructorTest(false);

        Set<Integer> expansion = new Set2<Integer>();
        expansion.add(1);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 1);

        exp.expand(expansion);

        assertEquals(1, exp.numVariables());
        assertEquals(expectedVars, exp.vars());

        assertFalse(exp.evaluate(createSet(1)));
        assertFalse(exp.evaluate(createSet()));
        assertFalse(exp.isSat());
        assertFalse(exp.isValid());
    }

    /*
     * Expand a single variable expression by 1 variable
     */
    @Test
    public final void testExpandSingleVariableBy1() {
        BooleanStructure exp = this.constructorTest(0);

        Set<Integer> expansion = new Set2<Integer>();
        expansion.add(1);

        exp.expand(expansion);

        assertEquals(2, exp.numVariables());
        assertTrue(exp.vars().entry(0) == 0 || exp.vars().entry(1) == 0);
        assertTrue(exp.vars().entry(0) == 1 || exp.vars().entry(1) == 1);

        // ( { 0, 1 }, { } )
        assertTrue(exp.evaluate(createSet(0, 1)));
        // ( { 0 }, { 1 } )
        assertTrue(exp.evaluate(createSet(0)));
        // ( { 1 }, { 0 } )
        assertFalse(exp.evaluate(createSet(1)));
        // ( { }, { 0, 1 } )
        assertFalse(exp.evaluate(createSet()));

    }

    /*
     * Expand a single variable expression by 2 variables
     */
    @Test
    public final void testExpandSingleVariableBy2() {
        BooleanStructure exp = this.constructorTest(0);

        Set<Integer> expansion = new Set2<Integer>();
        expansion.add(1);
        expansion.add(2);

        exp.expand(expansion);

        assertEquals(3, exp.numVariables());
        assertTrue(exp.vars().entry(0) == 0 || exp.vars().entry(1) == 0
                || exp.vars().entry(2) == 0);
        assertTrue(exp.vars().entry(0) == 1 || exp.vars().entry(1) == 1
                || exp.vars().entry(2) == 1);
        assertTrue(exp.vars().entry(0) == 2 || exp.vars().entry(1) == 2
                || exp.vars().entry(2) == 2);

        // ( { 0, 1, 2 }, { } )
        assertTrue(exp.evaluate(createSet(0, 1, 2)));
        // ( { 0, 1 }, { 2 } )
        assertTrue(exp.evaluate(createSet(0, 1)));
        // ( { 0, 2 }, { 1 } )
        assertTrue(exp.evaluate(createSet(0, 2)));
        // ( { 0 }, { 1, 2 } )
        assertTrue(exp.evaluate(createSet(0)));
        // ( { 1, 2 }, { 0 } )
        assertFalse(exp.evaluate(createSet(1, 2)));
        // ( { 1 }, { 0, 2 } )
        assertFalse(exp.evaluate(createSet(1)));
        // ( { 2 }, { 0, 1 } )
        assertFalse(exp.evaluate(createSet(2)));
        // ( { }, { 0, 1, 2 } )
        assertFalse(exp.evaluate(createSet()));

    }

    /*
     * Expand a many-variable expression by 1 variable
     */
    @Test
    public final void testExpandManyVariablesBy1() {
        BooleanStructure exp = this.constructorTest();
        Set<Integer> variables = new Set2<Integer>();
        for (int i = 0; i < 10; i++) {
            variables.add(i);
        }
        exp.expand(variables);

        Sequence<Integer> oldVars = exp.vars().newInstance();
        for (int i = 0; i < exp.vars().length(); i++) {
            oldVars.add(i, exp.vars().entry(i));
        }

        Set<Integer> expansion = new Set2<Integer>();
        expansion.add(20);

        exp.expand(expansion);

        assertEquals(11, exp.numVariables());
        assertTrue(IS_COMPATIBLE_ORDERING(oldVars, exp.vars()));

        assertTrue(exp.isSat());
    }

    /*
     * isTrue and isFalse Tests
     */

    /*
     * isTrue and isFalse work after empty constructor, which produces true
     */
    @Test
    public final void testEmptyConstructorIsTrue() {
        BooleanStructure exp = this.constructorTest();

        assertEquals(true, exp.isTrueStructure());
        assertEquals(false, exp.isFalseStructure());
    }

    /*
     * isTrue and isFalse work after boolean constructor (true)
     */
    @Test
    public final void testBooleanTrueConstructorIsTrue() {
        BooleanStructure exp = this.constructorTest(true);

        assertEquals(true, exp.isTrueStructure());
        assertEquals(false, exp.isFalseStructure());
    }

    /*
     * isTrue and isFalse work after boolean constructor (false)
     */
    @Test
    public final void testBooleanFalseConstructorIsFalse() {
        BooleanStructure exp = this.constructorTest(false);

        assertEquals(false, exp.isTrueStructure());
        assertEquals(true, exp.isFalseStructure());
    }

    /*
     * A one-variable expression is neither true nor false
     */
    @Test
    public final void testOneVariableExpressionIsTrue() {
        BooleanStructure exp = this.constructorTest(0);

        assertEquals(false, exp.isTrueStructure());
        assertEquals(false, exp.isFalseStructure());
    }

    /*
     * Restrict Tests
     */

    @Test
    public final void testRestrictLiterals() {
        BooleanStructure expT = this.constructorTest(true);
        BooleanStructure expF = this.constructorTest(false);

        expT.restrict(createSet(), createSet());
        expF.restrict(createSet(), createSet());

        assertTrue(expT.isTrueStructure());
        assertTrue(expF.isFalseStructure());
    }

    @Test
    public final void testRestrictSingleVariableExpressions() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(0);

        exp1.restrict(createSet(0), createSet());
        exp2.restrict(createSet(), createSet(0));

        assertTrue(exp1.isTrueStructure());
        assertTrue(exp2.isFalseStructure());
    }

    @Test
    public final void testRestrictTwoVariableExpression0or1() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);

        BooleanStructure exp3 = this.constructorTest(0);
        BooleanStructure exp4 = this.constructorTest(1);

        Sequence<Integer> newVars = new Sequence1L<Integer>();
        newVars.add(0, 0);
        newVars.add(1, 1);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 0);
        newVars2.add(1, 1);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);

        exp1.apply(BinaryOperator.OR, exp2, newVars);
        exp1.restrict(createSet(1), createSet());

        exp3.apply(BinaryOperator.OR, exp4, newVars2);
        exp3.restrict(createSet(), createSet(1));

        assertEquals(1, exp1.numVariables());
        assertEquals(expectedVars, exp1.vars());
        assertTrue(exp1.evaluate(createSet(0)));
        assertTrue(exp1.evaluate(createSet()));

        assertEquals(1, exp3.numVariables());
        assertEquals(expectedVars, exp3.vars());
        assertTrue(exp3.evaluate(createSet(0)));
        assertFalse(exp3.evaluate(createSet()));

    }

    /*
     * Reorder Tests
     */

    /*
     * Reorder on an expression with no variables
     */
    @Test
    public final void testReorderNoVariables() {
        BooleanStructure exp = this.constructorTest();

        Sequence<Integer> newVars = new Sequence1L<Integer>();

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();

        exp.reorder(newVars);

        assertEquals(0, exp.numVariables());
        assertEquals(expectedVars, exp.vars());
    }

    /*
     * Reorder on an expression with a single variables
     */
    @Test
    public final void testReorder1Variable() {
        BooleanStructure exp = this.constructorTest(0);

        Sequence<Integer> newVars = new Sequence1L<Integer>();
        newVars.add(0, 0);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);

        exp.reorder(newVars);

        assertEquals(1, exp.numVariables());
        assertEquals(expectedVars, exp.vars());
    }

    /*
     * Reorder on an expression with two variables
     */
    @Test
    public final void testReorder2Variables() {
        SyntaxTree t = new SyntaxTree("0 1 or");
        BooleanStructure exp = this.constructorTest(t);

        Sequence<Integer> newVars = new Sequence1L<Integer>();
        newVars.add(0, 1);
        newVars.add(1, 0);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 1);
        expectedVars.add(1, 0);

        exp.reorder(newVars);

        assertEquals(2, exp.numVariables());
        assertEquals(expectedVars, exp.vars());
    }

    /*
     * Reorder an expression with 3 variables
     */
    @Test
    public final void testReorder3Variables() {
        SyntaxTree t = new SyntaxTree("0 1 and 2 and");
        BooleanStructure exp = this.constructorTest(t);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 1);
        newVars1.add(1, 0);
        newVars1.add(2, 2);

        Sequence<Integer> expectedVars1 = new Sequence1L<Integer>();
        expectedVars1.add(0, 1);
        expectedVars1.add(1, 0);
        expectedVars1.add(2, 2);

        exp.reorder(newVars1);

        assertEquals(3, exp.numVariables());
        assertEquals(expectedVars1, exp.vars());

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 2);
        newVars2.add(1, 0);
        newVars2.add(2, 1);

        Sequence<Integer> expectedVars2 = new Sequence1L<Integer>();
        expectedVars2.add(0, 2);
        expectedVars2.add(1, 0);
        expectedVars2.add(2, 1);

        exp.reorder(newVars2);

        assertEquals(3, exp.numVariables());
        assertEquals(expectedVars2, exp.vars());
    }

    /*
     * Secondary methods
     */

    /*
     * toString Tests
     */

    /*
     * String representation of a True expression
     */
    @Test
    public final void testToStringTrueExpression() {
        BooleanStructure exp = this.constructorTest();

        assertEquals("( {{}}, <> )", exp.toString());
    }

    /*
     * String representation of a False expression
     */
    @Test
    public final void testToStringFalseExpression() {
        BooleanStructure exp = this.constructorTest(false);

        assertEquals("( {}, <> )", exp.toString());
    }

    /*
     * String representation of single variable expressions
     */
    @Test
    public final void testToStringSingleVariableExpressions() {
        BooleanStructure exp1 = this.constructorTest(18);
        BooleanStructure exp2 = this.constructorTest(1);

        exp2.apply(UnaryOperator.NOT);

        assertEquals("( {{18}}, <18> )", exp1.toString());
        assertEquals("( {{}}, <1> )", exp2.toString());

    }

    /*
     * String representation of expressions with two variables
     */
    @Test
    public final void testToStringTwoVariableExpressions() {
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorTest(18);

        BooleanStructure exp3 = this.constructorTest(1);
        BooleanStructure exp4 = this.constructorTest(18);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 1);
        newVars1.add(1, 18);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 1);
        newVars2.add(1, 18);

        exp1.apply(BinaryOperator.OR, exp2, newVars1);
        exp3.apply(BinaryOperator.AND, exp4, newVars2);

        assertEquals("( {{18,1},{1},{18}}, <1,18> )", exp1.toString());

        assertEquals("( {{18,1}}, <1,18> )", exp3.toString());

    }

    /*
     * toStringTT Tests
     */

    /*
     * Truth table of True expression
     */
    @Test
    public final void testToStringTTTrueExpression() {
        BooleanStructure exp = this.constructorTest();

        String expected = "<>" + System.lineSeparator();
        expected += "| T";
        assertEquals(expected, exp.toStringTT());
    }

    /*
     * Truth table of False expression
     */
    @Test
    public final void testToStringTTFalseExpression() {
        BooleanStructure exp = this.constructorTest(false);

        String expected = "<>" + System.lineSeparator();
        expected += "| F";
        assertEquals(expected, exp.toStringTT());
    }

    /*
     * Truth table of single variable expressions
     */
    @Test
    public final void testToStringTTSingleVariableExpressions() {
        BooleanStructure exp1 = this.constructorTest(18);
        BooleanStructure exp2 = this.constructorTest(1);

        exp2.apply(UnaryOperator.NOT);

        String expected = "<18>" + System.lineSeparator();
        expected += "T | T" + System.lineSeparator();
        expected += "F | F";
        assertEquals(expected, exp1.toStringTT());

        expected = "<1>" + System.lineSeparator();
        expected += "T | F" + System.lineSeparator();
        expected += "F | T";
        assertEquals(expected, exp2.toStringTT());
    }

    /*
     * Truth table of expressions with two variables
     */
    @Test
    public final void testToStringTTTwoVariableExpressions() {
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorTest(18);

        BooleanStructure exp3 = this.constructorTest(1);
        BooleanStructure exp4 = this.constructorTest(18);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 1);
        newVars1.add(1, 18);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 1);
        newVars2.add(1, 18);

        exp1.apply(BinaryOperator.OR, exp2, newVars1);
        exp3.apply(BinaryOperator.AND, exp4, newVars2);

        String expected = "<1,18>" + System.lineSeparator();
        expected += "T T | T" + System.lineSeparator();
        expected += "T F | T" + System.lineSeparator();
        expected += "F T | T" + System.lineSeparator();
        expected += "F F | F";
        assertEquals(expected, exp1.toStringTT());

        expected = "<1,18>" + System.lineSeparator();
        expected += "T T | T" + System.lineSeparator();
        expected += "T F | F" + System.lineSeparator();
        expected += "F T | F" + System.lineSeparator();
        expected += "F F | F";
        assertEquals(expected, exp3.toStringTT());
    }

    /*
     * Equals on two True expressions
     */
    @Test
    public final void testEquals2TrueExpressions() {
        BooleanStructure exp1 = this.constructorTest();
        BooleanStructure exp2 = this.constructorTest(true);

        assertTrue(exp1.equals(exp2));
        assertTrue(exp2.equals(exp1));
    }

    /*
     * Equals on a True expression and False expression
     */
    @Test
    public final void testEquals2TrueExpressionFalseExpression() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorTest(false);

        assertFalse(exp1.equals(exp2));
        assertFalse(exp2.equals(exp1));
    }

    /*
     * Equals on two False expressions
     */
    @Test
    public final void testEquals2FalseExpressions() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorTest(false);

        assertTrue(exp1.equals(exp2));
        assertTrue(exp2.equals(exp1));
    }

    /*
     * Equals on a single variable expression and a True expression
     */
    @Test
    public final void testEqualsTrueExpressionSingleVariableExpression() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorTest(2);

        assertFalse(exp1.equals(exp2));
        assertFalse(exp2.equals(exp1));
    }

    /*
     * Equals on a single variable expression and the False expression
     */
    @Test
    public final void testEqualsFalseExpressionSingleVariableExpression() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorTest(2);

        assertFalse(exp1.equals(exp2));
        assertFalse(exp2.equals(exp1));
    }

    /*
     * Equals on two single variable expressions
     */
    @Test
    public final void testEquals2SingleVariableExpressions() {
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorTest(1);

        BooleanStructure exp3 = this.constructorTest(2);

        assertTrue(exp1.equals(exp2));
        assertTrue(exp2.equals(exp1));

        assertFalse(exp1.equals(exp3));
        assertFalse(exp3.equals(exp1));
    }

    /*
     * Equals on an expression with two variables and a True expression
     */
    @Test
    public final void testEqualsTwoVariableExpressionTrueExpression() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(2);

        Sequence<Integer> newVars = new Sequence1L<Integer>();
        newVars.add(0, 1);
        newVars.add(1, 2);

        exp2.apply(BinaryOperator.OR, exp3, newVars);

        assertFalse(exp1.equals(exp2));
        assertFalse(exp2.equals(exp1));

    }

    /*
     * Equals on an expression with two variables and a False expression
     */
    @Test
    public final void testEqualsTwoVariableExpressionFalseExpression() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(2);

        Sequence<Integer> newVars = new Sequence1L<Integer>();
        newVars.add(0, 1);
        newVars.add(1, 2);

        exp2.apply(BinaryOperator.OR, exp3, newVars);

        assertFalse(exp1.equals(exp2));
        assertFalse(exp2.equals(exp1));

    }

    /*
     * Equals on an expression with two variables and a single variable
     * expression
     */
    @Test
    public final void testEqualsTwoVariableExpressionSingleVariableExpression() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(0);
        BooleanStructure exp3 = this.constructorTest(2);

        Sequence<Integer> newVars = new Sequence1L<Integer>();
        newVars.add(0, 0);
        newVars.add(1, 2);

        exp2.apply(BinaryOperator.OR, exp3, newVars);

        assertFalse(exp1.equals(exp2));
        assertFalse(exp2.equals(exp1));

    }

    /*
     * Equals on 2 two variable expression (false due to them being differing
     * expressions)
     */
    @Test
    public final void testEquals2TwoVariableExpressionsFalseSameOrder() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(0);
        BooleanStructure exp4 = this.constructorTest(1);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 0);
        newVars1.add(1, 1);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 0);
        newVars2.add(1, 1);

        exp1.apply(BinaryOperator.OR, exp2, newVars1);
        exp3.apply(BinaryOperator.AND, exp4, newVars2);

        assertFalse(exp1.equals(exp3));
        assertFalse(exp3.equals(exp1));

    }

    /*
     * Equals on 2 two variable expressions (false due to different orders)
     */
    @Test
    public final void testEquals2TwoVariableExpressionsFalseDifferentOrder() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(0);
        BooleanStructure exp4 = this.constructorTest(1);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 0);
        newVars1.add(1, 1);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 1);
        newVars2.add(1, 0);

        exp1.apply(BinaryOperator.OR, exp2, newVars1);
        exp3.apply(BinaryOperator.OR, exp4, newVars2);

        assertFalse(exp1.equals(exp3));
        assertFalse(exp3.equals(exp1));

    }

    /*
     * Equals on two variable expressions that are equivalent
     */
    @Test
    public final void testEquals2TwoVariableExpressionsTrue() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(0);
        BooleanStructure exp4 = this.constructorTest(1);

        BooleanStructure exp5 = this.constructorTest(0);
        BooleanStructure exp6 = this.constructorTest(1);
        BooleanStructure exp7 = this.constructorTest(0);
        BooleanStructure exp8 = this.constructorTest(1);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 0);
        newVars1.add(1, 1);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 0);
        newVars2.add(1, 1);

        Sequence<Integer> newVars3 = new Sequence1L<Integer>();
        newVars3.add(0, 0);
        newVars3.add(1, 1);

        Sequence<Integer> newVars4 = new Sequence1L<Integer>();
        newVars4.add(0, 0);
        newVars4.add(1, 1);

        exp1.apply(BinaryOperator.OR, exp2, newVars1);
        exp3.apply(BinaryOperator.OR, exp4, newVars2);
        exp5.apply(BinaryOperator.AND, exp6, newVars3);
        exp7.apply(BinaryOperator.AND, exp8, newVars4);

        assertTrue(exp1.equals(exp3));
        assertTrue(exp3.equals(exp1));
        assertTrue(exp5.equals(exp7));
        assertTrue(exp7.equals(exp5));

    }

    /*
     * isSat Tests
     */

    /*
     * Satisfiability for a True expression
     */
    @Test
    public final void testIsSatTrueExpression() {
        BooleanStructure exp = this.constructorTest(true);

        assertTrue(exp.isSat());
    }

    /*
     * Satisfiability for a False expression
     */
    @Test
    public final void testIsSatFalseExpression() {
        BooleanStructure exp = this.constructorTest(false);

        assertFalse(exp.isSat());
    }

    /*
     * Satisfiability for a single variable expression which is satisfiable
     */
    @Test
    public final void testIsSatSingleVariableExpressionTrue() {
        BooleanStructure exp = this.constructorTest(300);

        assertTrue(exp.isSat());
    }

    /*
     * Satisfiability for a single variable expression which isn't satisfiable
     */
    @Test
    public final void testIsSatSingleVariableExpressionFalse() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorTest(300);

        Sequence<Integer> newVars = new Sequence1L<Integer>();
        newVars.add(0, 300);

        exp2.apply(BinaryOperator.AND, exp1, newVars);

        assertFalse(exp2.isSat());
    }

    /*
     * Satisfiability for a two variable expression which is satisfiable
     */
    @Test
    public final void testIsSatTwoVariableExpressionTrue() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);

        Sequence<Integer> newVars = new Sequence1L<Integer>();
        newVars.add(0, 0);
        newVars.add(1, 1);

        exp1.apply(BinaryOperator.AND, exp2, newVars);

        assertTrue(exp1.isSat());
    }

    /*
     * Satisfiability for a two variable expression which isn't satisfiable
     */
    @Test
    public final void testIsSatTwoVariableExpressionFalse() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(false);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 0);
        newVars1.add(1, 1);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 0);
        newVars2.add(1, 1);

        exp1.apply(BinaryOperator.AND, exp2, newVars1);
        exp1.apply(BinaryOperator.AND, exp3, newVars2);

        assertFalse(exp1.isSat());
    }

    /*
     * isValid tests
     */

    /*
     * Validity for the True Expression
     */
    @Test
    public final void testIsValidTrueExpression() {
        BooleanStructure exp = this.constructorTest(true);

        assertTrue(exp.isValid());
    }

    /*
     * Validity for the False Expression
     */
    @Test
    public final void testIsValidFalseExpression() {
        BooleanStructure exp = this.constructorTest(false);

        assertFalse(exp.isValid());
    }

    /*
     * Validity for a single variable expression which is valid
     */
    @Test
    public final void testIsValidSingleVariableExpressionTrue() {
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorTest(true);

        Sequence<Integer> newVars = new Sequence1L<Integer>();
        newVars.add(0, 1);

        exp1.apply(BinaryOperator.OR, exp2, newVars);

        assertTrue(exp1.isValid());
    }

    /*
     * Validity for a single variable expression which isn't valid
     */
    @Test
    public final void testIsValidSingleVariableExpressionFalse() {
        BooleanStructure exp = this.constructorTest(1);

        assertFalse(exp.isValid());
    }

    /*
     * Validity for a two variable expression which is valid
     */
    @Test
    public final void testIsValidTwoVariableExpressionTrue() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(true);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 0);
        newVars1.add(1, 1);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 0);
        newVars2.add(1, 1);

        exp1.apply(BinaryOperator.AND, exp2, newVars1);
        exp1.apply(BinaryOperator.OR, exp3, newVars2);

        assertTrue(exp1.isValid());
    }

    /*
     * Validity for a two variable expression which isn't valid
     */

    @Test
    public final void testIsValidTwoVariableExpressionFalse() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);

        Sequence<Integer> newVars = new Sequence1L<Integer>();
        newVars.add(0, 0);
        newVars.add(1, 1);

        exp1.apply(BinaryOperator.AND, exp2, newVars);

        assertFalse(exp1.isValid());
    }

    /*
     * Validity for a two variable expression which is valid
     */
    @Test
    public final void testIsValidManyVariableExpressionTrue() {
        BooleanStructure exp = this.constructorTest();
        Set<Integer> variables = new Set2<Integer>();
        for (int i = 0; i < 10; i++) {
            variables.add(i);
        }

        exp.expand(variables);

        assertTrue(exp.isValid());
    }

    /*
     * numVariables Tests
     */

    /*
     * numVariables for expressions with 0 variables
     */
    @Test
    public final void testNumVariablesNoVariableExpressions() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorTest(false);

        assertEquals(0, exp1.numVariables());
        assertEquals(0, exp2.numVariables());
    }

    /*
     * numVariables for expressions with 1 variable
     */
    @Test
    public final void testNumVariablesSingleVariableExpressions() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);

        exp2.apply(UnaryOperator.NOT);

        assertEquals(1, exp1.numVariables());
        assertEquals(1, exp2.numVariables());
    }

    /*
     * numVariables for expressions with 2 variables
     */
    @Test
    public final void testNumVariablesTwoVariableExpressions() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);

        BooleanStructure exp3 = this.constructorTest(2);
        BooleanStructure exp4 = this.constructorTest(3);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 1);
        newVars1.add(0, 0);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 2);
        newVars2.add(0, 3);

        exp1.apply(BinaryOperator.AND, exp2, newVars1);
        exp3.apply(BinaryOperator.AND, exp4, newVars2);

        assertEquals(2, exp1.numVariables());
        assertEquals(2, exp3.numVariables());
    }

    /*
     * Equivalent Tests
     */

    /*
     * Equivalent on two True Expressions
     */
    @Test
    public final void testEquivalent2TrueExpressions() {
        BooleanStructure exp1 = this.constructorTest();
        BooleanStructure exp2 = this.constructorTest(true);

        assertTrue(exp1.isEquivalent(exp2));
        assertTrue(exp2.isEquivalent(exp1));
    }

    /*
     * Equivalent on a True Expression and False Expression
     */
    @Test
    public final void testEquivalentTrueExpressionFalseExpression() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorTest(false);

        assertFalse(exp1.isEquivalent(exp2));
        assertFalse(exp2.isEquivalent(exp1));
    }

    /*
     * Equivalent on two False Expressions
     */
    @Test
    public final void testEquivalent2FalseExpressions() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorTest(false);

        assertTrue(exp1.isEquivalent(exp2));
        assertTrue(exp2.isEquivalent(exp1));
    }

    /*
     * Equivalent on Single Variable Expressions
     */
    @Test
    public final void testEquivalent2SingleVariableExpressions() {
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorTest(1);

        BooleanStructure exp3 = this.constructorTest(2);

        assertTrue(exp1.isEquivalent(exp2));
        assertTrue(exp2.isEquivalent(exp1));

        assertFalse(exp1.isEquivalent(exp3));
        assertFalse(exp3.isEquivalent(exp1));
    }

    /*
     * Equivalent on Two Variable Expressions with the same ordering (false due
     * to them being different expressions)
     */
    @Test
    public final void testEquivalent2TwoVariableExpressionsFalseSameOrder() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(0);
        BooleanStructure exp4 = this.constructorTest(1);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 0);
        newVars1.add(1, 1);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 0);
        newVars2.add(1, 1);

        exp1.apply(BinaryOperator.OR, exp2, newVars1);
        exp3.apply(BinaryOperator.AND, exp4, newVars2);

        assertFalse(exp1.isEquivalent(exp3));
        assertFalse(exp3.isEquivalent(exp1));
    }

    /*
     * Equivalent on Two Variable Expressions with the same ordering
     */
    @Test
    public final void testEquivalent2TwoVariableExpressionsSameOrder() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(0);
        BooleanStructure exp4 = this.constructorTest(1);

        BooleanStructure exp5 = this.constructorTest(0);
        BooleanStructure exp6 = this.constructorTest(1);
        BooleanStructure exp7 = this.constructorTest(0);
        BooleanStructure exp8 = this.constructorTest(1);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 0);
        newVars1.add(1, 1);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 0);
        newVars2.add(1, 1);

        Sequence<Integer> newVars3 = new Sequence1L<Integer>();
        newVars3.add(0, 0);
        newVars3.add(1, 1);

        Sequence<Integer> newVars4 = new Sequence1L<Integer>();
        newVars4.add(0, 0);
        newVars4.add(1, 1);

        exp1.apply(BinaryOperator.OR, exp2, newVars1);
        exp3.apply(BinaryOperator.OR, exp4, newVars2);
        exp5.apply(BinaryOperator.AND, exp6, newVars3);
        exp7.apply(BinaryOperator.AND, exp8, newVars4);

        assertTrue(exp1.isEquivalent(exp3));
        assertTrue(exp3.isEquivalent(exp1));
        assertTrue(exp5.isEquivalent(exp7));
        assertTrue(exp7.isEquivalent(exp5));
    }

    /*
     * Equivalent on Two Variable Expressions with different ordering
     */
    @Test
    public final void testEquivalent2TwoVariableExpressionsDifferentOrder() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(0);
        BooleanStructure exp4 = this.constructorTest(1);

        BooleanStructure exp5 = this.constructorTest(0);
        BooleanStructure exp6 = this.constructorTest(1);
        BooleanStructure exp7 = this.constructorTest(0);
        BooleanStructure exp8 = this.constructorTest(1);

        Sequence<Integer> newVars1A = new Sequence1L<Integer>();
        newVars1A.add(0, 0);
        newVars1A.add(1, 1);

        Sequence<Integer> newVars1B = new Sequence1L<Integer>();
        newVars1B.add(0, 0);
        newVars1B.add(1, 1);

        Sequence<Integer> newVars2A = new Sequence1L<Integer>();
        newVars2A.add(0, 1);
        newVars2A.add(1, 0);

        Sequence<Integer> newVars2B = new Sequence1L<Integer>();
        newVars2B.add(0, 1);
        newVars2B.add(1, 0);

        exp1.apply(BinaryOperator.OR, exp2, newVars1A);
        exp3.apply(BinaryOperator.OR, exp4, newVars2A);
        exp5.apply(BinaryOperator.AND, exp6, newVars1B);
        exp7.apply(BinaryOperator.AND, exp8, newVars2B);

        assertTrue(exp1.isEquivalent(exp3));
        assertTrue(exp3.isEquivalent(exp1));
        assertTrue(exp5.isEquivalent(exp7));
        assertTrue(exp7.isEquivalent(exp5));

    }

    /*
     * Equivalent on two structures with different vars
     */
    @Test
    public final void testEquivalent2TwoVariableExpressionsDifferentVars() {
        SyntaxTree t1 = new SyntaxTree("0 1 or");
        SyntaxTree t2 = new SyntaxTree("0 1 or");

        BooleanStructure exp1 = this.constructorTest(t1);
        BooleanStructure exp2 = this.constructorTest(t2);

        Set<Integer> newVars = new Set2<Integer>();
        newVars.add(2);

        exp1.expand(newVars);

        assertTrue(exp1.isEquivalent(exp2));

    }

    /*
     * CopyFrom Tests
     */
    @Test
    public final void testCopyFromSingleVariableExpression() {
        BooleanStructure x = this.constructorTest(18);
        BooleanStructure old = this.constructorTest(18);

        BooleanStructure x2 = x.newInstance();
        x2.copyFrom(x);

        //preserves x
        assertEquals(old, x);
        //x2 = x
        assertEquals(x, x2);

        x.negate();

        //x2 has not changed
        assertEquals(old, x2);
        //x has changed
        assertFalse(x.equals(old));
    }

    @Test
    public final void testCopyFromTwoVariableExpression() {
        SyntaxTree t = new SyntaxTree("2 3 or");

        BooleanStructure x = this.constructorTest(t);
        BooleanStructure old = this.constructorTest(t);

        BooleanStructure x2 = x.newInstance();
        x2.copyFrom(x);

        //preserves x
        assertEquals(old, x);
        //x2 = x
        assertEquals(x, x2);

        x.negate();

        //x2 has not changed
        assertEquals(old, x2);
        //x has changed
        assertFalse(x.equals(old));
    }

    @Test
    public final void testCopyFromThreeVariableExpression() {
        SyntaxTree t = new SyntaxTree("2 3 or 1 and");

        BooleanStructure x = this.constructorTest(t);
        BooleanStructure old = this.constructorTest(t);

        BooleanStructure x2 = x.newInstance();
        x2.copyFrom(x);

        //preserves x
        assertEquals(old, x);
        //x2 = x
        assertEquals(x, x2);

        x.negate();

        //x2 has not changed
        assertEquals(old, x2);
        //x has changed
        assertFalse(x.equals(old));
    }

    @Test
    public final void testCopyFromFalseExpression() {
        SyntaxTree t = new SyntaxTree("1 1 not and");

        BooleanStructure x = this.constructorTest(t);
        BooleanStructure old = this.constructorTest(t);

        BooleanStructure x2 = x.newInstance();
        x2.copyFrom(x);

        //preserves x
        assertEquals(old, x);
        //x2 = x
        assertEquals(x, x2);

        x.negate();

        //x2 has not changed
        assertEquals(old, x2);
        //x has changed
        assertFalse(x.equals(old));
    }

    /*
     * Negate (without specified ordering) Tests
     */

    /*
     * Perform the negation operation on a single variable expression without a
     * specified ordering
     */
    @Test
    public final void testNegateSingleVariableExpression() {
        BooleanStructure exp = this.constructorTest(18);

        Set<Integer> assignmentsOneVar = new Set2<Integer>();
        assignmentsOneVar.add(18);

        Sequence<Integer> newVars = new Sequence1L<Integer>();
        newVars.add(0, 18);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 18);

        exp.negate();

        assertEquals(1, exp.numVariables());
        assertEquals(expectedVars, exp.vars());
        assertFalse(exp.evaluate(assignmentsOneVar));
        assertTrue(exp.evaluate(createSet()));
    }

    /*
     * Perform the negation operation on two variable expressions without
     * specified ordering
     */
    @Test
    public final void testNegateTwoVariableExpressions() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);

        BooleanStructure exp3 = this.constructorTest(0);
        BooleanStructure exp4 = this.constructorTest(1);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 0);
        newVars1.add(1, 1);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 0);
        newVars2.add(1, 1);

        Sequence<Integer> expectedVars = new Sequence1L<Integer>();
        expectedVars.add(0, 0);
        expectedVars.add(1, 1);

        exp1.apply(BinaryOperator.AND, exp2, newVars1);
        exp3.apply(BinaryOperator.OR, exp4, newVars2);

        exp1.negate();
        exp3.negate();

        assertEquals(2, exp1.numVariables());
        assertEquals(expectedVars, exp1.vars());
        assertFalse(exp1.evaluate(createSet(0, 1)));
        assertTrue(exp1.evaluate(createSet(1)));
        assertTrue(exp1.evaluate(createSet(0)));
        assertTrue(exp1.evaluate(createSet()));

        assertEquals(2, exp3.numVariables());
        assertEquals(expectedVars, exp3.vars());
        assertFalse(exp3.evaluate(createSet(0, 1)));
        assertFalse(exp3.evaluate(createSet(1)));
        assertFalse(exp3.evaluate(createSet(0)));
        assertTrue(exp3.evaluate(createSet()));
    }

    /*
     * Conj (without specified ordering) tests
     */

    /*
     * Perform the conjunction operation on 2 single variable expressions
     * without a specified ordering
     */
    @Test
    public final void testConj2SingleVariableExpressions() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);

        Sequence<Integer> vars1 = new Sequence1L<Integer>();
        vars1.add(0, 0);

        Sequence<Integer> vars2 = new Sequence1L<Integer>();
        vars2.add(0, 1);

        exp1.conj(exp2);

        assertEquals(2, exp1.numVariables());
        assertTrue(IS_COMPATIBLE_ORDERING(exp1.vars(), vars1));
        assertTrue(IS_COMPATIBLE_ORDERING(exp1.vars(), vars2));

        // ( { 0, 1 }, { } )
        assertTrue(exp1.evaluate(createSet(0, 1)));
        // ( { 0 }, { 1 } )
        assertFalse(exp1.evaluate(createSet(0)));
        // ( { 1 }, { 0 } )
        assertFalse(exp1.evaluate(createSet(1)));
        // ( { }, { 0, 1 } )
        assertFalse(exp1.evaluate(createSet()));
    }

    /*
     * Disj (without specified ordering) tests
     */

    /*
     * Perform the disjunction operation on 2 single variable expressions
     * without a specified ordering
     */
    @Test
    public final void testDisj2SingleVariableExpressions() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);

        Sequence<Integer> vars1 = new Sequence1L<Integer>();
        vars1.add(0, 0);

        Sequence<Integer> vars2 = new Sequence1L<Integer>();
        vars2.add(0, 1);

        exp1.disj(exp2);

        assertEquals(2, exp1.numVariables());
        assertTrue(IS_COMPATIBLE_ORDERING(exp1.vars(), vars1));
        assertTrue(IS_COMPATIBLE_ORDERING(exp1.vars(), vars2));

        // ( { 0, 1 }, { } )
        assertTrue(exp1.evaluate(createSet(0, 1)));
        // ( { 0 }, { 1 } )
        assertTrue(exp1.evaluate(createSet(0)));
        // ( { 1 }, { 0 } )
        assertTrue(exp1.evaluate(createSet(1)));
        // ( { }, { 0, 1 } )
        assertFalse(exp1.evaluate(createSet()));
    }

    /*
     * hashCode Tests
     */

    @Test
    public final void testHashCodeTrueExpression() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorTest(true);
        BooleanStructure exp3 = this.constructorTest();
        BooleanStructure exp4 = this.constructorTest(false);

        assertEquals(exp1.hashCode(), exp2.hashCode());
        assertEquals(exp2.hashCode(), exp3.hashCode());
        assertFalse(exp1.hashCode() == exp4.hashCode());

    }

    @Test
    public final void testHashCodeFalseExpression() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorTest(false);
        BooleanStructure exp3 = this.constructorTest(true);

        assertEquals(exp1.hashCode(), exp2.hashCode());
        assertFalse(exp1.hashCode() == exp3.hashCode());

    }

    @Test
    public final void testHashCodeSingleVariableExpression() {
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(true);
        BooleanStructure exp4 = this.constructorTest(false);

        assertEquals(exp1.hashCode(), exp2.hashCode());
        assertFalse(exp1.hashCode() == exp3.hashCode());
        assertFalse(exp1.hashCode() == exp4.hashCode());

    }

    @Test
    public final void testHashCodeTwoVariableExpression() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(0);
        BooleanStructure exp4 = this.constructorTest(1);
        BooleanStructure exp5 = this.constructorTest(true);
        BooleanStructure exp6 = this.constructorTest(false);

        exp1.conj(exp2);
        exp3.conj(exp4);

        assertEquals(exp1.hashCode(), exp3.hashCode());
        assertFalse(exp1.hashCode() == exp5.hashCode());
        assertFalse(exp1.hashCode() == exp6.hashCode());

    }

    @Test
    public final void testHashCodeManyVariableExpression() {
        BooleanStructure[] exp1 = new BooleanStructure[8];
        BooleanStructure[] exp2 = new BooleanStructure[8];
        for (int i = 0; i < 8; i++) {
            exp1[i] = this.constructorTest(i);
            exp2[i] = this.constructorTest(i);
        }
        for (int i = 1; i < 8; i++) {
            exp1[0].conj(exp1[i]);
            exp2[0].conj(exp2[i]);
        }
        BooleanStructure exp3 = this.constructorTest(true);
        BooleanStructure exp4 = this.constructorTest(false);

        assertEquals(exp1[0].hashCode(), exp2[0].hashCode());
        assertFalse(exp1[0].hashCode() == exp3.hashCode());
        assertFalse(exp1[0].hashCode() == exp4.hashCode());

    }

    @Test
    public final void testAutomaticOrderGenerationCommonElementAtEndOfBoth() {
        SyntaxTree t1 = new SyntaxTree("2 3 and");
        SyntaxTree t2 = new SyntaxTree("1 3 or");

        BooleanStructure exp1 = this.constructorTest(t1);
        BooleanStructure exp2 = this.constructorTest(t2);

        exp1.conj(exp2);

        assertTrue(exp1.vars().entry(0) == 2 || exp1.vars().entry(1) == 2);
        assertTrue(exp1.vars().entry(0) == 1 || exp1.vars().entry(1) == 1);
        assertTrue(exp1.vars().entry(2) == 3);
    }

    @Test
    public final void testAutomaticOrderGenerationCommonElementAtFrontOfBoth() {
        SyntaxTree t1 = new SyntaxTree("3 2 and");
        SyntaxTree t2 = new SyntaxTree("3 1 or");

        BooleanStructure exp1 = this.constructorTest(t1);
        BooleanStructure exp2 = this.constructorTest(t2);

        Sequence<Integer> vars1 = seqCopy(exp1.vars());
        Sequence<Integer> vars2 = seqCopy(exp2.vars());

        Set<Integer> orderElements = createSet(1, 2, 3);

        exp1.conj(exp2);

        assertTrue(IS_COMPATIBLE_ORDERING(exp1.vars(), vars1));
        assertTrue(IS_COMPATIBLE_ORDERING(exp1.vars(), vars2));
        assertTrue(setEquivalent(exp1.vars(), orderElements));

    }

    @Test
    public final void testAutomaticOrderGenerationCommonElementAtOppositeEnds1() {
        SyntaxTree t1 = new SyntaxTree("1 2 and 0 18 and and");
        SyntaxTree t2 = new SyntaxTree("18 3 and 300 or");

        BooleanStructure exp1 = this.constructorTest(t1);
        BooleanStructure exp2 = this.constructorTest(t2);

        Sequence<Integer> vars1 = seqCopy(exp1.vars());
        Sequence<Integer> vars2 = seqCopy(exp2.vars());

        Set<Integer> orderElements = createSet(0, 1, 2, 3, 18, 300);

        exp1.conj(exp2);

        assertTrue(IS_COMPATIBLE_ORDERING(exp1.vars(), vars1));
        assertTrue(IS_COMPATIBLE_ORDERING(exp1.vars(), vars2));
        assertTrue(setEquivalent(exp1.vars(), orderElements));

    }

    @Test
    public final void testAutomaticOrderGenerationCommonElementAtOppositeEnds2() {
        SyntaxTree t1 = new SyntaxTree("18 3 and 300 or");
        SyntaxTree t2 = new SyntaxTree("1 2 and 0 18 and and");

        BooleanStructure exp1 = this.constructorTest(t1);
        BooleanStructure exp2 = this.constructorTest(t2);

        Sequence<Integer> vars1 = seqCopy(exp1.vars());
        Sequence<Integer> vars2 = seqCopy(exp2.vars());

        Set<Integer> orderElements = createSet(0, 1, 2, 3, 18, 300);

        exp1.conj(exp2);

        assertTrue(IS_COMPATIBLE_ORDERING(exp1.vars(), vars1));
        assertTrue(IS_COMPATIBLE_ORDERING(exp1.vars(), vars2));
        assertTrue(setEquivalent(exp1.vars(), orderElements));

    }

    @Test
    public final void testAutomaticOrderGenerationTwoCommonElements() {
        SyntaxTree t1 = new SyntaxTree("0 3 and 1 300 or or");
        SyntaxTree t2 = new SyntaxTree("2 0 and 18 3 and and");

        BooleanStructure exp1 = this.constructorTest(t1);
        BooleanStructure exp2 = this.constructorTest(t2);

        Sequence<Integer> vars1 = seqCopy(exp1.vars());
        Sequence<Integer> vars2 = seqCopy(exp2.vars());

        Set<Integer> orderElements = createSet(0, 1, 2, 3, 18, 300);

        exp1.conj(exp2);

        assertTrue(IS_COMPATIBLE_ORDERING(exp1.vars(), vars1));
        assertTrue(IS_COMPATIBLE_ORDERING(exp1.vars(), vars2));
        assertTrue(setEquivalent(exp1.vars(), orderElements));
    }

    @Test
    public final void testAutomaticOrderGenerationThreeCommonElements1() {
        SyntaxTree t1 = new SyntaxTree("0 1 and 300 2 and 3 or or");
        SyntaxTree t2 = new SyntaxTree("0 2 and 18 3 and and");

        BooleanStructure exp1 = this.constructorTest(t1);
        BooleanStructure exp2 = this.constructorTest(t2);

        Sequence<Integer> vars1 = seqCopy(exp1.vars());
        Sequence<Integer> vars2 = seqCopy(exp2.vars());

        Set<Integer> orderElements = createSet(0, 1, 2, 3, 18, 300);

        exp1.conj(exp2);

        assertTrue(IS_COMPATIBLE_ORDERING(exp1.vars(), vars1));
        assertTrue(IS_COMPATIBLE_ORDERING(exp1.vars(), vars2));
        assertTrue(setEquivalent(exp1.vars(), orderElements));
    }

    @Test
    public final void testAutomaticOrderGenerationThreeCommonElements2() {
        SyntaxTree t1 = new SyntaxTree("0 2 and 18 3 and and");
        SyntaxTree t2 = new SyntaxTree("0 1 and 300 2 and 3 or or");

        BooleanStructure exp1 = this.constructorTest(t1);
        BooleanStructure exp2 = this.constructorTest(t2);

        Sequence<Integer> vars1 = seqCopy(exp1.vars());
        Sequence<Integer> vars2 = seqCopy(exp2.vars());

        Set<Integer> orderElements = createSet(0, 1, 2, 3, 18, 300);

        exp1.conj(exp2);

        assertTrue(IS_COMPATIBLE_ORDERING(exp1.vars(), vars1));
        assertTrue(IS_COMPATIBLE_ORDERING(exp1.vars(), vars2));
        assertTrue(setEquivalent(exp1.vars(), orderElements));
    }

    @Test
    public final void testSatAssignmentTrueStructure() {
        BooleanStructure exp1 = this.constructorTest();

        assertTrue(exp1.evaluate(exp1.satAssignment()));
    }

    @Test
    public final void testSatAssignmentSingleVarStructure() {
        BooleanStructure exp1 = this.constructorTest(1);

        assertTrue(exp1.evaluate(exp1.satAssignment()));
    }

    @Test
    public final void testSatAssignmentTwoVarStructure() {
        SyntaxTree st = new SyntaxTree("1 2 and");
        BooleanStructure exp1 = this.constructorTest(st);

        assertTrue(exp1.evaluate(exp1.satAssignment()));
    }

    @Test
    public final void testSatAssignmentTwoVarStructureNegation() {
        SyntaxTree st = new SyntaxTree("1 not 2 not and");
        BooleanStructure exp1 = this.constructorTest(st);

        assertTrue(exp1.evaluate(exp1.satAssignment()));
    }

    @Test
    public final void testSatAssignmentFiveVarStructure() {
        SyntaxTree st = new SyntaxTree("1 not 2 3 and 4 or and 5 or");
        BooleanStructure exp1 = this.constructorTest(st);

        assertTrue(exp1.evaluate(exp1.satAssignment()));
    }
}
