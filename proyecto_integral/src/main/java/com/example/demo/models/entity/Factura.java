package com.example.demo.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "facturas")
public class Factura implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descripcion;
	private String observacion;
	@Temporal(TemporalType.DATE)
	private Date fecha_creacion;
	
	//Sirve para la relación entre Cliente - Factura
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;
	
	//Sirve para la relación entre Factura - DetalleFactura
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "factura_id", nullable = false)
	private List<DetalleFactura> detalles;
	
	
	@PrePersist
	public void prePersist() {
		fecha_creacion = new Date();
	}
	
	
	public Factura() {
		this.detalles = new ArrayList<DetalleFactura>();
	}
	

	public List<DetalleFactura> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleFactura> detalles) {
		this.detalles = detalles;
	}

	public void addDetalleFactura(DetalleFactura detalle) {
		this.detalles.add(detalle);
	}


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public String getObservacion() {
		return observacion;
	}



	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}



	public Date getFecha_creacion() {
		return fecha_creacion;
	}



	public void setFecha_creacion(Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}



	public Cliente getCliente() {
		return cliente;
	}



	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public double getTotal() {
		double total = 0.0;
		for(int i=0; i<detalles.size();i++) {
			total += detalles.get(i).calcularImporte();
		}
		return total;
	}

	private static final long serialVersionUID = 1L;

}
