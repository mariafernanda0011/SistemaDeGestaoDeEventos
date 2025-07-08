
package util;

import java.util.Scanner;

/*
 * @author Leonan Teixeira //
 */
// Essa classe centraliza a leitura de dados -> "Padrão Singleton".

public class Entrada {
    private static Entrada entrada;
    
    public static Entrada getInstance(){ 
        if(entrada == null){
            entrada = new Entrada();
        }
        
        return entrada;
    }
    
    private Scanner scaner;
    private Entrada(){
        this.scaner = new Scanner(System.in);
    }
    
    public String lerString(){
        return this.scaner.nextLine();
    }

    public int lerInt() {
        while (true) {
            try {
                String linha = this.scaner.nextLine().trim();
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    }

    public double lerDouble() {
        return this.scaner.nextDouble();
    }
}
