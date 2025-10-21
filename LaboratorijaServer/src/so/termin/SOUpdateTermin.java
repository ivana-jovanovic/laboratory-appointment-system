/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.termin;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.StavkaTermina;
import domain.Termin;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import so.AbstractSO;

/**
 *
 * @author Ivana
 */
public class SOUpdateTermin extends AbstractSO {

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Termin)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Termin!");
        }

        Termin t = (Termin) ado;

        if (!t.getDatumVreme().after(new Date())) {
            throw new Exception("Datum i vreme termina mora biti posle danasnjeg datuma!");
        }

        if (t.getStavkeTermina().isEmpty()) {
            throw new Exception("Termin mora da ima barem jednu stavku!");
        }

    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        Termin t = (Termin) ado;

        DBBroker.getInstance().update(t);

        ArrayList<StavkaTermina> stareStavke
                = (ArrayList<StavkaTermina>) (ArrayList<?>) DBBroker.getInstance()
                        .select(new StavkaTermina(t, 0, 0, null, null));

        HashMap<Integer, StavkaTermina> mapaStarih = new HashMap<>();
        for (StavkaTermina st : stareStavke) {
            mapaStarih.put(st.getRb(), st);
        }

        HashMap<Integer, StavkaTermina> mapaNovih = new HashMap<>();
        for (StavkaTermina nova : t.getStavkeTermina()) {
            mapaNovih.put(nova.getRb(), nova);
        }

        for (StavkaTermina stara : stareStavke) {
            if (!mapaNovih.containsKey(stara.getRb())) {
                DBBroker.getInstance().delete(stara);
            }
        }

        for (StavkaTermina nova : t.getStavkeTermina()) {
            if (mapaStarih.containsKey(nova.getRb())) {
                DBBroker.getInstance().update(nova);
            }
        }

        for (StavkaTermina nova : t.getStavkeTermina()) {
            if (!mapaStarih.containsKey(nova.getRb())) {
                DBBroker.getInstance().insert(nova);
            }
        }

    }

}
