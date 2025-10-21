/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ivana
 */
public class Laboratorija extends AbstractDomainObject {

    private Long laboratorijaID;
    private String naziv;
    private String grad;

    @Override
    public String toString() {
        return naziv;
    }

    public Laboratorija(Long laboratorijaID, String naziv, String grad) {
        this.laboratorijaID = laboratorijaID;
        this.naziv = naziv;
        this.grad = grad;
    }

    public Laboratorija() {
    }

    @Override
    public String nazivTabele() {
        return " Laboratorija ";
    }

    @Override
    public String alijas() {
        return " l ";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {
            Laboratorija l = new Laboratorija(rs.getLong("LaboratorijaID"),
                    rs.getString("naziv"), rs.getString("grad"));

            lista.add(l);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (naziv, grad) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return "'" + naziv + "', '" + grad + "' ";
    }

    @Override
    public String vrednostiZaUpdate() {
        return " naziv = '" + naziv + "', grad = '" + grad + "' ";
    }

    @Override
    public String uslov() {
        return " LaboratorijaID = " + laboratorijaID;
    }

    @Override
    public String uslovZaSelect() {
        return "";
    }

    public Long getLaboratorijaID() {
        return laboratorijaID;
    }

    public void setLaboratorijaID(Long laboratorijaID) {
        this.laboratorijaID = laboratorijaID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

}
