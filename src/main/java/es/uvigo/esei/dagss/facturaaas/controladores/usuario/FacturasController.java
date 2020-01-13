/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.facturaaas.controladores.usuario;

import es.uvigo.esei.dagss.facturaaas.controladores.AutenticacionController;
import es.uvigo.esei.dagss.facturaaas.daos.FacturaDAO;
import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.Direccion;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author danid
 */
public class FacturasController {
    
    private List<Factura> facturas;
    private Factura facturaActual;
    private boolean esNuevo;
    private String textoBusqueda;

   
    @Inject
    private FacturaDAO dao;

    @Inject
    private AutenticacionController autenticacionController;

        
    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setClientes(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public Factura getFacturaActual() {
        return facturaActual;
    }

    public void setClienteActual(Factura facturaActual) {
        this.facturaActual = facturaActual;
    }

    public boolean isEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }
    
    

    @PostConstruct
    public void cargaInicial() {
        this.facturas = refrescarLista();
        this.facturaActual = null;
        this.esNuevo = false;
    }

    
    public void doBuscarConPropietario() {
        this.facturas = dao.buscarConPropietario(autenticacionController.getUsuarioLogueado());
    }

    public void doBuscarConPropietarioPorCliente(Cliente c) {
        this.facturas = dao.buscarPorClienteConPropietario(autenticacionController.getUsuarioLogueado(), c);
    }
    
    public void doBuscarTodos() {
        this.facturas = refrescarLista();
    }
    
    //Que debe hacer esta funcion?
    public void doNuevo() {
        this.esNuevo = true;
        this.facturaActual = new Factura();
        this.facturaActual.setPropietario(autenticacionController.getUsuarioLogueado());
        this.facturaActual.setComentarios(textoBusqueda);   //ni idea
        //TO DO
    }

    public void doEditar(Factura factura) {
        this.esNuevo = false;
        this.facturaActual = factura;
    }


    public void doGuardarEditado() {
        if (this.esNuevo) {
            dao.crear(facturaActual);
        } else {
            dao.actualizar(facturaActual);
        }
        this.facturas = refrescarLista();
        this.facturaActual = null;
        this.esNuevo = false;
    }

    public void doCancelarEditado() {
        this.facturaActual = null;
        this.esNuevo = false;
    }

    private List<Factura> refrescarLista() {
        return dao.buscarConPropietario(autenticacionController.getUsuarioLogueado());
    }
}
