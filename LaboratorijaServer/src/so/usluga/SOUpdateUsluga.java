/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.usluga;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Usluga;
import java.util.ArrayList;
import so.AbstractSO;

/**
 *
 * @author Ivana
 */
public class SOUpdateUsluga extends AbstractSO {

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Usluga)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Usluga!");
        }

        Usluga u = (Usluga) ado;

        if (u.getCena() <= 0) {
            throw new Exception("Cena usluge mora biti veća od 0!");
        }

        ArrayList<Usluga> usluge = (ArrayList<Usluga>) (ArrayList<?>) DBBroker.getInstance().select(ado);

        for (Usluga usluga : usluge) {
            if (!usluga.getUslugaID().equals(u.getUslugaID())) {
                if (usluga.getNaziv().equals(u.getNaziv())) {
                    throw new Exception("Usluga sa tim nazivom vec postoji!");
                }
            }
        }

    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        DBBroker.getInstance().update(ado);
    }

}
