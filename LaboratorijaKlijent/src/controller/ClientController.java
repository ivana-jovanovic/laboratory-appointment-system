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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import session.Session;
import transfer.Request;
import transfer.Response;
import transfer.util.ResponseStatus;
import transfer.util.Operation;

/**
 *
 * @author Ivana
 */
public class ClientController {

    private static ClientController instance;

    private ClientController() {
    }

    public static ClientController getInstance() {
        if (instance == null) {
            instance = new ClientController();
        }
        return instance;
    }

    public Zaposleni login(Zaposleni zaposleni) throws Exception {
        return (Zaposleni) sendRequest(Operation.LOGIN, zaposleni);
    }

    public void logout(Zaposleni ulogovani) throws Exception {
        sendRequest(Operation.LOGOUT, ulogovani);
    }

    public void addPacijent(Pacijent pacijent) throws Exception {
        sendRequest(Operation.ADD_PACIJENT, pacijent);
    }

    public void addTermin(Termin termin) throws Exception {
        sendRequest(Operation.ADD_TERMIN, termin);
    }
    
    public void addUsluga(Usluga usluga) throws Exception {
        sendRequest(Operation.ADD_USLUGA, usluga);
    }

    public void deletePacijent(Pacijent pacijent) throws Exception {
        sendRequest(Operation.DELETE_PACIJENT, pacijent);
    }

    public void deleteTermin(Termin termin) throws Exception {
        sendRequest(Operation.DELETE_TERMIN, termin);
    }
    
    public void deleteUsluga(Usluga usluga) throws Exception {
        sendRequest(Operation.DELETE_USLUGA, usluga);
    }

    public void updatePacijent(Pacijent pacijent) throws Exception {
        sendRequest(Operation.UPDATE_PACIJENT, pacijent);
    }

    public void updateTermin(Termin termin) throws Exception {
        sendRequest(Operation.UPDATE_TERMIN, termin);
    }
    
    public void updateUsluga(Usluga usluga) throws Exception {
        sendRequest(Operation.UPDATE_USLUGA, usluga);
    }

    public ArrayList<Pacijent> getAllPacijent() throws Exception {
        return (ArrayList<Pacijent>) sendRequest(Operation.GET_ALL_PACIJENT, null);
    }

    public ArrayList<Termin> getAllTermin(Pacijent pacijent) throws Exception {
        return (ArrayList<Termin>) sendRequest(Operation.GET_ALL_TERMIN, pacijent);
    }
    
    public ArrayList<Usluga> getAllUsluga() throws Exception {
        return (ArrayList<Usluga>) sendRequest(Operation.GET_ALL_USLUGA, null);
    }

    public ArrayList<Kategorija> getAllKategorija() throws Exception {
        return (ArrayList<Kategorija>) sendRequest(Operation.GET_ALL_KATEGORIJA, null);
    }

    private Object sendRequest(int operation, Object data) throws Exception {
        Request request = new Request(operation, data);

        ObjectOutputStream out = new ObjectOutputStream(Session.getInstance().getSocket().getOutputStream());
        out.writeObject(request);

        ObjectInputStream in = new ObjectInputStream(Session.getInstance().getSocket().getInputStream());
        Response response = (Response) in.readObject();

        if (response.getResponseStatus().equals(ResponseStatus.Error)) {
            throw response.getException();
        } else {
            return response.getData();
        }

    }

}
