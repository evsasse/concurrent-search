
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

public class Criterio {
    String nome;
    String extensao;
    long tammin, tammax;
    Calendar data;
    String cont;

    public Criterio(String nome, String extensao, long tammin, long tammax, Calendar data, String cont) {
        this.nome = nome;
        this.extensao = extensao;
        this.tammin = tammin;
        if(tammax == 0) this.tammax = Long.MAX_VALUE;
        else this.tammax = tammax;
        this.data = data;
        this.cont = cont;
    }
    
    boolean compativel(File arquivo) throws FileNotFoundException, IOException{
        if(!arquivo.getName().matches("^("+nome+")\\.*.*("+extensao+")$")){
            //System.out.println("Nome/Extensão não bate");
            //System.out.println(arquivo.getName()+" | "+ nome + extensao);
            return false;
        }
        
        if(arquivo.length() < tammin || arquivo.length() > tammax){
            //System.out.println("Tamanho não bate");
            return false;
        }
        
        Calendar dataArquivo = Calendar.getInstance();
        dataArquivo.setTimeInMillis(arquivo.lastModified());
        int ano, mes, dia;
        ano = dataArquivo.get(Calendar.YEAR);
        mes = dataArquivo.get(Calendar.MONTH);
        dia = dataArquivo.get(Calendar.DAY_OF_MONTH);
        //System.out.println(ano+" "+mes+" "+dia);
        dataArquivo.clear();
        dataArquivo.set(ano, mes, dia);
        if(data.isSet(Calendar.YEAR) && !data.equals(dataArquivo)){
            //System.out.println("Data não bate");
            //System.out.println(data.getTimeInMillis()+" esperado");
            //System.out.println(dataArquivo.getTimeInMillis()+" arquivo");
            return false;
        }
        
        if(!cont.equalsIgnoreCase("")){
            BufferedReader reader = new BufferedReader(new FileReader(arquivo.getAbsolutePath()));
            String line = null;
            boolean achou = false;
            while ((line = reader.readLine()) != null && !achou) {
                if(line.contains(cont)) achou = true;
            }
            if(!achou){
                //System.out.println("Conteúdo não bate");
                return false;
            }
        }
        
        System.out.println(arquivo.getName());
        
        return true;
    }
}
