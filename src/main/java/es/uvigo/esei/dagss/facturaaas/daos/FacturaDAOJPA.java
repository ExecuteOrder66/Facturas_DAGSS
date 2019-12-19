/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.DatosFacturacion;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author danid
 */
@Stateless
public class FacturaDAOJPA extends GenericoDAOJPA<Factura, Long> implements FacturaDAO {

    @Override
    public List<Factura> buscarConPropietario(Usuario propietario) {
        TypedQuery<Factura> query = em.createQuery("SELECT fac FROM Factura AS fac WHERE fac.cliente.id = :idPropietario", Factura.class);
        query.setParameter("idPropietario", propietario.getId());
        List<Factura> resultado = query.getResultList();
        if ((resultado != null) && !resultado.isEmpty()) {
            return resultado;
        } else {
            return null;
        }
    }   

    @Override
    public List<Factura> buscarPorClienteConPropietario(Usuario propietario, Cliente cliente) {
        TypedQuery<Factura> query = em.createQuery("SELECT f FROM Factura AS f "+
                                                   "WHERE f.propietario.id = :idPropietario "+
                                                   "AND f.cliente.id =: idCliente ", Factura.class);
        query.setParameter("idPropietario", propietario.getId());
        query.setParameter("idCliente", cliente.getId());        
        return query.getResultList();    
    }
    
    
    public Factura buscarPorIdCompleta (Long idFactura){
        Factura f = this.buscarPorClave(idFactura);
        f.getLineasDeFactura();
        
        return f;
    }
}