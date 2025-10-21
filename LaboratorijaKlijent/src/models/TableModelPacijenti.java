/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controller.ClientController;
import domain.Pacijent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Ivana
 */
public class TableModelPacijenti extends AbstractTableModel implements Runnable {

    private ArrayList<Pacijent> lista;
    private String[] kolone = {"ID", "Ime", "Prezime", "Email", "Telefon", "Kategorija"};
    private String parametar = "";

    public TableModelPacijenti() {
        try {
            lista = ClientController.getInstance().getAllPacijent();
        } catch (Exception ex) {
            Logger.getLogger(TableModelPacijenti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int i) {
        return kolone[i];
    }

    @Override
    public Object getValueAt(int row, int column) {
        Pacijent p = lista.get(row);

        switch (column) {
            case 0:
                return p.getPacijentID();
            case 1:
                return p.getIme();
            case 2:
                return p.getPrezime();
            case 3:
                return p.getEmail();
            case 4:
                return p.getTelefon();
            case 5:
                return p.getKategorija();

            default:
                return null;
        }
    }

    public Pacijent getSelectedPacijent(int row) {
        return lista.get(row);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(10000);
                refreshTable();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(TableModelPacijenti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setParametar(String parametar) {
        this.parametar = parametar;
        refreshTable();
    }

    public void refreshTable() {
        try {
            lista = ClientController.getInstance().getAllPacijent();
            if (!parametar.equals("")) {
                ArrayList<Pacijent> novaLista = new ArrayList<>();
                for (Pacijent p : lista) {
                    if (p.getIme().toLowerCase().contains(parametar.toLowerCase())
                            || p.getPrezime().toLowerCase().contains(parametar.toLowerCase())
                            || p.getEmail().toLowerCase().contains(parametar.toLowerCase())) {
                        novaLista.add(p);
                    }
                }
                lista = novaLista;
            }

            fireTableDataChanged();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
