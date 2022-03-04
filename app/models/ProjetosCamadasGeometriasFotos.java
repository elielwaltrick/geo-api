package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(schema = "vm2_view", name = "projetos_camadas_geometrias_fotos")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class ProjetosCamadasGeometriasFotos extends GenericObject {
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@Column(name = "id_projetos_camadas_geometrias_fotos", columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_projetos_camadas_geometrias")
	private ProjetosCamadasGeometrias projetoCamadaGeometria;

	@Column(name = "foto")
	private String foto;

	private Integer tipo;

	public String getFoto() {
		return foto;
	}

	public Integer getId() {
		return id;
	}

	public ProjetosCamadasGeometrias getProjetoCamadaGeometria() {
		return projetoCamadaGeometria;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setProjetoCamadaGeometria(ProjetosCamadasGeometrias projetoCamadaGeometria) {
		this.projetoCamadaGeometria = projetoCamadaGeometria;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

}
