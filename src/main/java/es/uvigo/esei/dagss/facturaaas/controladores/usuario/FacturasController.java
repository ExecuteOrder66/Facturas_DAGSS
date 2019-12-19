/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.facturaaas.controladores.usuario;

import es.uvigo.esei.dagss.facturaaas.controladores.AutenticacionController;
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
        return clienteActual;
    }

    public void setClienteActual(Cliente clienteActual) {
        this.clienteActual = clienteActual;
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
        this.clientes = refrescarLista();
        this.clienteActual = null;
        this.esNuevo = false;
    }

    
    public void doBuscarPorNombre() {
        this.clientes = dao.buscarPorNombreConPropietario(autenticacionController.getUsuarioLogueado(), textoBusqueda);
    }

    public void doBuscarPorLocalidad() {
        this.clientes = dao.buscarPorLocalidadConPropietario(autenticacionController.getUsuarioLogueado(), textoBusqueda);
    }
    
    public void doBuscarTodos() {
        this.clientes = refrescarLista();
    }
    
    
    public void doNuevo() {
        this.esNuevo = true;
        this.clienteActual = new Cliente();
        this.clienteActual.setPropietario(autenticacionController.getUsuarioLogueado());
        this.clienteActual.setDireccion(new Direccion("","","",""));
    }

    public void doEditar(Cliente cliente) {
        this.esNuevo = false;
        this.clienteActual = cliente;
    }


    public void doGuardarEditado() {
        if (this.esNuevo) {
            dao.crear(clienteActual);
        } else {
            dao.actualizar(clienteActual);
        }
        this.clientes = refrescarLista();
        this.clienteActual = null;
        this.esNuevo = false;
    }

    public void doCancelarEditado() {
        this.clienteActual = null;
        this.esNuevo = false;
    }

    private List<Cliente> refrescarLista() {
        return dao.buscarTodosConPropietario(autenticacionController.getUsuarioLogueado());
    }
}
