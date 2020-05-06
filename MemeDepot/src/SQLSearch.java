/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hannah Hawkins
 */
public class SQLSearch {
    String query;
    Object[] params;
    
    public SQLSearch(String query, Object[] params) {
        this.query = query;
        this.params = params;
    }
}
