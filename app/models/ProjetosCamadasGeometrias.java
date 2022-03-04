package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.geolatte.geom.GeometryType;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.vividsolutions.jts.geom.Geometry;

@Entity
@Table(schema = "vm2_view", name = "projetos_camadas_geometrias")
@TypeDef(name = "geometry", typeClass = GeometryType.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class ProjetosCamadasGeometrias extends GenericObject {
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@Column(name = "id_projetos_camadas_geometrias", columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_projetos_camadas")
	private ProjetosCamadas projetoCamada;

	@Column(columnDefinition = "geometry")
	private Geometry geom;

	public Geometry getGeom() {
		return geom;
	}

	public Integer getId() {
		return id;
	}

	public ProjetosCamadas getProjetoCamada() {
		return projetoCamada;
	}

	public void setGeom(Geometry geom) {
		this.geom = geom;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setProjetoCamada(ProjetosCamadas projetoCamada) {
		this.projetoCamada = projetoCamada;
	}

}
