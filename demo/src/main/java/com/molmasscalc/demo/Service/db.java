package com.molmasscalc.demo.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.molmasscalc.demo.Model.Molecule;

public class db {
    private boolean inDb = false;
    static final String DB_URL = "jdbc:postgresql://localhost:5432/moleculedb";
    static final String USER = "ivanpuri";
    static final String PASS = "";
    Molecule curr;

    public db(MoleculeService molService){
        this.curr = molService.getCurr();
        checkDataBase();

    }

    private void checkDataBase(){
        Statement statement;
        ResultSet rs;
        String sql = "SELECT * FROM MOLECULES; ";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);) {
            System.out.println("Connected Database  successfully...");
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                String moleculeName = rs.getString("FORMULA");
                if (this.curr.getFormula().equals(moleculeName)) {
                    this.inDb = true;
                }
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void insertInDb(){
        Statement statement;

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);) {
            System.out.println("Connected Database successfully...");
            statement = conn.createStatement();
            String sql = "INSERT INTO MOLECULES (FORMULA, MOLMASS) "
                    + "VALUES ('" + this.curr.getFormula() + "'," + this.curr.getMolMass() + " );";
            statement.executeUpdate(sql);
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records inserted successfully...");
    }

    public boolean isInDb() {
        return inDb;
    }

    
    
}
