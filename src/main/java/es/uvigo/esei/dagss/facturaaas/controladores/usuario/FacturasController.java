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
import es.uvigo.esei.dagss.facturaaas.daos.LineaDeFacturaDAO;
import es.uvigo.esei.dagss.facturaaas.daos.TipoIVADAO;
import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.DatosFacturacion;
import es.uvigo.esei.dagss.facturaaas.entidades.Direccion;
import es.uvigo.esei.dagss.facturaaas.entidades.EstadoFactura;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.FormaPago;
import es.uvigo.esei.dagss.facturaaas.entidades.LineaDeFactura;
import es.uvigo.esei.dagss.facturaaas.entidades.TipoIVA;
import java.io.Serializable;
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
public class FacturasController implements Serializable {
    
    private List<Factura> facturas;
    private List<LineaDeFactura> lineasFacturaActual;


    private Factura facturaActual;
    private LineaDeFactura lineaActual;
    private TipoIVA tipoIVA;


    private boolean esNuevo;

    private boolean esNuevaLinea;   //es lineaDeFactura nueva
    private Cliente clienteElegido; //Para insertar el cliente (elegido en lista desplegable) vinculado a la factura
    private FormaPago formaPago;    //Para insertar la formaPago (elegido en lista desplegable) vinculada a la factura
    private DatosFacturacion datosFacturacion;
  
    private Cliente clienteBusqueda; //Para buscar facturas de un cliente en concreto

    private EstadoFactura[] estadosFactura = EstadoFactura.values();
   
    @Inject
    private LineaDeFacturaDAO lineaDeFacturaDAO;
    
    @Inject
    private TipoIVADAO tipoIVADAO;
    
    @Inject
    private FacturaDAO facturaDAO;

    @Inject
    private ClienteDAO clienteDAO;
    
    @Inject
    private FormaPagoDAO formaPagoDAO;
    
    @Inject
    private AutenticacionController autenticacionController;

    @Inject
    private DatosFacturacionDAO datosFacturacionDAO;  //Para acceder a la formaPago por defecto del usuario
        
    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public List<LineaDeFactura> getLineasFacturaActual() {
        return lineasFacturaActual;
    }

    public void setLineasFacturaActual(List<LineaDeFactura> lineasFacturaActual) {
        this.lineasFacturaActual = lineasFacturaActual;
    }
    
    public boolean isEsNuevaLinea() {
        return esNuevaLinea;
    }

    public void setEsNuevaLinea(boolean esNuevaLinea) {
        this.esNuevaLinea = esNuevaLinea;
    }
    
    public LineaDeFactura getLineaActual() {
        return lineaActual;
    }

    public void setLineaActual(LineaDeFactura lineaActual) {
        this.lineaActual = lineaActual;
    }
    
    public TipoIVA getTipoIVA() {
        return tipoIVA;
    }

    public void setTipoIVA(TipoIVA tipoIVA) {
        this.tipoIVA = tipoIVA;
    }
    
    public Factura getFacturaActual() {
        return facturaActual;
    }

    public void setFacturaActual(Factura facturaActual) {
        this.facturaActual = facturaActual;
    }

    
    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Cliente getClienteElegido() {
        return clienteElegido;
    }

    public void setClienteElegido(Cliente clienteElegido) {
        this.clienteElegido = clienteElegido;
    }
    
    public EstadoFactura[] getEstadosFactura() {
        return estadosFactura;
    }
    
    public Cliente getClienteBusqueda() {
        return clienteBusqueda;
    }

    public void setClienteBusqueda(Cliente clienteBusqueda) {
        this.clienteBusqueda = clienteBusqueda;
    }
    
    public boolean isEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    public DatosFacturacion getDatosFacturacion() {
        return datosFacturacion;
    }

    public void setDatosFacturacion(DatosFacturacion datosFacturacion) {
        this.datosFacturacion = datosFacturacion;
    }

    @PostConstruct
    public void cargaInicial() {
        System.out.println("Carga inicial");
        this.facturas = refrescarLista();
        this.facturaActual = null;
        this.lineaActual = null;
        this.esNuevo = false;
        this.esNuevaLinea = false;
        System.out.println("fin carga inicial");
    }

    
    public void doBuscarConPropietario() {
        System.out.println("Buscar con propietario");
        //this.facturas = dao.buscarConPropietario(autenticacionController.getUsuarioLogueado());
        this.facturas = refrescarLista();
    }
    
    public void doBuscarConPropietarioPorCliente() {
        System.out.println("Buscar por Cliente");
        this.facturas = facturaDAO.buscarPorClienteConPropietario(autenticacionController.getUsuarioLogueado(), clienteBusqueda);
    }
    
    public void doBuscarTodos() {
        System.out.println("Buscar TODOS");
        this.facturas = refrescarLista();

        System.out.println("fin busca todos");
    }
    
    //La forma de pago debe estar inicializada con la forma de pago por defecto del usuario actual
    public void doNuevo() {
        this.esNuevo = true;
        this.facturaActual = new Factura();
        this.facturaActual.setPropietario(autenticacionController.getUsuarioLogueado());
        this.datosFacturacion= cargarDatosFacturacion();
        //Forma de pago por defecto del usuario extraido de sus datos de facturacion
        this.facturaActual.setFormaPago(datosFacturacion.getFormaPagoPorDefecto());

        this.facturaActual.setLineasDeFactura(new ArrayList<LineaDeFactura>()); //puse ArrayList por poner una implementacion de List con la que inicializar
        
    }
    
    public void doEditar(Factura factura) {
        this.esNuevo = false;
        this.facturaActual = factura;
    }


    public void doGuardarEditado() {
        if (this.esNuevo) {
            facturaActual.setCliente(clienteElegido);
            facturaActual.setFormaPago(formaPago);
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
    
    public List<FormaPago> listadoFormasPago() {
        return formaPagoDAO.buscarActivas();
    }
    
    public List<TipoIVA> listadoTipoIVA(){
        return this.tipoIVADAO.buscarActivos();
    }
    
    private DatosFacturacion cargarDatosFacturacion(){
        return datosFacturacionDAO.buscarConPropietario(autenticacionController.getUsuarioLogueado());
    }

    //Crear una linea de factura
    public void doNuevaLinea() {
        this.esNuevaLinea = true;
        this.lineaActual = new LineaDeFactura();
        this.lineaActual.setFactura(facturaActual);
        this.datosFacturacion = cargarDatosFacturacion();
        this.tipoIVA = datosFacturacion.getTipoIVAPorDefecto();
        this.lineaActual.setTipoIva(tipoIVA);
        this.lineaActual.setCantidad(1);
        this.lineaActual.setPrecioUnitario(1);
        this.lineaActual.setPorcentajeDescuento(0);
        this.lineaActual.setCliente(facturaActual.getCliente()); //------------------------------------REVISAR------------------------------
   
    }
    
    public void doEditarLinea(LineaDeFactura lin){
        this.esNuevaLinea = false;
        this.lineaActual = lin;
    }
    
    public void doGuardarEditadoLinea(){
        //Se calcula el total como: cantidad * precioUnitario * (1+IVA) * (1-Descuento)
        lineaActual.setTotal(
                (lineaActual.getCantidad()*lineaActual.getPrecioUnitario()) 
                        * ( 1+(tipoIVA.getPorcentaje()/100) )
                        * ( 1-(lineaActual.getPorcentajeDescuento()/100) )
            );  
        if(this.esNuevaLinea){
            lineaActual.setTipoIva(tipoIVA);
            lineaDeFacturaDAO.crear(lineaActual);
        }else{
            lineaDeFacturaDAO.actualizar(lineaActual);
        }
        this.lineasFacturaActual = refrescarListadoLineas();
        this.lineaActual=null;
        this.esNuevaLinea=false;
    }
    
    public void doCancelarEditadoLinea(){
        this.lineaActual=null;
        this.esNuevaLinea=false;
    }
    
    //---------------------------------------------------------REVISAR--------------------------
    public void doBuscarLineasDeFactura(){
        this.lineasFacturaActual = refrescarListadoLineas();
    }
    
    private List<LineaDeFactura> refrescarListadoLineas(){
        return lineaDeFacturaDAO.buscarConFactura(this.facturaActual);
    }
    
    public void doBorradoLinea(LineaDeFactura linea){
        lineaDeFacturaDAO.borrarLineaDeFactura(linea);
        this.lineasFacturaActual = refrescarListadoLineas();
    }
}
