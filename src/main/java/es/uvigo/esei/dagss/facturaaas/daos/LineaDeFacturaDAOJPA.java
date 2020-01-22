/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.LineaDeFactura;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author danid
 */
@Stateless
public class LineaDeFacturaDAOJPA extends GenericoDAOJPA<LineaDeFactura, Long> implements LineaDeFacturaDAO {

    @Override
    public List<LineaDeFactura> buscarConFactura(Factura factura) {
        System.out.println("buscar lineas de factura");
        if(factura == null){
            System.out.println("factura nula");
        }
        System.out.println("Factura id: "+factura.getId());
        System.out.println("wea");
            TypedQuery<LineaDeFactura> query = em.createQuery("SELECT lin FROM LineaDeFactura AS lin WHERE lin.factura.id = :idFactura", LineaDeFactura.class);
        query.setParameter("idFactura", factura.getId());
        List<LineaDeFactura> resultado = query.getResultList();
        if ((resultado != null) && !resultado.isEmpty()) {
            return resultado;
        } else {
            return null;
        }
    }

    @Override
    public void borrarLineaDeFactura(LineaDeFactura linea) {
        Long aux = linea.getId();
        System.out.println("Borrar lineas de factura");
        TypedQuery<LineaDeFactura> query = em.createQuery("DELETE lin FROM LineaDeFactura as lin WHERE lin.id = :idLin", LineaDeFactura.class);
        query.setParameter("idLin", linea.getId());
        int numResult = query.executeUpdate();
        if(numResult==1){
            System.out.println("LineaDeFactura con id: "+ aux+" Borrado con exito");
        }else{
            System.out.println("No se ha borrao");
        }
    }
    
}
