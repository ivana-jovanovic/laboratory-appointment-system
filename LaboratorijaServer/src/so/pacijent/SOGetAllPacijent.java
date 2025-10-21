/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.pacijent;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Pacijent;
import java.util.ArrayList;
import so.AbstractSO;

/**
 *
 * @author Ivana
 */
public class SOGetAllPacijent extends AbstractSO {

    private ArrayList<Pacijent> lista;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Pacijent)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Pacijent!");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        ArrayList<AbstractDomainObject> pacijenti = DBBroker.getInstance().select(ado);
        lista = (ArrayList<Pacijent>) (ArrayList<?>) pacijenti;
    }

    public ArrayList<Pacijent> getLista() {
        return lista;
    }

}
