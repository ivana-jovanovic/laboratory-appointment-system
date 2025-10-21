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
public class Usluga extends AbstractDomainObject {

    private Long uslugaID;
    private String naziv;
    private String opis;
    private double cena;

    @Override
    public String toString() {
        return naziv + " (Cena: " + cena + "din)";
    }

    public Usluga(Long uslugaID, String naziv, String opis, double cena) {
        this.uslugaID = uslugaID;
        this.naziv = naziv;
        this.opis = opis;
        this.cena = cena;
    }

    public Usluga() {
    }

    @Override
    public String nazivTabele() {
        return " Usluga ";
    }

    @Override
    public String alijas() {
        return " u ";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {
            Usluga u = new Usluga(rs.getLong("UslugaID"),
                    rs.getString("Naziv"), rs.getString("Opis"),
                    rs.getDouble("Cena"));

            lista.add(u);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (Naziv, Opis, Cena) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return "'" + naziv + "', '" + opis + "', "
                + " " + cena + " ";
    }

    @Override
    public String vrednostiZaUpdate() {
        return " opis = '" + opis + "', cena = " + cena + " ";
    }

    @Override
    public String uslov() {
        return " UslugaID = " + uslugaID;
    }

    @Override
    public String uslovZaSelect() {
        return "";
    }

    public Long getUslugaID() {
        return uslugaID;
    }

    public void setUslugaID(Long uslugaID) {
        this.uslugaID = uslugaID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

}
