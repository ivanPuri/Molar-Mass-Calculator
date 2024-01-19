package com.molmasscalc.demo.Service;

import org.springframework.stereotype.Component;
import com.molmasscalc.demo.Model.Molecule;


@Component
public class MoleculeService implements servicable {
    
    private Molecule curr;
    private db dbConnect;

    
    public MoleculeService(Molecule curr) {
        this.curr = curr;        
    }

    @Override
    public void insertbyFormula() {
        if(!dbConnect.isInDb()){
            dbConnect.insertInDb();
        }
    }

    @Override
    public boolean isValid() {
        return this.curr.isValid();
    }

    @Override
    public double calcWeight() {
        return this.curr.getMolMass();
    }

    public Molecule getCurr() {
        return curr;
    }

    public void setCurr(Molecule curr) {
        this.curr = curr;
    }

    public db getDbConnect() {
        return dbConnect;
    }

    public void setDbConnect() {
        this.dbConnect = new db(this);
    }

    
    
    
}
