package components.booleanstructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import components.set.Set;
import components.set.Set2;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class BooleanStructureTest extends BooleanStructureKernelTest {

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
        BooleanStructure exp1 = this.constructorTest();
        BooleanStructure exp2 = this.constructorRef();

        assertEquals(exp2.toString(), exp1.toString());

        assertEquals(exp2, exp1);
    }

    /*
     * String representation of a False expression
     */
    @Test
    public final void testToStringFalseExpression() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorRef(false);

        assertEquals(exp2.toString(), exp1.toString());

        assertEquals(exp2, exp1);
    }

    /*
     * String representation of single variable expressions
     */
    @Test
    public final void testToStringSingleVariableExpressions() {
        BooleanStructure exp1 = this.constructorTest(18);
        BooleanStructure exp2 = this.constructorRef(18);

        assertEquals(exp2.toString(), exp1.toString());

        assertEquals(exp2, exp1);
    }

    /*
     * toStringTT Tests
     */

    /*
     * Truth table of True expression
     */
    @Test
    public final void testToStringTTTrueExpression() {
        BooleanStructure exp1 = this.constructorTest();
        BooleanStructure exp2 = this.constructorRef();

        assertEquals(exp2.toStringTT(), exp1.toStringTT());

        assertEquals(exp2, exp1);
    }

    /*
     * Truth table of False expression
     */
    @Test
    public final void testToStringTTFalseExpression() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorRef(false);

        assertEquals(exp2.toStringTT(), exp1.toStringTT());

        assertEquals(exp2, exp1);
    }

    /*
     * Truth table of single variable expressions
     */
    @Test
    public final void testToStringTTSingleVariableExpressions() {
        BooleanStructure exp1 = this.constructorTest(18);
        BooleanStructure exp2 = this.constructorTest(1);
        BooleanStructure exp3 = this.constructorRef(18);
        BooleanStructure exp4 = this.constructorRef(1);

        assertEquals(exp3.toStringTT(), exp1.toStringTT());
        assertEquals(exp4.toStringTT(), exp2.toStringTT());

        assertEquals(exp3, exp1);
        assertEquals(exp4, exp2);
    }

    /*
     * isSat Tests
     */

    /*
     * Satisfiability for a True expression
     */
    @Test
    public final void testIsSatTrueExpression() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorRef(true);

        assertEquals(exp2.isSat(), exp1.isSat());

        assertEquals(exp2, exp1);
    }

    /*
     * Satisfiability for a False expression
     */
    @Test
    public final void testIsSatFalseExpression() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorRef(false);

        assertEquals(exp2.isSat(), exp1.isSat());

        assertEquals(exp2, exp1);
    }

    /*
     * Satisfiability for a single variable expression which is satisfiable
     */
    @Test
    public final void testIsSatSingleVariableExpressionTrue() {
        BooleanStructure exp1 = this.constructorTest(300);
        BooleanStructure exp2 = this.constructorRef(300);

        assertEquals(exp2.isSat(), exp1.isSat());

        assertEquals(exp2, exp1);
    }

    // TODO - Add tests for multiple variable expressions

    /*
     * isValid tests
     */

    /*
     * Validity for the True Expression
     */
    @Test
    public final void testIsValidTrueExpression() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorRef(true);

        assertEquals(exp2.isValid(), exp1.isValid());

        assertEquals(exp2, exp1);
    }

    /*
     * Validity for the False Expression
     */
    @Test
    public final void testIsValidFalseExpression() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorRef(false);

        assertEquals(exp2.isValid(), exp1.isValid());

        assertEquals(exp2, exp1);
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
        BooleanStructure exp2 = this.constructorRef(true);

        assertEquals(exp2.numVariables(), exp1.numVariables());

        assertEquals(exp2, exp1);
    }

    /*
     * numVariables for expressions with 1 variable
     */
    @Test
    public final void testNumVariablesSingleVariableExpressions() {
        BooleanStructure exp1 = this.constructorTest(0);
        BooleanStructure exp2 = this.constructorRef(0);

        assertEquals(exp2.numVariables(), exp1.numVariables());

        assertEquals(exp2, exp1);
    }

    /*
     * numVariables for expressions with 5 variable
     */
    @Test
    public final void testNumVariablesThreeVariableExpressions() {
        SyntaxTree tTest1 = new SyntaxTree("1 2 and 3 and");
        SyntaxTree tTest2 = new SyntaxTree("1 2 and 3 and");
        BooleanStructure exp1 = this.constructorTest(tTest1);
        BooleanStructure exp2 = this.constructorTest(tTest2);

        assertEquals(exp2.numVariables(), exp1.numVariables());

        assertEquals(exp2, exp1);
    }

    /*
     * Equivalent Tests
     */

    /*
     * Equivalent on two True Expressions
     */
    @Test
    public final void testEquivalent2TrueExpressions() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorRef(true);

        assertTrue(exp1.isEquivalent(exp2));
    }

    /*
     * Equivalent on a True Expression and False Expression
     */
    @Test
    public final void testEquivalentTrueExpressionFalseExpression() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorRef(false);

        assertFalse(exp1.isEquivalent(exp2));
    }

    /*
     * Equivalent on two False Expressions
     */
    @Test
    public final void testEquivalent2FalseExpressions() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorTest(false);

        assertTrue(exp1.isEquivalent(exp2));
    }

    /*
     * Equivalent on False and a Single Variable Expression
     */
    @Test
    public final void testEquivalentFalseAnd1VariableExpressions() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorTest(1);

        assertFalse(exp1.isEquivalent(exp2));
        assertFalse(exp2.isEquivalent(exp1));
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
        assertFalse(exp1.isEquivalent(exp3));

        assertTrue(exp2.isEquivalent(exp1));
        assertFalse(exp3.isEquivalent(exp1));
    }

    /*
     * Equivalent on Two Variable Expressions
     */
    @Test
    public final void testEquivalent2VariableExpressionsSameVariables() {
        SyntaxTree tTest1 = new SyntaxTree("1 2 and");
        SyntaxTree tTest2 = new SyntaxTree("1 2 and");
        SyntaxTree tTest3 = new SyntaxTree("1 2 or");

        BooleanStructure exp1 = this.constructorTest(tTest1);
        BooleanStructure exp2 = this.constructorTest(tTest2);
        BooleanStructure exp3 = this.constructorTest(tTest3);

        assertTrue(exp1.isEquivalent(exp2));
        assertFalse(exp1.isEquivalent(exp3));

        assertTrue(exp2.isEquivalent(exp1));
        assertFalse(exp3.isEquivalent(exp1));
    }

    /*
     * Equivalent on Two Variable Expressions (different variables)
     */
    @Test
    public final void testEquivalent2VariableExpressionsDifferentVariables() {
        SyntaxTree tTest1 = new SyntaxTree("1 2 and");
        SyntaxTree tTest2 = new SyntaxTree("1 2 and 3 and");
        SyntaxTree tTest3 = new SyntaxTree("1 3 or");

        BooleanStructure exp1 = this.constructorTest(tTest1);
        BooleanStructure exp2 = this.constructorTest(tTest2);
        BooleanStructure exp3 = this.constructorTest(tTest3);

        assertFalse(exp1.isEquivalent(exp2));
        assertFalse(exp1.isEquivalent(exp3));

        assertFalse(exp2.isEquivalent(exp3));
        assertFalse(exp3.isEquivalent(exp1));
    }

    /*
     * Equivalent for a double negation
     */
    @Test
    public final void testEquivalentDoubleNegation() {
        SyntaxTree tTest1 = new SyntaxTree("1 not not");
        SyntaxTree tTest2 = new SyntaxTree("1");

        BooleanStructure exp1 = this.constructorTest(tTest1);
        BooleanStructure exp2 = this.constructorTest(tTest2);

        assertTrue(exp1.isEquivalent(exp2));
        assertTrue(exp2.isEquivalent(exp1));
    }

    /*
     * Equivalent for DeMorgan's Law (OR)
     */
    @Test
    public final void testEquivalentDeMorgansOr() {
        SyntaxTree tTest1 = new SyntaxTree("12 18 or not");
        SyntaxTree tTest2 = new SyntaxTree("12 not 18 not and");

        BooleanStructure exp1 = this.constructorTest(tTest1);
        BooleanStructure exp2 = this.constructorTest(tTest2);

        assertTrue(exp1.isEquivalent(exp2));
        assertTrue(exp2.isEquivalent(exp1));
    }

    /*
     * Equivalent for DeMorgan's Law (AND)
     */
    @Test
    public final void testEquivalentDeMorgansAND() {
        SyntaxTree tTest1 = new SyntaxTree("12 18 and not");
        SyntaxTree tTest2 = new SyntaxTree("12 not 18 not or");

        BooleanStructure exp1 = this.constructorTest(tTest1);
        BooleanStructure exp2 = this.constructorTest(tTest2);

        assertTrue(exp1.isEquivalent(exp2));
        assertTrue(exp2.isEquivalent(exp1));
    }

    /*
     * Equivalent for a single variable tautology
     */
    @Test
    public final void testEquivalentSingleVariableTautology() {
        SyntaxTree tTest1 = new SyntaxTree("14 not 14 or");
        SyntaxTree tTest2 = new SyntaxTree("T");

        BooleanStructure exp1 = this.constructorTest(tTest1);
        BooleanStructure exp2 = this.constructorTest(tTest2);

        assertTrue(exp1.isEquivalent(exp2));
        assertTrue(exp2.isEquivalent(exp1));
    }

    /*
     * Equivalent for a single variable contradiction
     */
    @Test
    public final void testEquivalentSingleVariableContradiction() {
        SyntaxTree tTest1 = new SyntaxTree("15 not 15 and");
        SyntaxTree tTest2 = new SyntaxTree("F");

        BooleanStructure exp1 = this.constructorTest(tTest1);
        BooleanStructure exp2 = this.constructorTest(tTest2);

        assertTrue(exp1.isEquivalent(exp2));
        assertTrue(exp2.isEquivalent(exp1));
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
     * hashCode Tests
     */

    @Test
    public final void testHashCodeTrueExpression() {
        BooleanStructure exp1 = this.constructorTest(true);
        BooleanStructure exp2 = this.constructorRef(true);
        BooleanStructure exp3 = this.constructorRef();
        BooleanStructure exp4 = this.constructorRef(false);

        assertEquals(exp1.hashCode(), exp2.hashCode());
        assertEquals(exp2.hashCode(), exp3.hashCode());
        assertFalse(exp1.hashCode() == exp4.hashCode());

    }

    @Test
    public final void testHashCodeFalseExpression() {
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorRef(false);
        BooleanStructure exp3 = this.constructorRef(true);

        assertEquals(exp1.hashCode(), exp2.hashCode());
        assertFalse(exp1.hashCode() == exp3.hashCode());

    }

    @Test
    public final void testHashCodeSingleVariableExpression() {
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorRef(1);
        BooleanStructure exp3 = this.constructorRef(true);
        BooleanStructure exp4 = this.constructorRef(false);

        assertEquals(exp1.hashCode(), exp2.hashCode());
        assertFalse(exp1.hashCode() == exp3.hashCode());
        assertFalse(exp1.hashCode() == exp4.hashCode());

    }

    /*
     * copyFrom tests
     */

    /*
     * Copy a true structure into a true structure
     */
    @Test
    public final void testCopyTrueFromTrue() {
        BooleanStructure exp1 = this.constructorTest();
        BooleanStructure exp2 = this.constructorTest();
        BooleanStructure exp3 = this.constructorRef();

        exp1.copyFrom(exp2);
        assertEquals(exp3, exp1);
        assertEquals(exp3, exp2);
    }

    /*
     * Copy a false structure into a true structure
     */
    @Test
    public final void testCopyTrueFromFalse() {
        BooleanStructure exp1 = this.constructorTest();
        BooleanStructure exp2 = this.constructorTest(false);
        BooleanStructure exp3 = this.constructorRef(false);

        exp1.copyFrom(exp2);
        assertEquals(exp3, exp1);
        assertEquals(exp3, exp2);
    }

    /*
     * Copy a single variable structure into a true structure
     */
    @Test
    public final void testCopyTrueFromSingleVariable() {
        BooleanStructure exp1 = this.constructorTest();
        BooleanStructure exp2 = this.constructorTest(3);
        BooleanStructure exp3 = this.constructorRef(3);

        exp1.copyFrom(exp2);
        assertEquals(exp3, exp1);
        assertEquals(exp3, exp2);
    }

    /*
     * Copy a single variable structure into a single variable structure
     */
    @Test
    public final void testCopySingleVariableFromSingleVariable() {
        BooleanStructure exp1 = this.constructorTest(5);
        BooleanStructure exp2 = this.constructorTest(3);
        BooleanStructure exp3 = this.constructorRef(3);

        exp1.copyFrom(exp2);
        assertEquals(exp3, exp1);
        assertEquals(exp3, exp2);
    }

    /*
     * Copy a two variable structure into true
     */
    @Test
    public final void testCopyTrueFromTwoVariable() {
        SyntaxTree st = new SyntaxTree("1 2 or");
        BooleanStructure exp1 = this.constructorTest();
        BooleanStructure exp2 = this.constructorTest(st);
        BooleanStructure exp3 = this.constructorRef(st);

        exp1.copyFrom(exp2);
        assertEquals(exp3, exp1);
        assertEquals(exp3, exp2);
    }

    /*
     * Copy a two variable structure into a single variable structure
     */
    @Test
    public final void testCopySingleVariableFromTwoVariable() {
        SyntaxTree st = new SyntaxTree("1 2 or");
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorTest(st);
        BooleanStructure exp3 = this.constructorRef(st);

        exp1.copyFrom(exp2);
        assertEquals(exp3, exp1);
        assertEquals(exp3, exp2);
    }

    /*
     * Copy a two variable structure into a single variable structure
     */
    @Test
    public final void testCopyTwoVariableFromTwoVariable() {
        SyntaxTree st = new SyntaxTree("1 2 or");
        SyntaxTree st2 = new SyntaxTree("1 2 and");
        BooleanStructure exp1 = this.constructorTest(st2);
        BooleanStructure exp2 = this.constructorTest(st);
        BooleanStructure exp3 = this.constructorRef(st);

        exp1.copyFrom(exp2);
        assertEquals(exp3, exp1);
        assertEquals(exp3, exp2);
    }

    /*
     * Copy a two variable structure into a single variable structure
     */
    @Test
    public final void testCopyTrueFromThreeVariable() {
        SyntaxTree st = new SyntaxTree("0 1 and 2 and not");
        BooleanStructure exp1 = this.constructorTest();
        BooleanStructure exp2 = this.constructorTest(st);
        BooleanStructure exp3 = this.constructorRef(st);

        exp1.copyFrom(exp2);
        assertEquals(exp3, exp1);
        assertEquals(exp3, exp2);
    }

    /*
     * Copy a two variable structure into a single variable structure
     */
    @Test
    public final void testCopySingleVariableFromThreeVariable() {
        SyntaxTree st = new SyntaxTree("0 1 and 2 and not");
        BooleanStructure exp1 = this.constructorTest(1);
        BooleanStructure exp2 = this.constructorTest(st);
        BooleanStructure exp3 = this.constructorRef(st);

        exp1.copyFrom(exp2);
        assertEquals(exp3, exp1);
        assertEquals(exp3, exp2);
    }

    /*
     * Copy a four variable structure into a single variable structure
     */
    @Test
    public final void testCopyFalseFrom4Variable() {
        SyntaxTree st = new SyntaxTree("0 1 and 2 3 or and");
        BooleanStructure exp1 = this.constructorTest(false);
        BooleanStructure exp2 = this.constructorTest(st);
        BooleanStructure exp3 = this.constructorRef(st);

        exp1.copyFrom(exp2);
        assertEquals(exp3, exp1);
        assertEquals(exp3, exp2);
    }

    /*
     * Copy a four variable structure into a single variable structure
     */
    @Test
    public final void testCopyTwoVariableFrom4Variable() {
        SyntaxTree st = new SyntaxTree("0 1 and 2 3 or and");
        SyntaxTree st2 = new SyntaxTree("2 3 or not");
        BooleanStructure exp1 = this.constructorTest(st2);
        BooleanStructure exp2 = this.constructorTest(st);
        BooleanStructure exp3 = this.constructorRef(st);

        exp1.copyFrom(exp2);
        assertEquals(exp3, exp1);
        assertEquals(exp3, exp2);
    }

    /*
     * Copy should be independent of original
     */
    @Test
    public final void testCopyIsIndependent() {
        SyntaxTree t = new SyntaxTree("2 3 or 1 and");

        BooleanStructure x = this.constructorTest(t);
        BooleanStructure old = this.constructorRef(t);
        x.reorder(createSequence(1, 2, 3));
        old.reorder(createSequence(1, 2, 3));

        BooleanStructure x2 = x.newInstance();
        x2.copyFrom(x);

        //preserves x
        assertEquals(old, x);
        //x2 = x
        assertEquals(x2, x);

        x.negate();

        //x2 has not changed
        assertEquals(old, x2);
        //x has changed
        assertFalse(x.equals(old));
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
