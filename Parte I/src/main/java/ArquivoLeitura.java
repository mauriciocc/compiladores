import java.io.*;

public class ArquivoLeitura
{
    private BufferedReader bufferedReader;
    
    public ArquivoLeitura(String nomeArquivo)
    {
        FileReader fileReader = null;
        try
        {
            fileReader = new FileReader(nomeArquivo);
            bufferedReader = new BufferedReader(fileReader);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Arquivo de leitura n�o encontrado: " + nomeArquivo);
            //e.printStackTrace();
        }
    }
    
    public String lerLinha()
    {
        try
        {
            if (bufferedReader != null)
            {
                return bufferedReader.readLine();
            }
        }
        catch (IOException e)
        {
            System.err.println("Erro de leitura no arquivo");
            //e.printStackTrace();
        }
        return null;
    }
    
    public void fechar()
    {
        try
        {
            if (bufferedReader != null)
            {
                bufferedReader.close();
            }
        }
        catch(IOException e)
        {
            System.err.println("Erro ao fechar o arquivo");
            //e.printStackTrace();
        }
        bufferedReader = null;
    }
}