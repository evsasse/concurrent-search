import java.io.File;
import java.util.ArrayList;

public class Task {
    static ArrayList<File> caminhos = new ArrayList<>(); 
    
    public static synchronized File getTask(){
        if(caminhos.isEmpty()) return null;
        return caminhos.remove(0);
    }
    
    public static synchronized void setTask(File caminho){
        caminhos.add(caminho);
    }
}
