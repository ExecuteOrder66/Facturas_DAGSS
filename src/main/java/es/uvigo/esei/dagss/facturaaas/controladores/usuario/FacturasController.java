/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.facturaaas.controladores.usuario;

import es.uvigo.esei.dagss.facturaaas.controladores.AutenticacionController;
import es.uvigo.esei.dagss.facturaaas.daos.ClienteDAO;
import es.uvigo.esei.dagss.facturaaas.daos.DatosFacturacionDAO;
import es.uvigo.esei.dagss.facturaaas.daos.FacturaDAO;
import es.uvigo.esei.dagss.facturaaas.daos.FormaPagoDAO;
import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.Direccion;
import es.uvigo.esei.dagss.facturaaas.entidades.EstadoFactura;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.FormaPago;
import es.uvigo.esei.dagss.facturaaas.entidades.LineaDeFactura;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author danid
 */
@Named(value = "facturasController")
@ViewScoped
public class FacturasController {
    
    private List<Factura> facturas;
    private Factura facturaActual;
    private boolean esNuevo;
    private Cliente clienteBusqueda;

    private EstadoFactura[] estadosFactura = EstadoFactura.values();
   
    @Inject
    private FacturaDAO facturaDAO;

    @Inject
    private ClienteDAO clienteDAO;
    
    @Inject
    private FormaPagoDAO formaPagoDAO;
    
    @Inject
    private AutenticacionController autenticacionController;

    //Ver si acceder a PerfilControlle o a DatosFacturacionDAO
    @Inject
    private DatosFacturacionDAO datosFacturacionDAO;  //Para acceder al tipoIVA por defecto del usuario
        
    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public Factura getFacturaActual() {
        return facturaActual;
    }

    public void setFacturaActual(Factura facturaActual) {
        this.facturaActual = facturaActual;
    }

    public EstadoFactura[] getEstadosFactura() {
        return estadosFactura;
    }
    
    public boolean isEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    @PostConstruct
    public void cargaInicial() {
        this.facturas = refrescarLista();
        this.facturaActual = null;
        this.esNuevo = false;
    }

    
    public void doBuscarConPropietario() {
        //this.facturas = dao.buscarConPropietario(autenticacionController.getUsuarioLogueado());
        this.facturas = refrescarLista();
    }
    
    public void doBuscarConPropietarioPorCliente() {
        this.facturas = facturaDAO.buscarPorClienteConPropietario(autenticacionController.getUsuarioLogueado(), clienteBusqueda);
    }
    
    public void doBuscarTodos() {
        this.facturas = refrescarLista();
    }
    
    //La forma de pago debe estar inicializada con la forma de pago por defecto del usuario actual
    public void doNuevo() {
        this.esNuevo = true;
        this.facturaActual = new Factura();
        this.facturaActual.setPropietario(autenticacionController.getUsuarioLogueado());
        //Forma de pago por defecto del usuario extraido de sus datos de facturacion
        this.facturaActual.setFormaPago(
                datosFacturacionDAO.buscarConPropietario(autenticacionController.getUsuarioLogueado()).getFormaPagoPorDefecto());

        this.facturaActual.setLineasDeFactura(new ArrayList<LineaDeFactura>()); //puse ArrayList por poner una implementacion de List con la que inicializar
        
    }

    public void doEditar(Factura factura) {
        this.esNuevo = false;
        this.facturaActual = factura;
    }


    public void doGuardarEditado() {
        if (this.esNuevo) {
            facturaDAO.crear(facturaActual);
        } else {
            facturaDAO.actualizar(facturaActual);
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
        return facturaDAO.buscarConPropietario(autenticacionController.getUsuarioLogueado());
    }
    
    
    public List<Cliente> listadoClientes(){
        return clienteDAO.buscarTodosConPropietario(autenticacionController.getUsuarioLogueado());
    }
    
    public List<FormaPago> listadoFormaPago(){
        return formaPagoDAO.buscarActivas();
    }

}
