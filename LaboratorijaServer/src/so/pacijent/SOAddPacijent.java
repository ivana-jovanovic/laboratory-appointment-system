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
import java.util.regex.Pattern;
import so.AbstractSO;

/**
 *
 * @author Ivana
 */
public class SOAddPacijent extends AbstractSO {

    private static final Pattern EMAIL_PATTERN
            = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final Pattern TELEFON_PATTERN
            = Pattern.compile("^06[0-9]{7}$");

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Pacijent)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Pacijent!");
        }

        Pacijent p = (Pacijent) ado;

        if (!EMAIL_PATTERN.matcher(p.getEmail()).matches()) {
            throw new Exception("Email nije u ispravnom formatu!");
        }

        if (!TELEFON_PATTERN.matcher(p.getTelefon()).matches()) {
            throw new Exception("Telefon mora biti u formatu 06XXXXXXXX!");
        }

        ArrayList<Pacijent> pacijenti = (ArrayList<Pacijent>) (ArrayList<?>) DBBroker.getInstance().select(ado);

        for (Pacijent pacijent : pacijenti) {
            if (pacijent.getEmail().equals(p.getEmail())) {
                throw new Exception("Email mora biti jedinstven!");
            }
            if (pacijent.getTelefon().equals(p.getTelefon())) {
                throw new Exception("Email mora biti jedinstven!");
            }
        }

    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        DBBroker.getInstance().insert(ado);
    }

}
