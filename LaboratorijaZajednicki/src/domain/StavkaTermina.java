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
public class StavkaTermina extends AbstractDomainObject {

    private Termin termin;
    private int rb;
    private double iznos;
    private String napomena;
    private Usluga usluga;

    public StavkaTermina(Termin termin, int rb, double cena, String napomena, Usluga usluga) {
        this.termin = termin;
        this.rb = rb;
        this.iznos = cena;
        this.napomena = napomena;
        this.usluga = usluga;
    }

    public StavkaTermina() {
    }

    @Override
    public String nazivTabele() {
        return " StavkaTermina ";
    }

    @Override
    public String alijas() {
        return " st ";
    }

    @Override
    public String join() {
        return " JOIN USLUGA U ON (U.USLUGAID = ST.USLUGAID)\n"
                + "JOIN TERMIN T ON (T.TERMINID = ST.TERMINID)\n"
                + " JOIN PACIJENT P ON (P.PACIJENTID = T.PACIJENTID)\n"
                + "JOIN KATEGORIJA K ON (K.KATEGORIJAID = P.KATEGORIJAID)\n"
                + "JOIN ZAPOSLENI Z ON (Z.ZAPOSLENIID = T.ZAPOSLENIID)\n ";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {

            Kategorija k = new Kategorija(rs.getLong("KategorijaID"),
                    rs.getString("k.naziv"), rs.getDouble("popust"));

            Pacijent p = new Pacijent(rs.getLong("PacijentID"),
                    rs.getString("p.Ime"), rs.getString("p.Prezime"),
                    rs.getString("email"), rs.getString("telefon"), rs.getString("p.napomena"), k);

            Zaposleni z = new Zaposleni(rs.getLong("ZaposleniID"),
                    rs.getString("z.Ime"), rs.getString("z.Prezime"),
                    rs.getString("Username"), rs.getString("Password"));

            Termin t = new Termin(rs.getLong("terminID"), rs.getTimestamp("datumVreme"),
                    rs.getString("opis"), rs.getDouble("iznosBezPopusta"),
                    rs.getDouble("popust"),
                    rs.getDouble("ukupanIznos"), p, z, new ArrayList<>());

            Usluga u = new Usluga(rs.getLong("UslugaID"),
                    rs.getString("u.Naziv"), rs.getString("u.Opis"),
                    rs.getDouble("Cena"));

            StavkaTermina st = new StavkaTermina(t, rs.getInt("rb"), rs.getDouble("iznos"),
                    rs.getString("st.napomena"), u);

            lista.add(st);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (terminID, rb, iznos, napomena, UslugaID) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return "" + termin.getTerminID() + ", " + rb + ", "
                + " " + iznos + ", '" + napomena + "', " + usluga.getUslugaID();
    }

    @Override
    public String vrednostiZaUpdate() {
        return " napomena = '" + napomena + "'"
                + ", iznos = " + iznos
                + ", uslugaID = " + usluga.getUslugaID();
    }

    @Override
    public String uslov() {
        return " terminID = " + termin.getTerminID()
                + " AND rb = " + rb;
    }

    @Override
    public String uslovZaSelect() {
        return " WHERE T.TERMINID = " + termin.getTerminID();
    }

    public Termin getTermin() {
        return termin;
    }

    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    public int getRb() {
        return rb;
    }

    public void setRb(int rb) {
        this.rb = rb;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public Usluga getUsluga() {
        return usluga;
    }

    public void setUsluga(Usluga usluga) {
        this.usluga = usluga;
    }

}
