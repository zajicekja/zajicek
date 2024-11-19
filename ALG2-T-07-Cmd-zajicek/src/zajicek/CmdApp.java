package zajicek;

import java.io.IOException;
import java.util.Scanner;


public class CmdApp {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Cmd cmd = new Cmd();
        boolean cycle = true;
        while(cycle){
            try{
                System.out.print(cmd.getActualDir() + ">");
                String command = sc.nextLine();
                String[] parts = command.split("[ ]+");

                switch(parts[0]){
                    case "help" : System.out.println(cmd.help());break;

                    case "exit" : cycle=false;break;

                    case "dir" :
                        System.out.println(cmd.recursiveDir(cmd.getActualDir(), 0));
                       

                    case "cd" : cmd.cd(parts[0]);break;

                    case "mkfile" : cmd.mkfile(parts[1]);break;

                }
            } catch(IOException | IllegalArgumentException e  ) {
                System.out.println("Nastala chyba: " + e.getMessage());
            }
        }
    }   
    
}
