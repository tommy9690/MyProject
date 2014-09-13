package projecteuler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Problem12{
    
     public static void main(String []args){
        System.out.println("Hello World");
        //to get the divisions
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> divisors = new ArrayList<Integer>();
        int a = scanner.nextInt();
        int number = 0;
        //int next = 0;
        //int sqrta = (int) Math.floor(Math.sqrt(a));
        for( int counter = 1; counter <= a; counter ++ ) {
            //next = counter;
            number += counter;
            //System.out.println(number);
            divisors = getDivisors(number);
            if(divisors.size() > 500) {
                break;
            }
        }
        
        for (int elemennt:divisors) {
            System.out.println(elemennt);
        }
        System.out.println(divisors.size());

        

     }
     
     
     public static ArrayList<Integer> getDivisors(int number) {
    
        ArrayList<Integer> divisors = new ArrayList<Integer>();
        
        int sqrta = (int) Math.floor(Math.sqrt(number));
        for ( int c = 2; c <= sqrta; c++) {
            if(number % c == 0) {
                //System.out.println(c);
                divisors.add(c);
                int d = number/c;
                //if
                divisors.add(d);
            }
        }
        divisors.add(number);
        Collections.sort(divisors);
        return divisors;
    }
}

