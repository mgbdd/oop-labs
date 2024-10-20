import commands.Define;
import exceptions.UndefinedVariableException;
import exceptions.UnsupportedDefineNameException;
import exceptions.UnsupportedScanTypeException;
import main.ExecutionContext;
import org.junit.Test;
import static org.junit.Assert.*;
import commands.*;

import java.io.IOException;

public class MyTest {
    ExecutionContext context;


    @Test
    public void testDefine() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedDefineNameException, IOException, UnsupportedScanTypeException {
        context = new ExecutionContext("default");
        Define op = new Define();
        String[] parts = new String[3];
        parts[1] = "a";
        parts[2] = "4";
        op.execute(context, parts);
        assertTrue(context.getVariables().containsKey(parts[1]));
        assertEquals(4.0, context.getVariables().get(parts[1]), 0);
    }

    @Test
    public void testDefinedPushPop() throws IOException, UnsupportedScanTypeException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedDefineNameException, UndefinedVariableException {
        context = new ExecutionContext("default");
        String[] parts = new String[3];
        parts[1] = "a";
        parts[2] = "4";
        Define def = new Define();
        def.execute(context, parts);

        Push push = new Push();
        push.execute(context, parts);
        assertFalse(context.getStack().isEmpty());
        assertEquals(4.0, context.getStack().peek(), 0);

        Pop pop = new Pop();
        pop.execute(context, parts);
        assertTrue(context.getStack().isEmpty());

        push.execute(context, parts);
        double res = pop.getPopRes(context);
        assertEquals(res, 4.0, 0);
    }


    @Test
    public void testUndefinedPushPop() throws IOException, UnsupportedScanTypeException, ClassNotFoundException, InstantiationException, IllegalAccessException, UndefinedVariableException {
        context = new ExecutionContext("default");
        String[] parts = new String[3];
        parts[1] = "1";

        Push push = new Push();
        push.execute(context, parts);
        assertFalse(context.getStack().isEmpty());
        assertEquals(1.0, context.getStack().peek(), 0);

        Pop pop = new Pop();
        pop.execute(context, parts);
        assertTrue(context.getStack().isEmpty());

        push.execute(context, parts);
        double res = pop.getPopRes(context);
        assertEquals(res, 1.0, 0);
    }

    @Test
    public void testSqrt() throws IOException, UnsupportedScanTypeException, ClassNotFoundException, InstantiationException, IllegalAccessException, UndefinedVariableException {
        context = new ExecutionContext("default");
        String[] parts = new String[3];
        parts[1] = "9";

        Push push = new Push();
        push.execute(context, parts);

        Sqrt sqrt = new Sqrt();
        sqrt.execute(context, parts);

        assertEquals(3.0, context.getStack().peek(), 0);
    }

    @Test
    public void testAddition() throws IOException, UnsupportedScanTypeException, ClassNotFoundException, InstantiationException, IllegalAccessException, UndefinedVariableException {
        context = new ExecutionContext("default");
        String[] parts = new String[3];
        parts[1] = "3";
        Push push = new Push();
        push.execute(context, parts);
        parts[1] = "5";
        push.execute(context, parts);

        Addition add =new Addition();
        add.execute(context, parts);

        assertEquals(8, context.getStack().peek(), 0);
    }

    @Test
    public void testSubtraction() throws IOException, UnsupportedScanTypeException, ClassNotFoundException, InstantiationException, IllegalAccessException, UndefinedVariableException {
        context = new ExecutionContext("default");
        String[] parts = new String[3];
        parts[1] = "10";
        Push push = new Push();
        push.execute(context, parts);
        parts[1] = "11";
        push.execute(context, parts);

        Subtraction sub = new Subtraction();
        sub.execute(context, parts);

        assertEquals(-1, context.getStack().peek(), 0);
    }

    @Test
    public void testMultiplication() throws IOException, UnsupportedScanTypeException, ClassNotFoundException, InstantiationException, IllegalAccessException, UndefinedVariableException {
        context = new ExecutionContext("default");
        String[] parts = new String[3];
        parts[1] = "8";
        Push push = new Push();
        push.execute(context, parts);
        parts[1] = "6";
        push.execute(context, parts);

        Multiplication mult = new Multiplication();
        mult.execute(context, parts);

        assertEquals(48, context.getStack().peek(), 0);
    }

    @Test
    public void testDivision() throws IOException, UnsupportedScanTypeException, ClassNotFoundException, InstantiationException, IllegalAccessException, UndefinedVariableException {
        context = new ExecutionContext("default");
        String[] parts = new String[3];
        parts[1] = "39";
        Push push = new Push();
        push.execute(context, parts);
        parts[1] = "13";
        push.execute(context, parts);

        Division div = new Division();
        div.execute(context, parts);

        assertEquals(3, context.getStack().peek(), 0);
    }

}
