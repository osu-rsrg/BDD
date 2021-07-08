package components.booleanstructure;

public class BooleanStructure1Test extends BooleanStructureTest {

    @Override
    protected BooleanStructure constructorTest() {
        return new BooleanStructure1();
    }

    @Override
    protected BooleanStructure constructorTest(boolean b) {
        return new BooleanStructure1(b);
    }

    @Override
    protected BooleanStructure constructorTest(int i) {
        return new BooleanStructure1(i);
    }

    @Override
    protected BooleanStructure constructorTest(SyntaxTree st) {
        BooleanStructure1 newExp = new BooleanStructure1();
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
