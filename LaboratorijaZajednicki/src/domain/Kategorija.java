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
public class Kategorija extends AbstractDomainObject {

    private Long kategorijaID;
    private String naziv;
    private double popust;

    @Override
    public String toString() {
        return naziv;
    }

    public Kategorija(Long kategorijaID, String naziv, double popust) {
        this.kategorijaID = kategorijaID;
        this.naziv = naziv;
        this.popust = popust;
    }

    public Kategorija() {
    }

    @Override
    public String nazivTabele() {
        return " Kategorija ";
    }

    @Override
    public String alijas() {
        return " k ";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {
            Kategorija k = new Kategorija(rs.getLong("KategorijaID"),
                    rs.getString("k.naziv"), rs.getDouble("popust"));

            lista.add(k);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (naziv, popust) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return " '" + naziv + "', " + popust + " ";
    }

    @Override
    public String vrednostiZaUpdate() {
        return " naziv = '" + naziv + "', popust = " + popust;
    }

    @Override
    public String uslov() {
        return " kategorijaID = " + kategorijaID;
    }

    @Override
    public String uslovZaSelect() {
        return "";
    }

    public Long getKategorijaID() {
        return kategorijaID;
    }

    public void setKategorijaID(Long kategorijaID) {
        this.kategorijaID = kategorijaID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public double getPopust() {
        return popust;
    }

    public void setPopust(double popust) {
        this.popust = popust;
    }

}
