package components.booleanstructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.set.Set;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class BooleanStructureKernelTest
        extends BooleanStructureTestUtilities {

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

    /**
     * Invokes the appropriate BooleanStructure constructor for the reference
     * implementation and returns the result.
     *
     * @return the new BooleanStructure
     * @ensures constructorTest = ( ( { ( { }, { } ) }, { } ), <> )
     */
    protected abstract BooleanStructure constructorRef();

    /**
     * Invokes the appropriate BooleanStructure constructor for the reference
     * implementation and returns the result.
     *
     * @param b
     *            boolean to initialize from
     * @return the new BooleanStructure
     * @ensures constructorTest = ( ( { ( { }, { } ) }, { } ), <> ) if b
     *          constructorTest = ( ( { }, { } ), <> ) otherwise
     */
    protected abstract BooleanStructure constructorRef(boolean b);

    /**
     * Invokes the appropriate BooleanStructure constructor for the reference
     * implementation and returns the result.
     *
     * @param i
     *            int to initialize from
     * @return the new BooleanStructure
     * @requires i >= 0
     * @ensures constructorTest = ( ( { ( { i }, { } ) }, { i } ), <i> )
     */
    protected abstract BooleanStructure constructorRef(int i);

    /**
     * Invokes the appropriate BooleanStructure constructor for the reference
     * implementation and returns the result.
     *
     * @param t
     *            SyntaxTree to initialize from
     * @return the new BooleanStructure
     * @requires TODO
     * @ensures TODO
     */
    protected abstract BooleanStructure constructorRef(SyntaxTree t);

    /*
     * Constructor Tests
     */

    /*
     * Empty Constructor - Always evaluates to true
     */
    @Test
    public final void testEmptyConstructor() {
        BooleanStructure exp1 = this.constructorTest();
        BooleanStructure exp2 = this.constructorRef();

        assertEquals(exp2, exp1);
    }

    /*
     * True Constructor - Always evaluates to true
     */
    @Test
    public final void testBooleanConstructorTrue() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorRef(true);

        assertEquals(exp2, exp1);
    }

    /*
     * True Constructor - Always evaluates to false
     */
    @Test
    public final void testBooleanConstructorFalse() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorRef(false);

        assertEquals(exp2, exp1);
    }

    /*
     * Integer Constructor
     */
    @Test
    public final void testIntegerConstructor() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorRef(0);

        assertEquals(exp2, exp1);
    }

    /*
     * SyntaxTree constructor
     */

    /*
     * True Expression
     */
    @Test
    public final void testTreeConstructorTrueExpression() {
        SyntaxTree tTest = new SyntaxTree("T");
        SyntaxTree tRef = new SyntaxTree("T");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        assertEquals(exp2, exp1);
    }

    /*
     * False Expression
     */
    @Test
    public final void testTreeConstructorFalseExpression() {
        SyntaxTree tTest = new SyntaxTree("F");
        SyntaxTree tRef = new SyntaxTree("F");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        assertEquals(exp2, exp1);
    }

    /*
     * Single Variable Expression with a T - ( 0 and T )
     */
    @Test
    public final void testTreeConstructorTrueAndFalse() {
        SyntaxTree tTest = new SyntaxTree("T F and");
        SyntaxTree tRef = new SyntaxTree("T F and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        assertEquals(exp2, exp1);
    }

    /*
     * Single Variable Expression
     */
    @Test
    public final void testTreeConstructorSingleVariableExpression() {
        SyntaxTree tTest = new SyntaxTree("0");
        SyntaxTree tRef = new SyntaxTree("0");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        assertEquals(exp2, exp1);
    }

    /*
     * Single variable expression with a unary operator - not( 0 )
     */
    @Test
    public final void testTreeConstructorSingleVariableExpressionNot() {
        SyntaxTree tTest = new SyntaxTree("0 not");
        SyntaxTree tRef = new SyntaxTree("0 not");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        assertEquals(exp2, exp1);
    }

    /*
     * Single Variable Expression with a T - ( 0 and T )
     */
    @Test
    public final void testTreeConstructorSingleVariableAndTrue() {
        SyntaxTree tTest = new SyntaxTree("0 T and");
        SyntaxTree tRef = new SyntaxTree("0 T and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        assertEquals(exp2, exp1);
    }

    /*
     * Two variable expression (AND) - ( 0 and 1 )
     */
    @Test
    public final void testTreeConstructorTwoVariableExpressionAnd() {
        SyntaxTree tTest = new SyntaxTree("0 1 and");
        SyntaxTree tRef = new SyntaxTree("0 1 and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        assertEquals(exp2, exp1);
    }

    /*
     * Two variable expression - ( 0 or 1 )
     */
    @Test
    public final void testTreeConstructorTwoVariableExpressionOr() {
        SyntaxTree tTest = new SyntaxTree("0 1 or");
        SyntaxTree tRef = new SyntaxTree("0 1 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        assertEquals(exp2, exp1);
    }

    /*
     * Two variable expression with a negation - not( ( 0 or 1 ) )
     */
    @Test
    public final void testTreeConstructorTwoVariableExpressionOrNot() {
        SyntaxTree tTest = new SyntaxTree("0 1 or not");
        SyntaxTree tRef = new SyntaxTree("0 1 or not");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        assertEquals(exp2, exp1);
    }

    /*
     * Three variable expressions with and - ( ( 0 and 1 ) and 2 )
     */
    @Test
    public final void testTreeConstructorThreeVariableExpressionAnds() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 and");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        assertEquals(exp2, exp1);
    }

    /*
     * Three variable expression with and, negation - not( ( ( 0 and 1 ) and 2 )
     * )
     */
    @Test
    public final void testTreeConstructorThreeVariableExpressionAndsNot() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 and not");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 and not");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        assertEquals(exp2, exp1);
    }

    /*
     * Four variable expression with ands/ors - ( ( 0 and 1 ) and ( 2 or 3 ) )
     */
    @Test
    public final void testTreeConstructorFourVariableExpressionAndsOrs() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 3 or and");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 3 or and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        assertEquals(exp2, exp1);
    }

    /*
     * Tree constructor on a six variable expression
     */
    @Test
    public final void testTreeConstructorSixVariableExpression() {
        SyntaxTree tTest = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");
        SyntaxTree tRef = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        assertEquals(exp2, exp1);
    }

    /*
     * Kernel Methods
     */

    /*
     * Evaluation of the "empty" constructor based on multiple assignments of
     * varying sizes
     */
    @Test
    public final void testEvaluateEmptyExpression() {
        BooleanStructure exp1 = this.constructorTest();
        BooleanStructure exp2 = this.constructorRef();

        // ( { }, { } )
        assertTrue(exp2.evaluate(createSet()) == exp1.evaluate(createSet()));
        // ( { 0 }, { } )
        assertTrue(exp2.evaluate(createSet(0)) == exp1.evaluate(createSet(0)));
        // ( { }, { 0 } )
        assertTrue(exp2.evaluate(createSet()) == exp1.evaluate(createSet()));
        // ( { 0 }, { 1, 2 } )
        assertTrue(exp2.evaluate(createSet(0)) == exp1.evaluate(createSet(0)));
        // ( { 1, 2 }, { 0 } )
        assertTrue(exp2.evaluate(createSet(1, 2)) == exp1
                .evaluate(createSet(1, 2)));

        assertEquals(exp2, exp1);
    }

    /*
     * Evaluation of the "true" expression based on multiple assignments of
     * varying sizes
     */
    @Test
    public final void testEvaluateTrueExpression() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorRef(true);

        // ( { }, { } )
        assertTrue(exp2.evaluate(createSet()) == exp1.evaluate(createSet()));
        // ( { 0 }, { } )
        assertTrue(exp2.evaluate(createSet(0)) == exp1.evaluate(createSet(0)));
        // ( { }, { 0 } )
        assertTrue(exp2.evaluate(createSet()) == exp1.evaluate(createSet()));
        // ( { 0 }, { 1, 2 } )
        assertTrue(exp2.evaluate(createSet(0)) == exp1.evaluate(createSet(0)));
        // ( { 1, 2 }, { 0 } )
        assertTrue(exp2.evaluate(createSet(1, 2)) == exp1
                .evaluate(createSet(1, 2)));

        assertEquals(exp2, exp1);
    }

    /*
     * Evaluation of the "true" expression based on multiple assignments of
     * varying sizes
     */
    @Test
    public final void testEvaluateFalseExpression() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorRef(false);

        // ( { }, { } )
        assertTrue(exp2.evaluate(createSet()) == exp1.evaluate(createSet()));
        // ( { 0 }, { } )
        assertTrue(exp2.evaluate(createSet(0)) == exp1.evaluate(createSet(0)));
        // ( { }, { 0 } )
        assertTrue(exp2.evaluate(createSet()) == exp1.evaluate(createSet()));
        // ( { 0 }, { 1, 2 } )
        assertTrue(exp2.evaluate(createSet(0)) == exp1.evaluate(createSet(0)));
        // ( { 1, 2 }, { 0 } )
        assertTrue(exp2.evaluate(createSet(1, 2)) == exp1
                .evaluate(createSet(1, 2)));

        assertEquals(exp2, exp1);
    }

    /*
     * Evaluation of a single variable expression based on an assignment
     * containing the variable in the expression
     */
    @Test
    public final void testEvaulateOneVariableExpressionOneAssignment() {
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorRef(1);

        // ( { 1 }, { } )
        assertTrue(exp2.evaluate(createSet(1)) == exp1.evaluate(createSet(1)));
        // ( { }, { 1 } )
        assertTrue(exp2.evaluate(createSet()) == exp1.evaluate(createSet()));

        assertEquals(exp2, exp1);
    }

    /*
     * Evaluation of a single variable expression based on multiple assignments
     * of varying sizes, each containing the variable in the expression as well
     * as other variables not part of the expression
     */
    @Test
    public final void testEvaluateOneVariableExpressionMultipleAssignments() {
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorRef(1);

        // ( { 1, 2 }, { } )
        assertTrue(exp2.evaluate(createSet(1, 2)) == exp1
                .evaluate(createSet(1, 2)));
        // ( { }, { 1, 2 } )
        assertTrue(exp2.evaluate(createSet()) == exp1.evaluate(createSet()));
        // ( { 2, 18 }, { 1 } )
        assertTrue(exp2.evaluate(createSet(2, 18)) == exp1
                .evaluate(createSet(2, 18)));
        // ( { 2, 300 }, { 1, 18 }
        assertTrue(exp2.evaluate(createSet(2, 300)) == exp1
                .evaluate(createSet(2, 300)));
        // ( { 0, 1, 2 }, { 18, 300 } )
        assertTrue(exp2.evaluate(createSet(0, 1, 2)) == exp1
                .evaluate(createSet(0, 1, 2)));
    }

    /*
     * Evaluation of a two variable expression based on multiple assignments
     */
    @Test
    public final void testEvaulateTwoVariableExpressionMultipleAssignmentsAnd() {
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorTest(3);

        BooleanStructure exp3 = this.constructorRef(1);
        BooleanStructure exp4 = this.constructorRef(3);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 1);
        newVars1.add(1, 3);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 1);
        newVars2.add(1, 3);

        exp1.apply(BinaryOperator.AND, exp2, newVars1);
        exp3.apply(BinaryOperator.AND, exp4, newVars2);

        // ( { 1, 3 }, { } )
        assertTrue(exp3.evaluate(createSet(1, 3)) == exp1
                .evaluate(createSet(1, 3)));
        // ( { 1, 3 }, { 2, 18 } )
        assertTrue(exp3.evaluate(createSet(1, 3)) == exp1
                .evaluate(createSet(1, 3)));
        // ( { 1 }, { 3 } )
        assertTrue(exp3.evaluate(createSet(1)) == exp1.evaluate(createSet(1)));
        // ( { 3 }, { 1 } )
        assertTrue(exp3.evaluate(createSet(1)) == exp1.evaluate(createSet(3)));
        // ( { 3, 18 }, { 1, 300 } )
        assertTrue(
                exp3.evaluate(createSet(1)) == exp1.evaluate(createSet(3, 18)));
    }

    /*
     * Evaluation of a two variable expression based on multiple assignments
     */
    @Test
    public final void testEvaulateTwoVariableExpressionMultipleAssignmentsOr() {
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorTest(3);

        BooleanStructure exp3 = this.constructorRef(1);
        BooleanStructure exp4 = this.constructorRef(3);

        Sequence<Integer> newVars1 = new Sequence1L<Integer>();
        newVars1.add(0, 1);
        newVars1.add(1, 3);

        Sequence<Integer> newVars2 = new Sequence1L<Integer>();
        newVars2.add(0, 1);
        newVars2.add(1, 3);

        exp1.apply(BinaryOperator.OR, exp2, newVars1);
        exp3.apply(BinaryOperator.OR, exp4, newVars2);

        // ( { 1, 3 }, { } )
        assertTrue(exp3.evaluate(createSet(1, 3)) == exp1
                .evaluate(createSet(1, 3)));
        // ( { 1, 3 }, { 2, 18 } )
        assertTrue(exp3.evaluate(createSet(1, 3)) == exp1
                .evaluate(createSet(1, 3)));
        // ( { 1 }, { 3 } )
        assertTrue(exp3.evaluate(createSet(1)) == exp1.evaluate(createSet(1)));
        // ( { 3 }, { 1 } )
        assertTrue(exp3.evaluate(createSet(3)) == exp1.evaluate(createSet(3)));
        // ( { 3, 18 }, { 1, 300 } )
        assertTrue(exp3.evaluate(createSet(3, 18)) == exp1
                .evaluate(createSet(3, 18)));
        // ( { 2, 18 }, { 1, 3 } )
        assertTrue(exp3.evaluate(createSet(2, 18)) == exp1
                .evaluate(createSet(2, 18)));
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
        BooleanStructure exp1 = this.constructorTest();
        BooleanStructure exp2 = this.constructorRef();

        exp1.apply(UnaryOperator.IDENTITY);
        exp2.apply(UnaryOperator.IDENTITY);

        assertEquals(exp2, exp1);
    }

    /*
     * Perform the not operation on an empty expression. Same empty ordering.
     * Always evaluates to false
     */
    @Test
    public final void testUnaryApplyNotEmptyExpression() {
        BooleanStructure exp1 = this.constructorTest();
        BooleanStructure exp2 = this.constructorRef();

        exp1.apply(UnaryOperator.NOT);
        exp2.apply(UnaryOperator.NOT);

        assertEquals(exp2, exp1);
    }

    /*
     * Perform the identity operation on the true expression. Same empty
     * ordering. Always evaluates to true
     */
    @Test
    public final void testUnaryApplyIdentityTrueExpression() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorRef(true);

        exp1.apply(UnaryOperator.IDENTITY);
        exp2.apply(UnaryOperator.IDENTITY);

        assertEquals(exp2, exp1);
    }

    /*
     * Perform the not operation on the true expression. Same empty ordering.
     * Always evaluates to false
     */
    @Test
    public final void testUnaryApplyNotTrueExpression() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorRef(true);

        exp1.apply(UnaryOperator.NOT);
        exp2.apply(UnaryOperator.NOT);

        assertEquals(exp2, exp1);
    }

    /*
     * Perform the identity operation on the false expression. Same empty
     * ordering. Always evaluates to false
     */
    @Test
    public final void testUnaryApplyIdentityFalseExpression() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorRef(false);

        exp1.apply(UnaryOperator.IDENTITY);
        exp2.apply(UnaryOperator.IDENTITY);

        assertEquals(exp2, exp1);
    }

    /*
     * Perform the not operation on the false expression. Same empty ordering.
     * Always evaluates to true
     */
    @Test
    public final void testUnaryApplyNotFalseExpression() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorRef(false);

        exp1.apply(UnaryOperator.NOT);
        exp2.apply(UnaryOperator.NOT);

        assertEquals(exp2, exp1);
    }

    /*
     * Perform the identity operation on a single variable expression. Same,
     * single variable ordering. Evaluates to the same values as before the
     * apply
     */
    @Test
    public final void testUnaryApplyIdentitySingleVariableExpression() {
        BooleanStructure exp1 = this.constructorTest(3);
        BooleanStructure exp2 = this.constructorTest(3);

        exp1.apply(UnaryOperator.IDENTITY);
        exp2.apply(UnaryOperator.IDENTITY);

        assertEquals(exp2, exp1);
    }

    /*
     * Perform the not operation on a single variable expression. Same, single
     * variable ordering. Evaluates to the negation of the expression before the
     * apply
     */
    @Test
    public final void testUnaryApplyNotSingleVariableExpression() {
        BooleanStructure exp1 = this.constructorTest(18);
        BooleanStructure exp2 = this.constructorRef(18);

        exp1.apply(UnaryOperator.NOT);
        exp2.apply(UnaryOperator.NOT);

        assertEquals(exp2, exp1);
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

        BooleanStructure exp5 = this.constructorRef(0);
        BooleanStructure exp6 = this.constructorRef(1);
        BooleanStructure exp7 = this.constructorRef(0);
        BooleanStructure exp8 = this.constructorRef(1);

        Sequence<Integer> newVars1 = createSequence(0, 1);
        Sequence<Integer> newVars2 = createSequence(0, 1);

        Sequence<Integer> newVars3 = createSequence(0, 1);
        Sequence<Integer> newVars4 = createSequence(0, 1);

        exp1.apply(BinaryOperator.AND, exp2, newVars1);
        exp5.apply(BinaryOperator.AND, exp6, newVars2);

        exp3.apply(BinaryOperator.OR, exp4, newVars3);
        exp7.apply(BinaryOperator.OR, exp8, newVars4);

        exp1.apply(UnaryOperator.NOT);
        exp5.apply(UnaryOperator.NOT);
        exp3.apply(UnaryOperator.NOT);
        exp7.apply(UnaryOperator.NOT);

        assertEquals(exp5, exp1);
        assertEquals(exp7, exp3);
    }

    /*
     * Binary Apply Tests
     */

    /*
     * Perform the and operation on two true expressions
     */
    @Test
    public final void testBinaryApplyTwoTrues() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorTest(true);
        BooleanStructure exp3 = this.constructorRef(true);
        BooleanStructure exp4 = this.constructorRef(true);

        Sequence<Integer> newVars1 = createSequence();
        Sequence<Integer> newVars2 = createSequence();

        exp1.apply(BinaryOperator.AND, exp2, newVars1);
        exp3.apply(BinaryOperator.AND, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform the and operation on two false expressions
     */
    @Test
    public final void testBinaryApplyTwoFalses() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorTest(false);
        BooleanStructure exp3 = this.constructorRef(false);
        BooleanStructure exp4 = this.constructorRef(false);

        Sequence<Integer> newVars1 = createSequence();
        Sequence<Integer> newVars2 = createSequence();

        exp1.apply(BinaryOperator.AND, exp2, newVars1);
        exp3.apply(BinaryOperator.AND, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform the and operation on a true expression and a false expression
     */
    @Test
    public final void testBinaryApplyAndTrueFalse() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorTest(false);
        BooleanStructure exp3 = this.constructorRef(true);
        BooleanStructure exp4 = this.constructorRef(false);

        Sequence<Integer> newVars1 = createSequence();
        Sequence<Integer> newVars2 = createSequence();

        exp1.apply(BinaryOperator.AND, exp2, newVars1);
        exp3.apply(BinaryOperator.AND, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform the or operation on two true expressions
     */
    @Test
    public final void testBinaryApplyOrTwoTrues() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorTest(true);
        BooleanStructure exp3 = this.constructorRef(true);
        BooleanStructure exp4 = this.constructorRef(true);

        Sequence<Integer> newVars1 = createSequence();
        Sequence<Integer> newVars2 = createSequence();

        exp1.apply(BinaryOperator.OR, exp2, newVars1);
        exp3.apply(BinaryOperator.OR, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform the or operation on two false expressions
     */
    @Test
    public final void testBinaryApplyOrTwoFalses() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorTest(false);
        BooleanStructure exp3 = this.constructorRef(false);
        BooleanStructure exp4 = this.constructorRef(false);

        Sequence<Integer> newVars1 = createSequence();
        Sequence<Integer> newVars2 = createSequence();

        exp1.apply(BinaryOperator.OR, exp2, newVars1);
        exp3.apply(BinaryOperator.OR, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform the or operation on a true expression and false expression
     */
    @Test
    public final void testBinaryApplyOrTrueFalse() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorTest(false);
        BooleanStructure exp3 = this.constructorRef(true);
        BooleanStructure exp4 = this.constructorRef(false);

        Sequence<Integer> newVars1 = createSequence();
        Sequence<Integer> newVars2 = createSequence();

        exp1.apply(BinaryOperator.OR, exp2, newVars1);
        exp3.apply(BinaryOperator.OR, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform the equivals operation on two true expressions
     */
    @Test
    public final void testBinaryApplyEquivalsTwoTrues() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorTest(true);
        BooleanStructure exp3 = this.constructorRef(true);
        BooleanStructure exp4 = this.constructorRef(true);

        Sequence<Integer> newVars1 = createSequence();
        Sequence<Integer> newVars2 = createSequence();

        exp1.apply(BinaryOperator.EQUIVALS, exp2, newVars1);
        exp3.apply(BinaryOperator.EQUIVALS, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform the equivals operation on two false expressions
     */
    @Test
    public final void testBinaryApplyEquivalsTwoFalses() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorTest(false);
        BooleanStructure exp3 = this.constructorRef(false);
        BooleanStructure exp4 = this.constructorRef(false);

        Sequence<Integer> newVars1 = createSequence();
        Sequence<Integer> newVars2 = createSequence();

        exp1.apply(BinaryOperator.EQUIVALS, exp2, newVars1);
        exp3.apply(BinaryOperator.EQUIVALS, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform the equivals operation on a true expression and false expression
     */
    @Test
    public final void testBinaryApplyEquivalsTrueFalse() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorTest(false);
        BooleanStructure exp3 = this.constructorRef(true);
        BooleanStructure exp4 = this.constructorRef(false);

        Sequence<Integer> newVars1 = createSequence();
        Sequence<Integer> newVars2 = createSequence();

        exp1.apply(BinaryOperator.EQUIVALS, exp2, newVars1);
        exp3.apply(BinaryOperator.EQUIVALS, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform the or operation on a true expression and a single variable
     * expression
     */
    @Test
    public final void testBinaryApplyOrTrueSingleVariable() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(true);
        BooleanStructure exp3 = this.constructorRef(0);
        BooleanStructure exp4 = this.constructorRef(true);

        Sequence<Integer> newVars1 = createSequence(0);
        Sequence<Integer> newVars2 = createSequence(0);

        exp1.apply(BinaryOperator.OR, exp2, newVars1);
        exp3.apply(BinaryOperator.OR, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform the or operation on a false expression and a single variable
     * expression
     */
    @Test
    public final void testBinaryApplyOrFalseSingleVariable() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(false);
        BooleanStructure exp3 = this.constructorRef(0);
        BooleanStructure exp4 = this.constructorRef(false);

        Sequence<Integer> newVars1 = createSequence(0);
        Sequence<Integer> newVars2 = createSequence(0);

        exp1.apply(BinaryOperator.OR, exp2, newVars1);
        exp3.apply(BinaryOperator.OR, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform the and operation on a true expression and a single variable
     * expression
     */
    @Test
    public final void testBinaryApplyAndTrueSingleVariable() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(true);
        BooleanStructure exp3 = this.constructorRef(0);
        BooleanStructure exp4 = this.constructorRef(true);

        Sequence<Integer> newVars1 = createSequence(0);
        Sequence<Integer> newVars2 = createSequence(0);

        exp1.apply(BinaryOperator.AND, exp2, newVars1);
        exp3.apply(BinaryOperator.AND, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform the and operation on a false expression and a single variable
     * expression
     */
    @Test
    public final void testBinaryApplyAndFalseSingleVariable() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(false);
        BooleanStructure exp3 = this.constructorRef(0);
        BooleanStructure exp4 = this.constructorRef(false);

        Sequence<Integer> newVars1 = createSequence(0);
        Sequence<Integer> newVars2 = createSequence(0);

        exp1.apply(BinaryOperator.AND, exp2, newVars1);
        exp3.apply(BinaryOperator.AND, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform the equivals operation on a true expression and single variable
     * expressions
     */
    @Test
    public final void testBinaryApplyEquivalsTrueSingleVariable() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(true);
        BooleanStructure exp3 = this.constructorRef(0);
        BooleanStructure exp4 = this.constructorRef(true);

        Sequence<Integer> newVars1 = createSequence(0);
        Sequence<Integer> newVars2 = createSequence(0);

        exp1.apply(BinaryOperator.EQUIVALS, exp2, newVars1);
        exp3.apply(BinaryOperator.EQUIVALS, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform the equivals operation on a false expression and single variable
     * expressions
     */
    @Test
    public final void testBinaryApplyEquivalsFalseSingleVariable() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(false);
        BooleanStructure exp3 = this.constructorRef(0);
        BooleanStructure exp4 = this.constructorRef(false);

        Sequence<Integer> newVars1 = createSequence(0);
        Sequence<Integer> newVars2 = createSequence(0);

        exp1.apply(BinaryOperator.EQUIVALS, exp2, newVars1);
        exp3.apply(BinaryOperator.EQUIVALS, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform the and operation on two single variable expressions
     */
    @Test
    public final void testBinaryApplyAnd2SingleVariables() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorRef(0);
        BooleanStructure exp4 = this.constructorRef(1);

        Sequence<Integer> newVars1 = createSequence(0, 1);
        Sequence<Integer> newVars2 = createSequence(0, 1);

        exp1.apply(BinaryOperator.AND, exp2, newVars1);
        exp3.apply(BinaryOperator.AND, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform the or operation on two single variable expressions
     */
    @Test
    public final void testBinaryApplyOr2SingleVariables() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorRef(0);
        BooleanStructure exp4 = this.constructorRef(1);

        Sequence<Integer> newVars1 = createSequence(0, 1);
        Sequence<Integer> newVars2 = createSequence(0, 1);

        exp1.apply(BinaryOperator.OR, exp2, newVars1);
        exp3.apply(BinaryOperator.OR, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform the equivals operation on two single variable expressions
     */
    @Test
    public final void testBinaryApplyOr2EquivalsVariables() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorRef(0);
        BooleanStructure exp4 = this.constructorRef(1);

        Sequence<Integer> newVars1 = createSequence(0, 1);
        Sequence<Integer> newVars2 = createSequence(0, 1);

        exp1.apply(BinaryOperator.EQUIVALS, exp2, newVars1);
        exp3.apply(BinaryOperator.EQUIVALS, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform the or operation between a single variable and 2 variable
     * expression
     */
    @Test
    public final void testBinaryApplyOrTwoVariablesOredSingleVariable() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(2);

        BooleanStructure exp4 = this.constructorRef(0);
        BooleanStructure exp5 = this.constructorRef(1);
        BooleanStructure exp6 = this.constructorRef(2);

        Sequence<Integer> newVars1A = createSequence(0, 1);
        Sequence<Integer> newVars1B = createSequence(0, 1);

        Sequence<Integer> newVars2A = createSequence(0, 1, 2);
        Sequence<Integer> newVars2B = createSequence(0, 1, 2);

        // (x0 V x1) V x2
        exp1.apply(BinaryOperator.OR, exp2, newVars1A);
        exp1.apply(BinaryOperator.OR, exp3, newVars2A);

        exp4.apply(BinaryOperator.OR, exp5, newVars1B);
        exp4.apply(BinaryOperator.OR, exp6, newVars2B);

        assertEquals(exp4, exp1);
    }

    /*
     * Perform the or operation between a single variable and 2 variable
     * expression
     */
    @Test
    public final void testBinaryApplyOrTwoVariablesAndedSingleVariable() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(2);

        BooleanStructure exp4 = this.constructorRef(0);
        BooleanStructure exp5 = this.constructorRef(1);
        BooleanStructure exp6 = this.constructorRef(2);

        Sequence<Integer> newVars1A = createSequence(0, 1);
        Sequence<Integer> newVars1B = createSequence(0, 1);

        Sequence<Integer> newVars2A = createSequence(0, 1, 2);
        Sequence<Integer> newVars2B = createSequence(0, 1, 2);

        // (x0 V x1) V x2
        exp1.apply(BinaryOperator.AND, exp2, newVars1A);
        exp1.apply(BinaryOperator.OR, exp3, newVars2A);

        exp4.apply(BinaryOperator.AND, exp5, newVars1B);
        exp4.apply(BinaryOperator.OR, exp6, newVars2B);

        assertEquals(exp4, exp1);
    }

    /*
     * Perform the equivals operation between a single variable and 2 variable
     * expression
     */
    @Test
    public final void testBinaryApplyEquivalsTwoVariablesOredSingleVariable() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(2);

        BooleanStructure exp4 = this.constructorRef(0);
        BooleanStructure exp5 = this.constructorRef(1);
        BooleanStructure exp6 = this.constructorRef(2);

        Sequence<Integer> newVars1A = createSequence(0, 1);
        Sequence<Integer> newVars1B = createSequence(0, 1);

        Sequence<Integer> newVars2A = createSequence(0, 1, 2);
        Sequence<Integer> newVars2B = createSequence(0, 1, 2);

        // (x0 V x1) V x2
        exp1.apply(BinaryOperator.OR, exp2, newVars1A);
        exp1.apply(BinaryOperator.EQUIVALS, exp3, newVars2A);

        exp4.apply(BinaryOperator.OR, exp5, newVars1B);
        exp4.apply(BinaryOperator.EQUIVALS, exp6, newVars2B);

        assertEquals(exp4, exp1);
    }

    /*
     * Perform the and operation between a single variable and 2 variable
     * expression
     */
    @Test
    public final void testBinaryApplyAndTwoVariablesOredSingleVariable() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(2);

        BooleanStructure exp4 = this.constructorRef(0);
        BooleanStructure exp5 = this.constructorRef(1);
        BooleanStructure exp6 = this.constructorRef(2);

        Sequence<Integer> newVars1A = createSequence(0, 1);
        Sequence<Integer> newVars1B = createSequence(0, 1);

        Sequence<Integer> newVars2A = createSequence(0, 1, 2);
        Sequence<Integer> newVars2B = createSequence(0, 1, 2);

        // (x0 V x1) V x2
        exp1.apply(BinaryOperator.OR, exp2, newVars1A);
        exp1.apply(BinaryOperator.AND, exp3, newVars2A);

        exp4.apply(BinaryOperator.OR, exp5, newVars1B);
        exp4.apply(BinaryOperator.AND, exp6, newVars2B);

        assertEquals(exp4, exp1);
    }

    /*
     * Perform the and operation between a single variable and 2 variable
     * expression
     */
    @Test
    public final void testBinaryApplyAndTwoVariablesAndedSingleVariable() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(2);

        BooleanStructure exp4 = this.constructorRef(0);
        BooleanStructure exp5 = this.constructorRef(1);
        BooleanStructure exp6 = this.constructorRef(2);

        Sequence<Integer> newVars1A = createSequence(0, 1);
        Sequence<Integer> newVars1B = createSequence(0, 1);

        Sequence<Integer> newVars2A = createSequence(0, 1, 2);
        Sequence<Integer> newVars2B = createSequence(0, 1, 2);

        // (x0 V x1) V x2
        exp1.apply(BinaryOperator.AND, exp2, newVars1A);
        exp1.apply(BinaryOperator.AND, exp3, newVars2A);

        exp4.apply(BinaryOperator.AND, exp5, newVars1B);
        exp4.apply(BinaryOperator.AND, exp6, newVars2B);

        assertEquals(exp4, exp1);
    }

    /*
     * Perform the equivals operation between a single variable and 2 variable
     * expression
     */
    @Test
    public final void testBinaryApplyEquivalsTwoVariablesAndedSingleVariable() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorTest(2);

        BooleanStructure exp4 = this.constructorRef(0);
        BooleanStructure exp5 = this.constructorRef(1);
        BooleanStructure exp6 = this.constructorRef(2);

        Sequence<Integer> newVars1A = createSequence(0, 1);
        Sequence<Integer> newVars1B = createSequence(0, 1);

        Sequence<Integer> newVars2A = createSequence(0, 1, 2);
        Sequence<Integer> newVars2B = createSequence(0, 1, 2);

        // (x0 V x1) V x2
        exp1.apply(BinaryOperator.AND, exp2, newVars1A);
        exp1.apply(BinaryOperator.EQUIVALS, exp3, newVars2A);

        exp4.apply(BinaryOperator.AND, exp5, newVars1B);
        exp4.apply(BinaryOperator.EQUIVALS, exp6, newVars2B);

        assertEquals(exp4, exp1);
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

        BooleanStructure exp4 = this.constructorRef(1);
        BooleanStructure exp5 = this.constructorRef(2);
        BooleanStructure exp6 = this.constructorRef(2);

        Sequence<Integer> newVars1A = createSequence(1, 2);
        Sequence<Integer> newVars1B = createSequence(1, 2);

        Sequence<Integer> newVars2A = createSequence(1, 2);
        Sequence<Integer> newVars2B = createSequence(1, 2);

        // (x1 ^ x2) v x2
        exp1.apply(BinaryOperator.AND, exp2, newVars1A);
        exp1.apply(BinaryOperator.OR, exp3, newVars2A);

        exp4.apply(BinaryOperator.AND, exp5, newVars1B);
        exp4.apply(BinaryOperator.OR, exp6, newVars2B);

        assertEquals(exp4, exp1);
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

        BooleanStructure exp4 = this.constructorRef(1);
        BooleanStructure exp5 = this.constructorRef(2);
        BooleanStructure exp6 = this.constructorRef(2);

        Sequence<Integer> newVars1A = createSequence(1, 2);
        Sequence<Integer> newVars1B = createSequence(1, 2);

        Sequence<Integer> newVars2A = createSequence(1, 2);
        Sequence<Integer> newVars2B = createSequence(1, 2);

        // (x1 ^ x2) v x2
        exp1.apply(BinaryOperator.AND, exp2, newVars1A);
        exp1.apply(BinaryOperator.AND, exp3, newVars2A);

        exp4.apply(BinaryOperator.AND, exp5, newVars1B);
        exp4.apply(BinaryOperator.AND, exp6, newVars2B);

        assertEquals(exp4, exp1);
    }

    /*
     * Perform a binary apply on expressions with common variables (3 literals,
     * 2 variables)
     */
    @Test
    public final void testBinaryApply3Literals2VariablesAndEquivals() {
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorTest(2);
        BooleanStructure exp3 = this.constructorTest(2);

        BooleanStructure exp4 = this.constructorRef(1);
        BooleanStructure exp5 = this.constructorRef(2);
        BooleanStructure exp6 = this.constructorRef(2);

        Sequence<Integer> newVars1A = createSequence(1, 2);
        Sequence<Integer> newVars1B = createSequence(1, 2);

        Sequence<Integer> newVars2A = createSequence(1, 2);
        Sequence<Integer> newVars2B = createSequence(1, 2);

        // (x1 ^ x2) v x2
        exp1.apply(BinaryOperator.AND, exp2, newVars1A);
        exp1.apply(BinaryOperator.EQUIVALS, exp3, newVars2A);

        exp4.apply(BinaryOperator.AND, exp5, newVars1B);
        exp4.apply(BinaryOperator.EQUIVALS, exp6, newVars2B);

        assertEquals(exp4, exp1);
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

        BooleanStructure exp4 = this.constructorRef(1);
        BooleanStructure exp5 = this.constructorRef(2);
        BooleanStructure exp6 = this.constructorRef(2);

        Sequence<Integer> newVars1A = createSequence(1, 2);
        Sequence<Integer> newVars1B = createSequence(1, 2);

        Sequence<Integer> newVars2A = createSequence(1, 2);
        Sequence<Integer> newVars2B = createSequence(1, 2);

        // (x1 ^ x2) v x2
        exp1.apply(BinaryOperator.OR, exp2, newVars1A);
        exp1.apply(BinaryOperator.AND, exp3, newVars2A);

        exp4.apply(BinaryOperator.OR, exp5, newVars1B);
        exp4.apply(BinaryOperator.AND, exp6, newVars2B);

        assertEquals(exp4, exp1);
    }

    /*
     * Perform a binary apply on expressions with the same variables (4
     * Literals, 3 variables)
     */
    @Test
    public final void testBinaryApply4Literals3VariablesOr() {
        SyntaxTree t1 = new SyntaxTree("1 2 and");
        SyntaxTree t2 = new SyntaxTree("3 1 and");
        SyntaxTree t3 = new SyntaxTree("1 2 and");
        SyntaxTree t4 = new SyntaxTree("3 1 and");
        BooleanStructure exp1 = this.constructorTest(t1);
        BooleanStructure exp2 = this.constructorTest(t2);
        BooleanStructure exp3 = this.constructorRef(t3);
        BooleanStructure exp4 = this.constructorRef(t4);

        Sequence<Integer> newVars1 = createSequence(3, 1, 2);

        Sequence<Integer> newVars2 = createSequence(3, 1, 2);

        exp1.apply(BinaryOperator.OR, exp2, newVars1);
        exp3.apply(BinaryOperator.OR, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform a binary apply on expressions with the same variables (4
     * Literals, 3 variables)
     */
    @Test
    public final void testBinaryApply4Literals3VariablesAnd() {
        SyntaxTree t1 = new SyntaxTree("1 2 or");
        SyntaxTree t2 = new SyntaxTree("3 1 or");
        SyntaxTree t3 = new SyntaxTree("1 2 or");
        SyntaxTree t4 = new SyntaxTree("3 1 or");
        BooleanStructure exp1 = this.constructorTest(t1);
        BooleanStructure exp2 = this.constructorTest(t2);
        BooleanStructure exp3 = this.constructorRef(t3);
        BooleanStructure exp4 = this.constructorRef(t4);

        Sequence<Integer> newVars1 = createSequence(3, 1, 2);

        Sequence<Integer> newVars2 = createSequence(3, 1, 2);

        exp1.apply(BinaryOperator.AND, exp2, newVars1);
        exp3.apply(BinaryOperator.AND, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform a binary apply on expressions with the same variables (4
     * Literals, 3 variables)
     */
    @Test
    public final void testBinaryApply4Literals3VariablesEquivals() {
        SyntaxTree t1 = new SyntaxTree("1 2 and");
        SyntaxTree t2 = new SyntaxTree("3 1 and");
        SyntaxTree t3 = new SyntaxTree("1 2 and");
        SyntaxTree t4 = new SyntaxTree("3 1 and");
        BooleanStructure exp1 = this.constructorTest(t1);
        BooleanStructure exp2 = this.constructorTest(t2);
        BooleanStructure exp3 = this.constructorRef(t3);
        BooleanStructure exp4 = this.constructorRef(t4);

        Sequence<Integer> newVars1 = createSequence(3, 1, 2);

        Sequence<Integer> newVars2 = createSequence(3, 1, 2);

        exp1.apply(BinaryOperator.EQUIVALS, exp2, newVars1);
        exp3.apply(BinaryOperator.EQUIVALS, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform a binary apply on expressions with the same variables (4
     * Literals, 2 variables)
     */
    @Test
    public final void testBinaryApply4Literals2VariablesOr() {
        SyntaxTree t1 = new SyntaxTree("1 2 or");
        SyntaxTree t2 = new SyntaxTree("1 2 and");
        SyntaxTree t3 = new SyntaxTree("1 2 or");
        SyntaxTree t4 = new SyntaxTree("1 2 and");
        BooleanStructure exp1 = this.constructorTest(t1);
        BooleanStructure exp2 = this.constructorTest(t2);
        BooleanStructure exp3 = this.constructorRef(t3);
        BooleanStructure exp4 = this.constructorRef(t4);

        Sequence<Integer> newVars1 = createSequence(1, 2);

        Sequence<Integer> newVars2 = createSequence(1, 2);

        exp1.apply(BinaryOperator.OR, exp2, newVars1);
        exp3.apply(BinaryOperator.OR, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Perform a binary apply on expressions with the same variables (4
     * Literals, 2 variables)
     */
    @Test
    public final void testBinaryApply4Literals2VariablesEquivals() {
        SyntaxTree t1 = new SyntaxTree("1 2 or");
        SyntaxTree t2 = new SyntaxTree("1 2 and");
        SyntaxTree t3 = new SyntaxTree("1 2 or");
        SyntaxTree t4 = new SyntaxTree("1 2 and");
        BooleanStructure exp1 = this.constructorTest(t1);
        BooleanStructure exp2 = this.constructorTest(t2);
        BooleanStructure exp3 = this.constructorRef(t3);
        BooleanStructure exp4 = this.constructorRef(t4);

        Sequence<Integer> newVars1 = createSequence(1, 2);

        Sequence<Integer> newVars2 = createSequence(1, 2);

        exp1.apply(BinaryOperator.EQUIVALS, exp2, newVars1);
        exp3.apply(BinaryOperator.EQUIVALS, exp4, newVars2);

        assertEquals(exp3, exp1);
    }

    /*
     * Expand Tests
     */

    /*
     * Expand the True expression by 1 variable
     */
    @Test
    public final void testExpandTrueExpressionBy1() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorRef(true);

        Set<Integer> expansion1 = createSet(1);

        Set<Integer> expansion2 = createSet(1);

        exp1.expand(expansion1);
        exp2.expand(expansion2);

        assertTrue(exp2.isEquivalent(exp1));
        assertEquals(seqToSet(exp2.vars()), seqToSet(exp1.vars()));
    }

    /*
     * Expand the False expression by 1 variable
     */
    @Test
    public final void testExpandFalseExpressionBy1() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorRef(false);

        Set<Integer> expansion1 = createSet(1);

        Set<Integer> expansion2 = createSet(1);

        exp1.expand(expansion1);
        exp2.expand(expansion2);

        assertTrue(exp2.isEquivalent(exp1));
        assertEquals(seqToSet(exp2.vars()), seqToSet(exp1.vars()));
    }

    /*
     * Expand a single variable expression by 1 variable
     */
    @Test
    public final void testExpandSingleVariableBy1() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorRef(0);

        Set<Integer> expansion1 = createSet(1);

        Set<Integer> expansion2 = createSet(1);

        exp1.expand(expansion1);
        exp2.expand(expansion2);

        assertTrue(exp2.isEquivalent(exp1));
        assertEquals(seqToSet(exp2.vars()), seqToSet(exp1.vars()));
    }

    /*
     * Expand a single variable expression by 2 variables
     */
    @Test
    public final void testExpandSingleVariableBy2() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorRef(0);

        Set<Integer> expansion1 = createSet(1, 2);

        Set<Integer> expansion2 = createSet(1, 2);

        exp1.expand(expansion1);
        exp2.expand(expansion2);

        assertTrue(exp2.isEquivalent(exp1));
        assertEquals(seqToSet(exp2.vars()), seqToSet(exp1.vars()));
    }

    /*
     * Expand a two variable expression by 1 variables
     */
    @Test
    public final void testExpandTwoVariableBy1() {
        SyntaxTree tTest = new SyntaxTree("0 1 and");
        SyntaxTree tRef = new SyntaxTree("0 1 and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> expansion1 = createSet(3);

        Set<Integer> expansion2 = createSet(3);

        exp1.expand(expansion1);
        exp2.expand(expansion2);

        assertTrue(exp2.isEquivalent(exp1));
        assertEquals(seqToSet(exp2.vars()), seqToSet(exp1.vars()));
    }

    /*
     * Expand a two variable expression by 1 variables
     */
    @Test
    public final void testExpandTwoVariableBy2() {
        SyntaxTree tTest = new SyntaxTree("0 1 and");
        SyntaxTree tRef = new SyntaxTree("0 1 and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> expansion1 = createSet(3, 18);

        Set<Integer> expansion2 = createSet(3, 18);

        exp1.expand(expansion1);
        exp2.expand(expansion2);

        assertTrue(exp2.isEquivalent(exp1));
        assertEquals(seqToSet(exp2.vars()), seqToSet(exp1.vars()));
    }

    /*
     * Expand a two variable expression by 3 variables
     */
    @Test
    public final void testExpandTwoVariableBy3() {
        SyntaxTree tTest = new SyntaxTree("0 1 and");
        SyntaxTree tRef = new SyntaxTree("0 1 and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> expansion1 = createSet(3, 18, 300);

        Set<Integer> expansion2 = createSet(3, 18, 300);

        exp1.expand(expansion1);
        exp2.expand(expansion2);

        assertTrue(exp2.isEquivalent(exp1));
        assertEquals(seqToSet(exp2.vars()), seqToSet(exp1.vars()));
    }

    /*
     * isTrue and isFalse Tests
     */

    /*
     * isTrue and isFalse work after empty constructor, which produces true
     */
    @Test
    public final void testEmptyConstructorIsTrue() {
        BooleanStructure exp1 = this.constructorTest();
        BooleanStructure exp2 = this.constructorRef();

        assertEquals(exp2.isTrueStructure(), exp1.isTrueStructure());
        assertEquals(exp2.isFalseStructure(), exp1.isFalseStructure());

        assertEquals(exp2, exp1);
    }

    /*
     * isTrue and isFalse work after boolean constructor (true)
     */
    @Test
    public final void testBooleanTrueConstructorIsTrue() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorRef(true);

        assertEquals(exp2.isTrueStructure(), exp1.isTrueStructure());
        assertEquals(exp2.isFalseStructure(), exp1.isFalseStructure());

        assertEquals(exp2, exp1);
    }

    /*
     * isTrue and isFalse work after boolean constructor (false)
     */
    @Test
    public final void testBooleanFalseConstructorIsFalse() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorRef(false);

        assertEquals(exp2.isTrueStructure(), exp1.isTrueStructure());
        assertEquals(exp2.isFalseStructure(), exp1.isFalseStructure());

        assertEquals(exp2, exp1);
    }

    /*
     * A one-variable expression is neither true nor false
     */
    @Test
    public final void testOneVariableExpressionIsTrue() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorRef(0);

        assertEquals(exp2.isTrueStructure(), exp1.isTrueStructure());
        assertEquals(exp2.isFalseStructure(), exp1.isFalseStructure());

        assertEquals(exp2, exp1);
    }

    /*
     * Reorder tests
     */

    @Test
    public final void testReorderTrueExpression() {
        BooleanStructure exp1 = this.constructorTest();
        BooleanStructure exp2 = this.constructorRef();

        Sequence<Integer> order1 = createSequence();
        Sequence<Integer> order2 = createSequence();

        exp1.reorder(order1);
        exp2.reorder(order2);

        assertEquals(exp2, exp1);
    }

    @Test
    public final void testReorderFalseExpression() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorRef(false);

        Sequence<Integer> order1 = createSequence();
        Sequence<Integer> order2 = createSequence();

        exp1.reorder(order1);
        exp2.reorder(order2);

        assertEquals(exp2, exp1);
    }

    @Test
    public final void testReorderOneVariableExpression() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorRef(0);

        Sequence<Integer> order1 = createSequence(0);
        Sequence<Integer> order2 = createSequence(0);

        exp1.reorder(order1);
        exp2.reorder(order2);

        assertEquals(exp2, exp1);
    }

    @Test
    public final void testReorderTwoVariableExpressionAnd() {
        SyntaxTree tTest = new SyntaxTree("0 1 and");
        SyntaxTree tRef = new SyntaxTree("0 1 and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Sequence<Integer> order1 = createSequence(1, 0);
        Sequence<Integer> order2 = createSequence(1, 0);

        exp1.reorder(order1);
        exp2.reorder(order2);

        assertEquals(exp2, exp1);
    }

    @Test
    public final void testReorderTwoVariableExpressionOr() {
        SyntaxTree tTest = new SyntaxTree("0 1 or");
        SyntaxTree tRef = new SyntaxTree("0 1 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Sequence<Integer> order1 = createSequence(1, 0);
        Sequence<Integer> order2 = createSequence(1, 0);

        exp1.reorder(order1);
        exp2.reorder(order2);

        assertEquals(exp2, exp1);
    }

    @Test
    public final void testReorderThreeVariableExpression() {
        SyntaxTree tTest = new SyntaxTree("0 1 or 2 and");
        SyntaxTree tRef = new SyntaxTree("0 1 or 2 and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Sequence<Integer> order1 = createSequence(2, 0, 1);
        Sequence<Integer> order2 = createSequence(2, 0, 1);

        exp1.reorder(order1);
        exp2.reorder(order2);

        assertEquals(exp2, exp1);
    }

    @Test
    public final void testReorderVariableExpression() {
        SyntaxTree tTest = new SyntaxTree("0 not 1 or 2 0 and or 3 and");
        SyntaxTree tRef = new SyntaxTree("0 not 1 or 2 0 and or 3 and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Sequence<Integer> order1 = createSequence(1, 3, 2, 0);
        Sequence<Integer> order2 = createSequence(1, 3, 2, 0);

        exp1.reorder(order1);
        exp2.reorder(order2);

        assertEquals(exp2, exp1);
    }

    /*
     * Reorder a six variable expression
     */
    @Test
    public final void testReorderSixVariableExpression() {
        SyntaxTree tTest = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");
        SyntaxTree tRef = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        exp1.reorder(createSequence(1, 3, 5, 2, 4, 6));
        exp2.reorder(createSequence(1, 3, 5, 2, 4, 6));

        assertEquals(exp2, exp1);
    }

    @Test
    public final void testReorderEightVariableExpression() {
        SyntaxTree tTest = new SyntaxTree(
                "1 2 and 3 4 and or 5 6 and or 7 8 and or");
        SyntaxTree tRef = new SyntaxTree(
                "1 2 and 3 4 and or 5 6 and or 7 8 and or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Sequence<Integer> order1 = createSequence(1, 3, 5, 7, 2, 4, 6, 8);
        Sequence<Integer> order2 = createSequence(1, 3, 5, 7, 2, 4, 6, 8);

        exp1.reorder(order1);
        exp2.reorder(order2);

        assertEquals(exp2, exp1);

        Sequence<Integer> order3 = createSequence(1, 2, 3, 4, 5, 6, 7, 8);
        Sequence<Integer> order4 = createSequence(1, 2, 3, 4, 5, 6, 7, 8);

        exp1.reorder(order3);
        exp2.reorder(order4);

        assertEquals(exp2, exp1);

    }

    /*
     * Restrict Tests
     */

    /*
     * Restrict the true expression
     */
    @Test
    public final void testRestrictTrueExpression() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorRef(true);

        exp1.restrict(createSet(), createSet());
        exp2.restrict(createSet(), createSet());

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict the false expression
     */
    @Test
    public final void testRestrictFalseExpression() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorRef(false);

        exp1.restrict(createSet(), createSet());
        exp2.restrict(createSet(), createSet());

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict the variable to true in a single variable expression
     */
    @Test
    public final void testRestrictOneVariableExpressionOneTrue() {
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorRef(1);

        Set<Integer> r1 = createSet(1);
        Set<Integer> r2 = createSet(1);

        exp1.restrict(r1, createSet());
        exp2.restrict(r2, createSet());

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict the variable to false in a single variable expression
     */
    @Test
    public final void testRestrictOneVariableExpressionOneFalse() {
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorRef(1);

        Set<Integer> r1 = createSet(1);
        Set<Integer> r2 = createSet(1);

        exp1.restrict(createSet(), r1);
        exp2.restrict(createSet(), r2);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict a variable to true in a two variable expression
     */
    @Test
    public final void testRestrictTwoVariableExpressionOneTrue1() {
        SyntaxTree tTest = new SyntaxTree("0 1 or");
        SyntaxTree tRef = new SyntaxTree("0 1 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(0);
        Set<Integer> r2 = createSet(0);

        exp1.restrict(r1, createSet());
        exp2.restrict(r2, createSet());

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict the other variable to true in a two variable expression
     */
    @Test
    public final void testRestrictTwoVariableExpressionOneTrue2() {
        SyntaxTree tTest = new SyntaxTree("0 1 or");
        SyntaxTree tRef = new SyntaxTree("0 1 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(1);
        Set<Integer> r2 = createSet(1);

        exp1.restrict(r1, createSet());
        exp2.restrict(r2, createSet());

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict a variable to true in a two variable expression
     */
    @Test
    public final void testRestrictTwoVariableExpressionOneFalse1() {
        SyntaxTree tTest = new SyntaxTree("0 1 or");
        SyntaxTree tRef = new SyntaxTree("0 1 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(0);
        Set<Integer> r2 = createSet(0);

        exp1.restrict(createSet(), r1);
        exp2.restrict(createSet(), r2);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict the other variable to true in a two variable expression
     */
    @Test
    public final void testRestrictTwoVariableExpressionOneFalse2() {
        SyntaxTree tTest = new SyntaxTree("0 1 or");
        SyntaxTree tRef = new SyntaxTree("0 1 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(1);
        Set<Integer> r2 = createSet(1);

        exp1.restrict(createSet(), r1);
        exp2.restrict(createSet(), r2);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict two variables to true in a two variable expression
     */
    @Test
    public final void testRestrictTwoVariableExpressionTwoTrue() {
        SyntaxTree tTest = new SyntaxTree("0 1 or");
        SyntaxTree tRef = new SyntaxTree("0 1 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(0, 1);
        Set<Integer> r2 = createSet(0, 1);

        exp1.restrict(r1, createSet());
        exp2.restrict(r2, createSet());

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict two variables to false in a two variable expression
     */
    @Test
    public final void testRestrictTwoVariableExpressionTwoFalse() {
        SyntaxTree tTest = new SyntaxTree("0 1 or");
        SyntaxTree tRef = new SyntaxTree("0 1 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(0, 1);
        Set<Integer> r2 = createSet(0, 1);

        exp1.restrict(createSet(), r1);
        exp2.restrict(createSet(), r2);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict one variable true, one variable false in a two variable
     * expression
     */
    @Test
    public final void testRestrictTwoVariableExpressionOneTrueOneFalse1() {
        SyntaxTree tTest = new SyntaxTree("0 1 or");
        SyntaxTree tRef = new SyntaxTree("0 1 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1A = createSet(0);
        Set<Integer> r1B = createSet(1);
        Set<Integer> r2A = createSet(0);
        Set<Integer> r2B = createSet(1);

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict one variable true, one variable false in a two variable
     * expression
     */
    @Test
    public final void testRestrictTwoVariableExpressionOneTrueOneFalse2() {
        SyntaxTree tTest = new SyntaxTree("0 1 or");
        SyntaxTree tRef = new SyntaxTree("0 1 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1A = createSet(1);
        Set<Integer> r1B = createSet(0);
        Set<Integer> r2A = createSet(1);
        Set<Integer> r2B = createSet(0);

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict one variable true in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionOneTrue1() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(0);
        Set<Integer> r2 = createSet(0);

        exp1.restrict(r1, createSet());
        exp2.restrict(r2, createSet());

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict one variable true in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionOneTrue2() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(1);
        Set<Integer> r2 = createSet(1);

        exp1.restrict(r1, createSet());
        exp2.restrict(r2, createSet());

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict one variable true in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionOneTrue3() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(2);
        Set<Integer> r2 = createSet(2);

        exp1.restrict(r1, createSet());
        exp2.restrict(r2, createSet());

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict one variable false in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionOneFalse1() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(0);
        Set<Integer> r2 = createSet(0);

        exp1.restrict(createSet(), r1);
        exp2.restrict(createSet(), r2);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict one variable false in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionOneFalse2() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(1);
        Set<Integer> r2 = createSet(1);

        exp1.restrict(createSet(), r1);
        exp2.restrict(createSet(), r2);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict one variable false in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionOneFalse3() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(2);
        Set<Integer> r2 = createSet(2);

        exp1.restrict(createSet(), r1);
        exp2.restrict(createSet(), r2);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict two variables true in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionTwoTrue1() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(0, 1);
        Set<Integer> r2 = createSet(0, 1);

        exp1.restrict(r1, createSet());
        exp2.restrict(r2, createSet());

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict two variables true in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionTwoTrue2() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(0, 2);
        Set<Integer> r2 = createSet(0, 2);

        exp1.restrict(r1, createSet());
        exp2.restrict(r2, createSet());

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict two variables true in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionTwoTrue3() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(1, 2);
        Set<Integer> r2 = createSet(1, 2);

        exp1.restrict(r1, createSet());
        exp2.restrict(r2, createSet());

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict two variables false in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionTwoFalse1() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(0, 1);
        Set<Integer> r2 = createSet(0, 1);

        exp1.restrict(createSet(), r1);
        exp2.restrict(createSet(), r2);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict two variables false in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionTwoFalse2() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(0, 2);
        Set<Integer> r2 = createSet(0, 2);

        exp1.restrict(createSet(), r1);
        exp2.restrict(createSet(), r2);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict two variables false in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionTwoFalse3() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(1, 2);
        Set<Integer> r2 = createSet(1, 2);

        exp1.restrict(createSet(), r1);
        exp2.restrict(createSet(), r2);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict two variables true, one false in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionTwoTrueOneFalse1() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1A = createSet(0, 1);
        Set<Integer> r1B = createSet(2);
        Set<Integer> r2A = createSet(0, 1);
        Set<Integer> r2B = createSet(2);

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict two variables true, one false in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionTwoTrueOneFalse2() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1A = createSet(0, 2);
        Set<Integer> r1B = createSet(1);
        Set<Integer> r2A = createSet(0, 2);
        Set<Integer> r2B = createSet(1);

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict two variables true, one false in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionTwoTrueOneFalse3() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1A = createSet(1, 2);
        Set<Integer> r1B = createSet(0);
        Set<Integer> r2A = createSet(1, 2);
        Set<Integer> r2B = createSet(0);

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict two variables true, one false in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionTwoFalseOneTrue1() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1A = createSet(2);
        Set<Integer> r1B = createSet(0, 1);
        Set<Integer> r2A = createSet(2);
        Set<Integer> r2B = createSet(0, 1);

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict two variables true, one false in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionTwoFalseOneTrue2() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1A = createSet(1);
        Set<Integer> r1B = createSet(0, 2);
        Set<Integer> r2A = createSet(1);
        Set<Integer> r2B = createSet(0, 2);

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict two variables true, one false in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionTwoFalseOneTrue3() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1A = createSet(0);
        Set<Integer> r1B = createSet(1, 2);
        Set<Integer> r2A = createSet(0);
        Set<Integer> r2B = createSet(1, 2);

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict three variables true in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionThreeTrue() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(0, 1, 2);
        Set<Integer> r2 = createSet(0, 1, 2);

        exp1.restrict(r1, createSet());
        exp2.restrict(r2, createSet());

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict three variables false in a three variable expression
     */
    @Test
    public final void testRestrictThreeVariableExpressionThreeFalse() {
        SyntaxTree tTest = new SyntaxTree("0 1 and 2 or");
        SyntaxTree tRef = new SyntaxTree("0 1 and 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1 = createSet(0, 1, 2);
        Set<Integer> r2 = createSet(0, 1, 2);

        exp1.restrict(createSet(), r1);
        exp2.restrict(createSet(), r2);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict one variable true in a four variable expression
     */
    @Test
    public final void testRestrictFourVariableExpressionOneTrue() {
        SyntaxTree tTest = new SyntaxTree("1 not 2 and 3 or 4 and");
        SyntaxTree tRef = new SyntaxTree("1 not 2 and 3 or 4 and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1A = createSet(1);
        Set<Integer> r1B = createSet();
        Set<Integer> r2A = createSet(1);
        Set<Integer> r2B = createSet();

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict one variable false in a four variable expression
     */
    @Test
    public final void testRestrictFourVariableExpressionOneFalse() {
        SyntaxTree tTest = new SyntaxTree("1 not 2 and 3 or 4 and");
        SyntaxTree tRef = new SyntaxTree("1 not 2 and 3 or 4 and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1A = createSet();
        Set<Integer> r1B = createSet(4);
        Set<Integer> r2A = createSet();
        Set<Integer> r2B = createSet(4);

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict two variables true, one variable false in a four variable
     * expression
     */
    @Test
    public final void testRestrictFourVariableExpressionTwoTrueOneFalse() {
        SyntaxTree tTest = new SyntaxTree("1 not 2 and 3 or 4 and");
        SyntaxTree tRef = new SyntaxTree("1 not 2 and 3 or 4 and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1A = createSet(1, 3);
        Set<Integer> r1B = createSet(2);
        Set<Integer> r2A = createSet(1, 3);
        Set<Integer> r2B = createSet(2);

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict three variables true in a four variable expression
     */
    @Test
    public final void testRestrictFourVariableExpressionThreeTrue() {
        SyntaxTree tTest = new SyntaxTree("1 not 2 and 3 or 4 and");
        SyntaxTree tRef = new SyntaxTree("1 not 2 and 3 or 4 and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        Set<Integer> r1A = createSet(1, 3, 4);
        Set<Integer> r1B = createSet();
        Set<Integer> r2A = createSet(1, 3, 4);
        Set<Integer> r2B = createSet();

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict one variable true in a six variable expression
     */
    @Test
    public final void testRestrictSixVariableExpressionOneTrue() {
        SyntaxTree tTest = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");
        SyntaxTree tRef = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        exp1.reorder(createSequence(1, 3, 5, 2, 4, 6));
        exp2.reorder(createSequence(1, 3, 5, 2, 4, 6));

        Set<Integer> r1A = createSet(2);
        Set<Integer> r1B = createSet();
        Set<Integer> r2A = createSet(2);
        Set<Integer> r2B = createSet();

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict one variable false in a six variable expression
     */
    @Test
    public final void testRestrictSixVariableExpressionOneFalse() {
        SyntaxTree tTest = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");
        SyntaxTree tRef = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        exp1.reorder(createSequence(1, 3, 5, 2, 4, 6));
        exp2.reorder(createSequence(1, 3, 5, 2, 4, 6));

        Set<Integer> r1A = createSet();
        Set<Integer> r1B = createSet(1);
        Set<Integer> r2A = createSet();
        Set<Integer> r2B = createSet(1);

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict one variable true, one variable false in a six variable
     * expression
     */
    @Test
    public final void testRestrictSixVariableExpressionOneTrueOneFalse() {
        SyntaxTree tTest = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");
        SyntaxTree tRef = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        exp1.reorder(createSequence(1, 3, 5, 2, 4, 6));
        exp2.reorder(createSequence(1, 3, 5, 2, 4, 6));

        Set<Integer> r1A = createSet(3);
        Set<Integer> r1B = createSet(5);
        Set<Integer> r2A = createSet(3);
        Set<Integer> r2B = createSet(5);

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict two variables true, one variable false in a six variable
     * expression
     */
    @Test
    public final void testRestrictSixVariableExpressionTwoTrueOneFalse() {
        SyntaxTree tTest = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");
        SyntaxTree tRef = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        exp1.reorder(createSequence(1, 3, 5, 2, 4, 6));
        exp2.reorder(createSequence(1, 3, 5, 2, 4, 6));

        Set<Integer> r1A = createSet(3, 5);
        Set<Integer> r1B = createSet(4);
        Set<Integer> r2A = createSet(3, 5);
        Set<Integer> r2B = createSet(4);

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict four variables true in a six variable expression
     */
    @Test
    public final void testRestrictSixVariableExpressionFourTrue() {
        SyntaxTree tTest = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");
        SyntaxTree tRef = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        exp1.reorder(createSequence(1, 3, 5, 2, 4, 6));
        exp2.reorder(createSequence(1, 3, 5, 2, 4, 6));

        Set<Integer> r1A = createSet(3, 5, 2, 4);
        Set<Integer> r1B = createSet();
        Set<Integer> r2A = createSet(3, 5, 2, 4);
        Set<Integer> r2B = createSet();

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict four variables false in a six variable expression
     */
    @Test
    public final void testRestrictSixVariableExpressionFourFalse() {
        SyntaxTree tTest = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");
        SyntaxTree tRef = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        exp1.reorder(createSequence(1, 3, 5, 2, 4, 6));
        exp2.reorder(createSequence(1, 3, 5, 2, 4, 6));

        Set<Integer> r1A = createSet();
        Set<Integer> r1B = createSet(1, 5, 2, 6);
        Set<Integer> r2A = createSet();
        Set<Integer> r2B = createSet(1, 5, 2, 6);

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict three true, one false in a six variable expression
     */
    @Test
    public final void testRestrictSixVariableExpressionThreeTrueOneFalse() {
        SyntaxTree tTest = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");
        SyntaxTree tRef = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        exp1.reorder(createSequence(1, 3, 5, 2, 4, 6));
        exp2.reorder(createSequence(1, 3, 5, 2, 4, 6));

        Set<Integer> r1A = createSet(2, 4, 6);
        Set<Integer> r1B = createSet(1, 5);
        Set<Integer> r2A = createSet(2, 4, 6);
        Set<Integer> r2B = createSet(1, 5);

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict three false, one true in a six variable expression
     */
    @Test
    public final void testRestrictSixVariableExpressionThreeFalseOneTrue() {
        SyntaxTree tTest = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");
        SyntaxTree tRef = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        exp1.reorder(createSequence(1, 3, 5, 2, 4, 6));
        exp2.reorder(createSequence(1, 3, 5, 2, 4, 6));

        Set<Integer> r1A = createSet(2, 4);
        Set<Integer> r1B = createSet(1, 5, 6);
        Set<Integer> r2A = createSet(2, 4);
        Set<Integer> r2B = createSet(1, 5, 6);

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict six variables true in a six variable expression
     */
    @Test
    public final void testRestrictSixVariableExpressionSixTrue() {
        SyntaxTree tTest = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");
        SyntaxTree tRef = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        exp1.reorder(createSequence(1, 3, 5, 2, 4, 6));
        exp2.reorder(createSequence(1, 3, 5, 2, 4, 6));

        Set<Integer> r1A = createSet(1, 3, 5, 2, 4, 6);
        Set<Integer> r1B = createSet();
        Set<Integer> r2A = createSet(1, 3, 5, 2, 4, 6);
        Set<Integer> r2B = createSet();

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }

    /*
     * Restrict six variables false in a six variable expression
     */
    @Test
    public final void testRestrictSixVariableExpressionSixFalse() {
        SyntaxTree tTest = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");
        SyntaxTree tRef = new SyntaxTree("1 2 or 3 4 or and 5 6 or and");

        BooleanStructure exp1 = this.constructorTest(tTest);
        BooleanStructure exp2 = this.constructorRef(tRef);

        exp1.reorder(createSequence(1, 3, 5, 2, 4, 6));
        exp2.reorder(createSequence(1, 3, 5, 2, 4, 6));

        Set<Integer> r1A = createSet();
        Set<Integer> r1B = createSet(1, 3, 5, 2, 4, 6);
        Set<Integer> r2A = createSet();
        Set<Integer> r2B = createSet(1, 3, 5, 2, 4, 6);

        exp1.restrict(r1A, r1B);
        exp2.restrict(r2A, r2B);

        assertEquals(exp2, exp1);
    }
}
