db.funkos.insert({
  _id: UUID(),
  name: "Nombre del Funko",
  model: "Modelo del Funko",
  description: "Descripción del Funko",
  price: 19.99,
  stock: 50,
  image: "https://via.placeholder.com/150",
  createdAt: new ISODate(),
  updatedAt: new ISODate()
});
