/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.beans;

import cl.Aplications.appBean;
import static cl.Aplications.appBean.sha256;
import cl.entity.Professional;
import cl.session.BussinesFacade;
import cl.session.ProfessionalFacade;
import cl.session.ProfessionalFacadeLocal;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author AndresEduardo
 */
@ManagedBean
@RequestScoped
public class ProfessionalBean {

    @EJB
    private final ProfessionalFacadeLocal professionalFacade;
    @EJB
    private final cl.session.BussinesFacadeLocal bussinesFacade;
    private String dv, rut, clave;
    private Professional professional;

    public ProfessionalBean() {
        bussinesFacade = new BussinesFacade();
        professionalFacade = new ProfessionalFacade();
    }

    @PostConstruct
    public void myInit() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        if (extContext.getSessionMap().get("UsuarioSistema") != null) {
            professional = (Professional) extContext.getSessionMap().get("UsuarioSistema");
        } else {
            professional = new Professional();
        }
    }

    public void login() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        rut = rut.replace("-", "");
        rut = rut.replace(".", "");
        String rut1 = rut.substring(0, rut.length() - 1);
        String dv2 = rut.charAt(rut.length() - 1) + "";
        clave = sha256(clave);
        Professional professionalAux = bussinesFacade.getProfessional(Integer.parseInt(rut1), dv2, clave);
        if (professionalAux == null) {
            System.out.println("null");
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error en Credenciales1", "Datos Invalidos"));
        } else {
            if (professionalAux.getPassword().equals(clave)) {
                ExternalContext extContext = context.getExternalContext();
                extContext.getSessionMap().put("UsuarioSistema", professionalAux);
                if (professionalAux.getRoleid().getRoleid() == 1) {
                    System.out.println("1");
                    String url = extContext.encodeActionURL(context.getApplication().getViewHandler().getActionURL(context, "/layouts/administrador.xhtml"));
                    extContext.redirect(url);
                }
                if (professionalAux.getRoleid().getRoleid() == 2) {
                    String url = extContext.encodeActionURL(context.getApplication().getViewHandler().getActionURL(context, "/layouts/admisionista.xhtml"));
                    extContext.redirect(url);
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error en Credenciales2", "Datos Invalidos"));
            }
        }
    }

    public void onValidaRut() {
        rut = rut.replace("-", "");
        rut = rut.replace(".", "");
        String rut1 = rut.substring(0, rut.length() - 1);
        dv = rut.charAt(rut.length() - 1) + "";
        if (!appBean.validarRut(rut1, dv)) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Rut Invalido", "Rut Invalido"));
        }
    }

    public void onFormateaRut() {
        this.setRut(appBean.FormateaRut(rut));
    }

    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

}
