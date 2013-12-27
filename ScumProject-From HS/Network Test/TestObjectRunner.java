public class TestObjectRunner
{
    public static void main (String[] args)
    {
        TestObject test = new TestObject(5,5,"Hello");
        System.out.println(test);
        test.increment();
        System.out.println(test);
    }
}