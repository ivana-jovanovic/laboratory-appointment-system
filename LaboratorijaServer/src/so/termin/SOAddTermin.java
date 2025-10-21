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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import so.AbstractSO;

/**
 *
 * @author Ivana
 */
public class SOAddTermin extends AbstractSO {

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

        PreparedStatement ps = DBBroker.getInstance().insert(ado);

        ResultSet tableKeys = ps.getGeneratedKeys();
        tableKeys.next();
        Long noviTerminID = tableKeys.getLong(1);

        Termin noviTermin = (Termin) ado;
        noviTermin.setTerminID(noviTerminID);

        for (StavkaTermina stavkaTermina : noviTermin.getStavkeTermina()) {
            stavkaTermina.setTermin(noviTermin);
            DBBroker.getInstance().insert(stavkaTermina);
        }

    }

}
