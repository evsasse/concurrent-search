
import java.io.File;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import javax.swing.JOptionPane;

public class Main {
    
    public static int maxThreads = 12;
    public static int maxSemaforo = 6;
    public static Semaphore semaforo = new Semaphore(6,true);
    
    private static int limiteTempo = 10000;
    private static int qtdThreadsI = 0;
    public static synchronized void setThreadI(){ qtdThreadsI++;}
    //private static int qtdThreadsF = 0;
    public static boolean terminou = false;
    //public static synchronized void setThreadF(){ qtdThreadsF++;}
    
    public static void main(String[] args){
        
        String sCaminho = JOptionPane.showInputDialog("Digite o caminho: (Deixe vazio para 'C:\\')");
        if(sCaminho.equals("")) sCaminho = "C:\\";
        File caminho = new File(sCaminho);
        
        String sNome = JOptionPane.showInputDialog("Digite o nome: (Deixe vazio para todos)");
        
        String sExtensao = JOptionPane.showInputDialog("Digite a extensão: (Deixe vazio para qualquer)");
        
        int tMin = Integer.parseInt(JOptionPane.showInputDialog("Digite o tamanho mínimo: (Digite 0 para qualquer)"));

        int tMax = Integer.parseInt(JOptionPane.showInputDialog("Digite o tamanho máximo: (Digite 0 para qualquer)"));

        Calendar data = Calendar.getInstance();
        data.clear();
        int ano = Integer.parseInt(JOptionPane.showInputDialog("Digite o ano: (Digite 0 para qualquer data)"));
        if (ano != 0){
            int mes = Integer.parseInt(JOptionPane.showInputDialog("Digite o mês:"));
            int dia = Integer.parseInt(JOptionPane.showInputDialog("Digite o dia:"));
            data.set(ano, mes-1, dia);
        }
        //data.set(2014,5,26);
        
        String sConteudo = JOptionPane.showInputDialog("Digite o conteúdo: (Deixe vazio para qualquer)");
        
        Criterio criterio = new Criterio(sNome,sExtensao,0,0,data,"");
        
        System.out.println("-----------------");
        System.out.println("Iniciando Procura");
        System.out.println("-----------------");
        
        LinkedList<File> resultado = new LinkedList<>();
        
        Task.setTask(caminho);
        
        for(int i=0;i<maxThreads;i++){
            Busca busca = new Busca(criterio, resultado);
            busca.start();
        }
        
        int a = 0;
        do{
            System.out.println("Procurando...");
            try{
                Thread.sleep(100);
            }catch(Exception e){}
            a++;
        }while(!terminou && a < (limiteTempo/100));
        
        System.out.println("-----------------");
        
        if(a == limiteTempo/100) System.out.println("Limite de tempo atingido!");
        System.out.println((a/10)+"s tempo levado");
        System.out.println(qtdThreadsI+" threads criadas");
        
        System.out.println("-----------------");
        System.out.println("Procura Terminada");
        System.out.println("-----------------");
        
        if(resultado.size() == 0){
            System.out.println("Nenhum arquivo encontrado");
        }else{
            System.out.println("Arquivos encontrados:");
            System.out.println("");
            for(int i=0;i<resultado.size();++i){
                try{
                    System.out.println(resultado.get(i).getAbsolutePath());
                }catch(Exception e){}
            }
        }
        System.exit(0);
    }
}
