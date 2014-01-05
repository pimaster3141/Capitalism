package sandbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * interface for quick regex testing
 * 
 */
public class RegexRunner
{
    //public static Pattern pattern = Pattern.compile("%.*|V:.+|(\\|[\\]:]|(:|\\[|\\|)?\\||\\[[12]|[\\[\\]])|\\([0-9]|(z|((\\^\\^?|__?|=)?([A-Ga-g]('+|,+)?)))([0-9]*(/[0-9]*)?)" );
    public static Pattern pattern = Pattern.compile("(disconnect)|(((make)|(join)|(leave)) \\p{Graph}+)|((move)( [2-9JQKA][HSCD])*)");

    public static void display(String s)
    {
        Matcher matcher = pattern.matcher(s);

        System.out.println("m.matches(): " + (matcher.matches()));
        matcher.reset();
        // Check all occurences
        while (matcher.find())
        {
            System.out.print("Start index: " + matcher.start());
            System.out.print(" End index: " + matcher.end() + " ");
            System.out.println(matcher.group());
        }

        Matcher matcher2 = pattern.matcher(s);
        System.out.println("rejected: [" + matcher2.replaceAll("").trim() + "]");
        System.out.println(matcher.replaceAll("").trim().equals(""));
    }

    public static void main(String[] args) throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String expression;
        do
        {
            // display prompt
            System.out.print("$ ");
            // read input
            expression = in.readLine();
            // terminate if input empty
            if (!expression.equals(""))
                display(expression);
        } while (!expression.equals(""));
    }
}