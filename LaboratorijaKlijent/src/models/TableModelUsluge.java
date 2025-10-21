/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controller.ClientController;
import domain.Usluga;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Ivana
 */
public class TableModelUsluge extends AbstractTableModel implements Runnable {

    private ArrayList<Usluga> lista;
    private String[] kolone = {"ID", "Naziv", "Opis", "Cena"};
    private String parametar = "";

    public TableModelUsluge() {
        try {
            lista = ClientController.getInstance().getAllUsluga();
        } catch (Exception ex) {
            Logger.getLogger(TableModelUsluge.class.getName()).log(Level.SEVERE, null, ex);
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
        Usluga u = lista.get(row);

        switch (column) {
            case 0:
                return u.getUslugaID();
            case 1:
                return u.getNaziv();
            case 2:
                return u.getOpis();
            case 3: 
                return u.getCena();
                
            default:
                return null;
        }
    }

    public Usluga getSelectedUsluga(int row) {
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
            Logger.getLogger(TableModelUsluge.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setParametar(String parametar) {
        this.parametar = parametar;
        refreshTable();
    }

    public void refreshTable() {
        try {
            lista = ClientController.getInstance().getAllUsluga();
            if (!parametar.equals("")) {
                ArrayList<Usluga> novaLista = new ArrayList<>();
                for (Usluga u : lista) {
                    if (u.getNaziv().toLowerCase().contains(parametar.toLowerCase())
                            || u.getOpis().toLowerCase().contains(parametar.toLowerCase())) {
                        novaLista.add(u);
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
