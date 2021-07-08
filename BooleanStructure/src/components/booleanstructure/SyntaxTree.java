package components.booleanstructure;

import components.simplereader.SimpleReader;
import components.stack.Stack;
import components.stack.Stack2;

/**
 * Utility class used to represent Boolean formulas in Reverse Polish Notation.
 * Used to easily construct {@code BooleanStructure}s representing complex
 * Boolean formulas.
 */
public class SyntaxTree {

    /**
     * conventions: <pre>
     * [ operators are "and", "or", "not",
     *   leaves are integers,
     *   "not" has one child, which is this.right ]
     * </pre>
     */

    /**
     * Node label
     */
    private String label;

    /**
     * Left child
     */
    private SyntaxTree left;

    /**
     * Right child
     */
    private SyntaxTree right;

    /**
     * Number of nodes in tree
     */
    private int size;

    /**
     * Private 0 argument constructor. Only used to create necessary child nodes
     * when parsing formula string.
     */
    private SyntaxTree() {
        this.label = null;
        this.left = null;
        this.right = null;
        this.size = 0;
    }

    /**
     * Constructor from a string in RPN
     *
     * @param formula
     *            - Boolean formula in RPN to be modeled in tree form
     * @requires formula is a member of the language defined by the following grammar
     *   <bexp> ::= <int> | <bexp> not | <bexp> <bexp> and | <bexp> <bexp> or
     *   <int>  ::= [an integer value]
     */
    public SyntaxTree(String formula) {

        SyntaxTree root = parseIntoTree(formula);
        this.label = root.label;
        this.left = root.left;
        this.right = root.right;
        this.size = root.size;

    }

    /**
     * Constructor from an input file in DIMACS syntax. See:
     *
     * @param in
     *            - SimpleReader for DIMAC expression (in CNF)
     */
    public SyntaxTree(SimpleReader in) {

        SyntaxTree root = parseDIMACIntoTree(in);
        this.label = root.label;
        this.left = root.left;
        this.right = root.right;
        this.size = root.size;

    }

    /**
     * Returns the label of the SyntaxTree node
     */
    public String label() {
        return this.label;
    }

    /**
     * Returns the left child of the SyntaxTree node
     */
    public SyntaxTree left() {
        return this.left;
    }

    /**
     * Returns the right child of the SyntaxTree node
     */
    public SyntaxTree right() {
        return this.right;
    }

    /**
     * Returns the size of the SyntaxTree
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns String Infix representation of the formula
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        // Split into variable case and operator cases
        if (this.right == null) {
            result.append(this.label);
        } else if (this.label.equals("not")) {
            result.append(
                    "( " + this.label + " " + this.right.toString() + " )");
        } else {
            result.append("( " + this.left.toString() + " " + this.label + " "
                    + this.right.toString() + " )");
        }

        return result.toString();
    }

    /**
     * Check whether a node has a logical operator as its label
     *
     * @param node
     *            - Node whose label is being checked
     * @return true iff node.label is in { and, or, not }
     */
    private static boolean isOp(SyntaxTree node) {
        boolean isOp = false;

        if (node.label.matches("and|or|not")) {
            isOp = true;
        }

        return isOp;
    }

    /**
     * Parses string representing a formula in RPN into a tree structure
     *
     * @param formula
     *            - string to parse into a SyntaxTree
     * @return root node of a SyntaxTree representing formula passed in
     *
     *         NOTE - In the case of a single child, the child is assigned to
     *         the right
     */
    private static SyntaxTree parseIntoTree(String formula) {
        String[] literals = formula.split(" ");

        Stack<SyntaxTree> RPNStack = new Stack2<SyntaxTree>();
        for (int i = 0; i < literals.length; i++) {
            SyntaxTree current = new SyntaxTree();
            current.size = 1;
            current.label = literals[i];

            if (isOp(current)) {
                SyntaxTree right = RPNStack.pop();
                current.right = right;
                current.size += right.size;

                if (!current.label.equals("not")) {
                    SyntaxTree left = RPNStack.pop();
                    current.left = left;
                    current.size += left.size;
                }

            }

            RPNStack.push(current);

        }

        return RPNStack.pop();
    }

    private static SyntaxTree parseDIMACIntoTree(SimpleReader in) {

        String first = in.nextLine();
        while (first.charAt(0) == 'c') {
            // ignore initial lines that start with 'c' (comments)
            first = in.nextLine();
        }

        assert first.substring(0, 5).equals("p cnf");
        String[] declaration = first.split(" ");
        int clauseCount = Integer.valueOf(declaration[3]);
        assert (clauseCount >= 1);

        SyntaxTree current = formConjunct(in);
        int count = 1;
        while (count < clauseCount) {
            SyntaxTree oldRoot = current;
            current = new SyntaxTree();
            current.label = "and";
            current.left = oldRoot;
            current.right = formConjunct(in);
            current.size = current.left.size + current.right.size + 1;
            count++;
        }

        return current;
    }

    private static SyntaxTree formConjunct(SimpleReader in) {
        String conjunct = in.nextLine();
        String[] variables = conjunct.split(" ");
        SyntaxTree current = formTerm(variables[0]);
        for (int i = 1; i < variables.length - 1; i++) {
            SyntaxTree oldRoot = current;
            current = new SyntaxTree();
            current.label = "or";
            current.left = oldRoot;
            current.right = formTerm(variables[i]);
            current.size = current.left.size + current.right.size + 1;
        }
        return current;
    }

    private static SyntaxTree formTerm(String var) {
        SyntaxTree result = new SyntaxTree();
        result.label = var;
        result.size = 1;
        if (var.charAt(0) == '-') {
            result.label = var.substring(1);
            SyntaxTree leaf = result;
            result = new SyntaxTree();
            result.label = "not";
            result.right = leaf;
            result.size = 2;
        }
        return result;
    }

}
