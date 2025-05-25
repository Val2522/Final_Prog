package com.wiki;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public class Monstruo {
 private SimpleIntegerProperty idMonstruo;
 private SimpleStringProperty nombre;
 private SimpleStringProperty imagen;
 private SimpleIntegerProperty id_tipo;
 private SimpleStringProperty tamaño;
 private SimpleStringProperty lore;
 private SimpleStringProperty nombre_tipo;
 private SimpleStringProperty habitat;

 // Constructor
 public Monstruo(int idMonstruo, String nombre, String imagen, int id_tipo, String tamaño, String lore, String habitat, String nombre_tipo) {
 this.idMonstruo = new SimpleIntegerProperty(idMonstruo);
 this.nombre = new SimpleStringProperty(nombre);
 this.imagen = new SimpleStringProperty(imagen);
 this.id_tipo = new SimpleIntegerProperty(id_tipo);
 this.tamaño = new SimpleStringProperty(tamaño);
 this.lore = new SimpleStringProperty(lore);
 this.habitat = new SimpleStringProperty(habitat);
 this.nombre_tipo = new SimpleStringProperty(nombre_tipo);
 }

 // Getters and Setters
 public int getIdMonstruo() {
 return idMonstruo.get();
 }

 public void setIdMonstruo(int idMonstruo) {
 this.idMonstruo.set(idMonstruo);
 }

 public String getNombre() {
 return nombre.get();
 }

 public void setNombre(String nombre) {
 this.nombre.set(nombre);
 }

 public String getImagen() {
 return imagen.get();
 }

 public void setImagen(String imagen) {
 this.imagen.set(imagen);
 }

 public int getIdTipo() {
 return id_tipo.get();
 }

 public void setIdTipo(int id_tipo) {
 this.id_tipo.set(id_tipo);
 }

 public String getTamaño() {
 return tamaño.get();
 }

 public void setTamaño(String tamaño) {
 this.tamaño.set(tamaño);
 }

 public String getLore() {
 return lore.get();
 }

 public void setLore(String lore) {
 this.lore.set(lore);
 }

 public String getNombreTipo() {
 return nombre_tipo.get();
 }

 public void setNombreTipo(String nombre_tipo) {
 this.nombre_tipo.set(nombre_tipo);
 }

 public String getHabitat() {
 return habitat.get();
 }

 public void setHabitat(String habitat) {
 this.habitat.set(habitat);
 }

 // Obtener todos los monstruos
 public static void getAll(ObservableList<Monstruo> monstruoList) {
 monstruoList.clear();
 String query = "SELECT Monstruos.id_monstruo AS idMonstruo, Monstruos.nombre, Monstruos.imagen, Monstruos.tamaño, Monstruos.lore, " +
 "Habitats.nombre AS habitat, Tipos.nombre AS nombreTipo, Monstruos.id_tipo AS id_tipo " +
 "FROM Monstruos " +
 "INNER JOIN Tipos ON Monstruos.id_tipo = Tipos.id_tipo " +
 "INNER JOIN Monstruos_Habitats ON Monstruos.id_monstruo = Monstruos_Habitats.id_monstruo " +
 "INNER JOIN Habitats ON Monstruos_Habitats.id_habitat = Habitats.id_habitat;";

 try (Connection connection = connectionBD.getInstance().getConnection();
 PreparedStatement stmt = connection.prepareStatement(query);
 ResultSet rs = stmt.executeQuery()) {

 while (rs.next()) {
 Monstruo e = new Monstruo(
 rs.getInt("idMonstruo"),
 rs.getString("nombre"),
 rs.getString("imagen"),
 rs.getInt("id_tipo"),
 rs.getString("tamaño"),
 rs.getString("lore"),
 rs.getString("habitat"),
 rs.getString("nombreTipo")
 );
 monstruoList.add(e);
 }

 } catch (SQLException e) {
 e.printStackTrace();
 }
 }

 // Guardar o actualizar monstruo
 public int save() {
 int filasAfectadas = 0;
 String query;
 try (Connection connection = connectionBD.getInstance().getConnection()) {
 if (this.getIdMonstruo() > 0) {
 // Update
 query = "UPDATE Monstruos SET nombre = ?, imagen = ?, id_tipo = ?, tamaño = ?, lore = ? WHERE id_monstruo = ?";
 try (PreparedStatement stmt = connection.prepareStatement(query)) {
 stmt.setString(1, this.getNombre());
 stmt.setString(2, this.getImagen());
 stmt.setInt(3, this.getIdTipo());
 stmt.setString(4, this.getTamaño());
 stmt.setString(5, this.getLore());
 stmt.setInt(6, this.getIdMonstruo());
 filasAfectadas = stmt.executeUpdate();
 }
 } else {
 // Insert
 query = "INSERT INTO Monstruos (nombre, imagen, id_tipo, tamaño, lore) VALUES (?, ?, ?, ?, ?)";
 try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
 stmt.setString(1, this.getNombre());
 stmt.setString(2, this.getImagen());
 stmt.setInt(3, this.getIdTipo());
 stmt.setString(4, this.getTamaño());
 stmt.setString(5, this.getLore());
 filasAfectadas = stmt.executeUpdate();

 // Recuperar el ID generado automáticamente
 try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
 if (generatedKeys.next()) {
 this.setIdMonstruo(generatedKeys.getInt(1));
 }
 }
 }
 }
 } catch (SQLException e) {
 e.printStackTrace();
 }
 return filasAfectadas;
 }

 public int deleteDebilidades() {
 int rowsAffected = 0;
 String query = "DELETE FROM Monstruos_Debilidades WHERE id_monstruo = ?";
 try (Connection connection = connectionBD.getInstance().getConnection();
 PreparedStatement stmt = connection.prepareStatement(query)) {
 stmt.setInt(1, this.getIdMonstruo());
 rowsAffected = stmt.executeUpdate();
 System.out.println("Deleted " + rowsAffected + " debilidades for Monstruo ID: " + this.getIdMonstruo());
 } catch (SQLException e) {
 e.printStackTrace();
 }
 return rowsAffected;
 }

 public int deleteHabitats() {
 int rowsAffected = 0;
 String query = "DELETE FROM Monstruos_Habitats WHERE id_monstruo = ?";
 try (Connection connection = connectionBD.getInstance().getConnection();
 PreparedStatement stmt = connection.prepareStatement(query)) {
 stmt.setInt(1, this.getIdMonstruo());
 rowsAffected = stmt.executeUpdate();
 System.out.println("Deleted " + rowsAffected + " habitats for Monstruo ID: " + this.getIdMonstruo());
 } catch (SQLException e) {
 e.printStackTrace();
 }
 return rowsAffected;
 }

 // Eliminar monstruo
 public int delete() {
 int filasAfectadas = 0; // Declare filasAfectadas
 String query = "DELETE FROM Monstruos WHERE id_monstruo = ?";
 try (Connection connection = connectionBD.getInstance().getConnection();
 PreparedStatement stmt = connection.prepareStatement(query)) {
 stmt.setInt(1, this.getIdMonstruo());
 System.out.println("Deleting Monstruo with ID: " + this.getIdMonstruo()); // ADD THIS
 System.out.println("Delete Query: " + query); // ADD THIS
 filasAfectadas = stmt.executeUpdate();
 System.out.println("Rows Affected: " + filasAfectadas); // ADD THIS
 } catch (SQLException e) {
 e.printStackTrace();
 }
 return filasAfectadas;
 }
}