/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.beans;

import cl.session.BussinesFacade;
import cl.session.BussinesFacadeLocal;
import cl.session.UnitFacade;
import cl.session.UnitFacadeLocal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author AndresEduardo
 */
@ManagedBean
@RequestScoped
public class UnitBean {
    @EJB
    private final BussinesFacadeLocal bussinesFacade;
    @EJB
    private final UnitFacadeLocal unitFacade;
    

    
    public UnitBean() {
        unitFacade = new UnitFacade();
        bussinesFacade = new BussinesFacade();
    }
    
}
