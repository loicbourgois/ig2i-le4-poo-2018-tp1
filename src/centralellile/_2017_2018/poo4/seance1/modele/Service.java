/*
 * The MIT License
 *
 * Copyright 2018 Team SI.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package centralellile._2017_2018.poo4.seance1.modele;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Entité représentant un service
 * @author user
 */
@Entity
public class Service implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="SERVNO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="SERVNAME",length = 50,unique = true,nullable = false)
	private String nom;

	@Column(name="SERVLOCALISATION",length = 50,nullable = false)
	private String localisation;

	@OneToMany(mappedBy="service",cascade=CascadeType.PERSIST)
	private Set<Medecin> ensMedecins;

	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="manager")
	private Medecin manager;

	/**
	 * Constructeur par défault
	 */
	public Service() {
		this.ensMedecins = new HashSet<>();
	}

	/**
	 * Constructeur par données
	 * @param nom
	 * @param localisation
	 */
	public Service(String nom, String localisation) {
		this();
		this.nom = nom.toUpperCase();
		this.localisation = localisation;
	}

	public Long getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public String getLocalisation() {
		return localisation;
	}

	public Medecin getManager() {
		return manager;
	}

	public Collection<Medecin> getEnsMedecins() {
		return new HashSet<>(ensMedecins);
	}

	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}

	public void setManager(Medecin manager) {
		this.manager = manager;
	}

	/**
	 * Permet d'ajouter un médecin à un service
	 * @param m
	 * @return
	 */
	public boolean addMedecin(Medecin m){
		Service s_old = m.getService();
		if(ensMedecins.add(m)){
			if(s_old != null){
				s_old.ensMedecins.remove(m);
			}
			m.setService(this);
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (nom != null ? nom.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Service)) {
			return false;
		}
		Service other = (Service) object;
		if ((this.nom == null && other.nom.toUpperCase()!= null) ||
			(this.nom != null && !this.nom.equals(other.nom.toUpperCase()))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String result = "Service " + id + " [\n\tNom : " + nom + "\n\tLocalisation : " + localisation;
		result += "\n\t contient : \n\t";
		for(Medecin m : ensMedecins){
			result += m.toString();
		}
		return result + "\n]";
	}

}
