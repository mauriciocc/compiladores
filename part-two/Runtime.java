/* Classe que implementa o metodo de runtime para a linguagem Mini
 */

import java.io.*; 
 
public class Runtime
{
    private static BufferedReader in;

    // Metodo para retornar um inteiro
    public static int readInt()
    {
        if (in == null)
        {
            in = new BufferedReader(new InputStreamReader(System.in)); 
        }
        int n = 0;
        try
        {
            String s = in.readLine();
            if (s != null)
            {
                n = Integer.parseInt(s.trim());
            }
        }
        catch (IOException e)
        {
            System.err.println("Error reading standard input: " + e.getMessage());
        }
        catch (NumberFormatException e)
        {
            System.err.println("Invalid integer format");
        }
        return (n);
    }
}