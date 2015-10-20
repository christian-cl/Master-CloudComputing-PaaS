package api;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
	public class Restaurante implements Serializable {
		/**
		 * 
		 */
	private int prueba;
		private static final long serialVersionUID = 1L;
		private String nombre, email, direccion, telefono, descripcion;
		public Restaurante(){}
		public Restaurante(String nombre, String email, String direccion, String telefono, String descripcion) {
			this.nombre = nombre;
			this.email = email;
			this.direccion = direccion;
			this.telefono = telefono;
			this.descripcion = descripcion;
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
	}