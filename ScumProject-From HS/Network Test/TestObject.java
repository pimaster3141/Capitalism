 public class TestObject
{
    public int field1;
    public int field2; 
    public String string1;
    
    public TestObject (int x, int y, String n)
    {
        field1 = x;
        field2 = y;
        string1 = n;
    }
    
    public int get1()
    {
        return field1;
    }
    
    public int get2()
    {
        return field2;
    }
    
    public String getString()
    {
        return string1;
    }
    
    public void increment()
    {
        field1++;
        field2--;
    }
    
    public String toString()
    {
        String result = super.toString();
        result = result + "\n field1 = " + field1;
        result = result + "\n field2 = " + field2;
        result = result + "\n String1 = " + string1;
        return result;
    }
}