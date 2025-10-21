/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controller.ClientController;
import domain.Pacijent;
import domain.Termin;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Ivana
 */
public class TableModelTermini extends AbstractTableModel implements Runnable {

    private ArrayList<Termin> lista;
    private String[] kolone = {"ID", "Klijent", "Datum i vreme", "Ukupna cena"};
    private String parametar = "";

    public TableModelTermini() {
        try {
            lista = ClientController.getInstance().getAllTermin(null);
            
        } catch (Exception ex) {
            Logger.getLogger(TableModelTermini.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TableModelTermini(Pacijent pacijent) {
        try {
            lista = ClientController.getInstance().getAllTermin(pacijent);
        } catch (Exception ex) {
            Logger.getLogger(TableModelTermini.class.getName()).log(Level.SEVERE, null, ex);
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
        Termin t = lista.get(row);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        switch (column) {
            case 0:
                return t.getTerminID();
            case 1:
                return t.getPacijent();
            case 2:
                return sdf.format(t.getDatumVreme());
            case 3:
                return t.getUkupanIznos() + "din";

            default:
                return null;
        }
    }

    public Termin getSelectedTermin(int row) {
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
            Logger.getLogger(TableModelTermini.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setParametar(String parametar) {
        this.parametar = parametar;
        refreshTable();
    }

    public void refreshTable() {
        try {
            lista = ClientController.getInstance().getAllTermin(null);
            if (!parametar.equals("")) {
                ArrayList<Termin> novaLista = new ArrayList<>();
                for (Termin t : lista) {
                    if (t.getPacijent().getIme().toLowerCase().contains(parametar.toLowerCase())
                            || t.getPacijent().getPrezime().toLowerCase().contains(parametar.toLowerCase())) {
                        novaLista.add(t);
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
