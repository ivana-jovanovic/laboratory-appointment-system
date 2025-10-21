/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Ivana
 */
public class Termin extends AbstractDomainObject {

    private Long terminID;
    private Date datumVreme;
    private String opis;
    private double iznosBezPopusta;
    private double popust;
    private double ukupanIznos;
    private Pacijent pacijent;
    private Zaposleni zaposleni;
    private ArrayList<StavkaTermina> stavkeTermina;

    public Termin(Long terminID, Date datumVreme, String opis, double iznosBezPopusta, double popust, double ukupanIznos, Pacijent pacijent, Zaposleni zaposleni, ArrayList<StavkaTermina> stavkeTermina) {
        this.terminID = terminID;
        this.datumVreme = datumVreme;
        this.opis = opis;
        this.iznosBezPopusta = iznosBezPopusta;
        this.popust = popust;
        this.ukupanIznos = ukupanIznos;
        this.pacijent = pacijent;
        this.zaposleni = zaposleni;
        this.stavkeTermina = stavkeTermina;
    }

    public Termin() {
    }

    @Override
    public String nazivTabele() {
        return " Termin ";
    }

    @Override
    public String alijas() {
        return " t ";
    }

    @Override
    public String join() {
        return " JOIN PACIJENT P ON (P.PACIJENTID = T.PACIJENTID)\n"
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

            lista.add(t);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (datumVreme, opis, iznosBezPopusta, popust, ukupanIznos, PacijentID, ZaposleniID) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return "'" + new Timestamp(datumVreme.getTime()) + "', '" + opis + "', "
                + iznosBezPopusta + ", " + popust + ", "
                + " " + ukupanIznos + ", " + pacijent.getPacijentID() + ", "
                + zaposleni.getZaposleniID();
    }

    @Override
    public String vrednostiZaUpdate() {
        return " datumVreme = '" + new Timestamp(datumVreme.getTime()) + "', "
                + "opis = '" + opis + "', "
                + "iznosBezPopusta = " + iznosBezPopusta + ", "
                + "ukupanIznos = " + ukupanIznos + " ";
    }

    @Override
    public String uslov() {
        return " terminID = " + terminID;
    }

    @Override
    public String uslovZaSelect() {
        if (pacijent == null) {
            return "";
        }
        return " WHERE P.PACIJENTID = " + pacijent.getPacijentID();
    }

    public Long getTerminID() {
        return terminID;
    }

    public void setTerminID(Long terminID) {
        this.terminID = terminID;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(Date datumVreme) {
        this.datumVreme = datumVreme;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getUkupanIznos() {
        return ukupanIznos;
    }

    public void setUkupanIznos(double ukupanIznos) {
        this.ukupanIznos = ukupanIznos;
    }

    public Pacijent getPacijent() {
        return pacijent;
    }

    public void setPacijent(Pacijent pacijent) {
        this.pacijent = pacijent;
    }

    public Zaposleni getZaposleni() {
        return zaposleni;
    }

    public void setZaposleni(Zaposleni zaposleni) {
        this.zaposleni = zaposleni;
    }

    public ArrayList<StavkaTermina> getStavkeTermina() {
        return stavkeTermina;
    }

    public void setStavkeTermina(ArrayList<StavkaTermina> stavkeTermina) {
        this.stavkeTermina = stavkeTermina;
    }

    public double getIznosBezPopusta() {
        return iznosBezPopusta;
    }

    public void setIznosBezPopusta(double iznosBezPopusta) {
        this.iznosBezPopusta = iznosBezPopusta;
    }

    public double getPopust() {
        return popust;
    }

    public void setPopust(double popust) {
        this.popust = popust;
    }

}
