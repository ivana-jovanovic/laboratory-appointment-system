/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.pacijent;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Pacijent;
import so.AbstractSO;

/**
 *
 * @author Ivana
 */
public class SODeletePacijent extends AbstractSO {

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Pacijent)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Pacijent!");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        DBBroker.getInstance().delete(ado);
    }

}
