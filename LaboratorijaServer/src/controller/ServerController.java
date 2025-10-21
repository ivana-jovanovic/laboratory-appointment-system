/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.Kategorija;
import domain.Pacijent;
import domain.Termin;
import domain.Usluga;
import domain.Zaposleni;
import java.util.ArrayList;
import so.kategorija.SOGetAllKategorija;
import so.login.SOLogin;
import so.pacijent.SOAddPacijent;
import so.pacijent.SODeletePacijent;
import so.pacijent.SOGetAllPacijent;
import so.pacijent.SOUpdatePacijent;
import so.termin.SOAddTermin;
import so.termin.SODeleteTermin;
import so.termin.SOGetAllTermin;
import so.termin.SOUpdateTermin;
import so.usluga.SOAddUsluga;
import so.usluga.SODeleteUsluga;
import so.usluga.SOGetAllUsluga;
import so.usluga.SOUpdateUsluga;

/**
 *
 * @author Ivana
 */
public class ServerController {

    private static ServerController instance;
    private ArrayList<Zaposleni> ulogovaniZaposleni = new ArrayList<>();

    private ServerController() {
    }

    public static ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }
        return instance;
    }

    public ArrayList<Zaposleni> getUlogovaniZaposleni() {
        return ulogovaniZaposleni;
    }

    public void setUlogovaniZaposleni(ArrayList<Zaposleni> ulogovaniZaposleni) {
        this.ulogovaniZaposleni = ulogovaniZaposleni;
    }

    public Zaposleni login(Zaposleni zap) throws Exception {
        SOLogin so = new SOLogin();
        so.templateExecute(zap);
        return so.getUlogovani();
    }

    public void addPacijent(Pacijent pacijent) throws Exception {
        (new SOAddPacijent()).templateExecute(pacijent);
    }

    public void addTermin(Termin termin) throws Exception {
        (new SOAddTermin()).templateExecute(termin);
    }

    public void addUsluga(Usluga usluga) throws Exception {
        (new SOAddUsluga()).templateExecute(usluga);
    }

    public void deletePacijent(Pacijent pacijent) throws Exception {
        (new SODeletePacijent()).templateExecute(pacijent);
    }

    public void deleteTermin(Termin termin) throws Exception {
        (new SODeleteTermin()).templateExecute(termin);
    }

    public void deleteUsluga(Usluga usluga) throws Exception {
        (new SODeleteUsluga()).templateExecute(usluga);
    }

    public void updatePacijent(Pacijent pacijent) throws Exception {
        (new SOUpdatePacijent()).templateExecute(pacijent);
    }

    public void updateTermin(Termin termin) throws Exception {
        (new SOUpdateTermin()).templateExecute(termin);
    }

    public void updateUsluga(Usluga usluga) throws Exception {
        (new SOUpdateUsluga()).templateExecute(usluga);
    }

    public ArrayList<Pacijent> getAllPacijent() throws Exception {
        SOGetAllPacijent so = new SOGetAllPacijent();
        so.templateExecute(new Pacijent());
        return so.getLista();
    }

    public ArrayList<Termin> getAllTermin(Pacijent pacijent) throws Exception {
        SOGetAllTermin so = new SOGetAllTermin();

        Termin termin = new Termin();
        termin.setPacijent(pacijent);
        // Ako je pacijent NULL, ne radimo WHERE

        so.templateExecute(termin);
        return so.getLista();
    }

    public ArrayList<Usluga> getAllUsluga() throws Exception {
        SOGetAllUsluga so = new SOGetAllUsluga();
        so.templateExecute(new Usluga());
        return so.getLista();
    }

    public ArrayList<Kategorija> getAllKategorija() throws Exception {
        SOGetAllKategorija so = new SOGetAllKategorija();
        so.templateExecute(new Kategorija());
        return so.getLista();
    }

    public void logout(Zaposleni ulogovani) {
        ulogovaniZaposleni.remove(ulogovani);
    }

}
