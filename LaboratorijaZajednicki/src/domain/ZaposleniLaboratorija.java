/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Ivana
 */
public class ZaposleniLaboratorija extends AbstractDomainObject {

    private Laboratorija laboratorija;
    private Zaposleni zaposleni;
    private Date datum;
    private String opisRada;

    public ZaposleniLaboratorija(Laboratorija laboratorija, Zaposleni zaposleni, Date datum, String opisRada) {
        this.laboratorija = laboratorija;
        this.zaposleni = zaposleni;
        this.datum = datum;
        this.opisRada = opisRada;
    }

    public ZaposleniLaboratorija() {
    }

    @Override
    public String nazivTabele() {
        return " ZaposleniLaboratorija ";
    }

    @Override
    public String alijas() {
        return " zl ";
    }

    @Override
    public String join() {
        return " JOIN ZAPOSLENI Z ON (Z.ZAPOSLENIID = ZL.ZAPOSLENIID)\n"
                + "JOIN LABORATORIJA L ON (L.LABORATORIJAID = ZL.LABORATORIJAID) ";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {
            Zaposleni z = new Zaposleni(rs.getLong("ZaposleniID"),
                    rs.getString("Ime"), rs.getString("Prezime"),
                    rs.getString("Username"), rs.getString("Password"));

            Laboratorija l = new Laboratorija(rs.getLong("LaboratorijaID"),
                    rs.getString("naziv"), rs.getString("grad"));

            ZaposleniLaboratorija zl = new ZaposleniLaboratorija(l, z, rs.getDate("datum"),
                    rs.getString("opisRada"));

            lista.add(zl);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (LaboratorijaID, ZaposleniID, datum, opisRada) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return " " + laboratorija.getLaboratorijaID() + ", " + zaposleni.getZaposleniID() + ", "
                + "'" + new java.sql.Date(datum.getTime()) + "', '" + opisRada + "' ";
    }

    @Override
    public String vrednostiZaUpdate() {
        return " opisRada = '" + opisRada + "' ";
    }

    @Override
    public String uslov() {
        return " zaposleniID = " + zaposleni.getZaposleniID();
    }

    @Override
    public String uslovZaSelect() {
        return "";
    }

    public Laboratorija getLaboratorija() {
        return laboratorija;
    }

    public void setLaboratorija(Laboratorija laboratorija) {
        this.laboratorija = laboratorija;
    }

    public Zaposleni getZaposleni() {
        return zaposleni;
    }

    public void setZaposleni(Zaposleni zaposleni) {
        this.zaposleni = zaposleni;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getOpisRada() {
        return opisRada;
    }

    public void setOpisRada(String opisRada) {
        this.opisRada = opisRada;
    }

}
