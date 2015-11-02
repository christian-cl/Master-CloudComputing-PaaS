package api;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
	public class Restaurante implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String nombre, email, direccion, telefono, descripcion, latitud, longitud, newemail="", etiqueta;
		private List<String> links;
		public Restaurante(){}
		public Restaurante(String nombre, String email, String direccion, String telefono,
				String descripcion, String latitud, String longitud, String etiqueta, List<String> links) {
			this.nombre = nombre;
			this.email = email;
			this.direccion = direccion;
			this.telefono = telefono;
			this.descripcion = descripcion;
			this.latitud = latitud;
			this.longitud = longitud;
			this.etiqueta = etiqueta;
			this.links = links;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getDireccion() {
			return direccion;
		}
		public void setDireccion(String direccion) {
			this.direccion = direccion;
		}
		public String getTelefono() {
			return telefono;
		}
		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		@Override
		public String toString() {
			return "Restaurante [nombre=" + nombre + ", email=" + email + ", direccion=" + direccion + ", telefono="
					+ telefono + ", descripcion=" + descripcion + "]";
		}
		public String getLatitud() {
			return latitud;
		}
		public void setLatitud(String latitud) {
			this.latitud = latitud;
		}
		public String getLongitud() {
			return longitud;
		}
		public void setLongitud(String longitud) {
			this.longitud = longitud;
		}
		public String getNewemail() {
			return newemail;
		}
		public void setNewemail(String newemail) {
			this.newemail = newemail;
		}
		public String getEtiqueta() {
			return etiqueta;
		}
		public void setEtiqueta(String etiqueta) {
			this.etiqueta = etiqueta;
		}
		public List<String> getLinks() {
			return links;
		}
		public void setLinks(List<String> links) {
			this.links = links;
		}
	}