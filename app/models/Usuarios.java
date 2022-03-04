package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import play.data.validation.Constraints;

@Entity
@Table(schema = "vm2_view", name = "usuarios")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class Usuarios extends GenericObject {
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@Column(name = "id_usuarios", columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "auth_token")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String authToken;

	@Column(name = "email", length = 255, unique = true, nullable = false)
	@Constraints.MaxLength(255)
	@Constraints.Required
	@Constraints.Email
	private String email;

	@Column(nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String senha;

	@Column(name = "nome")
	private String nome;

	@Column(name = "id_tenants")
	private Integer idTenants;

	public String getAuthToken() {
		return authToken;
	}

	public String getEmail() {
		return email;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIdTenants() {
		return idTenants;
	}

	public String getNome() {
		return nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIdTenants(Integer idTenants) {
		this.idTenants = idTenants;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
