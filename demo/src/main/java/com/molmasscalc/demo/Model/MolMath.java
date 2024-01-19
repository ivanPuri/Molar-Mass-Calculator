package com.molmasscalc.demo.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;

public class MolMath {
    
    Hashtable<String, Float> massTable;
    ArrayList<Integer> indexStarts;
    private float molarMass;
    private boolean valid;
    private HashSet<String> singleSymbol;
    private HashSet<String> doubleSymbol;


    public MolMath(String formula) throws FileNotFoundException{
        try {
            if(!Character.isUpperCase(formula.charAt(0))){
                this.valid = false;
                return;
            }
        } catch (Exception e) {
            valid = false;
            return;
        }
            
        setUp();
        
        for(int i = 0; i < formula.length(); i++){
            if(Character.isLetter(formula.charAt(i)) || Character.isDigit(formula.charAt(i))){
                if(Character.isLetter(formula.charAt(i))){
                    if(Character.isUpperCase(formula.charAt(i))){

                        // accounting for the last index
                        if(i != formula.length() - 1){
                            // looking for one letter elements
                            if(singleSymbol.contains(formula.substring(i, i + 1))){
                                // checking if following char is a number
                                if(Character.isDigit(formula.charAt(i + 1))){
                                    int temp = i+1;
                                    StringBuilder subscript = new StringBuilder();
                                    while (temp < formula.length() && Character.isDigit(formula.charAt(temp))){
                                        subscript.append(formula.charAt(temp));
                                        temp++;
                                    }
                                    molarMass += massTable.get(formula.substring(i, i + 1)) * Float.parseFloat(subscript.toString());
                                }
                                // checking to see if folling char is another element
                                else if(Character.isLetter(formula.charAt(i + 1)) && Character.isUpperCase(formula.charAt(i+1))){
                                    molarMass += massTable.get(formula.substring(i, i + 1));
                                }else{
                                    this.valid = false;
                                    return;
                                }
                            }else if(i != formula.length() - 2){
                                // looking for two letter elements
                                if(doubleSymbol.contains(formula.substring(i,i+1))){
                                    // checking to see if the following char is a valid lowercase letter
                                    if(massTable.contains(formula.subSequence(i, i + 2))){
                                        // checking if there if the following char is a number
                                        if(Character.isDigit(formula.charAt(i+3))){
                                            int temp = i+3;
                                            StringBuilder subscript = new StringBuilder();
                                            while(temp < formula.length() &&Character.isDigit(formula.charAt(temp))){
                                                subscript.append(formula.charAt(temp));
                                                temp++;
                                            }
                                            molarMass += massTable.get(formula.substring(i, i + 2)) * Float.parseFloat(subscript.toString());
                                        }
                                        // checking to see if the following char is another element
                                        else if(Character.isLetter(i+3) && (singleSymbol.contains(formula.substring(i,i+4)) || doubleSymbol.contains(formula.substring(i,i+4)))){
                                            molarMass += massTable.get(formula.substring(i, i + 2));
                                        }else{
                                            this.valid = false;
                                            return;
                                        }
                                    }else{
                                        this.valid = false;
                                        return;
                                    }
                                }
                            }else{
                                if(massTable.contains(formula.substring(i,i+2))){
                                    molarMass += massTable.get(formula.substring(i, i + 2));
                                }else{
                                    this.valid = false;
                                    return;
                                }
                            }   
                        }else{
                            if(singleSymbol.contains(formula.substring(i))){
                                molarMass += massTable.get(formula.substring(i));
                            }else{
                                this.valid = false;
                                return;
                            }
                        }
                    }else
                        if(Character.isUpperCase(formula.charAt(i - 1)) && doubleSymbol.contains(formula.substring(i-1,i))){
                            continue;
                        }else{
                            this.valid = false;
                            return;
                        }
                }else
                    continue;
            }else{
                this.valid = false;
                return;
            }
        }
        this.valid = true;
    }

    public void setUp() throws FileNotFoundException{
        File file = new File("/Users/ivanpuri/Desktop/Java/Java Projects/demo/element.txt");
        Scanner scan = new Scanner(file);
        massTable = new Hashtable<>();
        singleSymbol = new HashSet<>();
        doubleSymbol = new HashSet<>();
        this.molarMass = 0;

        
        while(scan.hasNextLine()){
            String symbol = scan.next();
            float mass = scan.nextFloat();

            if(symbol.length() < 2)
                singleSymbol.add(symbol);
            else if(symbol.length() == 2)
                doubleSymbol.add(symbol.substring(0,1));
            
            
            massTable.put(symbol, mass);
        }
        scan.close();
    }

    public float getMolarMass(){
        return this.molarMass;
    }

    public boolean isValid(){
        return this.valid;
    }
    

}
