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
public class Pacijent extends AbstractDomainObject {
    
    private Long pacijentID;
    private String ime;
    private String prezime;
    private String email;
    private String telefon;
    private String napomena;
    private Kategorija kategorija;

    @Override
    public String toString() {
        return ime + " " + prezime + " (Kategorija: " + kategorija + ")";
    }

    public Pacijent(Long pacijentID, String ime, String prezime, String email, String telefon, String napomena, Kategorija kategorija) {
        this.pacijentID = pacijentID;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.telefon = telefon;
        this.napomena = napomena;
        this.kategorija = kategorija;
    }

    public Pacijent() {
    }
    
    @Override
    public String nazivTabele() {
        return " Pacijent ";
    }

    @Override
    public String alijas() {
        return " p ";
    }

    @Override
    public String join() {
        return " JOIN KATEGORIJA K ON (K.KATEGORIJAID = P.KATEGORIJAID) ";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {
            
            Kategorija k = new Kategorija(rs.getLong("KategorijaID"),
                    rs.getString("k.naziv"), rs.getDouble("popust"));
            
            Pacijent p = new Pacijent(rs.getLong("PacijentID"),
                    rs.getString("Ime"), rs.getString("Prezime"),
                    rs.getString("email"), rs.getString("telefon"), rs.getString("napomena"), k);

            lista.add(p);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (Ime, Prezime, email, telefon, napomena, KategorijaID) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return "'" + ime + "', '" + prezime + "', "
                + "'" + email + "', '" + telefon + "', '" + napomena + "', "
                + kategorija.getKategorijaID();
    }
    
    @Override
    public String vrednostiZaUpdate() {
        return " email = '" + email + "', telefon = '" + telefon + "', "
                + "napomena = '" + napomena + "', kategorijaID = " + kategorija.getKategorijaID();
    }
    
    @Override
    public String uslov() {
        return " PacijentID = " + pacijentID;
    }

    @Override
    public String uslovZaSelect() {
        return "";
    }

    public Long getPacijentID() {
        return pacijentID;
    }

    public void setPacijentID(Long pacijentID) {
        this.pacijentID = pacijentID;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }
    
}
