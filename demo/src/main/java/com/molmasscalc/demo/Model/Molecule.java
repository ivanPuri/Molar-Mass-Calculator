package com.molmasscalc.demo.Model;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class Molecule {
    
    private double molMass ;
    private String formula;
    private MolMath molMath;

   
    @Autowired
    public Molecule(String formula) throws FileNotFoundException {
        this.formula = formula;
        molMath = new MolMath(this.formula);
        DecimalFormat df = new DecimalFormat("#.###");
        this.molMass = Double.parseDouble(df.format(molMath.getMolarMass()));
    }
 
    public double getMolMass() {
        return molMass;
    }
    public void setMolMass(double molMass) {
        this.molMass = molMass;
    }
    public String getFormula() {
        return formula;
    }
    public void setFormula(String formula) {
        this.formula = formula;
    }
    public boolean isValid(){
        return this.molMath.isValid();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("--Molecule Info--");
        sb.append("\n");
        sb.append(this.formula);
        sb.append("\n");
        sb.append(this.molMass);
        return sb.toString();
    }
    
}


