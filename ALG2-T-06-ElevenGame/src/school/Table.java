/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package school;

import datastore.DataStore;
import java.util.ArrayList;

/**
 *
 * @author Vitvarova-J-31c9
 */
public class Table {
    private ArrayList<Card> table = new ArrayList();
    
    public Table(ArrayList<Card> cards){
        this.table = cards;
       
    }
    
    public Card getCard(int index){
        return table.get(index);
    }

    public ArrayList<Card> getTable() {
        return table;
    }
}