/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import controller.ServerController;
import domain.Pacijent;
import domain.Termin;
import domain.Usluga;
import domain.Zaposleni;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import transfer.Request;
import transfer.Response;
import transfer.util.ResponseStatus;
import transfer.util.Operation;

/**
 *
 * @author Ivana
 */
public class ThreadClient extends Thread {

    private Socket socket;

    ThreadClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Request request = (Request) in.readObject();
                Response response = handleRequest(request);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response handleRequest(Request request) {
        Response response = new Response(null, null, ResponseStatus.Success);
        try {
            switch (request.getOperation()) {
                case Operation.ADD_PACIJENT:
                    ServerController.getInstance().addPacijent((Pacijent) request.getData());
                    break;
                case Operation.ADD_TERMIN:
                    ServerController.getInstance().addTermin((Termin) request.getData());
                    break;
                case Operation.ADD_USLUGA:
                    ServerController.getInstance().addUsluga((Usluga) request.getData());
                    break;
                case Operation.DELETE_PACIJENT:
                    ServerController.getInstance().deletePacijent((Pacijent) request.getData());
                    break;
                case Operation.DELETE_TERMIN:
                    ServerController.getInstance().deleteTermin((Termin) request.getData());
                    break;
                case Operation.DELETE_USLUGA:
                    ServerController.getInstance().deleteUsluga((Usluga) request.getData());
                    break;
                case Operation.UPDATE_PACIJENT:
                    ServerController.getInstance().updatePacijent((Pacijent) request.getData());
                    break;
                case Operation.UPDATE_TERMIN:
                    ServerController.getInstance().updateTermin((Termin) request.getData());
                    break;
                case Operation.UPDATE_USLUGA:
                    ServerController.getInstance().updateUsluga((Usluga) request.getData());
                    break;
                case Operation.GET_ALL_KATEGORIJA:
                    response.setData(ServerController.getInstance().getAllKategorija());
                    break;
                case Operation.GET_ALL_PACIJENT:
                    response.setData(ServerController.getInstance().getAllPacijent());
                    break;
                case Operation.GET_ALL_TERMIN:
                    response.setData(ServerController.getInstance().getAllTermin((Pacijent) request.getData()));
                    break;
                case Operation.GET_ALL_USLUGA:
                    response.setData(ServerController.getInstance().getAllUsluga());
                    break;
                case Operation.LOGIN:
                    Zaposleni zaposleni = (Zaposleni) request.getData();
                    Zaposleni zap = ServerController.getInstance().login(zaposleni);
                    response.setData(zap);
                    break;
                case Operation.LOGOUT:
                    Zaposleni ulogovani = (Zaposleni) request.getData();
                    ServerController.getInstance().logout(ulogovani);
                    break;
                default:
                    return null;
            }
        } catch (Exception ex) {
            response.setResponseStatus(ResponseStatus.Error);
            response.setException(ex);
        }
        return response;
    }

}
