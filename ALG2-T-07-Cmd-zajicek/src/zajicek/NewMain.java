package zajicek;

import java.util.InputMismatchException;
import java.util.Scanner;

public class NewMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true){
            try {
                System.out.println("Zadej integer:");
                System.out.println(sc.nextInt());
                break;
            } catch (InputMismatchException e) {
                System.out.println("Nevhodny format vstupu");
                sc.nextLine(); //vycisteni Scanneru
            }
        }
    }

}
