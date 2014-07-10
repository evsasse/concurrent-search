
import java.io.File;
import java.util.LinkedList;

public class Busca extends Thread{
    File caminho;
    Criterio criterio;
    LinkedList<File> resultado;

    public Busca(Criterio criterio, LinkedList<File> resultado) {
        //System.out.println(caminho.getAbsolutePath());
        //this.caminho = caminho;
        this.criterio = criterio;
        this.resultado = resultado;
        Main.setThreadI();
    }

    @Override
    public void run() {
        while(true){
            do{
                caminho = Task.getTask();
            }while(caminho == null);
            
            try{
                Main.semaforo.acquire();
                
                //System.out.println(getName());
                
                File[] arquivo = caminho.listFiles();
                for(int i=0;i<arquivo.length;++i){
                    if(arquivo[i].isDirectory()){
                        Task.setTask(arquivo[i]);
                        //Busca busca = new Busca(arquivo[i], criterio, resultado);
                        //busca.start();
                    }else{
                        try {
                            if(criterio.compativel(arquivo[i])){
                                resultado.add(arquivo[i]);
                            }
                        } catch (Exception ex) {
                            System.out.println("Arquivo aberto");
                        }
                    }
                }
            }catch(Exception e){
            }finally{
                Main.semaforo.release();
            }
            
            if(Main.semaforo.availablePermits() == Main.maxSemaforo) break;
            
        }
        Main.terminou = true;
        System.out.println("-----------------");
        System.out.println(getName()+" TERMINOU");
        System.out.println("-----------------");
    }
}
