package components.booleanstructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.net.URL;

import org.junit.Ignore;
import org.junit.Test;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

public class SyntaxTreeTest {

    /**
     * Constructor Test Cases
     */
    @Test
    public final void testStringConstructorTrueExpression() {
        SyntaxTree st = new SyntaxTree("T");

        assertEquals(1, st.size());
        assertEquals("T", st.label());
        assertNull(st.right());
        assertNull(st.left());
    }

    @Test
    public final void testStringConstructorFalseExpression() {
        SyntaxTree st = new SyntaxTree("F");

        assertEquals(1, st.size());
        assertEquals("F", st.label());
        assertNull(st.right());
        assertNull(st.left());
    }

    @Test
    public final void testStringConstructorSingleVariableExpression() {
        SyntaxTree st = new SyntaxTree("0");

        assertEquals(1, st.size());
        assertEquals("0", st.label());
        assertNull(st.right());
        assertNull(st.left());
    }

    // not( 0 )
    @Test
    public final void testStringConstructorSingleVariableExpressionNot() {
        SyntaxTree st = new SyntaxTree("0 not");

        assertEquals(2, st.size());
        assertEquals("not", st.label());
        assertEquals("0", st.right().label());
        assertNull(st.left());
    }

    // ( 0 and 1 )
    @Test
    public final void testStringConstructorTwoVariableExpressionAnd() {
        SyntaxTree st = new SyntaxTree("0 1 and");

        assertEquals(3, st.size());
        assertEquals("and", st.label());
        assertEquals("1", st.right().label());
        assertEquals("0", st.left().label());

    }

    // ( 0 and T )
    @Test
    public final void testStringConstructorTwoVariableExpressionAndTrueSingleVariable() {
        SyntaxTree st = new SyntaxTree("0 T and");

        assertEquals(3, st.size());
        assertEquals("and", st.label());
        assertEquals("T", st.right().label());
        assertEquals("0", st.left().label());

    }

    // ( 0 or 1 )
    @Test
    public final void testStringConstructorTwoVariableExpressionOr() {
        SyntaxTree st = new SyntaxTree("0 1 or");

        assertEquals(3, st.size());
        assertEquals("or", st.label());
        assertEquals("1", st.right().label());
        assertEquals("0", st.left().label());
    }

    // not( ( 0 or 1 ) )
    @Test
    public final void testStringConstructorTwoVariableExpressionOrNot() {
        SyntaxTree st = new SyntaxTree("0 1 or not");

        assertEquals(4, st.size());
        assertEquals("not", st.label());
        assertEquals("or", st.right().label());
        assertEquals("1", st.right().right().label());
        assertEquals("0", st.right().left().label());
    }

    // not( ( 0 and 1 ) )
    @Test
    public final void testStringConstructorTwoVariableExpressionAndNot() {
        SyntaxTree st = new SyntaxTree("0 1 and not");

        assertEquals(4, st.size());
        assertEquals("not", st.label());
        assertEquals("and", st.right().label());
        assertEquals("1", st.right().right().label());
        assertEquals("0", st.right().left().label());
    }

    // ( not( 0 ) and not( 1 ) )
    @Test
    public final void testStringConstructorTwoVariableExpressionNotsAnd() {
        SyntaxTree st = new SyntaxTree("0 not 1 not and");

        assertEquals(5, st.size());
        assertEquals("and", st.label());
        assertEquals("not", st.right().label());
        assertEquals("not", st.left().label());
        assertEquals("1", st.right().right().label());
        assertEquals("0", st.left().right().label());
    }

    // ( ( 0 and 1 ) and 2 )
    @Test
    public final void testStringConstructorThreeVariableExpressionAnds() {
        SyntaxTree st = new SyntaxTree("0 1 and 2 and");

        assertEquals(5, st.size());
        assertEquals("and", st.label());
        assertEquals("2", st.right().label());
        assertEquals("and", st.left().label());
        assertEquals("1", st.left().right().label());
        assertEquals("0", st.left().left().label());
    }

    // ( ( 0 or 1 ) or 2 )
    @Test
    public final void testStringConstructorThreeVariableExpressionOrs() {
        SyntaxTree st = new SyntaxTree("0 1 or 2 or");

        assertEquals(5, st.size());
        assertEquals("or", st.label());
        assertEquals("2", st.right().label());
        assertEquals("or", st.left().label());
        assertEquals("1", st.left().right().label());
        assertEquals("0", st.left().left().label());
    }

    // not( ( ( 0 and 1 ) and 2 ) )
    @Test
    public final void testStringConstructorThreeVariableExpressionAndsNot() {
        SyntaxTree st = new SyntaxTree("0 1 and 2 and not");

        assertEquals(6, st.size());
        assertEquals("not", st.label());
        assertEquals("and", st.right().label());
        assertEquals("2", st.right().right().label());
        assertEquals("and", st.right().left().label());
        assertEquals("1", st.right().left().right().label());
        assertEquals("0", st.right().left().left().label());
    }

    // ( ( 0 and 1 ) and ( 2 and 3 ) )
    @Test
    public final void testStringConstructorFourVariableExpressionAnds() {
        SyntaxTree st = new SyntaxTree("0 1 and 2 3 and and");

        assertEquals(7, st.size());
        assertEquals("and", st.label());
        assertEquals("and", st.left().label());
        assertEquals("1", st.left().right().label());
        assertEquals("0", st.left().left().label());
        assertEquals("and", st.right().label());
        assertEquals("3", st.right().right().label());
        assertEquals("2", st.right().left().label());
    }

    // ( ( 0 or 1 ) or ( 2 or 3 ) )
    @Test
    public final void testStringConstructorFourVariableExpressionOrs() {
        SyntaxTree st = new SyntaxTree("0 1 or 2 3 or or");

        assertEquals(7, st.size());
        assertEquals("or", st.label());
        assertEquals("or", st.left().label());
        assertEquals("1", st.left().right().label());
        assertEquals("0", st.left().left().label());
        assertEquals("or", st.right().label());
        assertEquals("3", st.right().right().label());
        assertEquals("2", st.right().left().label());
    }

    // ( ( 0 and 1 ) and ( 2 or 3 ) )
    @Test
    public final void testStringConstructorFourVariableExpressionAndsOrs() {
        SyntaxTree st = new SyntaxTree("0 1 and 2 3 or and");

        assertEquals(7, st.size());
        assertEquals("and", st.label());
        assertEquals("and", st.left().label());
        assertEquals("1", st.left().right().label());
        assertEquals("0", st.left().left().label());
        assertEquals("or", st.right().label());
        assertEquals("3", st.right().right().label());
        assertEquals("2", st.right().left().label());
    }

    // not( ( not( ( 1 and 2 ) ) or ( ( 3 and 4 ) and 5 ) ) )
    @Test
    public final void testStringConstructorComplexExpression1() {
        SyntaxTree st = new SyntaxTree("1 2 and not 3 4 and 5 and or not");

        assertEquals(11, st.size());
        assertEquals("not", st.label());
        assertEquals("or", st.right().label());
        assertEquals("not", st.right().left().label());
        assertEquals("and", st.right().left().right().label());
        assertEquals("1", st.right().left().right().left().label());
        assertEquals("2", st.right().left().right().right().label());
        assertEquals("and", st.right().right().label());
        assertEquals("and", st.right().right().left().label());
        assertEquals("3", st.right().right().left().left().label());
        assertEquals("4", st.right().right().left().right().label());
        assertEquals("5", st.right().right().right().label());
    }

    // ( ( 0 or 2 ) and 3 ) or ( 15 and ( 2 and 4 ) )
    @Test
    public final void testStringConstructorComplexExpression2() {
        SyntaxTree st = new SyntaxTree("0 2 or 3 and 15 2 4 and and or");

        assertEquals(11, st.size());
        assertEquals("or", st.label());
        assertEquals("and", st.right().label());
        assertEquals("and", st.right().right().label());
        assertEquals("2", st.right().right().left().label());
        assertEquals("4", st.right().right().right().label());
        assertEquals("15", st.right().left().label());
        assertEquals("and", st.left().label());
        assertEquals("3", st.left().right().label());
        assertEquals("or", st.left().left().label());
        assertEquals("2", st.left().left().right().label());
        assertEquals("0", st.left().left().left().label());
    }

    // ( ( 18 and ( 12 or 32 ) ) and ( ( 15 or 2 ) or not( 3 ) ) ) and 7 )
    @Test
    public final void testStringConstructorComplexExpression3() {
        SyntaxTree st = new SyntaxTree(
                "18 12 32 or and 15 2 or 3 not or and 7 and");

        assertEquals(14, st.size());
        assertEquals("and", st.label());
        assertEquals("7", st.right().label());
        assertEquals("and", st.left().label());
        assertEquals("or", st.left().right().label());
        assertEquals("not", st.left().right().right().label());
        assertEquals("3", st.left().right().right().right().label());
        assertEquals("or", st.left().right().left().label());
        assertEquals("2", st.left().right().left().right().label());
        assertEquals("15", st.left().right().left().left().label());
        assertEquals("and", st.left().left().label());
        assertEquals("or", st.left().left().right().label());
        assertEquals("32", st.left().left().right().right().label());
        assertEquals("12", st.left().left().right().left().label());
        assertEquals("18", st.left().left().left().label());
    }

    // not ( not ( not ( not ( not ( not ( 18 ) ) ) ) ) )
    @Test
    public final void testStringConstructorComplexExpression4() {
        SyntaxTree st = new SyntaxTree("18 not not not not not not");

        assertEquals(7, st.size());
        assertEquals("not", st.label());
        assertEquals("not", st.right().label());
        assertEquals("not", st.right().right().label());
        assertEquals("not", st.right().right().right().label());
        assertEquals("not", st.right().right().right().right().label());
        assertEquals("not", st.right().right().right().right().right().label());
        assertEquals("18",
                st.right().right().right().right().right().right().label());
    }

    /**
     * toString Test Cases
     */

    @Test
    public final void testToStringTrue() {
        SyntaxTree st = new SyntaxTree("T");

        assertEquals("T", st.toString());
    }

    @Test
    public final void testToStringFalse() {
        SyntaxTree st = new SyntaxTree("F");

        assertEquals("F", st.toString());
    }

    @Test
    public final void testToStringSingleVariable() {
        SyntaxTree st = new SyntaxTree("0");

        assertEquals("0", st.toString());
    }

    @Test
    public final void testToStringSingleVariableNot() {
        SyntaxTree st1 = new SyntaxTree("0 not");
        SyntaxTree st2 = new SyntaxTree("T not");
        SyntaxTree st3 = new SyntaxTree("F not");

        assertEquals("( not 0 )", st1.toString());
        assertEquals("( not T )", st2.toString());
        assertEquals("( not F )", st3.toString());
    }

    @Test
    public final void testToStringTwoVariableAnd() {
        SyntaxTree st = new SyntaxTree("0 1 and");

        assertEquals("( 0 and 1 )", st.toString());
    }

    @Test
    public final void testToStringTwoVariableOr() {
        SyntaxTree st = new SyntaxTree("0 1 or");

        assertEquals("( 0 or 1 )", st.toString());
    }

    // ( not ( 0 and 1 ) )
    @Test
    public final void testToStringTwoVariableExpressionAndNot() {
        SyntaxTree st = new SyntaxTree("0 1 and not");

        assertEquals("( not ( 0 and 1 ) )", st.toString());
    }

    // ( ( 0 or 1 ) or 2 )
    @Test
    public final void testToStringThreeVariableExpressionOrs() {
        SyntaxTree st = new SyntaxTree("0 1 or 2 or");

        assertEquals("( ( 0 or 1 ) or 2 )", st.toString());
    }

    // ( ( not 0 ) and ( not 1 ) )
    @Test
    public final void testToStringTwoVariableExpressionNotsAnd() {
        SyntaxTree st = new SyntaxTree("0 not 1 not and");

        assertEquals("( ( not 0 ) and ( not 1 ) )", st.toString());
    }

    // ( ( 0 and 1 ) and ( 2 or 3 ) )
    @Test
    public final void testToStringFourVariableExpressionAndsOrs() {
        SyntaxTree st = new SyntaxTree("0 1 and 2 3 or and");

        assertEquals("( ( 0 and 1 ) and ( 2 or 3 ) )", st.toString());
    }

    // ( not ( ( not ( 1 and 2 ) ) or ( ( 3 and 4 ) and 5 ) ) )
    @Test
    public final void testToStringComplexExpression1() {
        SyntaxTree st = new SyntaxTree("1 2 and not 3 4 and 5 and or not");

        assertEquals("( not ( ( not ( 1 and 2 ) ) or ( ( 3 and 4 ) and 5 ) ) )",
                st.toString());
    }

    // ( ( ( 0 or 2 ) and 3 ) or ( 15 and ( 2 and 4 ) ) )
    @Test
    public final void testToStringComplexExpression2() {
        SyntaxTree st = new SyntaxTree("0 2 or 3 and 15 2 4 and and or");

        assertEquals("( ( ( 0 or 2 ) and 3 ) or ( 15 and ( 2 and 4 ) ) )",
                st.toString());
    }

    // ( ( ( 18 and ( 12 or 32 ) ) and ( ( 15 or 2 ) or ( not 3 ) ) ) and 7 )
    @Test
    public final void testToStringComplexExpression3() {
        SyntaxTree st = new SyntaxTree(
                "18 12 32 or and 15 2 or 3 not or and 7 and");

        assertEquals(
                "( ( ( 18 and ( 12 or 32 ) ) and ( ( 15 or 2 ) or ( not 3 ) ) ) and 7 )",
                st.toString());
    }

    // ( not ( not ( not ( not ( not ( not 18 ) ) ) ) ) )
    @Test
    public final void testToStringComplexExpression4() {
        SyntaxTree st = new SyntaxTree("18 not not not not not not");

        assertEquals("( not ( not ( not ( not ( not ( not 18 ) ) ) ) ) )",
                st.toString());
    }

    @Ignore
    @Test
    public final void testFileConstructorOneConjunct() {
        URL url = this.getClass().getResource("DIMACS/123.txt");
        SimpleReader in = new SimpleReader1L(url.toString());

        SyntaxTree st = new SyntaxTree(in);

        assertEquals("( ( 1 or 2 ) or 3 )", st.toString());
    }

    @Ignore
    @Test
    public final void testFileConstructorTwoConjuncts() {
        URL url = this.getClass().getResource("DIMACS/1230n1n2n3.txt");
        SimpleReader in = new SimpleReader1L(url.toString());

        SyntaxTree st = new SyntaxTree(in);

        assertEquals(
                "( ( ( 1 or 2 ) or 3 ) and ( ( ( not 1 ) or ( not 2 ) ) or ( not 3 ) ) )",
                st.toString());
    }

    @Ignore
    @Test
    public final void testFileConstructorThreeConjuncts() {
        URL url = this.getClass().getResource("DIMACS/102030.txt");
        SimpleReader in = new SimpleReader1L(url.toString());

        SyntaxTree st = new SyntaxTree(in);

        assertEquals("( ( 1 and 2 ) and 3 )", st.toString());
    }

}
