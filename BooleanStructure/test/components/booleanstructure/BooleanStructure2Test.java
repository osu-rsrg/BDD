package components.booleanstructure;

public class BooleanStructure2Test extends BooleanStructureTest {

    @Override
    protected BooleanStructure constructorTest() {
        return new BooleanStructure2();
    }

    @Override
    protected BooleanStructure constructorTest(boolean b) {
        return new BooleanStructure2(b);
    }

    @Override
    protected BooleanStructure constructorTest(int i) {
        return new BooleanStructure2(i);
    }

    @Override
    protected BooleanStructure constructorTest(SyntaxTree st) {
        BooleanStructure2 newExp = new BooleanStructure2();
        newExp.setFromTree(st);
        return newExp;
    }

    @Override
    protected BooleanStructure constructorRef() {
        return new BooleanStructureR();
    }

    @Override
    protected BooleanStructure constructorRef(boolean b) {
        return new BooleanStructureR(b);
    }

    @Override
    protected BooleanStructure constructorRef(int i) {
        return new BooleanStructureR(i);
    }

    @Override
    protected BooleanStructure constructorRef(SyntaxTree st) {
        BooleanStructureR newExp = new BooleanStructureR();
        newExp.setFromTree(st);
        return newExp;
    }

}
