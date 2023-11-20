db.funkos.insert({
  _id: UUID(), // Genera un UUID para el campo _id
  name: "Nombre del Funko",
  model: "Modelo del Funko",
  description: "Descripción del Funko",
  price: 19.99,
  stock: 50,
  image: "https://via.placeholder.com/150",
  createdAt: ISODate(),
  updatedAt: ISODate()
});