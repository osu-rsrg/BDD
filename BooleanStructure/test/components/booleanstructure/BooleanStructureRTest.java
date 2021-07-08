package components.booleanstructure;

public class BooleanStructureRTest extends BooleanStructureReferenceTest {
    @Override
    protected BooleanStructure constructorTest() {
        return new BooleanStructureR();
    }

    @Override
    protected BooleanStructure constructorTest(boolean b) {
        return new BooleanStructureR(b);
    }

    @Override
    protected BooleanStructure constructorTest(int i) {
        return new BooleanStructureR(i);
    }

    @Override
    protected BooleanStructure constructorTest(SyntaxTree st) {
        BooleanStructureR newExp = new BooleanStructureR();
        newExp.setFromTree(st);
        return newExp;
    }
}
