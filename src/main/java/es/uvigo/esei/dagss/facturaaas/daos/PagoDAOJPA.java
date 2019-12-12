/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.LineaDeFactura;
import es.uvigo.esei.dagss.facturaaas.entidades.Pago;
import es.uvigo.esei.dagss.facturaaas.entidades.Usuario;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author danid
 */
public class PagoDAOJPA extends GenericoDAOJPA<Pago, Long> implements PagoDAO{

    @Override
    public Pago buscarConPropietario(Usuario propietario) {
        TypedQuery<Pago> query = em.createQuery("SELECT pag FROM Pago AS pag WHERE pag.cliente.id = :idPropietario", Pago.class);
        query.setParameter("idPropietario", propietario.getId());
        List<Pago> resultado = query.getResultList();
        if ((resultado != null) && !resultado.isEmpty()) {
            return resultado.get(0);
        } else {
            return null;
        }
    }
    
    
    
}
